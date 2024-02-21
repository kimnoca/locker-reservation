package yu.cse.locker.domain.user.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class LoginRequestDto {

    @NotNull
    @Size(min = 8, max = 8)
    private String studentId;

    @NotNull
    @Size(min = 3, max = 100)
    private String password;
}
