package yu.cse.locker.domain.user.api;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yu.cse.locker.domain.user.application.UserService;
import yu.cse.locker.domain.user.domain.User;
import yu.cse.locker.domain.user.dto.RegisterRequestDto;


@RestController()
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<User> singUp(@Valid @RequestBody RegisterRequestDto RegisterRequestDto) {
        return ResponseEntity.ok(userService.singUp(RegisterRequestDto));
    }

}
