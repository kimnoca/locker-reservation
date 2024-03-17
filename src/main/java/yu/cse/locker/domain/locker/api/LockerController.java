package yu.cse.locker.domain.locker.api;


import java.security.Security;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
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
import yu.cse.locker.global.exception.AlreadyExistLockerException;
import yu.cse.locker.global.exception.NotAuthenticationException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/locker")
public class LockerController {

    private final LockerService lockerService;

    @Transactional
    @PostMapping("/reservation")
    public ResponseEntity<?> reservationLockerNew(@RequestBody LockerRequestDto lockerRequestDto,
                                                  @AuthenticationPrincipal UserDetails user) {

        if (user == null) {
            throw new NotAuthenticationException("유효한 토큰이 아닙니다. 로그인을 다시 진행 해주세요");
        }

        Locker reservationLocker = lockerService.reservationLocker(lockerRequestDto, user);

        if (reservationLocker == null) {
            return ResponseEntity.status(HttpStatus.OK).body(new DefaultResponse<>(200, "예약 취소", null));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new DefaultResponse<>(201, "예약 성공",
                new LockerRequestDto(reservationLocker.getRoomLocation(), reservationLocker.getRow(),
                        reservationLocker.getColumn())));
    }


    @GetMapping("/{location}")
    public ResponseEntity<?> lockersList(@PathVariable("location") int location,
                                         @AuthenticationPrincipal UserDetails userDetails) {
        LockerListResponseDto lockerListResponseDto = lockerService.lockerList(location);

        if (userDetails == null) {
            lockerListResponseDto.setMyLocker(null);
        } else {
            Optional<Locker> myLockerOptional = lockerService.getLockerByStudentId(userDetails.getUsername(), location);
            if (myLockerOptional.isPresent()) {
                Locker myLocker = myLockerOptional.get();
                System.out.println(myLocker);
                lockerListResponseDto.setMyLocker(
                        new LockerResponseDto(myLocker.getRow(), myLocker.getColumn()));
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(new DefaultResponse<>(200, "조회 성공", lockerListResponseDto));
    }


}
