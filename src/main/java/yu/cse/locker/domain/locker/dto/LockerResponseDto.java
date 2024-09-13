package yu.cse.locker.domain.locker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import yu.cse.locker.domain.locker.domain.Locker;

@Getter
@Builder
@AllArgsConstructor
public final class LockerResponseDto {
	private final int row;
	private final int col;

	public static LockerResponseDto from(Locker locker) {
		return new LockerResponseDto(locker.getRow(), locker.getColumn());
	}
}
