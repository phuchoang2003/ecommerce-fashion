package org.example.ecommercefashion.services.impl;

import com.longnh.exceptions.ExceptionHandle;
import com.longnh.utils.FnCommon;
import lombok.RequiredArgsConstructor;
import org.example.ecommercefashion.dtos.request.ChangePasswordRequest;
import org.example.ecommercefashion.dtos.request.UserRequest;
import org.example.ecommercefashion.dtos.request.UserRoleAssignRequest;
import org.example.ecommercefashion.dtos.response.MessageResponse;
import org.example.ecommercefashion.dtos.response.ResponsePage;
import org.example.ecommercefashion.dtos.response.UserResponse;
import org.example.ecommercefashion.entities.mysql.Role;
import org.example.ecommercefashion.entities.mysql.User;
import org.example.ecommercefashion.exceptions.ErrorMessage;
import org.example.ecommercefashion.repositories.mysql.UserRepository;
import org.example.ecommercefashion.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final EntityManager entityManager;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    @Override
    public void checkUsersExists(Set<Long> userIds) {
        Set<Long> ids = userRepository.findByIdIn(userIds);
        if (ids.size() != userIds.size()) {
            Set<Long> missingIds = new HashSet<>(userIds);
            missingIds.removeAll(ids);
            String errorMessage = String.format("%s : %s", ErrorMessage.USER_NOT_FOUND, missingIds);
            throw new ExceptionHandle(HttpStatus.NOT_FOUND, errorMessage);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserResponse createUser(UserRequest userRequest) {
        User user = new User();
        FnCommon.copyProperties(user, userRequest);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        entityManager.persist(user);
        return mapEntityToResponse(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        User user = entityManager.find(User.class, id);
        if (user == null) {
            return null;
        }
        FnCommon.copyProperties(user, userRequest);
        entityManager.merge(user);
        return mapEntityToResponse(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MessageResponse deleteUser(Long id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            user.setDeleted(true);
            entityManager.merge(user);
        }
        return MessageResponse.builder().message("User deleted successfully").build();
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = entityManager.find(User.class, id);
        if (user == null) {
            return null;
        }
        return mapEntityToResponse(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public MessageResponse assignRoleAdmin(String email) {
        User user =
                Optional.ofNullable(userRepository.findByEmail(email))
                        .orElseThrow(
                                () -> new ExceptionHandle(HttpStatus.NOT_FOUND, ErrorMessage.USER_NOT_FOUND.val()));

        user.setIsAdmin(true);
        entityManager.merge(user);

        return MessageResponse.builder().message("Role assigned successfully").build();
    }

    @Override
    public MessageResponse changePassword(ChangePasswordRequest changePasswordRequest) {
        User user =
                Optional.ofNullable(userRepository.findByEmail(changePasswordRequest.getEmail()))
                        .orElseThrow(
                                () -> new ExceptionHandle(HttpStatus.NOT_FOUND, ErrorMessage.USER_NOT_FOUND.val()));
        String currentPassword = user.getPassword();
        if (passwordEncoder.matches(changePasswordRequest.getNewPassword(), currentPassword)) {
            throw new ExceptionHandle(
                    HttpStatus.BAD_REQUEST, ErrorMessage.CURRENT_PASSWORD_SAME_NEW_PASSWORD.val());
        }
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        entityManager.merge(user);
        return MessageResponse.builder().message("Password changed successfully").build();
    }

    @Override
    public ResponsePage<User, UserResponse> getAllUsers(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);
        return new ResponsePage<>(userPage, UserResponse.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MessageResponse assignUserRole(UserRoleAssignRequest userRoleAssignRequest) {
        User user =
                Optional.ofNullable(userRepository.findByEmail(userRoleAssignRequest.getEmail()))
                        .orElseThrow(
                                () -> new ExceptionHandle(HttpStatus.NOT_FOUND, ErrorMessage.USER_NOT_FOUND.val()));

        for (Long roleId : userRoleAssignRequest.getRoleIds()) {
            Role role =
                    Optional.ofNullable(entityManager.find(Role.class, roleId))
                            .orElseThrow(
                                    () -> new ExceptionHandle(HttpStatus.NOT_FOUND, ErrorMessage.ROLE_NOT_FOUND.val()));
            user.getRoles().add(role);
        }

        entityManager.merge(user);
        return MessageResponse.builder().message("Role assigned successfully").build();
    }

    private UserResponse mapEntityToResponse(User user) {
        UserResponse userResponse = new UserResponse();
        FnCommon.copyProperties(userResponse, user);
        return userResponse;
    }
}
