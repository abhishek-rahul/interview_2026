package lld.tictactoe.policy.impl;


import lld.tictactoe.domain.Board;
import lld.tictactoe.domain.Cell;
import lld.tictactoe.domain.enums.Mark;
import lld.tictactoe.policy.WinPolicy;


public class StandardWinPolicy implements WinPolicy {

    public StandardWinPolicy() {
    }

    @Override
    public boolean hasWon(Board board, Mark mark) {
        Cell[][] c = board.getCells();
        int n = board.getSize();

        // rows
        for (int i = 0; i < n; i++) {
            boolean ok = true;
            for (int j = 0; j < n; j++) {
                if (c[i][j].mark != mark) {
                    ok = false;
                    break;
                }
            }
            if (ok) return true;
        }

        // columns
        for (int j = 0; j < n; j++) {
            boolean ok = true;
            for (int i = 0; i < n; i++) {
                if (c[i][j].mark != mark) {
                    ok = false;
                    break;
                }
            }
            if (ok) return true;
        }

        // main diagonal
        boolean okDiag = true;
        for (int i = 0; i < n; i++) {
            if (c[i][i].mark != mark) {
                okDiag = false;
                break;
            }
        }
        if (okDiag) return true;

        // anti-diagonal
        boolean okAnti = true;
        for (int i = 0; i < n; i++) {
            if (c[i][n - 1 - i].mark != mark) {
                okAnti = false;
                break;
            }
        }
        return okAnti;
    }
}
