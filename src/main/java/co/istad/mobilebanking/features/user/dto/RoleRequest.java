package co.istad.mobilebanking.features.user.dto;

import jakarta.validation.constraints.NotBlank;

public record RoleRequest(
        @NotBlank
        String name
) {
}
