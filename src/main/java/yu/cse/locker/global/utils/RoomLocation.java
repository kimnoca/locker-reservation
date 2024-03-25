package yu.cse.locker.global.utils;


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

    public static int getMaxColumn(int locationCode) {
        for (RoomLocation location : RoomLocation.values()) {
            if (location.locationCode == locationCode) {
                return location.maxColumn;
            }
        }
        return -1;
    }
}


