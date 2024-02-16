package et.com.gebeya.parkinglotservice.config;
import et.com.gebeya.parkinglotservice.security.RoleHeaderAuthenticationFilter;
import et.com.gebeya.parkinglotservice.security.RoleHeaderAuthenticationProvider;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    protected static final RequestMatcher[] UNAUTHORIZED_MATCHERS = {
            new AntPathRequestMatcher("/api/v1/parking-lot/provider",HttpMethod.POST.name()),
            new AntPathRequestMatcher(" /api/v1/parking-lot/driver",HttpMethod.POST.name()),
            new AntPathRequestMatcher("/actuator",HttpMethod.GET.name()),
            new AntPathRequestMatcher("/actuator/**",HttpMethod.GET.name()),

    };

    protected static final String [] SWAGGER_MATCHERS = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/swagger-ui.html/**",
            "/actuator",
            "/actuator/**"
    };

    protected static final RequestMatcher[] DRIVER_MATCHERS = {
            new AntPathRequestMatcher("/api/v1/parking-lot/provider/**", HttpMethod.GET.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/driver/**", HttpMethod.GET.name()),
            new AntPathRequestMatcher(" /api/v1/parking-lot/driver/**",HttpMethod.PATCH.name()),
            new AntPathRequestMatcher(" /api/v1/parking-lot/driver",HttpMethod.POST.name()),
            new AntPathRequestMatcher(" /api/v1/parking-lot/lot/**",HttpMethod.GET.name())

    };

    protected static final String [] PROVIDER_MATCHERS = {

            "/api/v1/parking-lot/provider/**",
            "/api/v1/parking-lot/provider"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request.requestMatchers(UNAUTHORIZED_MATCHERS).permitAll())
                .authorizeHttpRequests(request -> request.requestMatchers(SWAGGER_MATCHERS).permitAll())
                .authorizeHttpRequests(request -> request.requestMatchers(PROVIDER_MATCHERS).hasAuthority("PROVIDER"))
                .authorizeHttpRequests(request -> request.requestMatchers(DRIVER_MATCHERS).hasAuthority("USER"))
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .exceptionHandling(handling -> {
                    handling.authenticationEntryPoint(unauthorizedEntryPoint());
                    handling.accessDeniedHandler(accessDeniedHandler());

                })
                .addFilterBefore(new RoleHeaderAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new RoleHeaderAuthenticationProvider();
    }

    @Bean
    public AuthenticationEntryPoint unauthorizedEntryPoint() {
        return (request, response, authException) ->
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) ->
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

}
