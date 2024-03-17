package yu.cse.locker.global.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import yu.cse.locker.global.ErrorResponse;


@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        String errorMsg = getExceptionMessage(authException);
        System.out.println(errorMsg);
        ErrorResponse errorResponse = ErrorResponse
                .builder()
                .responseMessage(errorMsg)
                .httpStatusCode(401)
                .build();
        String result = objectMapper.writeValueAsString(errorResponse);
        response.setStatus(401);
        response.getWriter().write(result);
    }

    public String getExceptionMessage(AuthenticationException ex) {

        if (ex instanceof BadCredentialsException) {
            return "학번 또는 비밀번호가 일치 하지 않습니다.";
        } else if (ex instanceof InsufficientAuthenticationException) {
            return "유효한 토큰이 아닙니다. 로그인을 다시 진행 해주세요";
        }
        return ex.getMessage();
    }
}
