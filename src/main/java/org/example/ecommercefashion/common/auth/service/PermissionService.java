package org.example.ecommercefashion.common.auth.service;

import org.example.ecommercefashion.common.auth.dto.PermissionRequest;
import org.example.ecommercefashion.module.chat.dto.MessageResponse;
import org.example.ecommercefashion.common.auth.dto.PermissionResponse;
import org.example.ecommercefashion.common.core.dto.ResponsePage;
import org.example.ecommercefashion.common.auth.entity.Permission;
import org.springframework.data.domain.Pageable;

public interface PermissionService {

    PermissionResponse createPermission(PermissionRequest request);

    PermissionResponse getPermissionById(Long id);

    MessageResponse deletePermission(Long id);

    PermissionResponse updatePermission(Long id, PermissionRequest request);

    ResponsePage<Permission, PermissionResponse> getAllPermissions(Pageable pageable);
}
