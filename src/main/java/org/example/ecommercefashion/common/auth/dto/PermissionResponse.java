package org.example.ecommercefashion.common.auth.dto;

import lombok.Builder;
import org.example.ecommercefashion.common.auth.entity.Permission;

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
