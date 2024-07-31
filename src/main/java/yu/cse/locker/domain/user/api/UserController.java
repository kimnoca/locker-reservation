package yu.cse.locker.domain.user.api;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yu.cse.locker.domain.user.application.UserService;
import yu.cse.locker.domain.user.dto.CertificationNumberDto;
import yu.cse.locker.domain.user.dto.LoginRequestDto;
import yu.cse.locker.domain.user.dto.PhoneNumberDto;
import yu.cse.locker.domain.user.dto.RegisterRequestDto;
import yu.cse.locker.global.DefaultResponse;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/signup")
    public ResponseEntity<?> singUp(@RequestBody @Valid RegisterRequestDto RegisterRequestDto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(new DefaultResponse<>(201, "회원가입 성공",
                userService.singUp(RegisterRequestDto)));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(new DefaultResponse<>(200, "로그인 성공", userService.login(loginRequestDto)));
    }

    // TODO : 전역적인 응답 메시지, exception handling 메시지 구현 필요함

    // 추후에 객체를 return 하는 방향으로 리팩토링이 필요함 (응답 메시지로 표현이 필요해 보임)
    @PostMapping("/certification")
    public ResponseEntity<?> phoneCertification(@Valid @RequestBody PhoneNumberDto phoneNumberDto) {
        userService.sendCertificationMessage(phoneNumberDto);
        return ResponseEntity.status(HttpStatus.OK).body(new DefaultResponse<>(200, "전송 성공", null));
    }

    @PostMapping("/certification-check")
    public ResponseEntity<?> certificationNumberCheck(
            @RequestBody @Valid CertificationNumberDto certificationNumberDto) {
        userService.verifyCertificationMessage(certificationNumberDto);
        return ResponseEntity.status(HttpStatus.OK).body(new DefaultResponse<>(200, "인증 성공", null));
    }
}

