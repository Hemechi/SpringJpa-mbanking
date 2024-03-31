package co.istad.mobilebanking.features.user.dto;

import java.math.BigDecimal;

public record UserEditRequest(
        String cityOrProvince,
        String khanOrDistrict,
        String sangkatOrCommune,
        String employeeType,
        String position,
        String companyName,
        String mainSourceOfIncome,
        BigDecimal monthlyIncomeRange
) {
}
