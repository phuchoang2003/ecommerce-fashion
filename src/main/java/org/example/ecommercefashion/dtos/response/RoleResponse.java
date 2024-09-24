package org.example.ecommercefashion.dtos.response;

import lombok.Builder;
import org.example.ecommercefashion.entities.postgres.Role;

import java.util.Set;
import java.util.stream.Collectors;

@Builder
public record RoleResponse(

        Long id,

        String name,

        Set<PermissionResponse> permissions
) {
    public static RoleResponse fromEntity(Role role) {
        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .permissions(role.getPermissions().stream().map(PermissionResponse::fromEntity).collect(Collectors.toSet()))
                .build();
    }
}
