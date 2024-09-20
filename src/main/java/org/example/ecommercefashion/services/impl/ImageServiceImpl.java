package org.example.ecommercefashion.services.impl;

import com.longnh.exceptions.ExceptionHandle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ecommercefashion.dtos.request.ImageRequest;
import org.example.ecommercefashion.dtos.response.ImageResponse;
import org.example.ecommercefashion.entities.postgres.Image;
import org.example.ecommercefashion.entities.postgres.Product;
import org.example.ecommercefashion.entities.postgres.ProductImage;
import org.example.ecommercefashion.enums.StatusImage;
import org.example.ecommercefashion.enums.TypeImage;
import org.example.ecommercefashion.exceptions.ErrorMessage;
import org.example.ecommercefashion.repositories.postgres.ImageRepository;
import org.example.ecommercefashion.services.ImageService;
import org.example.ecommercefashion.services.StorageService;
import org.example.ecommercefashion.utils.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final StorageService storageService;
    private final ImageRepository imageRepository;

    @Override
    public String getUrlInStorageByUrlInDb(String url) {
        return storageService.getObjectUrl(url);
    }

    @Override
    public ImageResponse getUrlInStorage(Image image) {
        return mapEntityToResponse(image);
    }

    @Override
    public List<ImageResponse> getUrlsInStorage(List<Image> images) {
        return images.stream().map(this::mapEntityToResponse).toList();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    @Retryable(value = IOException.class, maxAttempts = 5, backoff = @Backoff(delay = 1000, multiplier = 2, maxDelay = 10000))
    public List<ImageResponse> uploadImagesProduct(List<MultipartFile> files, Product product) {
        FileUtils.isValidFiles(files);
        List<ImageResponse> uploadedImages = new ArrayList<>();

        for (MultipartFile file : files) {
            try (InputStream inputStream = file.getInputStream()) {
                Image image = imageRepository.save(mapMultipartFileToImage(file));
                image.setProductImage(new ProductImage(image.getId(), product.getId()));
                storageService.putObject(inputStream, file, image.getUrl());
                uploadedImages.add(mapEntityToResponse(image));

            } catch (Exception e) {
                throw new RuntimeException();
            }
        }
        return uploadedImages;
    }

    @Recover
    public List<Image> recover(Exception e, List<MultipartFile> files, Product product) {
        throw new RuntimeException("Failed to upload files after several attempts", e);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Image uploadImage(List<MultipartFile> files) {

        FileUtils.onlyAcceptOneFile(files);
        FileUtils.isValidFiles(files);
        MultipartFile file = files.get(0);

        try (InputStream inputStream = file.getInputStream()) {
            Image image = imageRepository.save(mapMultipartFileToImage(file));
            storageService.putObject(inputStream, file, image.getUrl());
            return image;
        } catch (IOException e) {
            throw new ExceptionHandle(HttpStatus.BAD_REQUEST, ErrorMessage.ERROR_UPLOADING_FILE.val());
        }
    }

    @Override
    public Image getImage(Long idImage) {
        return imageRepository.findById(idImage)
                .orElseThrow(() -> new ExceptionHandle(HttpStatus.NOT_FOUND, ErrorMessage.IMAGE_NOT_FOUND.val()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<ImageResponse> uploadImages(ImageRequest request) {
        FileUtils.isValidFiles(request.getFiles());
        return request.getFiles()
                .stream()
                .map(this::processFile)
                .toList();
    }


    private ImageResponse processFile(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            Image image = imageRepository.save(mapMultipartFileToImage(file));
            storageService.putObject(inputStream, file, image.getUrl());

            return mapEntityToResponse(image);

        } catch (IOException e) {
            return ImageResponse.builder()
                    .status(StatusImage.FAILED)
                    .fileName(file.getOriginalFilename())
                    .build();
        }
    }

    @Override
    public ImageResponse getImageById(Long idImage) {
        Image image = getImage(idImage);
        return mapEntityToResponse(image);
    }

    @Override
    public ImageResponse getImageBydUrl(String url) {
        Image image = imageRepository.findByUrl(url).orElseThrow(() -> new ExceptionHandle(HttpStatus.NOT_FOUND, ErrorMessage.IMAGE_NOT_FOUND.val()));
        return mapEntityToResponse(image);
    }

    @Override
    public void deleteImage(Long idImage) {
        Image image = getImage(idImage);
        image.setDeleted(true);
        image.setDeletedAt(new Timestamp(System.currentTimeMillis()));
    }


    @Override
    public void deleteImageByUrl(String url) {
        Image image = imageRepository.findByUrl(url).orElseThrow(() -> new ExceptionHandle(HttpStatus.NOT_FOUND, ErrorMessage.IMAGE_NOT_FOUND.val()));
        image.setDeleted(true);
        image.setDeletedAt(new Timestamp(System.currentTimeMillis()));
        imageRepository.save(image);
    }


    private Image mapMultipartFileToImage(MultipartFile file) {
        return Image.builder()
                .size(file.getSize())
                .title(file.getOriginalFilename())
                .type(TypeImage.fromMimeType(file.getContentType()))
                .url(generateObjectName(file))
                .build();
    }

    private String generateObjectName(MultipartFile file) {
        return LocalDateTime.now() + "_" + file.getOriginalFilename();
    }


    private ImageResponse mapEntityToResponse(Image image) {
        String urlStorage = storageService.getObjectUrl(image.getUrl());

        return ImageResponse.builder()
                .contentType(image.getType())
                .size(image.getSize())
                .fileName(image.getTitle())
                .createdAt(image.getCreatedAt())
                .status(StatusImage.SUCCESS)
                .imageUrl(urlStorage)
                .idImage(image.getId())
                .build();
    }
}
