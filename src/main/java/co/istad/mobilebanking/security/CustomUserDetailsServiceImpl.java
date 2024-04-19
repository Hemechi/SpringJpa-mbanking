package co.istad.mobilebanking.security;

import co.istad.mobilebanking.domain.User;
import co.istad.mobilebanking.features.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@ToString
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {

        //load user from db
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UsernameNotFoundException("User has not been found"));


        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setUser(user);

//        log.info("User: {}:",user);

        return customUserDetails;
    }
}
