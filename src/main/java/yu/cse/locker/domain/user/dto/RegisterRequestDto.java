package yu.cse.locker.domain.user.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Size(min = 3, max = 50)
    private String studentId;

    @NotNull
    @Size(min = 3, max = 100)
    private String password;

    @NotNull
    @Size(min = 3, max = 100)
    private String phoneNumber;

    @NotNull
    @Size(min = 3, max = 100)
    private String studentName;

    @NotNull
    @Size(min = 3, max = 100)
    private String departmentName;
}
