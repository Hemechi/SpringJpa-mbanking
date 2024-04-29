package co.istad.mobilebanking.features.auth;

import co.istad.mobilebanking.features.auth.dto.AuthRequest;
import co.istad.mobilebanking.features.auth.dto.AuthResponse;
import co.istad.mobilebanking.features.auth.dto.ChangePasswordRequest;
import co.istad.mobilebanking.features.auth.dto.RefreshTokenRequest;
import org.springframework.security.oauth2.jwt.Jwt;

public interface AuthService {
    void changePassword(Jwt jwt, ChangePasswordRequest changePasswordRequest);
    AuthResponse login(AuthRequest authRequest);
    AuthResponse refresh(RefreshTokenRequest refreshTokenRequest);
}
