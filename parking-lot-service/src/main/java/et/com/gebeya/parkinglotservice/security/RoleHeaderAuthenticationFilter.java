package et.com.gebeya.parkinglotservice.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class RoleHeaderAuthenticationFilter extends OncePerRequestFilter {
@Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain) throws ServletException, IOException {
        String headerRole = request.getHeader("Role");
        String roleId = request.getHeader("RoleId");
        if (headerRole != null && roleId!=null) {
            Authentication authentication = new RoleHeaderAuthenticationToken(headerRole, roleId);
            authentication = new RoleHeaderAuthenticationProvider().authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        else{
            Authentication authentication = new RoleHeaderAuthenticationToken(headerRole, roleId);
            authentication = new RoleHeaderAuthenticationProvider().authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

}
