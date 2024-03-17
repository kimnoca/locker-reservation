package yu.cse.locker.domain.locker.dto;


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
public class MyLockerDto {
    int roomLocation;
    int col;
    int row;
}
