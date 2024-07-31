package yu.cse.locker.domain.user.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
    // token, studentName
    private String studentName;
    private String accessToken;


    public static LoginResponseDto of(String studentName, String accessToken) {
        return LoginResponseDto.builder()
                .studentName(studentName)
                .accessToken(accessToken)
                .build();
    }
}
