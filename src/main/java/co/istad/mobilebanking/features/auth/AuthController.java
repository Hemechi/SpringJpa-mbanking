package co.istad.mobilebanking.features.auth;

import co.istad.mobilebanking.features.auth.dto.AuthRequest;
import co.istad.mobilebanking.features.auth.dto.AuthResponse;
import co.istad.mobilebanking.features.auth.dto.ChangePasswordRequest;
import co.istad.mobilebanking.features.auth.dto.RefreshTokenRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auths")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    AuthResponse login(@Valid @RequestBody AuthRequest authRequest) {
        return authService.login(authRequest);
    }

    @PostMapping("/refresh")
    AuthResponse refresh(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refresh(refreshTokenRequest);
    }

    @PutMapping("/change-password")
    void changePassword(@RequestBody ChangePasswordRequest changePasswordRequest,
                        @AuthenticationPrincipal Jwt jwt) {
        authService.changePassword(jwt,changePasswordRequest);
    }
}
