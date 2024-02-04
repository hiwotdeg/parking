package et.com.gebeya.authservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import et.com.gebeya.authservice.enums.Authority;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
@Entity
@Builder


public class Users extends BaseModel implements UserDetails {
    @JsonIgnoreProperties(ignoreUnknown = true)
//    @NotBlank(message = "UserName is mandatory")
    @Column(name = "phonenumber")
    private String userName;
//    @Size(min = 6, max = 16)
    @Column(name = "password")
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
//    @NotBlank
    private Authority authority;
    private Integer roleId;

    public static class UsersBuilder {
        private boolean isActive;

        public UsersBuilder isActive(boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public Users build() {
            Users users = new Users();
            users.setIsActive(this.isActive);
            users.setUserName(this.userName);
            users.setPassword(this.password);
            users.setAuthority(this.authority);
            users.setRoleId(this.roleId);
            return users;
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(authority.name()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.getIsActive();
    }


    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.getIsActive();
    }


}
