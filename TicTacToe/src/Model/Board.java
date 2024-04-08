package Model;

import java.util.List;

public class Board {
    private int size;

    private List<List<Cell>> matrix;

    public void displayBoard(Board board) {
        for (int i = 0 ; i < board.size; i++) {
            for (int j = 0; j < board.size; j++) {
                //INCOMPLETE
            }
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<List<Cell>> getCells() {
        return matrix;
    }

    public void setCells(List<List<Cell>> cells) {
        this.matrix = cells;
    }
}
