package yu.cse.locker.domain.user.api;


import jakarta.validation.Valid;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;
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
    private final DefaultMessageService messageService;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private static final String SERVICE_NAME = "[영남대학교 컴퓨터학부 사물함 예약 시스템]";
    private static final int CERTIFICATION_NUMBER_SIZE = 4;

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

    @PostMapping("/phone-validate")
    public ResponseEntity<PhoneNumberDto> phoneValidate(@Valid @RequestBody PhoneNumberDto phoneNumberDto) {

        Message message = new Message();

        System.out.println(phoneNumberDto.getPhoneNumber());

        message.setFrom("01024419667");
        message.setTo(phoneNumberDto.getPhoneNumber());

        message.setText(SERVICE_NAME + "\n인증번호는 " + createCertificationNumber() + "입니다.");

        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        System.out.println(response);

        return null;
    }

    @PostMapping("/check-validate-number")
    public ResponseEntity<CertificationNumberDto> validateNumberCheck(@Valid @RequestBody CertificationNumberDto phoneValidateDto) {
        return null;
    }

    public String createCertificationNumber() {

        StringBuilder certificationNumber = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < CERTIFICATION_NUMBER_SIZE; i++) {
            certificationNumber.append(random.nextInt(10));
        }

        return certificationNumber.toString();
    }


}
