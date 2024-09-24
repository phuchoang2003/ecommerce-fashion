package org.example.ecommercefashion.services.impl;

import com.longnh.exceptions.ExceptionHandle;
import lombok.RequiredArgsConstructor;
import org.example.ecommercefashion.dtos.request.PermissionRequest;
import org.example.ecommercefashion.dtos.response.MessageResponse;
import org.example.ecommercefashion.dtos.response.PermissionResponse;
import org.example.ecommercefashion.dtos.response.ResponsePage;
import org.example.ecommercefashion.entities.postgres.Permission;
import org.example.ecommercefashion.exceptions.ErrorMessage;
import org.example.ecommercefashion.repositories.postgres.PermissionRepository;
import org.example.ecommercefashion.services.PermissionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final EntityManager entityManager;
    private final PermissionRepository permissionRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PermissionResponse createPermission(PermissionRequest request) {
        Permission permission = new Permission();
        permission.setName(request.getName());
        entityManager.persist(permission);
        return PermissionResponse.fromEntity(permission);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PermissionResponse updatePermission(Long id, PermissionRequest request) {
        Permission permission =
                Optional.ofNullable(entityManager.find(Permission.class, id))
                        .orElseThrow(
                                () -> new ExceptionHandle(HttpStatus.NOT_FOUND, ErrorMessage.PERMISSION_NOT_FOUND));
        permission.setName(request.getName());
        return PermissionResponse.fromEntity(permission);
    }

    @Override
    public ResponsePage<Permission, PermissionResponse> getAllPermissions(Pageable pageable) {
        Page<Permission> permissionPage = permissionRepository.findAll(pageable);
        return new ResponsePage<>(permissionPage, PermissionResponse.class);
    }

    @Override
    public PermissionResponse getPermissionById(Long id) {
        Permission permission =
                Optional.ofNullable(entityManager.find(Permission.class, id))
                        .orElseThrow(
                                () -> new ExceptionHandle(HttpStatus.NOT_FOUND, ErrorMessage.PERMISSION_NOT_FOUND));
        return PermissionResponse.fromEntity(permission);
    }

    @Override
    @Transactional
    public MessageResponse deletePermission(Long id) {
        Permission permission =
                Optional.ofNullable(entityManager.find(Permission.class, id))
                        .orElseThrow(
                                () -> new ExceptionHandle(HttpStatus.NOT_FOUND, ErrorMessage.PERMISSION_NOT_FOUND));
        entityManager.remove(permission);
        return MessageResponse.builder().message("Permission delete successfully").build();
    }


}
