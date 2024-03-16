package yu.cse.locker.domain.locker.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LockerListResponseDto {
    private int maxRow;
    private int maxColumn;
    private LockerResponseDto myLocker;
    private List<LockerResponseDto> lockers;
}
