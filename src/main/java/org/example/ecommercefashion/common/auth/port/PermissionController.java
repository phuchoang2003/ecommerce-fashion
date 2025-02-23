package org.example.ecommercefashion.common.auth.port;

import lombok.RequiredArgsConstructor;
import org.example.ecommercefashion.common.auth.dto.PermissionRequest;
import org.example.ecommercefashion.common.auth.dto.PermissionResponse;
import org.example.ecommercefashion.common.core.dto.ResponsePage;
import org.example.ecommercefashion.common.auth.entity.Permission;
import org.example.ecommercefashion.common.auth.service.PermissionService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/permission")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @PostMapping
    public PermissionResponse createPermission(
            @RequestBody @Valid PermissionRequest request) {
        return permissionService.createPermission(request);
    }

    @GetMapping("/{id}")
    public PermissionResponse getPermissionById(@PathVariable Long id) {
        return permissionService.getPermissionById(id);
    }

    @DeleteMapping("/{id}")
    public void deletePermission(@PathVariable Long id) {
        permissionService.deletePermission(id);
    }

    @PutMapping("/{id}")
    public PermissionResponse updatePermission(
            @Valid @RequestBody PermissionRequest request,
            @PathVariable Long id) {
        return permissionService.updatePermission(id, request);
    }

    @GetMapping
    public ResponsePage<Permission, PermissionResponse> getAllPermissions(Pageable pageable) {
        return permissionService.getAllPermissions(pageable);
    }
}
