package org.example.ecommercefashion.services;

import org.example.ecommercefashion.dtos.request.ChangePasswordRequest;
import org.example.ecommercefashion.dtos.request.UserRequest;
import org.example.ecommercefashion.dtos.request.UserRoleAssignRequest;
import org.example.ecommercefashion.dtos.response.MessageResponse;
import org.example.ecommercefashion.dtos.response.ResponsePage;
import org.example.ecommercefashion.dtos.response.UserResponse;
import org.example.ecommercefashion.entities.postgres.User;
import org.springframework.data.domain.Pageable;

import java.util.Set;

@SuppressWarnings("unused")
public interface UserService {

    UserResponse createUser(UserRequest userRequest);

    UserResponse updateUser(Long id, UserRequest userRequest);

    MessageResponse deleteUser(Long id);

    UserResponse getUserResponseById(Long id);

    MessageResponse assignRoleAdmin(String email);

    MessageResponse changePassword(ChangePasswordRequest changePasswordRequest, String token);

    ResponsePage<User, UserResponse> getAllUsers(Pageable pageable);

    MessageResponse assignUserRole(UserRoleAssignRequest userRoleAssignRequest);

    void checkUsersExists(Set<Long> userIds);

    User getUserByEmail(String email);

    User getUserById(Long id);
    
}
