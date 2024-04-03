package co.istad.mobilebanking.features.user.dto;

import java.time.LocalDate;

public record UserResponse(
        String uuid,
        String name,
        String profileImage,
        String gender,
        LocalDate dob
) {
}
