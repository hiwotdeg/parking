package et.com.gebeya.authservice.repository;

import et.com.gebeya.authservice.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findFirstByUserName(String userName);

    Optional<Users> findByRoleId(Integer id);
}
