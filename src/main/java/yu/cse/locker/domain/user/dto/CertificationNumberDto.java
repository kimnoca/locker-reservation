package yu.cse.locker.domain.user.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CertificationNumberDto {

    @NotNull
    private String phoneNumber;

    @NotNull
    @Size(min = 4, max = 4, message = "4글자 인증번호를 입력해주세요")
    private String certificationNumber;

}

