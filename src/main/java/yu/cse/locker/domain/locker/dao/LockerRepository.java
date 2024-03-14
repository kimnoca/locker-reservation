package yu.cse.locker.domain.locker.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yu.cse.locker.domain.locker.domain.Locker;


@Repository
public interface LockerRepository extends JpaRepository<Locker, Long> {
    List<Locker> findLockersByRoomLocation(int roomLocation);

    Optional<Locker> findLockerByOwnerName(String studentId);

}
