package et.com.gebeya.authservice.config;

import et.com.gebeya.authservice.enums.Authority;
import et.com.gebeya.authservice.model.Users;
import et.com.gebeya.authservice.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AuthConfig {

    private final UsersService usersService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request.requestMatchers("/api/v1/**").permitAll())
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(usersService.userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        createAdminUser();
        return authProvider;
    }

    private void createAdminUser() {
        try {
            Users users = usersService.loadUserByUsername("admin");
        } catch (Exception e) {
            Users users = Users.builder()
                    .isActive(true)
                    .authority(Authority.ADMIN)
                    .userName("admin")
                    .password(passwordEncoder().encode("password"))
                    .build();
            usersService.createUpdateUser(users);
        }
    }



}
