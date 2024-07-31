package yu.cse.locker.domain.locker.api;


import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import yu.cse.locker.domain.locker.application.LockerService;
import yu.cse.locker.domain.locker.domain.Locker;
import yu.cse.locker.domain.locker.dto.LockerListResponseDto;
import yu.cse.locker.domain.locker.dto.LockerRequestDto;
import yu.cse.locker.domain.locker.dto.MyLockerDto;
import yu.cse.locker.global.DefaultResponse;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/locker")
public class LockerController {

    private final LockerService lockerService;

    @Transactional
    @PostMapping("/reservation")
    public ResponseEntity<?> reservationLockerNew(@RequestBody LockerRequestDto lockerRequestDto,
                                                  @AuthenticationPrincipal User user) {

        Locker reservationLocker = lockerService.reservationLocker(lockerRequestDto, user.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED).body(new DefaultResponse<>(201, "예약 성공",
                new LockerRequestDto(reservationLocker.getRoomLocation(), reservationLocker.getRow(),
                        reservationLocker.getColumn())));
    }

    @Transactional
    @DeleteMapping("/reservation")
    public ResponseEntity<?> unReservationLocker(@AuthenticationPrincipal User user) {

        lockerService.deleteLocker(user.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(new DefaultResponse<>(200, "예약 취소", null));
    }


    @GetMapping("/{location}")
    public ResponseEntity<?> lockersList(@PathVariable("location") int location,
                                         @AuthenticationPrincipal UserDetails userDetails) {
        LockerListResponseDto lockerListResponseDto = lockerService.lockerList(location);

        if (userDetails == null) {
            lockerListResponseDto.setMyLocker(null);
        } else {
            Optional<Locker> myLockerOptional = lockerService.getLockerByStudentId(userDetails.getUsername());
            if (myLockerOptional.isPresent()) {
                Locker myLocker = myLockerOptional.get();
                lockerListResponseDto.setMyLocker(
                        new MyLockerDto(myLocker.getRoomLocation(), myLocker.getColumn(), myLocker.getRow()));
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(new DefaultResponse<>(200, "조회 성공", lockerListResponseDto));
    }

//    @GetMapping("/admin/{location}")
//    public ResponseEntity<?> adminLockerList(@PathVariable("location") int location) {
//
//    }


}
