package org.example.ecommercefashion.dtos.response;


import lombok.Builder;
import org.example.ecommercefashion.dtos.projection.SizeChartDto;
import org.example.ecommercefashion.entities.postgres.SizeChart;

@Builder
public record SizeChartResponse(Long id, String name, String description, Long imageId, String storageUrl) {


    public static SizeChartResponse fromModel(SizeChart sizeChart, String storageUrl) {
        return SizeChartResponse.builder()
                .id(sizeChart.getId())
                .name(sizeChart.getName())
                .description(sizeChart.getDescription())
                .imageId(sizeChart.getSizeChartImageId())
                .storageUrl(storageUrl)
                .build();
    }

    public static SizeChartResponse fromDto(SizeChartDto dto, String storageUrl) {
        return SizeChartResponse.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .imageId(dto.getImageId())
                .storageUrl(storageUrl)
                .build();
    }
}

