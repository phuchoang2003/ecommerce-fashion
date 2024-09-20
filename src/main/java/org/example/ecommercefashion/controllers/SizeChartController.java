package org.example.ecommercefashion.controllers;

import lombok.RequiredArgsConstructor;
import org.example.ecommercefashion.dtos.request.SizeChartRequest;
import org.example.ecommercefashion.dtos.response.ResponsePage;
import org.example.ecommercefashion.dtos.response.SizeChartResponse;
import org.example.ecommercefashion.entities.postgres.SizeChart;
import org.example.ecommercefashion.services.SizeChartService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/size_charts")
@RequiredArgsConstructor
public class SizeChartController {
    private final SizeChartService sizeChartService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SizeChartResponse> create(@Valid @RequestPart(value = "request", required = true) SizeChartRequest request,
                                                    @RequestPart(value = "file", required = true) List<MultipartFile> files) {
        SizeChartResponse response = sizeChartService.create(request, files);
        return ResponseEntity.ok(response);
    }


    @GetMapping("{id}")
    public ResponseEntity<SizeChartResponse> findById(@PathVariable Long id) {
        SizeChartResponse response = sizeChartService.findById(id);
        return ResponseEntity.ok(response);
    }


    @GetMapping
    public ResponseEntity<ResponsePage<SizeChart, SizeChartResponse>> findAll(Pageable pageable) {

        return ResponseEntity.ok(sizeChartService.findAll(pageable));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        sizeChartService.delete(id);
        return ResponseEntity.ok().build();
    }


}
