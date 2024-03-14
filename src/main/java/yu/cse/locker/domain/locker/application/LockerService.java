package yu.cse.locker.domain.locker.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yu.cse.locker.domain.locker.dao.LockerRepository;
import yu.cse.locker.domain.locker.domain.Locker;
import yu.cse.locker.domain.locker.dto.LockerRequestDto;

@Service
@RequiredArgsConstructor
public class LockerService {

    private final LockerRepository lockerRepository;

    @Transactional
    public Locker reservationLocker(LockerRequestDto lockerRequestDto, String studentId) {

        Locker locker = Locker.builder()
                .ownerName(studentId)
                .roomLocation(lockerRequestDto.getRoomLocation())
                .row(lockerRequestDto.getRow())
                .column(lockerRequestDto.getColumn())
                .build();

//        lockerRepository.findLockerByOwnerName(user.getUsername()); // 사용자가 에약한 사물함과 같은 사물함이면 취소
        // 예약한 사물함이 있는데 옮기는 경우?

        return lockerRepository.save(locker);
    }

    public List<Locker> lockerList(int locationRoom) {
        return null;
    }

}
