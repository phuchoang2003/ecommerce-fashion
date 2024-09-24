package org.example.ecommercefashion.dtos.response;

import lombok.Builder;
import org.example.ecommercefashion.entities.postgres.Permission;

@Builder
public record PermissionResponse(

        Long id,

        String name
) {
    public static PermissionResponse fromEntity(Permission permission) {
        return PermissionResponse.builder()
                .id(permission.getId())
                .name(permission.getName())
                .build();
    }
}
