package org.example.ecommercefashion.services.impl;

import com.longnh.exceptions.ExceptionHandle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.example.ecommercefashion.dtos.projection.ProductBriefDTO;
import org.example.ecommercefashion.dtos.request.ProductRequest;
import org.example.ecommercefashion.dtos.response.*;
import org.example.ecommercefashion.entities.postgres.*;
import org.example.ecommercefashion.enums.ProductState;
import org.example.ecommercefashion.exceptions.ErrorMessage;
import org.example.ecommercefashion.repositories.postgres.AttributeRepository;
import org.example.ecommercefashion.repositories.postgres.ProductRepository;
import org.example.ecommercefashion.services.CategoryService;
import org.example.ecommercefashion.services.ImageService;
import org.example.ecommercefashion.services.ProductService;
import org.example.ecommercefashion.services.SizeChartService;
import org.example.ecommercefashion.utils.SlugUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final CategoryService categoryService;

    private final ProductRepository productRepository;

    private final AttributeRepository attributeRepository;

    private final ImageService imageService;

    private final SizeChartService sizeChartService;


    @Override
    public ResponsePage<Product, ProductBriefResponse> findAll(Pageable pageable) {
        Page<ProductBriefDTO> productBriefDTOS = productRepository.filter(pageable);
        Page<ProductBriefResponse> responses = productBriefDTOS.map(this::convertToProductBriefResponse);
        return new ResponsePage<>(responses);
    }


    private ProductBriefResponse convertToProductBriefResponse(ProductBriefDTO productBriefDTO) {
        String url = imageService.getUrlInStorageByUrlInDb(productBriefDTO.getUrls()
                .stream()
                .findFirst()
                .orElseThrow(() -> new ExceptionHandle(HttpStatus.NOT_FOUND, ErrorMessage.IMAGE_NOT_FOUND.val())));

        return ProductBriefResponse.fromDto(productBriefDTO, url);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        Product product = getById(id);
        product.setDeleted(true);
        Set<ProductVariant> productVariants = product.getProductVariants();
        for (ProductVariant productVariant : productVariants) {
            productVariant.setDeleted(true);
            productVariant.setState(ProductState.DISCONTINUED);
        }
        Set<ProductImage> productImages = product.getProductImages();
        product.removeProductImages(productImages);
        for (ProductImage productImage : productImages) {
            productImage.getImage().setDeleted(true);
        }
        product.setState(ProductState.DISCONTINUED);
        productRepository.save(product);

    }

    @Override
    public ProductDetailResponse findProductResponseById(Long id) {
        Product product = getById(id);
        Set<SizeChartResponse> sizeChartResponses = sizeChartService.findByIdIn(product.getSupportSizeChartIds());
        List<Image> images = product.getProductImages()
                .stream()
                .map(ProductImage::getImage)
                .toList();
        List<ImageResponse> imageResponses = imageService.getUrlsInStorage(images);
        return ProductDetailResponse.fromEntity(product, imageResponses, sizeChartResponses);
    }

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ExceptionHandle(HttpStatus.NOT_FOUND, ErrorMessage.PRODUCT_NOT_FOUND.val()));
    }


    private void filterSizeChartResponses(Set<SizeChartResponse> responses, ProductRequest request) {
        responses.removeIf(response ->
                !request.getSupportSizeChartIds().contains(response.id())
        );
    }


    private Set<Attribute> processAttributes(ProductRequest request) {
        Set<Attribute> attributes = attributeRepository.findByAttributeValuesIdIn(request.getAttributeValueIds());
        validAttributeMandatory(request, attributes);
        return attributes;
    }


    private List<ProductVariant> processProductVariants(ProductRequest request, Product product) {
        List<ProductVariant> productVariants = new ArrayList<>();
        Set<Set<Variant>> variantAfterSplitValue = splitVariant(request);

        for (Set<Variant> variants : variantAfterSplitValue) {
            productVariants.add(ProductVariant.fromRequest(variants, product, request));
        }

        return productVariants;
    }

    private void updateBasicProduct(ProductRequest request, Product product, Category category) {
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setCategoryId(request.getCategoryId());
        product.setCategory(category);
        product.setSlug(SlugUtils.generateSlug(request.getName()));
        product.setSupportSizeChartIds(new ArrayList<>(request.getSupportSizeChartIds()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductDetailResponse update(ProductRequest request, List<MultipartFile> files, Long id) {
        Product product = getById(id);
        Category category = categoryService.getById(request.getCategoryId());
        Set<SizeChartResponse> sizeChartResponses = processSizeCharts(request, category);
        Set<Attribute> attributes = processAttributes(request);

        updateBasicProduct(request, product, category);

        // xu ly attribute value
        // xóa cứng
        Set<RelProductAttributeValue> removeRelProductAttributeValues = handleDeleteRelProductAttributeValue(product, request);
        product.removeAttributeValues(removeRelProductAttributeValues);
        // thêm mới
        Set<RelProductAttributeValue> relProductAttributeValues = createOrUpdateRelProductAttributeValue(product, request, attributes);
        product.addAttributeValues(relProductAttributeValues);


        // xu ly product variant
        // find theo productId rồi cập nhật
        Pair<Set<ProductVariant>, Set<ProductVariant>> pair = handleProductVariant(product, request);
        Set<ProductVariant> addProductVariants = pair.getRight();
        Set<ProductVariant> removeProductVariants = pair.getLeft();
        removeProductVariants.forEach(productVariant -> productVariant.setDeleted(true));
        product.removeProductVariants(removeProductVariants);
        product.addProductVariants(addProductVariants);

        // tính lại số lượng
        product.setQuantity(calculateQuantity(request, product.getProductVariants()));

        // xoa image cu
        product.getProductImages().stream().map(ProductImage::getImage).forEach(image -> image.setDeleted(true));
        Set<ProductImage> removeProductImages = product.getProductImages();
        product.removeProductImages(removeProductImages);

        // them image moi
        List<ImageResponse> imageResponses = imageService.uploadImagesProduct(files, product);
        return ProductDetailResponse.fromEntity(product, imageResponses, sizeChartResponses);

    }

    public Pair<Set<ProductVariant>, Set<ProductVariant>> handleProductVariant(Product product, ProductRequest request) {
        Set<ProductVariant> productVariantsExist = product.getProductVariants();

        Set<ProductVariant> productVariantsRequest = new HashSet<>(processProductVariants(request, product));

        Set<ProductVariant> existingVariantsSet = new HashSet<>(productVariantsExist);
        Set<ProductVariant> requestedVariantsSet = new HashSet<>(productVariantsRequest);

        Set<ProductVariant> variantsToAdd = requestedVariantsSet.stream()
                .filter(variant -> !existingVariantsSet.contains(variant))
                .collect(Collectors.toSet());

        Set<ProductVariant> variantsToRemove = existingVariantsSet.stream()
                .filter(variant -> !requestedVariantsSet.contains(variant))
                .collect(Collectors.toSet());

        return Pair.of(variantsToRemove, variantsToAdd);
    }


    public Set<RelProductAttributeValue> handleDeleteRelProductAttributeValue(Product product, ProductRequest request) {
        Pair<Set<Long>, Set<RelProductAttributeValue>> pair = getExistIdsAndValues(product);
        Set<Long> existIds = pair.getLeft();
        Set<RelProductAttributeValue> existRelProductAttributeValues = pair.getRight();
        Set<Long> ids = request.getAttributeValueIds();
        Set<Long> removeIds = existIds.stream()
                .filter(id -> !ids.contains(id))
                .collect(Collectors.toSet());

        return existRelProductAttributeValues.stream()
                .filter(relProductAttributeValue -> removeIds.contains(relProductAttributeValue.getAttributeValue().getId()))
                .collect(Collectors.toSet());
    }

    private Pair<Set<Long>, Set<RelProductAttributeValue>> getExistIdsAndValues(Product product) {
        Set<RelProductAttributeValue> existRelProductAttributeValues = product.getRelProductAttributeValues();
        Set<Long> existIds = existRelProductAttributeValues.stream()
                .map(relProductAttributeValue -> relProductAttributeValue.getAttributeValue().getId())
                .collect(Collectors.toSet());

        return Pair.of(existIds, existRelProductAttributeValues);
    }


    public Set<RelProductAttributeValue> createOrUpdateRelProductAttributeValue(Product product, ProductRequest request, Set<Attribute> attributes) {
        Pair<Set<Long>, Set<RelProductAttributeValue>> pair = getExistIdsAndValues(product);
        Set<Long> existIds = pair.getLeft();
        Set<Long> ids = request.getAttributeValueIds();
        Set<Long> addIds = ids.stream()
                .filter(id -> !existIds.contains(id))
                .collect(Collectors.toSet());
        return processAttributeValues(addIds, attributes, product);

    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductDetailResponse create(ProductRequest request, List<MultipartFile> files) {
        Set<Attribute> attributes = processAttributes(request);
        // xu ly sizeChart
        Category category = categoryService.getById(request.getCategoryId());
        Set<SizeChartResponse> sizeChartResponses = processSizeCharts(request, category);

        Product product = productRepository.save(Product.fromRequest(request, category));


        // xu ly attribute value
        Set<RelProductAttributeValue> relProductAttributeValues = processAttributeValues(request.getAttributeValueIds(), attributes, product);
        product.addAttributeValues(relProductAttributeValues);

        // xu ly product variant
        Set<ProductVariant> productVariants = new HashSet<>(processProductVariants(request, product));
        product.addProductVariants(productVariants);

        // tinh tong product
        product.setQuantity(calculateQuantity(request, productVariants));
        // luu anh
        List<ImageResponse> imageResponses = imageService.uploadImagesProduct(files, product);
        return ProductDetailResponse.fromEntity(product, imageResponses, sizeChartResponses);
    }


    private int calculateQuantity(ProductRequest request, Set<ProductVariant> productVariants) {
        int total = productVariants.stream().mapToInt(ProductVariant::getQuantity).sum();
        return total != 0 ? total : request.getQuantity();
    }

    private Set<RelProductAttributeValue> processAttributeValues(Set<Long> attributeValueIds, Set<Attribute> attributes, Product product) {
        Set<AttributeValue> attributeValues = attributes.stream()
                .map(Attribute::getAttributeValues)
                .flatMap(Collection::stream)
                .filter(attributeValue -> attributeValueIds.contains(attributeValue.getId()))
                .collect(Collectors.toSet());

        return attributeValues.stream()
                .map(attributeValue -> new RelProductAttributeValue(product, attributeValue))
                .collect(Collectors.toSet());
    }


    private Set<SizeChartResponse> processSizeCharts(ProductRequest request, Category category) {
        Set<SizeChartResponse> sizeChartResponses = sizeChartService.findByIdIn(category.getSupportSizeChartIds());
        validSizeChartIds(request, sizeChartResponses);
        filterSizeChartResponses(sizeChartResponses, request);
        return sizeChartResponses;
    }

    private void validSizeChartIds(ProductRequest request, Set<SizeChartResponse> sizeChartResponses) {
        Set<Long> sizeChartIds = sizeChartResponses
                .stream()
                .map(SizeChartResponse::id)
                .collect(Collectors.toSet());
        Set<Long> requestSizeChartIds = request.getSupportSizeChartIds();
        for (Long id : sizeChartIds) {
            if (!requestSizeChartIds.contains(id)) {
                throw new ExceptionHandle(HttpStatus.BAD_REQUEST,
                        ErrorMessage.CATEGORY_ONLY_SUPPORT_SIZE_CHART.val() + " id: " +
                                sizeChartIds.stream()
                                        .map(String::valueOf)
                                        .collect(Collectors.joining(", ")));
            }
        }
    }

    private Set<Set<Variant>> splitVariant(ProductRequest request) {
        List<Variant> variants = request.getVariants();
        Set<Set<Variant>> variantAfterSplitValue = new HashSet<>();

        if (variants.isEmpty()) {
            return variantAfterSplitValue;
        }

        List<String[]> splitValues = new ArrayList<>();
        for (Variant variant : variants) {
            splitValues.add(variant.getValue().split("\\|"));
        }
        combineVariants(variants, splitValues, 0, new LinkedHashSet<>(), variantAfterSplitValue);

        return variantAfterSplitValue;
    }

    private void combineVariants(List<Variant> variants, List<String[]> splitValues, int index,
                                 Set<Variant> currentSet, Set<Set<Variant>> result) {
        if (index == variants.size()) {
            result.add(new LinkedHashSet<>(currentSet));
            return;
        }
        String variantName = variants.get(index).getName();
        for (String value : splitValues.get(index)) {
            currentSet.add(new Variant(variantName, value));
            combineVariants(variants, splitValues, index + 1, currentSet, result);
            currentSet.remove(new Variant(variantName, value));
        }
    }


    private void validAttributeMandatory(ProductRequest request, Set<Attribute> attributes) {
        Set<Long> ids = attributes.stream().map(Attribute::getId).collect(Collectors.toSet());

        if (ids.size() != request.getAttributeValueIds().size()) {
            throw new ExceptionHandle(HttpStatus.BAD_REQUEST, ErrorMessage.ONLY_ALLOW_ONE_ATTRIBUTE_CORESPONDING_ATTRIBUTE_VALUE.val());
        }


        Set<Long> attributeIdsMandatory = attributeRepository.findAllAttributeByCategoryIdAndIsMandatory(request.getCategoryId())
                .stream()
                .map(Attribute::getId)
                .collect(Collectors.toSet());

        for (Long id : attributeIdsMandatory) {
            if (!ids.contains(id)) {
                throw new ExceptionHandle(HttpStatus.BAD_REQUEST, ErrorMessage.ATTRIBUTE_IS_MANDATORY.val() + " with id: " + id);
            }
        }
    }
}
