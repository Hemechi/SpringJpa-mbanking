package co.istad.mobilebanking.features.transaction.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransactionCreateRequest(
        @NotBlank(message = "Transfer account no of owner is required")
        String ownerActNo,
        @NotBlank(message = "Transfer account no is required")
        String transferReceiverActNo,
        @NotNull(message = "Amount is required")
        @Positive
        BigDecimal amount,
        String remark
) {
}
