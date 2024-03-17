package yu.cse.locker.domain.locker.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import yu.cse.locker.domain.locker.domain.Locker;
import yu.cse.locker.domain.user.domain.User;


@Repository
public interface LockerRepository extends JpaRepository<Locker, Long> {
    List<Locker> findLockersByRoomLocation(int roomLocation);

    Locker findLockerByUser(User User);

    Optional<Locker> findLockerByUser_StudentIdAndRoomLocation(String studentId, int location);

    Optional<Locker> findLockerByRoomLocationAndColumnAndRow(int location, int column, int row);

}
