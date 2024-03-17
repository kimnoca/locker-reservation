package yu.cse.locker.domain.locker.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
// 추후 출력을 위한 DTO
public class LockerInfoResponseDto {
    private String studentName;
    private String departmentName;
    private String studentId;
    private int row;
    private int col;
}
