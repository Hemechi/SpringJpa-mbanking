package co.istad.mobilebanking.features.account.dto;

import co.istad.mobilebanking.domain.AccountType;
import co.istad.mobilebanking.features.account_type.dto.AccountTypeResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AccountCreateRequest(
        @NotBlank(message = "Alias is required")
        String alias,
        @NotNull(message = "First balance is required ($5 up)")
        BigDecimal balance,
        @NotBlank(message = "Account type is required")
        String accountTypeAlias,
        @NotBlank(message = "User owner is required")
        String userUuid,
        String cardNumber //if user create account type card
) {
}
