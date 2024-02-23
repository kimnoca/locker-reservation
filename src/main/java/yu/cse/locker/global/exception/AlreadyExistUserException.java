package yu.cse.locker.global.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class AlreadyExistUserException extends RuntimeException {
    private String message;
}
