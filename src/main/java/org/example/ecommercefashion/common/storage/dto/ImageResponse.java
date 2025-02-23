package org.example.ecommercefashion.common.storage.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.example.ecommercefashion.common.storage.enums.StatusImage;
import org.example.ecommercefashion.common.storage.enums.TypeImage;

import java.sql.Timestamp;


@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ImageResponse(Long idImage,
                            String fileName,
                            TypeImage contentType,
                            Long size,
                            String imageUrl,
                            Timestamp createdAt,
                            StatusImage status
) {
}
