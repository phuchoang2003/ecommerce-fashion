package org.example.ecommercefashion.services.impl;

import com.longnh.exceptions.ExceptionHandle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ecommercefashion.dtos.projection.SizeChartDto;
import org.example.ecommercefashion.dtos.request.SizeChartRequest;
import org.example.ecommercefashion.dtos.response.ResponsePage;
import org.example.ecommercefashion.dtos.response.SizeChartResponse;
import org.example.ecommercefashion.entities.postgres.Image;
import org.example.ecommercefashion.entities.postgres.SizeChart;
import org.example.ecommercefashion.exceptions.ErrorMessage;
import org.example.ecommercefashion.repositories.postgres.SizeChartRepository;
import org.example.ecommercefashion.services.ImageService;
import org.example.ecommercefashion.services.SizeChartService;
import org.example.ecommercefashion.services.StorageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SizeChartServiceImpl implements SizeChartService {

    private final SizeChartRepository sizeChartRepository;

    private final ImageService imageService;

    private final StorageService storageService;


    @Override
    public SizeChart getById(Long id) {
        return sizeChartRepository.findById(id).orElseThrow(() -> new ExceptionHandle(HttpStatus.BAD_REQUEST, ErrorMessage.SIZE_CHART_NOT_FOUND.val()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SizeChartResponse create(SizeChartRequest request, List<MultipartFile> files) {
        Image image = imageService.uploadImage(files);
        String url = storageService.getObjectUrl(image.getUrl());
        SizeChart sizeChart = SizeChart.fromRequest(request, image.getId());
        return SizeChartResponse.fromModel(sizeChartRepository.save(sizeChart), url);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        SizeChart sizeChart = getById(id);
        sizeChartRepository.delete(sizeChart);
        imageService.deleteImage(sizeChart.getSizeChartImageId());
    }


    @Override
    public SizeChartResponse findById(Long id) {
        SizeChartDto dto = sizeChartRepository.findSizeChartWithImageById(id).orElseThrow(() -> new ExceptionHandle(HttpStatus.BAD_REQUEST, ErrorMessage.SIZE_CHART_NOT_FOUND.val()));
        String urlStorage = storageService.getObjectUrl(dto.getUrl());
        return SizeChartResponse.fromDto(dto, urlStorage);
    }


    @Override
    public Set<SizeChartResponse> findByIdIn(List<Long> ids) {
        Set<SizeChartDto> dtos = sizeChartRepository.findByIds(ids);
        return dtos.stream()
                .map(dto -> SizeChartResponse.fromDto(dto, storageService.getObjectUrl(dto.getUrl())))
                .collect(Collectors.toSet());
    }

    @Override
    public ResponsePage<SizeChart, SizeChartResponse> findAll(Pageable pageable) {
        Page<SizeChartDto> dtos = sizeChartRepository.filter(pageable);
        Page<SizeChartResponse> responses = dtos.map(dto -> SizeChartResponse.fromDto(dto, storageService.getObjectUrl(dto.getUrl())));
        return new ResponsePage<>(responses);
    }
}
