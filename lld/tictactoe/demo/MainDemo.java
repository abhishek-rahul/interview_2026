package lld.tictactoe.demo;
import lld.tictactoe.orchestrator.TicTacToeGame;


public class MainDemo {

    public static void main(String[] args) {

        TicTacToeGame game = new TicTacToeGame();
        game.startGame();

        System.out.println("Status: " + game.getGameStatus()); // IN_PROGRESS

        game.makeMove("P1", 0, 0);
        System.out.println("Status: " + game.getGameStatus());

        game.makeMove("P2", 1, 0);
        System.out.println("Status: " + game.getGameStatus());

        game.makeMove("P1", 0, 1);
        System.out.println("Status: " + game.getGameStatus());

        game.makeMove("P2", 1, 1);
        System.out.println("Status: " + game.getGameStatus());

        game.makeMove("P1", 0, 2); // P1 wins
        System.out.println("Status: " + game.getGameStatus()); // WON
        System.out.println("Winner: " + (game.getWinner() == null ? "none" : game.getWinner().id));

        // failure path: move after finish
        try {
            game.makeMove("P2", 2, 2);
        } catch (Exception e) {
            System.out.println("Expected error: " + e.getMessage());
        }
    }
}
