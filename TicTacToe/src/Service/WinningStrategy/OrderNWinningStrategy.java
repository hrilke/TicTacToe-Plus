package Service.WinningStrategy;

import Model.Board;
import Model.Cell;
import Model.Move;
import Model.Player;

import java.util.List;

public class OrderNWinningStrategy implements WinningStrategy{
    @Override
    public Player checkWinner(Board board, Move move) {
        int row = move.getCell().getRow();
        int col = move.getCell().getCol();
        List<List<Cell>> matrix = board.getMatrix();
        int n = matrix.size();


        if (row == col && checkLeftDiagonal(board,move)
         || row + col == n-1 && checkRightDiagonal(board, move)
         || checkCorners(board, move)
         || checkRow(board,move)
         || checkCol(board,move)
        ) {
           return move.getPlayer();
        }
        return null;
    }
    private boolean checkCorners(Board board, Move move){
        List<List<Cell>> matrix = board.getMatrix();
        char symbol = move.getPlayer().getSymbol();
        int n = board.getSize();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((i == j || i + j == n-1) && !(i == j && i + j == n-1)) {
                    if ( board.getMatrix().get(i).get(j).getPlayer() == null
                            || board.getMatrix().get(i).get(j).getPlayer().getSymbol() != (symbol)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean checkLeftDiagonal(Board board, Move move) {
        int n = board.getSize();
        char symbol = move.getPlayer().getSymbol();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    if ( board.getMatrix().get(i).get(j).getPlayer() == null
                      || board.getMatrix().get(i).get(j).getPlayer().getSymbol() != (symbol)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    private boolean checkRightDiagonal(Board board, Move move) {
        int n = board.getSize();
        char symbol = move.getPlayer().getSymbol();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i + j == n-1) {
                    if (board.getMatrix().get(i).get(j).getPlayer() == null
                     || board.getMatrix().get(i).get(j).getPlayer().getSymbol() != (symbol)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean checkRow(Board board, Move move) {
        int n = board.getSize();
        int row = move.getCell().getRow();
        char symbol = move.getPlayer().getSymbol();
        for (int i = 0; i < n; i++) {
            if(board.getMatrix().get(row).get(i).getPlayer() == null
             || symbol != board.getMatrix().get(row).get(i).getPlayer().getSymbol()) {
                return false;
            }
        }
        return true;
    }
    private boolean checkCol(Board board, Move move) {
        int n = board.getSize();
        int col = move.getCell().getCol();
        char symbol = move.getPlayer().getSymbol();
        for (int i = 0; i < n; i++) {
            if(board.getMatrix().get(i).get(col).getPlayer() == null
             || symbol != board.getMatrix().get(i).get(col).getPlayer().getSymbol()) {
                return false;
            }
        }
        return true;
    }
}
