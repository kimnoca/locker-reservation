package yu.cse.locker.global;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Builder

public class DefaultResponse<T> {

    private int httpStatusCode;
    private String responseMessage;
    private T data;
}
