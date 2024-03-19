package yu.cse.locker.domain.locker.application;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
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
import yu.cse.locker.global.exception.NotExistLockerException;
import yu.cse.locker.global.utils.RoomLocation;

@Service
@RequiredArgsConstructor
public class LockerService {

    private final LockerRepository lockerRepository;
    private final UserService userService;
    private final int MAX_COLUMN_SIZE = 5;

    @Transactional
    public Locker reservationLocker(LockerRequestDto lockerRequestDto, String currentUser) {

        User user = userService.getCurrentUser(currentUser)
                .orElseThrow(() -> new NotAuthenticationException("유효한 토큰이 아닙니다. 로그인을 다시 진행 해주세요"));

        if (lockerRepository.findLockerByUser_StudentId(currentUser).isPresent()) {
            throw new AlreadyExistLockerException("이미 사용중인 사물함이 있습니다.");
        }

        if (findLockerByLockerInformation(lockerRequestDto).isPresent()) {
            throw new AlreadyExistLockerException("이미 사용중인 사물함 입니다.");
        }

        Locker locker = Locker.builder()
                .user(user)
                .roomLocation(lockerRequestDto.getRoomLocation())
                .row(lockerRequestDto.getRow())
                .column(lockerRequestDto.getColumn())
                .build();

        return lockerRepository.save(locker);
    }

    public Optional<Locker> getLockerByStudentId(String studentId) {
        return lockerRepository.findLockerByUser_StudentId(studentId);
    }

    @Transactional
    public void deleteLocker(String studentId) {

        lockerRepository.findLockerByUser_StudentId(studentId)
                .orElseThrow(() -> new NotExistLockerException("취소할 사물함이 없습니다."));

        lockerRepository.deleteLockerByUser_StudentId(studentId);
    }

    public LockerListResponseDto lockerList(int locationRoom) {

        List<Locker> lockers = lockerRepository.findLockersByRoomLocation(locationRoom);

        List<LockerResponseDto> lockerResponseDtoList = lockers.stream()
                .map(locker -> new LockerResponseDto(locker.getRow(), locker.getColumn()))
                .toList();

        int maxRow = RoomLocation.getMaxRow(locationRoom);

        if (maxRow == -1) {
            throw new NotExistLockerException("잘못된 요청 입니다.");
        }

        return LockerListResponseDto
                .builder()
                .maxRow(maxRow)
                .maxColumn(MAX_COLUMN_SIZE)
                .lockers(lockerResponseDtoList)
                .build();
    }

    public Optional<Locker> findLockerByLockerInformation(LockerRequestDto lockerRequestDto) {
        return lockerRepository.findLockerByRoomLocationAndColumnAndRow(lockerRequestDto.getRoomLocation(),
                lockerRequestDto.getColumn(), lockerRequestDto.getRow());
    }

}
