package co.istad.mobilebanking.features.account.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AccountUpdateTransferLimitRequest(
        @NotNull(message = "Transfer Limit is required")
        BigDecimal transferLimit
) {
}
