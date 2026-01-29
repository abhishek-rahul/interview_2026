package lld.bookmyshow.domain;

import java.util.Objects;

public class Seat {
    private final String id;   // e.g. A1
    private final String row;  // e.g. A
    private final int number;  // e.g. 1

    public Seat(String id, String row, int number) {
        this.id = Objects.requireNonNull(id);
        this.row = Objects.requireNonNull(row);
        this.number = number;
    }

    public String getId() { return id; }
    public String getRow() { return row; }
    public int getNumber() { return number; }
}
