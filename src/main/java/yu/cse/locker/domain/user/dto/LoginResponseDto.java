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

    public LoginResponseDto(int roomLocation, int row, int column) {
    }
//    private String refreshToken 추후에 필요하면 구현 예정
}
