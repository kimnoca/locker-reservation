package yu.cse.locker.global.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IsNotVerifyCertificationException extends RuntimeException {
    private String message;
}
