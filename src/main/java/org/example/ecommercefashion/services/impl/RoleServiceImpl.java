package org.example.ecommercefashion.services.impl;

import com.longnh.exceptions.ExceptionHandle;
import com.longnh.utils.FnCommon;
import lombok.RequiredArgsConstructor;
import org.example.ecommercefashion.dtos.request.RoleRequest;
import org.example.ecommercefashion.dtos.response.MessageResponse;
import org.example.ecommercefashion.dtos.response.RoleResponse;
import org.example.ecommercefashion.entities.postgres.Permission;
import org.example.ecommercefashion.entities.postgres.Role;
import org.example.ecommercefashion.exceptions.ErrorMessage;
import org.example.ecommercefashion.services.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final EntityManager entityManager;

    @Override
    @Transactional
    public RoleResponse createRole(RoleRequest roleRequest) {
        Role role = new Role();
        FnCommon.coppyNonNullProperties(role, roleRequest);

        Set<Permission> permissionSet = new HashSet<>();
        for (Long permissionId : roleRequest.getPermissionIds()) {
            Permission permissionEntity =
                    Optional.ofNullable(entityManager.find(Permission.class, permissionId))
                            .orElseThrow(
                                    () ->
                                            new ExceptionHandle(HttpStatus.NOT_FOUND, ErrorMessage.PERMISSION_NOT_FOUND));
            permissionSet.add(permissionEntity);
        }

        role.setPermissions(permissionSet);
        entityManager.persist(role);
        return RoleResponse.fromEntity(role);
    }

    @Override
    @Transactional
    public RoleResponse updateRole(Long id, RoleRequest roleRequest) {
        Role role = entityManager.find(Role.class, id);
        if (role == null) {
            throw new ExceptionHandle(HttpStatus.NOT_FOUND, ErrorMessage.ROLE_NOT_FOUND);
        }
        FnCommon.copyNonNullProperties(role, roleRequest);
        entityManager.merge(role);
        return RoleResponse.fromEntity(role);
    }


    @Override
    public RoleResponse getRoleById(Long id) {
        Role role = entityManager.find(Role.class, id);
        if (role == null) {
            throw new ExceptionHandle(HttpStatus.NOT_FOUND, ErrorMessage.ROLE_NOT_FOUND);
        }
        return RoleResponse.fromEntity(role);
    }

    @Override
    public MessageResponse deleteRole(Long id) {
        Role role = entityManager.find(Role.class, id);
        if (role == null) {
            throw new ExceptionHandle(HttpStatus.NOT_FOUND, ErrorMessage.ROLE_NOT_FOUND);
        }
        entityManager.remove(role);
        return MessageResponse.builder().message("Role delete successfully").build();
    }


}
