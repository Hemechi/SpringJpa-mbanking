package co.istad.mobilebanking.features.auth;

import co.istad.mobilebanking.domain.User;
import co.istad.mobilebanking.features.auth.dto.AuthRequest;
import co.istad.mobilebanking.features.auth.dto.AuthResponse;
import co.istad.mobilebanking.features.auth.dto.ChangePasswordRequest;
import co.istad.mobilebanking.features.auth.dto.RefreshTokenRequest;
import co.istad.mobilebanking.features.token.TokenService;
import co.istad.mobilebanking.features.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void changePassword(Jwt jwt, ChangePasswordRequest changePasswordRequest) {
        User user = userRepository.findByPhoneNumber(jwt.getId()).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User not found"));
        // check confirm password and password
        if(!changePasswordRequest.password().equals(changePasswordRequest.password())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Confirmed Password not match");
        }

        // check old password from database with password input
        if(!passwordEncoder.matches(changePasswordRequest.oldPassword(), user.getPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Old Password not match");
        }
        // check new password and old password
        if (passwordEncoder.matches(changePasswordRequest.password(), user.getPassword())) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "New password is the same as old password");
        }
        user.setPassword(passwordEncoder.encode(changePasswordRequest.password()));
        userRepository.save(user);

    }

    @Override
    public AuthResponse login(AuthRequest authRequest) {

        Authentication auth = new UsernamePasswordAuthenticationToken(
                authRequest.phoneNumber(),
                authRequest.password()
        );

        log.info("auth: {}",auth.getAuthorities());
        auth = daoAuthenticationProvider.authenticate(auth);

        return tokenService.createToken(auth);

    }

    @Override
    public AuthResponse refresh(RefreshTokenRequest refreshTokenRequest) {

        Authentication auth = new BearerTokenAuthenticationToken(
                refreshTokenRequest.refreshToken()
        );

        auth = jwtAuthenticationProvider.authenticate(auth);

        return tokenService.createToken(auth);
    }

}
