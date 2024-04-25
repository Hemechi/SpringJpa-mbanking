package co.istad.mobilebanking.features.auth;

import co.istad.mobilebanking.features.auth.dto.AuthRequest;
import co.istad.mobilebanking.features.auth.dto.AuthResponse;
import co.istad.mobilebanking.features.auth.dto.RefreshTokenRequest;

public interface AuthService {
    AuthResponse login(AuthRequest authRequest);
    AuthResponse refresh(RefreshTokenRequest refreshTokenRequest);
}
