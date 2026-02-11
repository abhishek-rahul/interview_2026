package lld.tictactoe.orchestrator;

import lld.tictactoe.domain.Board;
import lld.tictactoe.domain.Player;
import lld.tictactoe.domain.enums.GameStatus;
import lld.tictactoe.domain.enums.Mark;
import lld.tictactoe.policy.WinPolicy;
import lld.tictactoe.policy.impl.StandardWinPolicy;


public class TicTacToeGame {

    private Board board;
    private Player p1;
    private Player p2;
    private Player currentPlayer;

    private WinPolicy winPolicy;

    private boolean finished;
    private GameStatus status;
    private Player winner;

    public TicTacToeGame() {
        // policy wiring
        this.winPolicy = new StandardWinPolicy();
    }

    public void startGame() {
        this.board = new Board(3);

        this.p1 = new Player("P1", Mark.X);
        this.p2 = new Player("P2", Mark.O);
        this.currentPlayer = p1;

        this.finished = false;
        this.status = GameStatus.IN_PROGRESS;
        this.winner = null;

        System.out.println("Game started");
        System.out.println("Turn: " + currentPlayer.id + " (" + currentPlayer.mark + ")");
    }

    public void makeMove(String playerId, int row, int col) {
        if (finished)
            throw new IllegalStateException("Game already finished");

        if (!currentPlayer.id.equals(playerId))
            throw new IllegalStateException("Not " + playerId + "'s turn");

        board.placeMark(row, col, currentPlayer.mark);
    	board.display(); // 👈 visual state

        if (winPolicy.hasWon(board, currentPlayer.mark)) {
            finished = true;
            status = GameStatus.WON;
            winner = currentPlayer;
            System.out.println("Winner is " + winner.id);
            return;
        }

        if (board.isFull()) {
            finished = true;
            status = GameStatus.DRAW;
            System.out.println("Game Draw");
            return;
        }

        currentPlayer = (currentPlayer == p1) ? p2 : p1;
        System.out.println("Turn: " + currentPlayer.id + " (" + currentPlayer.mark + ")");
    }

    // ---- READ APIs ----
    public GameStatus getGameStatus() {
        return status;
    }

    public Player getWinner() {
        return winner;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}

