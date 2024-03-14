package yu.cse.locker.domain.locker.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class LockerRequestDto {
    private int roomLocation;
    private int row;
    private int column;
}
