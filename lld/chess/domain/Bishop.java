package lld.chess.domain;

//import lld.chess.domain.*;
//import lld.chess.policy.*;
//import lld.chess.demo.*;
//import lld.chess.orchestrator.*;
import lld.chess.domain.enums.*;

public class Bishop extends Piece {
    public Bishop(Color color) {
        super(color, PieceType.BISHOP);
    }

    @Override
    public boolean canMove(Board board, Position from, Position to) {
        if (hasOwnPieceAt(board, to)) return false;

        boolean diagonal = from.absRowDiff(to) == from.absColDiff(to);
        return diagonal && board.isPathClear(from, to);
    }
}
