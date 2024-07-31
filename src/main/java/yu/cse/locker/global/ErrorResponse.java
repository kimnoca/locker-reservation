package yu.cse.locker.global;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ErrorResponse {

    private int httpStatusCode;
    private String responseMessage;
}
