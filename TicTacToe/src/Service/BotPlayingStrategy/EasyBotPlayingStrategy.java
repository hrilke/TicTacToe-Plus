package Service.BotPlayingStrategy;

import Exceptions.GameOverException;
import Model.Board;
import Model.Cell;
import Model.ENUM.CellState;
import Model.Move;
import Model.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EasyBotPlayingStrategy implements BotPlayingStrategy {

    @Override
    public Move makeMove(Board board, Player jarvis) {
        int size = board.getSize();
        List<List<Cell>> matrix = board.getMatrix();
        ArrayList<Cell> cells = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (matrix.get(i).get(j).getCellState().equals(CellState.EMPTY)) {
                    cells.add(new Cell(i,j));
                }
            }
        }
        Collections.shuffle(cells);
        if (cells.size() > 0) {
            Cell select = cells.get(0);
            board.getMatrix().get(select.getRow()).get(select.getCol()).setCellState(CellState.FILLED);
            board.getMatrix().get(select.getRow()).get(select.getCol()).setPlayer(jarvis);
            return new Move(board.getMatrix().get(select.getRow()).get(select.getCol()), jarvis);
        }
        else throw new GameOverException("No target cell left to play");
    }
}
