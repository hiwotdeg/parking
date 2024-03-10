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
    private static final String ADMIN = "ROLE_ADMIN";
    private static final String PROVIDER = "ROLE_PROVIDER";
    private static final String DRIVER = "ROLE_DRIVER";
    protected static final RequestMatcher[] UNAUTHORIZED_MATCHERS = {
            new AntPathRequestMatcher("/api/v1/parking-lot/providers", HttpMethod.POST.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/drivers", HttpMethod.POST.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/sendMessage", HttpMethod.POST.name()),  //19
            new AntPathRequestMatcher("/actuator", HttpMethod.GET.name()),
            new AntPathRequestMatcher("/actuator/**", HttpMethod.GET.name()),
    };
    protected static final String[] SWAGGER_MATCHERS = {
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html",
            "/actuator",
            "/actuator/**"
    };

    protected static final RequestMatcher[] DRIVER_MATCHERS = {
            new AntPathRequestMatcher("/api/v1/parking-lot/drivers/{id}", HttpMethod.PATCH.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/drivers/my", HttpMethod.GET.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/lots/{parkingLotId}/pricing", HttpMethod.GET.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/lots/{parkingLotId}/reservations", HttpMethod.POST.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/reservations/{reservationId}/cancel", HttpMethod.POST.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/lots/{parkingLotId}/reviews", HttpMethod.POST.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/lots/{parkingLotId}/reviews/{reviewId}", HttpMethod.PATCH.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/lots/{parkingLotId}/reviews/{reviewId}", HttpMethod.DELETE.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/vehicles", HttpMethod.POST.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/vehicles/{id}", HttpMethod.PATCH.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/vehicles/my", HttpMethod.GET.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/vehicles/{id}", HttpMethod.DELETE.name()),
    };

    protected static final RequestMatcher[] PROVIDER_MATCHERS = {
            new AntPathRequestMatcher("/api/v1/parking-lot/lots/{parkingLotId}/operation-hours", HttpMethod.POST.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/lots/{parkingLotId}/operation-hours/{operationHourId}", HttpMethod.DELETE.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/lots", HttpMethod.POST.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/lots/{id}", HttpMethod.PATCH.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/lots/my", HttpMethod.GET.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/lots/{id}", HttpMethod.DELETE.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/providers/{id}", HttpMethod.PATCH.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/providers/my", HttpMethod.GET.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/reservations/{reservationId}/requests", HttpMethod.POST.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/reservations/my", HttpMethod.GET.name()),

    };

    protected static final RequestMatcher[] ADMIN_MATCHERS = {
            new AntPathRequestMatcher("/api/v1/parking-lot/drivers", HttpMethod.GET.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/lots", HttpMethod.GET.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/providers", HttpMethod.GET.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/reservations", HttpMethod.GET.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/reviews", HttpMethod.GET.name()),
    };

    protected static final RequestMatcher[] DRIVER_AND_PROVIDER_MATCHERS = {
            new AntPathRequestMatcher("/api/v1/parking-lot/drivers/{id}", HttpMethod.GET.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/lots/{id}", HttpMethod.GET.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/lots/{parkingLotId}/reviews", HttpMethod.GET.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/vehicles/{id}", HttpMethod.GET.name()),          //12
    };

    protected static final RequestMatcher[] DRIVER_PROVIDER_ADMIN_MATCHERS = {
            new AntPathRequestMatcher("/api/v1/parking-lot/lots/{parkingLotId}/operation-hours", HttpMethod.GET.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/lots/{parkingLotId}/operation-hours/{operationHourId}", HttpMethod.GET.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/providers/{id}", HttpMethod.GET.name()),
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request.requestMatchers(UNAUTHORIZED_MATCHERS).permitAll())
                .authorizeHttpRequests(request -> request.requestMatchers(SWAGGER_MATCHERS).permitAll())
                .authorizeHttpRequests(request -> request.requestMatchers(DRIVER_AND_PROVIDER_MATCHERS).hasAnyAuthority(DRIVER, PROVIDER))
                .authorizeHttpRequests(request -> request.requestMatchers(DRIVER_PROVIDER_ADMIN_MATCHERS).hasAnyAuthority(DRIVER, PROVIDER, ADMIN))
                .authorizeHttpRequests(request -> request.requestMatchers(ADMIN_MATCHERS).hasAuthority(ADMIN))
                .authorizeHttpRequests(request -> request.requestMatchers(PROVIDER_MATCHERS).hasAuthority(PROVIDER))
                .authorizeHttpRequests(request -> request.requestMatchers(DRIVER_MATCHERS).hasAuthority(DRIVER))
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS)).exceptionHandling(handling -> {
                    handling.authenticationEntryPoint(unauthorizedEntryPoint());
                    handling.accessDeniedHandler(accessDeniedHandler());

                }).addFilterBefore(new RoleHeaderAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
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
        return (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
