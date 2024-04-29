package co.istad.mobilebanking.features.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record ChangePasswordRequest(
        @NotBlank(message = "old password is required")
        String oldPassword,
        @NotBlank(message = "Password is required")
        String password,
        @NotBlank(message = "Confirmed Password is required")
        String confirmedPassword
) {
}
