package yu.cse.locker.domain.user.application;

import java.util.Collections;
import java.util.Optional;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import yu.cse.locker.domain.user.dao.MessageRepository;
import yu.cse.locker.domain.user.dao.UserRepository;
import yu.cse.locker.domain.user.domain.User;
import yu.cse.locker.domain.user.dto.CertificationNumberDto;
import yu.cse.locker.domain.user.dto.LoginRequestDto;
import yu.cse.locker.domain.user.dto.LoginResponseDto;
import yu.cse.locker.domain.user.dto.PhoneNumberDto;
import yu.cse.locker.domain.user.dto.RegisterRequestDto;
import yu.cse.locker.domain.user.dto.RegisterResponseDto;
import yu.cse.locker.global.auth.TokenProvider;
import yu.cse.locker.global.exception.AlreadyExistUserException;
import yu.cse.locker.global.exception.IsNotVerifyCertificationException;


@Service
@RequiredArgsConstructor
public class UserService {

    private static final String SERVICE_NAME = "[영남대학교 컴퓨터학부 사물함 예약 시스템]";
    private static final int CERTIFICATION_NUMBER_SIZE = 4;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MessageRepository messageRepository;
    private final DefaultMessageService messageService;
    private final CustomUserDetailsService customUserDetailsService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public RegisterResponseDto singUp(RegisterRequestDto registerRequestDto) {

        userRepository.findByStudentId(registerRequestDto.getStudentId()).ifPresent(user -> {
            throw new AlreadyExistUserException("이미 존재하는 유저입니다.");
        });

        User user = User.builder()
                .studentId(registerRequestDto.getStudentId())
                .password(passwordEncoder.encode(registerRequestDto.getPassword()))
                .studentName(registerRequestDto.getStudentName())
                .departmentName(registerRequestDto.getDepartmentName())
                .phoneNumber(registerRequestDto.getPhoneNumber())
                .authorities(Collections.singleton("ROLE_USER"))
                .build();

        userRepository.save(user);

        return RegisterResponseDto.fromEntity(user);
    }

    
    // TODO : setAuthentication(authentication) -> jwt filter 에서 설정하도록 변경 필요함
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequestDto.getStudentId(), loginRequestDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = tokenProvider.createToken(authentication);

        User loginUser = (User) customUserDetailsService.loadUserByUsername(authentication.getName());

        return LoginResponseDto.of(loginUser.getStudentName(), accessToken);
    }

    public void sendCertificationMessage(PhoneNumberDto phoneNumberDto) {
        if (messageRepository.checkHasPhoneNumber(phoneNumberDto.getPhoneNumber())) { // 이미 있으면 삭제후 다시 전송
            messageRepository.deleteMassageCertification(phoneNumberDto.getPhoneNumber());
        }

        Message message = new Message();

        message.setFrom("01024419667");
        message.setTo(phoneNumberDto.getPhoneNumber());
        String certificationNumber = createCertificationNumber();
        message.setText(SERVICE_NAME + "\n인증번호는 " + certificationNumber + "입니다.");

        SingleMessageSentResponse response = messageService.sendOne(new SingleMessageSendingRequest(message));

        messageRepository.createMassageCertification(phoneNumberDto.getPhoneNumber(), certificationNumber);
    }

    public void verifyCertificationMessage(CertificationNumberDto certificationNumberDto) {
        if (isVerify(certificationNumberDto)) {
            throw new IsNotVerifyCertificationException("인증 실패");
        }
        messageRepository.deleteMassageCertification(certificationNumberDto.getPhoneNumber());
    }

    private boolean isVerify(CertificationNumberDto certificationNumberDto) {
        return !(messageRepository.checkHasPhoneNumber(certificationNumberDto.getPhoneNumber()) &&
                messageRepository.getCertificationCode(certificationNumberDto.getPhoneNumber())
                        .equals(certificationNumberDto.getCertificationNumber()));
    }

    public String createCertificationNumber() {
        StringBuilder certificationNumber = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < CERTIFICATION_NUMBER_SIZE; i++) {
            certificationNumber.append(random.nextInt(10));
        }
        return certificationNumber.toString();
    }

    public Optional<User> getCurrentUser(String username) {
        return userRepository.findByStudentId(username);
    }
}
