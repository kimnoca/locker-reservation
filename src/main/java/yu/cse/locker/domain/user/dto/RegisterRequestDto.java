package yu.cse.locker.domain.user.dto;


import jakarta.validation.constraints.NotBlank;
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
public class RegisterRequestDto {

    @NotNull
    @NotBlank
    @Size(min = 8, max = 50)
    private String studentId;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 100)
    private String password;

    @NotNull
    @NotBlank
    @Size(min = 11, max = 100)
    private String phoneNumber;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 100)
    private String studentName;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 100)
    private String departmentName;
}
