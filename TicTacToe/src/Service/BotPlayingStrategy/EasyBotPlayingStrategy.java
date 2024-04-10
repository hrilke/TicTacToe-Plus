package Service.BotPlayingStrategy;

import Exceptions.GameOverException;
import Model.Board;
import Model.Cell;
import Model.ENUM.CellState;
import Model.Move;
import Model.Player;

import java.util.List;

public class EasyBotPlayingStrategy implements BotPlayingStrategy {


    @Override
    public Move makeMove(Board board, Player jarvis) {
        List<List<Cell>> matrix = board.getMatrix();

        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.size(); j++) {
                if(matrix.get(i).get(j).getCellState().equals(CellState.EMPTY)) {
                    board.getMatrix().get(i).get(j).setCellState(CellState.FILLED);
                    board.getMatrix().get(i).get(j).setPlayer(jarvis);
                    return new Move(board.getMatrix().get(i).get(j), jarvis);
                }
            }
        }
        throw new GameOverException("No target cell left to play");
    }
}
