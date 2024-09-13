package yu.cse.locker.domain.locker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yu.cse.locker.domain.locker.domain.Locker;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyLockerDto {
	private int roomLocation;
	private int col;
	private int row;

	public static MyLockerDto from(Locker locker) {
		if (locker == null) {
			return null;
		}
		return new MyLockerDto(locker.getRoomLocation(), locker.getColumn(), locker.getRow());
	}
}
