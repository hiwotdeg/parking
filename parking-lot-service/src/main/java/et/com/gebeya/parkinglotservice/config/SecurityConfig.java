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
            new AntPathRequestMatcher("/api/v1/parking-lot/drivers/*", HttpMethod.PATCH.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/drivers", HttpMethod.DELETE.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/lots/*/reviews", HttpMethod.POST.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/lots/*/reviews/*", HttpMethod.PATCH.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/lots/*/reviews/*", HttpMethod.DELETE.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/vehicles",HttpMethod.POST.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/vehicles",HttpMethod.GET.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/vehicles/*",HttpMethod.GET.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/vehicles/*",HttpMethod.PATCH.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/vehicles/*",HttpMethod.DELETE.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/lots/*/pricing/**", HttpMethod.GET.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/lots/*/reservations", HttpMethod.POST.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/reservations/*/cancel",HttpMethod.POST.name())
    };

    protected static final RequestMatcher[] PROVIDER_MATCHERS = {
            new AntPathRequestMatcher("/api/v1/parking-lot/providers/*", HttpMethod.PATCH.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/providers", HttpMethod.DELETE.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/lots/*", HttpMethod.PATCH.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/lots", HttpMethod.POST.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/lots/*", HttpMethod.DELETE.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/lots/*/operation-hours", HttpMethod.POST.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/lots/*/operation-hours/*", HttpMethod.DELETE.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/lots/reservations/**", HttpMethod.GET.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/reservations/*/requests",HttpMethod.POST.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/reservations/*")
    };

    protected static final RequestMatcher[] DRIVER_AND_PROVIDER_MATCHERS = {
            new AntPathRequestMatcher("/api/v1/parking-lot/providers/*", HttpMethod.GET.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/drivers/*", HttpMethod.GET.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/lots/*", HttpMethod.GET.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/lots/*/reviews/*", HttpMethod.GET.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/lots/*/operation-hours", HttpMethod.GET.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/lots/*/operation-hours/*", HttpMethod.GET.name()),
            new AntPathRequestMatcher("/api/v1/parking-lot/lots/*/reviews", HttpMethod.GET.name()),          //12
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request.requestMatchers(UNAUTHORIZED_MATCHERS).permitAll())
                .authorizeHttpRequests(request -> request.requestMatchers(SWAGGER_MATCHERS).permitAll())
                .authorizeHttpRequests(request -> request.requestMatchers(DRIVER_AND_PROVIDER_MATCHERS).hasAnyAuthority("ROLE_DRIVER","ROLE_PROVIDER"))
                .authorizeHttpRequests(request -> request.requestMatchers(PROVIDER_MATCHERS).hasAuthority("ROLE_PROVIDER"))
                .authorizeHttpRequests(request -> request.requestMatchers(DRIVER_MATCHERS).hasAuthority("ROLE_DRIVER"))

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
