package org.example.ecommercefashion.module.user.service;

import org.example.ecommercefashion.common.auth.dto.ChangePasswordRequest;
import org.example.ecommercefashion.module.user.dto.UserRequest;
import org.example.ecommercefashion.module.user.dto.UserRoleAssignRequest;
import org.example.ecommercefashion.module.chat.dto.MessageResponse;
import org.example.ecommercefashion.common.core.dto.ResponsePage;
import org.example.ecommercefashion.module.user.dto.UserResponse;
import org.example.ecommercefashion.module.user.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.Set;

@SuppressWarnings("unused")
public interface UserService {


    User createUser(UserRequest userRequest);

    UserResponse createUserResponse(UserRequest userRequest);

    UserResponse updateUser(Long id, UserRequest userRequest);

    void deleteUser(Long id);

    UserResponse getUserResponseById(Long id);

    MessageResponse assignRoleAdmin(String email);

    MessageResponse changePassword(ChangePasswordRequest changePasswordRequest, String token);

    ResponsePage<User, UserResponse> getAllUsers(Pageable pageable);

    MessageResponse assignUserRole(UserRoleAssignRequest userRoleAssignRequest);

    void checkUsersExists(Set<Long> userIds);

    User getUserByEmail(String email);

    User getUserById(Long id);

}
