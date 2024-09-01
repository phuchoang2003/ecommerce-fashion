package org.example.ecommercefashion.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponse {

    private Long id;

    private String name;

    private Set<PermissionResponse> permissions;
}
