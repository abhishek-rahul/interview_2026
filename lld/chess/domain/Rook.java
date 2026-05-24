package lld.chess.domain;

// import lld.chess.domain.*;
// import lld.chess.policy.*;
// import lld.chess.demo.*;
// import lld.chess.orchestrator.*;
import lld.chess.domain.enums.*;

public class Rook extends Piece {
    public Rook(Color color) {
        super(color, PieceType.ROOK);
    }

    @Override
    public boolean canMove(Board board, Position from, Position to) {
        if (hasOwnPieceAt(board, to)) return false;

        boolean straight = from.row() == to.row() || from.col() == to.col();
        return straight && board.isPathClear(from, to);
    }
}
