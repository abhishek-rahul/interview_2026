package lld.chess.policy;

import lld.chess.domain.*;
//import lld.chess.policy.*;
//import lld.chess.demo.*;
//import lld.chess.orchestrator.*;
import lld.chess.domain.enums.*;

public class MoveValidator {
    public String validate(Board board, Move move, Color currentTurn) {
        if (move == null) return "Move cannot be null";

        Position from = move.from();
        Position to = move.to();

        if (!board.isInside(from) || !board.isInside(to)) {
            return "Move outside board";
        }

        if (from.equals(to)) {
            return "Source and target are same";
        }

        Piece piece = board.getPiece(from);

        if (piece == null) {
            return "No piece at source";
        }

        if (piece.color() != currentTurn) {
            return "Wrong turn. Current turn is " + currentTurn;
        }

        Piece target = board.getPiece(to);

        if (target != null && target.color() == piece.color()) {
            return "Cannot capture own piece";
        }

        if (!piece.canMove(board, from, to)) {
            return piece.type() + " cannot move like this";
        }

        return null;
    }
}
