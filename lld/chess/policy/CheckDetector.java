package lld.chess.policy;

import lld.chess.domain.*;
//import lld.chess.policy.*;
//import lld.chess.demo.*;
//import lld.chess.orchestrator.*;
import lld.chess.domain.enums.*;

public class CheckDetector {
    public boolean isInCheck(Board board, Color kingColor) {
        Position kingPosition = board.findKing(kingColor);

        if (kingPosition == null) {
            return true;
        }

        Color opponent = kingColor.opposite();

        for (Position from : board.getPiecePositions(opponent)) {
            Piece attacker = board.getPiece(from);

            if (attacker != null && attacker.canMove(board, from, kingPosition)) {
                return true;
            }
        }

        return false;
    }
}
