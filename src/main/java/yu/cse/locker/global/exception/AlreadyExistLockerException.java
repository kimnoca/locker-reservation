package yu.cse.locker.global.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AlreadyExistLockerException extends RuntimeException {
    private String message;
}
