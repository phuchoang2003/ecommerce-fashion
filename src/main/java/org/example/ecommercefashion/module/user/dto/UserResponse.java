package org.example.ecommercefashion.module.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.example.ecommercefashion.common.auth.enums.GenderEnum;
import org.example.ecommercefashion.module.user.entity.User;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record UserResponse(
        Long id,
        String email,
        String fullName,
        String phoneNumber,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        Date birth,

        GenderEnum gender,
        String avatar
) {

    public static UserResponse fromEntity(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .birth(user.getBirth())
                .gender(user.getGender())
                .avatar(user.getAvatar())
                .build();
    }

}
