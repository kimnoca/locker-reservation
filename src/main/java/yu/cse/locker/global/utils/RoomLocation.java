package yu.cse.locker.global.utils;


public enum RoomLocation {
    LOCATION_115(115, 10),
    LOCATION_116(116, 26),
    LOCATION_117(117, 10),
    LOCATION_123(123, 8),
    LOCATION_124(124, 12),
    LOCATION_220(220, 22),
    LOCATION_221(221, 12),
    LOCATION_3220(3220, 8), // 332 앞
    LOCATION_3221(3221, 4), // 332 뒤
    LOCATION_323(323, 14);

    private final int locationCode;
    private final int maxRow;

    RoomLocation(int locationCode, int maxRow) {
        this.locationCode = locationCode;
        this.maxRow = maxRow;
    }

    public static int getMaxRow(int locationCode) {
        for (RoomLocation location : RoomLocation.values()) {
            if (location.locationCode == locationCode) {
                return location.maxRow;
            }
        }
        return -1;
    }
}
