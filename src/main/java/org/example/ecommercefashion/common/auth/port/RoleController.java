package org.example.ecommercefashion.common.auth.port;

import lombok.RequiredArgsConstructor;
import org.example.ecommercefashion.common.auth.dto.RoleRequest;
import org.example.ecommercefashion.common.auth.dto.RoleResponse;
import org.example.ecommercefashion.common.auth.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public RoleResponse createRole(@Valid @RequestBody RoleRequest roleRequest) {
        return roleService.createRole(roleRequest);
    }

    @GetMapping("/{id}")
    public RoleResponse getRoleById(@PathVariable Long id) {
        return roleService.getRoleById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
    }

    @PutMapping("/{id}")
    public RoleResponse updateRole(
            @PathVariable Long id, @Valid @RequestBody RoleRequest roleRequest) {
        return roleService.updateRole(id, roleRequest);
    }
}
