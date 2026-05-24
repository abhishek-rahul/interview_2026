package lld.chess.domain;

//import lld.chess.domain.*;
//import lld.chess.policy.*;
//import lld.chess.demo.*;
//import lld.chess.orchestrator.*;
import lld.chess.domain.enums.*;

public class Pawn extends Piece {
    public Pawn(Color color) {
        super(color, PieceType.PAWN);
    }

    @Override
    public boolean canMove(Board board, Position from, Position to) {
        if (hasOwnPieceAt(board, to)) return false;

        int rowDiff = from.rowDiff(to);
        int colDiff = from.colDiff(to);

        int direction = color() == Color.WHITE ? -1 : 1;
        int startRow = color() == Color.WHITE ? 6 : 1;

        Piece target = board.getPiece(to);

        if (colDiff == 0 && rowDiff == direction && target == null) {
            return true;
        }

        if (from.row() == startRow && colDiff == 0 && rowDiff == 2 * direction && target == null) {
            Position middle = new Position(from.row() + direction, from.col());
            return board.getPiece(middle) == null;
        }

        return Math.abs(colDiff) == 1 &&
               rowDiff == direction &&
               target != null &&
               target.color() != color();
    }
}
