package yu.cse.locker.domain.locker.api;


import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import yu.cse.locker.domain.locker.domain.Locker;
import yu.cse.locker.domain.locker.application.LockerService;
import yu.cse.locker.domain.locker.dto.LockerListResponseDto;
import yu.cse.locker.domain.locker.dto.LockerRequestDto;
import yu.cse.locker.domain.locker.dto.LockerResponseDto;
import yu.cse.locker.domain.user.application.UserService;
import yu.cse.locker.domain.user.domain.User;
import yu.cse.locker.global.DefaultResponse;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/locker")
public class LockerController {

    private final LockerService lockerService;
    private final UserService userService;

    @Transactional
    @PostMapping("/reservation")
    public ResponseEntity<?> reservationLocker(@RequestBody LockerRequestDto lockerRequestDto,
                                               @AuthenticationPrincipal UserDetails user) {

        // 내가 다른사람의 사물함을 예약 하려고 시도 하면?

        Optional<User> ownerUser = userService.getUser(user.getUsername());

        User currentUser = ownerUser.orElseThrow(() -> new IllegalStateException("User not found"));

        Locker userLocker = lockerService.getLockerByUser(currentUser);

        if (userLocker != null) {
            if (checkSameLocker(userLocker, lockerRequestDto)){
                lockerService.unReservationLocker(userLocker.getLockerId());
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new DefaultResponse<>(204, "예약 취소", lockerRequestDto));
            }
            lockerService.updateLockerLocation(lockerRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new DefaultResponse<>(201, "예약 성공", lockerRequestDto));
        }

        Locker locker = lockerService.reservationLocker(lockerRequestDto, currentUser);

        LockerResponseDto lockerResponseDto = LockerResponseDto
                .builder()
                .row(locker.getRow())
                .col(locker.getColumn())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(new DefaultResponse<>(201, "예약 성공", lockerResponseDto));
    }

    @GetMapping("/{location}")
    public ResponseEntity<?> lockersList(@PathVariable("location") int location) {

//        if (user == null){
//
//        }

        LockerListResponseDto lockerResponseDto = lockerService.lockerList(location);

        return ResponseEntity.status(HttpStatus.OK).body(new DefaultResponse<>(200, "조회 성공", lockerResponseDto));
    }

    public boolean checkSameLocker(Locker userLocker, LockerRequestDto currentLocker) {
        if (userLocker.getRoomLocation() == currentLocker.getRoomLocation()
                && userLocker.getColumn() == currentLocker.getColumn()
                && userLocker.getRow() == currentLocker.getRow()) {
            return true;
        }
        return false;
    }


}
