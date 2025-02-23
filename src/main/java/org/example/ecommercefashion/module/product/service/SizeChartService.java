package org.example.ecommercefashion.module.product.service;

import org.example.ecommercefashion.module.product.dto.SizeChartRequest;
import org.example.ecommercefashion.common.core.dto.ResponsePage;
import org.example.ecommercefashion.module.product.dto.SizeChartResponse;
import org.example.ecommercefashion.module.product.entity.SizeChart;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface SizeChartService {
    SizeChartResponse create(SizeChartRequest request, List<MultipartFile> files);

    void delete(Long id);

    SizeChartResponse findById(Long id);

    ResponsePage<SizeChart, SizeChartResponse> findAll(Pageable pageable);

    SizeChart getById(Long id);

    Set<SizeChartResponse> findByIdIn(List<Long> ids);

}
