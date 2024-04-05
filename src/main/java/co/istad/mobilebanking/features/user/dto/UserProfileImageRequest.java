package co.istad.mobilebanking.features.user.dto;

import jakarta.validation.constraints.NotNull;

public record UserProfileImageRequest(
        @NotNull
        String mediaName
) {
}
