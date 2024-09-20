package org.example.ecommercefashion.entities.postgres;

import lombok.*;
import org.example.ecommercefashion.dtos.request.SizeChartRequest;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "size_charts")
@Entity
public class SizeChart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "size_chart_image_id", unique = true)
    private Long sizeChartImageId;

    @Column(name = "description")
    private String description;


    public static SizeChart fromRequest(SizeChartRequest request, long imageId) {
        return SizeChart.builder()
                .sizeChartImageId(imageId)
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }
}
