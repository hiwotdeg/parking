package et.com.gebeya.parkinglotservice.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class RoleHeaderAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String headerRole = ((RoleHeaderAuthenticationToken) authentication).getHeaderRole();
        // Validate role from header (e.g., check against a database or a configuration)
        if ((!headerRole.isEmpty())) {
            List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(headerRole));
            return new UsernamePasswordAuthenticationToken(headerRole, null, authorities);

        } else {
            throw new BadCredentialsException("Invalid role");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return RoleHeaderAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
