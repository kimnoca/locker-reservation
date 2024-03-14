package yu.cse.locker.domain.locker.api;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import yu.cse.locker.domain.locker.domain.Locker;
import yu.cse.locker.domain.locker.application.LockerService;
import yu.cse.locker.domain.locker.dto.LockerRequestDto;
import yu.cse.locker.global.DefaultResponse;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/locker")
public class LockerController {

    private final LockerService lockerService;

    @PostMapping("/reservation")
    public ResponseEntity<?> reservationLocker(@RequestBody LockerRequestDto lockerRequestDto,
                                               @AuthenticationPrincipal User user) {

        System.out.println(user.getUsername());

        Locker locker = lockerService.reservationLocker(lockerRequestDto, user.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED).body(new DefaultResponse<>(201, "예약 성공", locker));
    }


}
