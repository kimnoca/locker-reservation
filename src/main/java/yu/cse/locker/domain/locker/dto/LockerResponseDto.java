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
    private int location;
    private int row;
    private int col;

    public static LockerResponseDto of(Locker locker){
        return LockerResponseDto
                .builder()
                .col(locker.getColumn())
                .row(locker.getRow())
                .location(locker.getRoomLocation())
                .build();
    }
}
