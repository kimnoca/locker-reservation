package yu.cse.locker.domain.user.dao;

import io.micrometer.observation.ObservationFilter;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yu.cse.locker.domain.user.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByStudentId(String studentId);

    Long findUserIdByStudentId(String studentId);
}
