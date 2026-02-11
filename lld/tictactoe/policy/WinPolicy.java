package lld.tictactoe.policy;

import lld.tictactoe.domain.Board;
import lld.tictactoe.domain.enums.Mark;

public interface WinPolicy {
    boolean hasWon(Board board, Mark mark);
}
