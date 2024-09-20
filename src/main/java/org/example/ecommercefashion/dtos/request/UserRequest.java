package org.example.ecommercefashion.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.ecommercefashion.annotations.EnumPattern;
import org.example.ecommercefashion.annotations.ValidPassword;
import org.example.ecommercefashion.annotations.ValidPhoneNumber;
import org.example.ecommercefashion.enums.GenderEnum;

import javax.validation.constraints.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @ValidPassword
    private String password;

    @NotNull(message = "Full name is required")
    @Size(min = 2, max = 50, message = "Full name must be between 2 and 50 characters")
    private String fullName;

    @NotNull(message = "Phone number is required")
    @ValidPhoneNumber
    private String phoneNumber;

    @Past(message = "Birth date must be in the past")
    private Date birth;

    @NotNull(message = "gender is required")
    @EnumPattern(name = "gender", regexp = "MALE|FEMALE|OTHER")
    private GenderEnum gender;

    private String avatar;
}
