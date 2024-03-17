package yu.cse.locker.domain.locker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yu.cse.locker.domain.locker.domain.Locker;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LockerResponseDto {
    private int row;
    private int col;
}
