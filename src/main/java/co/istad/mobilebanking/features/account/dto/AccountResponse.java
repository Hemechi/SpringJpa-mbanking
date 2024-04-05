package co.istad.mobilebanking.features.account.dto;

import co.istad.mobilebanking.features.account_type.dto.AccountTypeResponse;

import java.math.BigDecimal;

public record AccountResponse(
        String actName,
        String alias,
        BigDecimal balance,
       AccountTypeResponse accountTypeResponse
) {
}
