package yu.cse.locker.domain.user.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yu.cse.locker.domain.user.domain.User;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponseDto {
    private String studentId;
    private String studentName;

    public static RegisterResponseDto fromEntity(User user) {
        return RegisterResponseDto.builder()
                .studentId(user.getStudentId())
                .studentName(user.getStudentName())
                .build();
    }
}
