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
import yu.cse.locker.domain.user.domain.User;

@Service
@RequiredArgsConstructor
public class LockerService {

    private final LockerRepository lockerRepository;

    @Transactional
    public Locker reservationLocker(LockerRequestDto lockerRequestDto, User user) {

        System.out.println(user);

        Locker locker = Locker.builder()
                .user(user)
                .roomLocation(lockerRequestDto.getRoomLocation())
                .row(lockerRequestDto.getRow())
                .column(lockerRequestDto.getColumn())
                .build();

//        lockerRepository.findLockerByOwnerName(user.getUsername()); // 사용자가 에약한 사물함과 같은 사물함이면 취소
        // 예약한 사물함이 있는데 옮기는 경우?

        return lockerRepository.save(locker);
    }

    public Locker getLockerByUser(User user) {
        return lockerRepository.findLockerByUser(user);
    }

    public Optional<Locker> getLockerByStudentId(String studentId) {
        return lockerRepository.findLockerByUser_StudentId(studentId);
    }

    public LockerListResponseDto lockerList(int locationRoom) {

//        Optional<Locker> currentUserLocker = null;
//
//        if (!user.getUsername().equals("anonymousUser")){
//            currentUserLocker = lockerRepository.findLockerByOwnerName(user.getUsername());
//        }

        List<Locker> lockers = lockerRepository.findLockersByRoomLocation(locationRoom);

        List<LockerResponseDto> lockerResponseDtoList = lockers.stream()
                .map(locker -> new LockerResponseDto(locker.getRoomLocation(), locker.getRow(), locker.getColumn()))
                .toList();

        return LockerListResponseDto
                .builder()
                .maxRow(5)
                .maxColumn(5)
                .lockers(lockerResponseDtoList)
                .build();
    }

    public void updateLockerLocation(LockerRequestDto lockerRequestDto) {
        lockerRepository.updateLocker(lockerRequestDto.getRoomLocation(), lockerRequestDto.getColumn(),
                lockerRequestDto.getRow());
    }

//    public


    @Transactional
    public void unReservationLocker(Long id) {
        lockerRepository.deleteById(id);
    }

}
