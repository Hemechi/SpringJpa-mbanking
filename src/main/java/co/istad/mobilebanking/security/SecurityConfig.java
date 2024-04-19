package co.istad.mobilebanking.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
//disable default spring config
@EnableWebSecurity
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }

    //    In Memory User : user store in RAM
//    JDBC user : user store in permenant place
//    @Bean
//    InMemoryUserDetailsManager inMemoryUserDetailsManager() {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//
//        UserDetails userAdmin = User.builder()
//                .username("admin")
////                decrypt password for raw password
//                .password(passwordEncoder.encode("admin"))
//                .roles("USER", "ADMIN")
//                .build();
//        UserDetails userEditor = User.builder()
//                .username("editor")
//                .password(passwordEncoder.encode("editor"))
//                .roles("USER", "EDITOR")
//                .build();
//
//        manager.createUser(userAdmin);
//        manager.createUser(userEditor);
//
//        return manager;
//
//    }


    //    ជញ្ជាំង, HttpSecurity : មេ
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

//        TODO: your security logic

        //        every end-point that client request must check (security)
        //        hasRole : only one role, HasAnyRole : many role
        httpSecurity
                .authorizeHttpRequests(request -> request
                        .requestMatchers( HttpMethod.GET,"/api/v1/users/**").hasAnyRole("ADMIN","CUSTOMER")
                        .requestMatchers( HttpMethod.DELETE,"/api/v1/users/**").hasRole("ADMIN")
                        .requestMatchers( HttpMethod.PUT,"/api/v1/users/**").hasRole("ADMIN")
                        .requestMatchers( HttpMethod.POST,"/api/v1/users/**").permitAll()
                        .anyRequest()
                        .authenticated());

//        every end-point that client request will not stuck with security : ចូលតាមសេរី
//        httpSecurity
//                .authorizeHttpRequests(request -> request
//                        .anyRequest().permitAll());

//        since we disable the default config, it no longer understand even the login
//        put back the default config of login
//        httpBasic : username, password
        httpSecurity.httpBasic(Customizer.withDefaults());

//        csrf : send data to server
        //disable csrf
        httpSecurity.csrf(token -> token.disable());
        //change to stateless
        httpSecurity.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return httpSecurity.build();

    }
}
