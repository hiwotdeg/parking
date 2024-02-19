package et.com.gebeya.paymentservice.security;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;

@Getter
public class RoleHeaderAuthenticationToken extends AbstractAuthenticationToken {
    private String headerRole;
    private Integer roleId;

    public RoleHeaderAuthenticationToken(String headerRole, String roleId) {
        super(null);

        this.headerRole = headerRole;
        assert roleId != null;
        this.roleId = Integer.valueOf(roleId);
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
