package org.example.ecommercefashion.common.auth.service;

import org.example.ecommercefashion.common.auth.dto.RoleRequest;
import org.example.ecommercefashion.module.chat.dto.MessageResponse;
import org.example.ecommercefashion.common.auth.dto.RoleResponse;

public interface RoleService {

    RoleResponse createRole(RoleRequest roleRequest);

    RoleResponse updateRole(Long id, RoleRequest roleRequest);

    RoleResponse getRoleById(Long id);

    MessageResponse deleteRole(Long id);
}
