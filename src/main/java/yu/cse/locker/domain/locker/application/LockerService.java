package yu.cse.locker.domain.locker.application;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yu.cse.locker.domain.locker.dao.LockerRepository;
import yu.cse.locker.domain.locker.domain.Locker;
import yu.cse.locker.domain.locker.dto.LockerListResponseDto;
import yu.cse.locker.domain.locker.dto.LockerRequestDto;
import yu.cse.locker.domain.locker.dto.LockerResponseDto;
import yu.cse.locker.domain.user.application.UserService;
import yu.cse.locker.domain.user.domain.User;
import yu.cse.locker.global.exception.AlreadyExistLockerException;
import yu.cse.locker.global.exception.NotAuthenticationException;

@Service
@RequiredArgsConstructor
public class LockerService {

    private final LockerRepository lockerRepository;
    private final UserService userService;

    @Transactional
    public Locker reservationLocker(LockerRequestDto lockerRequestDto, UserDetails userDetails) {

        User user = userService.getCurrentUser(userDetails)
                .orElseThrow(() -> new NotAuthenticationException("유효한 토큰이 아닙니다. 로그인을 다시 진행 해주세요"));

        Locker currentUserLocker = getLockerByUser(user);

        if (currentUserLocker != null) {
            if (checkSameRequestLockerAndUserLocker(currentUserLocker, lockerRequestDto)) {
                lockerRepository.deleteById(currentUserLocker.getLockerId());
                return null;
            }
            if (findLockerByLockerInformation(lockerRequestDto).isPresent()) {
                throw new AlreadyExistLockerException("이미 사용중인 사물함 입니다.");
            }
            throw new AlreadyExistLockerException("이미 사용중인 사물함이 있습니다.");
        }

        Locker locker = Locker.builder()
                .user(user)
                .roomLocation(lockerRequestDto.getRoomLocation())
                .row(lockerRequestDto.getRow())
                .column(lockerRequestDto.getColumn())
                .build();

        return lockerRepository.save(locker);
    }

    @Transactional
    public void unReservationLocker(LockerRequestDto lockerRequestDto, UserDetails userDetails) {

    }



    public Locker getLockerByUser(User user) {
        return lockerRepository.findLockerByUser(user);
    }

    public Optional<Locker> getLockerByStudentId(String studentId) {
        return lockerRepository.findLockerByUser_StudentId(studentId);
    }

    public LockerListResponseDto lockerList(int locationRoom) {

        List<Locker> lockers = lockerRepository.findLockersByRoomLocation(locationRoom);

        List<LockerResponseDto> lockerResponseDtoList = lockers.stream()
                .map(locker -> new LockerResponseDto(locker.getRow(), locker.getColumn()))
                .toList();

        return LockerListResponseDto
                .builder()
                .maxRow(5)
                .maxColumn(5)
                .lockers(lockerResponseDtoList)
                .build();
    }

    public boolean checkSameRequestLockerAndUserLocker(Locker userLocker, LockerRequestDto currentLocker) {
        if (userLocker.getRoomLocation() == currentLocker.getRoomLocation()
                && userLocker.getColumn() == currentLocker.getColumn()
                && userLocker.getRow() == currentLocker.getRow()) {
            return true;
        }
        return false;
    }

    public Optional<Locker> findLockerByLockerInformation(LockerRequestDto lockerRequestDto) {
        return lockerRepository.findLockerByRoomLocationAndColumnAndRow(lockerRequestDto.getRoomLocation(),
                lockerRequestDto.getColumn(), lockerRequestDto.getRow());
    }

}
