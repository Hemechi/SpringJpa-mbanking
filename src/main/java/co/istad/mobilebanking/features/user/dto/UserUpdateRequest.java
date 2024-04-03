package co.istad.mobilebanking.features.user.dto;

import java.time.LocalDate;

public record UserUpdateRequest(
        String name,
        String gender,
        LocalDate dob,
        String studentIdCard
) {
}
