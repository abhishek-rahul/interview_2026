package lld.tictactoe.domain;

import lld.tictactoe.domain.enums.Mark;


public class Board {
    private final int size;
    private final Cell[][] cells;

    public Board(int size) {
        this.size = size;
        this.cells = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
    }

    public void display() {
        System.out.println();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Mark m = cells[i][j].mark;
                char ch = (m == Mark.EMPTY) ? '-' : m.name().charAt(0);
                System.out.print(" " + ch + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void placeMark(int row, int col, Mark mark) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            throw new IllegalArgumentException(
                "Invalid board position (" + row + "," + col + ")"
            );
        }
        cells[row][col].placeMark(mark);
    }

    public boolean isFull() {
        for (Cell[] row : cells) {
            for (Cell c : row) {
                if (c.isEmpty()) return false;
            }
        }
        return true;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public int getSize() {
        return size;
    }
}
