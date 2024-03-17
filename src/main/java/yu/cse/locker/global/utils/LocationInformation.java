package yu.cse.locker.global.utils;


public enum LocationInformation {

    ROOM117A(5, 5);

    private final int column;
    private final int row;


    LocationInformation(int column, int row) {
        this.column = column;
        this.row = row;
    }
}
