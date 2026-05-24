package lld.chess.domain;

//import lld.chess.domain.*;
//import lld.chess.policy.*;
//import lld.chess.demo.*;
//import lld.chess.orchestrator.*;
//import lld.chess.domain.enums.*;

import java.util.Objects;

public class Position {
    private final int row;
    private final int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int row() {
        return row;
    }

    public int col() {
        return col;
    }

    public int rowDiff(Position to) {
        return to.row - this.row;
    }

    public int colDiff(Position to) {
        return to.col - this.col;
    }

    public int absRowDiff(Position to) {
        return Math.abs(rowDiff(to));
    }

    public int absColDiff(Position to) {
        return Math.abs(colDiff(to));
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Position)) return false;
        Position other = (Position) o;
        return row == other.row && col == other.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public String toString() {
        return "(" + row + "," + col + ")";
    }
}
