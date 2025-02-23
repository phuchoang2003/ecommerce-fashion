package org.example.ecommercefashion.module.user.port;

import lombok.RequiredArgsConstructor;
import org.example.ecommercefashion.common.auth.dto.ChangePasswordRequest;
import org.example.ecommercefashion.module.user.dto.UserRequest;
import org.example.ecommercefashion.module.user.dto.UserRoleAssignRequest;
import org.example.ecommercefashion.common.core.dto.ResponsePage;
import org.example.ecommercefashion.module.user.dto.UserResponse;
import org.example.ecommercefashion.module.user.entity.User;
import org.example.ecommercefashion.module.user.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUserResponse(userRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id, @Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.updateUser(id, userRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserResponseById(id));
    }

    @PatchMapping("/assign-role-admin")
    public ResponseEntity<?> assignRoleAdmin(@Valid @RequestBody String email) {

        return ResponseEntity.ok(userService.assignRoleAdmin(email));
    }

    @PatchMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestHeader("Authorization") String token, @Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        return ResponseEntity.ok(userService.changePassword(changePasswordRequest, token));
    }

    @GetMapping
    public ResponseEntity<ResponsePage<User, UserResponse>> getAllUsers(Pageable pageable) {
        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }

    @PatchMapping("/assign-user-role")
    public ResponseEntity<?> assignUserRole(@Valid @RequestBody UserRoleAssignRequest userRoleAssignRequest) {
        return ResponseEntity.ok(userService.assignUserRole(userRoleAssignRequest));
    }


}
