package co.istad.mobilebanking.features.mail.dto;

import jakarta.validation.constraints.NotBlank;

public record MailResponse(
        @NotBlank
        String message
) {
}
