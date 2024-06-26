package Model;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private int size;
    private final List<List<Cell>> matrix;
    private Move lastMove;

    public Move getLastMove() {
        return lastMove;
    }

    public void setLastMove(Move lastMove) {
        this.lastMove = lastMove;
    }

    public Board(int size) {
        this.size = size;
        matrix = new ArrayList<>();

        for (int i = 0 ; i < size; i++) {

            matrix.add(new ArrayList<>());

            for (int j = 0; j < size; j++) {
                matrix.get(i).add(new Cell(i,j));
            }
        }
    }
    public Board(List<List<Cell>> matrix) {
        this.matrix = new ArrayList<>();
        for (int i = 0; i < matrix.size(); i++) {
            this.matrix.add(new ArrayList<>());
            for (int j = 0; j < matrix.size(); j++) {
                this.matrix.get(i).add(matrix.get(i).get(j));
            }
        }
    }

    public void displayBoard() {
        for (int i = 0; i < size; i++) {
            List<Cell> cells = matrix.get(i);

            for (Cell cell : cells){
                cell.displayCell();
            }
            System.out.println();
        }
    }

    public int getSize() {
        return size;
    }

    public List<List<Cell>> getMatrix() {
        return matrix;
    }


    public Board deepCopy(Board board) {
        Board newBoard = new Board(board.getSize());
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                newBoard.getMatrix().get(i).set(j,board.getMatrix().get(i).get(j).deepCopy(board.getMatrix().get(i).get(j)));
            }
        }
        return newBoard;
    }
}
