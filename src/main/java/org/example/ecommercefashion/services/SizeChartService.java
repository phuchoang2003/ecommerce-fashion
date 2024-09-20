package org.example.ecommercefashion.services;

import org.example.ecommercefashion.dtos.request.SizeChartRequest;
import org.example.ecommercefashion.dtos.response.ResponsePage;
import org.example.ecommercefashion.dtos.response.SizeChartResponse;
import org.example.ecommercefashion.entities.postgres.SizeChart;
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
