package co.istad.mobilebanking.features.account.dto;

import co.istad.mobilebanking.features.account_type.dto.AccountTypeResponse;
import co.istad.mobilebanking.features.user.dto.UserResponse;

import java.math.BigDecimal;

public record AccountResponse(
        String actNo,
        String actName,
        String alias,
        BigDecimal balance,
        BigDecimal transferLimit,
        AccountTypeResponse accountType,
        UserResponse user
) {
}
