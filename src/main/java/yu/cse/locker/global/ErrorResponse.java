package yu.cse.locker.global;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
public class ErrorResponse {

    private int httpStatusCode;
    private String responseMessage;
}
