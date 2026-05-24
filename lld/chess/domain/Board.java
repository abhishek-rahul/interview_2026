package lld.chess.domain;

//import lld.chess.domain.*;
//import lld.chess.policy.*;
//import lld.chess.demo.*;
//import lld.chess.orchestrator.*;
import lld.chess.domain.enums.*;
import java.util.*;

public class Board {
    private static final int SIZE = 8;
    private final Piece[][] pieces = new Piece[SIZE][SIZE];

    public void setup() {
        clear();

        pieces[0][0] = new Rook(Color.BLACK);
        pieces[0][1] = new Knight(Color.BLACK);
        pieces[0][2] = new Bishop(Color.BLACK);
        pieces[0][3] = new Queen(Color.BLACK);
        pieces[0][4] = new King(Color.BLACK);
        pieces[0][5] = new Bishop(Color.BLACK);
        pieces[0][6] = new Knight(Color.BLACK);
        pieces[0][7] = new Rook(Color.BLACK);

        for (int col = 0; col < SIZE; col++) {
            pieces[1][col] = new Pawn(Color.BLACK);
        }

        pieces[7][0] = new Rook(Color.WHITE);
        pieces[7][1] = new Knight(Color.WHITE);
        pieces[7][2] = new Bishop(Color.WHITE);
        pieces[7][3] = new Queen(Color.WHITE);
        pieces[7][4] = new King(Color.WHITE);
        pieces[7][5] = new Bishop(Color.WHITE);
        pieces[7][6] = new Knight(Color.WHITE);
        pieces[7][7] = new Rook(Color.WHITE);

        for (int col = 0; col < SIZE; col++) {
            pieces[6][col] = new Pawn(Color.WHITE);
        }
    }

    public void clear() {
        for (int row = 0; row < SIZE; row++) {
            Arrays.fill(pieces[row], null);
        }
    }

    public boolean isInside(Position p) {
        return p != null &&
               p.row() >= 0 &&
               p.row() < SIZE &&
               p.col() >= 0 &&
               p.col() < SIZE;
    }

    public Piece getPiece(Position p) {
        if (!isInside(p)) return null;
        return pieces[p.row()][p.col()];
    }

    public void movePiece(Position from, Position to) {
        pieces[to.row()][to.col()] = pieces[from.row()][from.col()];
        pieces[from.row()][from.col()] = null;
    }

    public boolean isPathClear(Position from, Position to) {
        int rowStep = Integer.compare(to.row(), from.row());
        int colStep = Integer.compare(to.col(), from.col());

        int row = from.row() + rowStep;
        int col = from.col() + colStep;

        while (row != to.row() || col != to.col()) {
            if (pieces[row][col] != null) {
                return false;
            }

            row += rowStep;
            col += colStep;
        }

        return true;
    }

    public Position findKing(Color color) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Piece piece = pieces[row][col];

                if (piece != null &&
                    piece.color() == color &&
                    piece.type() == PieceType.KING) {
                    return new Position(row, col);
                }
            }
        }

        return null;
    }

    public List<Position> getPiecePositions(Color color) {
        List<Position> result = new ArrayList<>();

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Piece piece = pieces[row][col];

                if (piece != null && piece.color() == color) {
                    result.add(new Position(row, col));
                }
            }
        }

        return result;
    }

    public void print() {
        System.out.println();
        System.out.println("    0   1   2   3   4   5   6   7");

        for (int row = 0; row < SIZE; row++) {
            System.out.print(row + "   ");

            for (int col = 0; col < SIZE; col++) {
                Piece piece = pieces[row][col];
                System.out.print((piece == null ? "--" : piece.symbol()) + "  ");
            }

            System.out.println();
        }

        System.out.println();
    }
}
