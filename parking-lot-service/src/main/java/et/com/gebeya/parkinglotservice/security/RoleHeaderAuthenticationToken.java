package et.com.gebeya.parkinglotservice.security;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;

@Getter
public class RoleHeaderAuthenticationToken extends AbstractAuthenticationToken {
    private String headerRole;

    public RoleHeaderAuthenticationToken(String headerRole) {
        super(null);
        this.headerRole = headerRole;
        setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return headerRole;
    }

    // Getters for headerRole and authorities
}
