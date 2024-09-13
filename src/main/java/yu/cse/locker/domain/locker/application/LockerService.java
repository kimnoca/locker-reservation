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
import yu.cse.locker.domain.locker.dto.MyLockerDto;
import yu.cse.locker.domain.user.application.UserService;
import yu.cse.locker.domain.user.domain.User;
import yu.cse.locker.global.exception.AlreadyExistLockerException;
import yu.cse.locker.global.exception.NotAuthenticationException;
import yu.cse.locker.global.exception.NotExistLockerException;
import yu.cse.locker.global.utils.RoomLocation;

@Service
@RequiredArgsConstructor
public class LockerService {

	private static final int MAX_ROW_SIZE = 5;

	private final LockerRepository lockerRepository;
	private final UserService userService;

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

	public LockerListResponseDto lockerListByRoomLocation(int roomLocation, UserDetails userDetails) {

		List<Locker> lockers = lockerRepository.findLockersByRoomLocation(roomLocation);

		List<LockerResponseDto> lockerResponseDtoList = lockers.stream()
			.map(LockerResponseDto::from)
			.toList();

		MyLockerDto myLockerDto = null;

		if (userDetails != null) {
			Locker locker = lockerRepository.findLockerByUser_StudentId(userDetails.getUsername()).orElse(null);
			myLockerDto = MyLockerDto.from(locker);
		}

		return LockerListResponseDto.builder()
			.maxRow(MAX_ROW_SIZE)
			.myLocker(myLockerDto)
			.maxColumn(RoomLocation.getMaxColumnFromLocationCode(roomLocation))
			.lockers(lockerResponseDtoList)
			.build();
	}

	public Optional<Locker> findLockerByLockerInformation(LockerRequestDto lockerRequestDto) {
		return lockerRepository.findLockerByRoomLocationAndColumnAndRow(lockerRequestDto.getRoomLocation(),
			lockerRequestDto.getColumn(), lockerRequestDto.getRow());
	}
}