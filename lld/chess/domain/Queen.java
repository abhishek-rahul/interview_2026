package lld.chess.domain;

//import lld.chess.domain.*;
//import lld.chess.policy.*;
//import lld.chess.demo.*;
//import lld.chess.orchestrator.*;
import lld.chess.domain.enums.*;
public class Queen extends Piece {
    public Queen(Color color) {
        super(color, PieceType.QUEEN);
    }

    @Override
    public boolean canMove(Board board, Position from, Position to) {
        if (hasOwnPieceAt(board, to)) return false;

        boolean straight = from.row() == to.row() || from.col() == to.col();
        boolean diagonal = from.absRowDiff(to) == from.absColDiff(to);

        return (straight || diagonal) && board.isPathClear(from, to);
    }
}
