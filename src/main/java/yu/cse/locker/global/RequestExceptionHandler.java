package yu.cse.locker.global;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;


//@ControllerAdvice   // 전역 설정을 위한 annotaion
//@RestController
//public class RequestExceptionHandler {
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<String> ValidationException(MethodArgumentNotValidException exception) {
//        exception.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//        });
//        ObjectError objectError = exception.getBindingResult().getAllErrors().stream().findFirst().get();
//        String errorMsg = "field : " + ((FieldError) objectError).getField() + ", errorMessage" + objectError.getDefaultMessage();
//        return ResponseEntity.badRequest().body(errorMsg);
//    }
//}
