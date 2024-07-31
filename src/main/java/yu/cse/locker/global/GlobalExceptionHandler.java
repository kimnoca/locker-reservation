package yu.cse.locker.global;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import yu.cse.locker.global.exception.AlreadyExistLockerException;
import yu.cse.locker.global.exception.AlreadyExistUserException;
import yu.cse.locker.global.exception.IsNotVerifyCertificationException;
import yu.cse.locker.global.exception.NotAuthenticationException;
import yu.cse.locker.global.exception.NotExistLockerException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AlreadyExistUserException.class)
    private ResponseEntity<?> alreadyExistUserExceptionHandler(AlreadyExistUserException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .httpStatusCode(409)
                .responseMessage(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(IsNotVerifyCertificationException.class)
    private ResponseEntity<?> isNotVerifyCertificationExceptionHandler(IsNotVerifyCertificationException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .httpStatusCode(400)
                .responseMessage(ex.getMessage()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(NotAuthenticationException.class)
    private ResponseEntity<?> notAuthenticationExceptionHandler(NotAuthenticationException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .httpStatusCode(401)
                .responseMessage(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(AlreadyExistLockerException.class)
    private ResponseEntity<?> alreadyExistLockerExceptionHandler(AlreadyExistLockerException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .httpStatusCode(409)
                .responseMessage(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<?> constraintViolationException(MethodArgumentNotValidException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .httpStatusCode(400)
                .responseMessage(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(NotExistLockerException.class)
    private ResponseEntity<?> noExistLockerExceptionHandler(NotExistLockerException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .httpStatusCode(400)
                .responseMessage(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}
