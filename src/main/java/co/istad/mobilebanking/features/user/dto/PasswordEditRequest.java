package co.istad.mobilebanking.features.user.dto;

public record PasswordEditRequest(
        String password,
        String newPassword,
        String confirmedPassword
) {
}
