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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yu.cse.locker.domain.user.application.UserService;
import yu.cse.locker.domain.user.domain.User;
import yu.cse.locker.domain.user.dto.LoginRequestDto;
import yu.cse.locker.domain.user.dto.RegisterRequestDto;
import yu.cse.locker.domain.user.dto.TokenDto;
import yu.cse.locker.global.auth.TokenProvider;


@RestController()
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/signup")
    public ResponseEntity<User> singUp(@Valid @RequestBody RegisterRequestDto RegisterRequestDto) {
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

//    @PostMapping("/phone-validate")
//    public ResponseEntity<PhoneNumberDto> phoneValidate(@Valid @RequestBody PhoneNumberDto phoneNumberDto){
//        return null;
//    }

//    @PostMapping("/check-validate-number")
//    public ResponseEntity<PhoneValidateDto> validateNumberCheck(@Valid @RequestBody PhoneValidateDto phoneValidateDto) {
//        return null;
//    }

}
