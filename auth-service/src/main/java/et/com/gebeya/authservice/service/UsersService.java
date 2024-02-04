package et.com.gebeya.authservice.service;




import et.com.gebeya.authservice.model.Users;
import et.com.gebeya.authservice.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;
    public UserDetailsService userDetailsService() {
        return this::loadUserByUsername;
    }

    public void createUpdateUser(Users users) {
        usersRepository.save(users);
    }


@Cacheable(value = "user", key = "#userName")
    public Users loadUserByUsername(String userName) {
        return usersRepository.findFirstByUserName(userName).orElseThrow(() -> new IllegalArgumentException("Invalid user name or password"));

    }


}
