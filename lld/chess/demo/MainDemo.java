package lld.chess.demo;

import lld.chess.domain.*;
//import lld.chess.policy.*;
//import lld.chess.demo.*;
import lld.chess.orchestrator.*;
import lld.chess.domain.enums.*;

public class MainDemo {
    public static void main(String[] args) {
        Player white = new Player("Rahul", Color.WHITE);
        Player black = new Player("Amit", Color.BLACK);

        ChessGame game = new ChessGame(white, black);
        game.start();
        game.printBoard();

        game.makeMove(new Position(6, 4), new Position(4, 4)); // White pawn
        game.printBoard();

        game.makeMove(new Position(6, 3), new Position(4, 3)); // Wrong turn
        game.printBoard();

        game.makeMove(new Position(1, 4), new Position(3, 4)); // Black pawn
        game.printBoard();

        game.makeMove(new Position(7, 0), new Position(5, 0)); // Rook blocked
        game.printBoard();

        game.makeMove(new Position(7, 6), new Position(5, 5)); // White knight
        game.printBoard();

        game.makeMove(new Position(0, 1), new Position(2, 2)); // Black knight
        game.printBoard();

        game.makeMove(new Position(7, 5), new Position(4, 2)); // White bishop
        game.printBoard();
    }
}
