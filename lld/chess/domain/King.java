package lld.chess.domain;

//import lld.chess.domain.*;
//import lld.chess.policy.*;
//import lld.chess.demo.*;
//import lld.chess.orchestrator.*;
import lld.chess.domain.enums.*;

public class King extends Piece {
    public King(Color color) {
        super(color, PieceType.KING);
    }

    @Override
    public boolean canMove(Board board, Position from, Position to) {
        if (hasOwnPieceAt(board, to)) return false;

        return from.absRowDiff(to) <= 1 && from.absColDiff(to) <= 1;
    }
}
