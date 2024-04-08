package co.istad.mobilebanking.features.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AccountRenameRequest(
        @NotBlank(message = "New name is required!")
        @Size(message = "Account name must be less than or equal 100 characters")
        String newName
) {
}
