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


    private static final RequestMatcher[] ENDPOINTS = {
            new AntPathRequestMatcher("/api/v1/parking-lot/providers", HttpMethod.POST.name()),          //0
            new AntPathRequestMatcher("/api/v1/parking-lot/providers/*", HttpMethod.PATCH.name()),     //1
            new AntPathRequestMatcher("/api/v1/parking-lot/providers/*", HttpMethod.GET.name()),       //2
            new AntPathRequestMatcher("/api/v1/parking-lot/providers", HttpMethod.DELETE.name()),       //3
            new AntPathRequestMatcher("/api/v1/parking-lot/drivers", HttpMethod.POST.name()),            //4
            new AntPathRequestMatcher("/api/v1/parking-lot/drivers/*", HttpMethod.GET.name()),         //5
            new AntPathRequestMatcher("/api/v1/parking-lot/drivers/*", HttpMethod.PATCH.name()),        //6
            new AntPathRequestMatcher("/api/v1/parking-lot/drivers", HttpMethod.DELETE.name()),          //7
            new AntPathRequestMatcher("/api/v1/parking-lot/lots/*", HttpMethod.GET.name()),            //8
            new AntPathRequestMatcher("/api/v1/parking-lot/lots/*", HttpMethod.PATCH.name()),           //9
            new AntPathRequestMatcher("/api/v1/parking-lot/lots", HttpMethod.POST.name()),               //10
            new AntPathRequestMatcher("/api/v1/parking-lot/lots/*", HttpMethod.DELETE.name()),             //11
            new AntPathRequestMatcher("/api/v1/parking-lot/lots/*/reviews/*", HttpMethod.GET.name()),          //12
            new AntPathRequestMatcher("/api/v1/parking-lot/lots/*/reviews", HttpMethod.POST.name()),            //13
            new AntPathRequestMatcher("/api/v1/parking-lot/lots/*/reviews/*", HttpMethod.PATCH.name()),        //14
            new AntPathRequestMatcher("/api/v1/parking-lot/lots/*/reviews/*", HttpMethod.DELETE.name()),          //15
            new AntPathRequestMatcher("/api/v1/parking-lot/operation-hours", HttpMethod.POST.name()),    //16
            new AntPathRequestMatcher("/api/v1/parking-lot/operation-hours/**", HttpMethod.GET.name()),  //17
            new AntPathRequestMatcher("/api/v1/parking-lot/operation-hours/**", HttpMethod.PATCH.name()),//18
            new AntPathRequestMatcher("/api/v1/parking-lot/operation-hours", HttpMethod.DELETE.name()),  //19

    };


    protected static final RequestMatcher[] UNAUTHORIZED_MATCHERS = {
            ENDPOINTS[0],
            ENDPOINTS[4],
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
            ENDPOINTS[6],
            ENDPOINTS[7],
            ENDPOINTS[13],
            ENDPOINTS[14],
            ENDPOINTS[15]
    };

    protected static final RequestMatcher[] PROVIDER_MATCHERS = {
            ENDPOINTS[1],
            ENDPOINTS[3],
            ENDPOINTS[9],
            ENDPOINTS[10],
            ENDPOINTS[11],
            ENDPOINTS[16],
            ENDPOINTS[18],
            ENDPOINTS[19],
    };

    protected static final RequestMatcher[] DRIVER_AND_PROVIDER_MATCHERS = {
            ENDPOINTS[2],
            ENDPOINTS[5],
            ENDPOINTS[8],
            ENDPOINTS[12],
            ENDPOINTS[17],
            new AntPathRequestMatcher("/api/v1/parking-lot/lots/*/reviews", HttpMethod.GET.name()),          //12
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request.requestMatchers(UNAUTHORIZED_MATCHERS).permitAll())
                .authorizeHttpRequests(request -> request.requestMatchers(SWAGGER_MATCHERS).permitAll())
                .authorizeHttpRequests(request -> request.requestMatchers(DRIVER_AND_PROVIDER_MATCHERS).hasAnyAuthority("ROLE_USER","ROLE_PROVIDER"))
                .authorizeHttpRequests(request -> request.requestMatchers(PROVIDER_MATCHERS).hasAuthority("ROLE_PROVIDER"))
                .authorizeHttpRequests(request -> request.requestMatchers(DRIVER_MATCHERS).hasAuthority("ROLE_USER"))

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
