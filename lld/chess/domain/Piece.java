package lld.chess.domain;

//import lld.chess.domain.*;
//import lld.chess.policy.*;
//import lld.chess.demo.*;
//import lld.chess.orchestrator.*;
import lld.chess.domain.enums.*;

public abstract class Piece {
    private final Color color;
    private final PieceType type;

    protected Piece(Color color, PieceType type) {
        this.color = color;
        this.type = type;
    }

    public Color color() {
        return color;
    }

    public PieceType type() {
        return type;
    }

    public abstract boolean canMove(Board board, Position from, Position to);

    protected boolean hasOwnPieceAt(Board board, Position to) {
        Piece target = board.getPiece(to);
        return target != null && target.color() == color;
    }

    public String symbol() {
        String prefix = color == Color.WHITE ? "W" : "B";

        switch (type) {
            case KING: return prefix + "K";
            case QUEEN: return prefix + "Q";
            case ROOK: return prefix + "R";
            case BISHOP: return prefix + "B";
            case KNIGHT: return prefix + "N";
            case PAWN: return prefix + "P";
            default: return "--";
        }
    }
}
