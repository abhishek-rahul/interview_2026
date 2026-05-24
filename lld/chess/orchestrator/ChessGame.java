package lld.chess.orchestrator;

import lld.chess.domain.*;
import lld.chess.policy.*;
//import lld.chess.demo.*;
import lld.chess.domain.enums.*;



public class ChessGame {
    private final Player whitePlayer;
    private final Player blackPlayer;
    private final Board board;
    private final MoveValidator moveValidator;
    private final CheckDetector checkDetector;

    private Color currentTurn;
    private GameStatus status;

    public ChessGame(Player whitePlayer, Player blackPlayer) {
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.board = new Board();
        this.moveValidator = new MoveValidator();
        this.checkDetector = new CheckDetector();
        this.currentTurn = Color.WHITE;
        this.status = GameStatus.NOT_STARTED;
    }

    public void start() {
        board.setup();
        currentTurn = Color.WHITE;
        status = GameStatus.IN_PROGRESS;

        System.out.println("Chess started");
        System.out.println("White: " + whitePlayer.name());
        System.out.println("Black: " + blackPlayer.name());
        System.out.println("Current turn: " + currentTurn);
    }

    public boolean makeMove(Position from, Position to) {
        if (status == GameStatus.NOT_STARTED) {
            System.out.println("Game not started");
            return false;
        }

        Move move = new Move(from, to);
        System.out.println("Trying move: " + currentTurn + " " + from + " -> " + to);

        String error = moveValidator.validate(board, move, currentTurn);

        if (error != null) {
            System.out.println("Rejected: " + error);
            return false;
        }

        Piece movingPiece = board.getPiece(from);
        Piece capturedPiece = board.getPiece(to);

        board.movePiece(from, to);

        if (capturedPiece != null) {
            System.out.println(movingPiece.symbol() + " captured " + capturedPiece.symbol());
        } else {
            System.out.println(movingPiece.symbol() + " moved");
        }

        Color opponent = currentTurn.opposite();

        if (checkDetector.isInCheck(board, opponent)) {
            status = GameStatus.CHECK;
            System.out.println(opponent + " king is in CHECK");
        } else {
            status = GameStatus.IN_PROGRESS;
        }

        currentTurn = opponent;
        System.out.println("Next turn: " + currentTurn);

        return true;
    }

    public void printBoard() {
        board.print();
    }
}
