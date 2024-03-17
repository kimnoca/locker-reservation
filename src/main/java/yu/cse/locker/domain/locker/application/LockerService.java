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
import yu.cse.locker.global.exception.AlreadyExistLockerException;

@Service
@RequiredArgsConstructor
public class LockerService {

    private final LockerRepository lockerRepository;

    @Transactional
    public Locker reservationLocker(LockerRequestDto lockerRequestDto, User user) {

        Locker locker = Locker.builder()
                .user(user)
                .roomLocation(lockerRequestDto.getRoomLocation())
                .row(lockerRequestDto.getRow())
                .column(lockerRequestDto.getColumn())
                .build();

        return lockerRepository.save(locker);
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

    public void updateLockerLocation(LockerRequestDto lockerRequestDto) {
        lockerRepository.updateLocker(lockerRequestDto.getRoomLocation(), lockerRequestDto.getColumn(),
                lockerRequestDto.getRow());
    }

    public boolean checkDuplicationLocker(LockerRequestDto lockerRequestDto) {
        Optional<Locker> locker = lockerRepository.findLockerByRoomLocationAndColumnAndRow(
                lockerRequestDto.getRoomLocation(), lockerRequestDto.getColumn(), lockerRequestDto.getRow());
        return locker.isPresent();
    }


    @Transactional
    public void unReservationLocker(Long id) {
        lockerRepository.deleteById(id);
    }

}
