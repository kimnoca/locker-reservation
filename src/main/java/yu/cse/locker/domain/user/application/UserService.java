package yu.cse.locker.domain.user.application;

import java.util.Collections;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yu.cse.locker.domain.user.dao.UserRepository;
import yu.cse.locker.domain.user.domain.User;
import yu.cse.locker.domain.user.dto.RegisterRequestDto;


@Service
@RequiredArgsConstructor

public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    @Transactional
    public User singUp(RegisterRequestDto registerRequestDto) {

        User user = User.builder()
                .studentId(registerRequestDto.getStudentId())
                .password(passwordEncoder.encode(registerRequestDto.getPassword()))
                .studentName(registerRequestDto.getStudentName())
                .departmentName(registerRequestDto.getDepartmentName())
                .phoneNumber(registerRequestDto.getPhoneNumber())
                .authorities(Collections.singleton("ROLE_USER"))
                .build();

        return userRepository.save(user);
    }

    //


}
