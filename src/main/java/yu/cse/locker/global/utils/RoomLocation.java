package yu.cse.locker.global.utils;


import java.util.Arrays;
import lombok.Getter;
import yu.cse.locker.global.exception.NotExistLockerException;


@Getter
public enum RoomLocation {
    LOCATION_115(115, 20),
    LOCATION_116(116, 16),
    LOCATION_117(117, 10),
    LOCATION_123(123, 8),
    LOCATION_124(124, 12),
    LOCATION_220(220, 22),
    LOCATION_221(221, 12),
    LOCATION_3220(3220, 8), // 332 앞
    LOCATION_3221(3221, 4), // 332 뒤
    LOCATION_323(323, 14);

    private final int locationCode;
    private final int maxColumn;

    RoomLocation(int locationCode, int maxColumn) {
        this.locationCode = locationCode;
        this.maxColumn = maxColumn;
    }

    public static int getMaxColumnFromLocationCode(int locationCode) {
        return Arrays.stream(RoomLocation.values())
                .filter(room -> room.locationCode == locationCode)
                .findFirst().orElseThrow(() -> new NotExistLockerException("잘못된 요청 입니다."))
                .getMaxColumn();
    }
}


