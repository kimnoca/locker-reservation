package yu.cse.locker.domain.user.api;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yu.cse.locker.domain.user.application.UserService;
import yu.cse.locker.domain.user.domain.User;
import yu.cse.locker.domain.user.dto.CertificationNumberDto;
import yu.cse.locker.domain.user.dto.LoginRequestDto;
import yu.cse.locker.domain.user.dto.PhoneNumberDto;
import yu.cse.locker.domain.user.dto.RegisterRequestDto;
import yu.cse.locker.domain.user.dto.TokenDto;
import yu.cse.locker.global.auth.TokenProvider;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;


    @PostMapping("/signup")
    public ResponseEntity<User> singUp(@RequestBody @Valid RegisterRequestDto RegisterRequestDto) {
        return ResponseEntity.ok(userService.singUp(RegisterRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginRequestDto loginRequestDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequestDto.getStudentId(), loginRequestDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = tokenProvider.createToken(authentication);

        return new ResponseEntity<>(new TokenDto(accessToken), new HttpHeaders(), HttpStatus.OK);
    }

//    @GetMapping("/tokenTest")
//    public String tokenTest() {
//        return "token";
//    }

    // 추후에 객체를 return 하는 방향으로 리팩토링이 필요함 (응답 메시지로 표현이 필요해 보임)
    @PostMapping("/phone-certification")
    public ResponseEntity<String> phoneCertification(@Valid @RequestBody PhoneNumberDto phoneNumberDto) {
        return ResponseEntity.ok(userService.sendCertificationMessage(phoneNumberDto));
    }

    @PostMapping("/certification-check")
    public ResponseEntity<String> certificationNumberCheck(
            @RequestBody @Valid CertificationNumberDto certificationNumberDto) {
        return ResponseEntity.ok(userService.verifyCertificationMessage(certificationNumberDto));
    }
}
