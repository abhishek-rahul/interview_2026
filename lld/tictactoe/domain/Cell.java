package lld.tictactoe.domain;

import lld.tictactoe.domain.enums.Mark;

public class Cell {
    int row;
    int col;
    public Mark mark;

    Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.mark = Mark.EMPTY;
    }

    boolean isEmpty() {
        return mark == Mark.EMPTY;
    }

    public void placeMark(Mark mark) {
        if (!isEmpty()) {
            throw new IllegalStateException(
                "Cell already occupied at (" + row + "," + col + ")"
            );
        }
        this.mark = mark;
    }
}
