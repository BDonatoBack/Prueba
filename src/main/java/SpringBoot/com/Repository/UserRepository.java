package SpringBoot.com.Repository;
import SpringBoot.com.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {
    boolean existsByuserName(String username);
}
