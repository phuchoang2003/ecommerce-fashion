package org.example.ecommercefashion.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.ecommercefashion.annotations.ValidPassword;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest {

    @NotBlank(message = "You must enter your current password!")
    private String currentPassword;

    @ValidPassword
    private String newPassword;


    @AssertTrue(message = "New password must be different from the current password!")
    public boolean isNewPasswordNotEqualOldPassword() {
        return !newPassword.equals(currentPassword);
    }
}
