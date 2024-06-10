package Controller;
import Model.*;
import Model.ENUM.CellState;
import Model.ENUM.GameStatus;
import Service.WinningStrategy.WinningStrategyFactory;
import Service.WinningStrategy.WinningStrategyName;

import java.util.List;

public class GameController {
    public Game createGame(int dimension, List<Player> players, WinningStrategyName name) {
        return Game.builder()
                .setDimension(dimension)
                .setPlayers(players)
                .setWinningStrategy(WinningStrategyFactory.getWinningStrategy(name, dimension))
                .build();
    }

    public void displayBoard(Game game) {
        game.getCurrentBoard().displayBoard();
    }

    public GameStatus getGameStatus(Game game) {
        return game.getGameStatus();
    }

    public Move executeMove(Game game, Player player) {
        return player.makeMove(game.getCurrentBoard());
    }

    public Player checkWinner(Game game, Move lastPlayedMove) {
        return game.getWinningStrategy().checkWinner(game.getCurrentBoard(), lastPlayedMove);
    }

    public void undoMove(Game game, Move played) {
        int i = played.getCell().getRow();
        int j = played.getCell().getCol();

        game.getCurrentBoard().getMatrix().get(i).get(j).setCellState(CellState.EMPTY);
        game.getCurrentBoard().getMatrix().get(i).get(j).setPlayer(null);
    }

    public void replayGame(Game game, String winner){

        for (int i = 0; i < game.getBoardsStates().size(); i++) {
            System.out.println("Move No:" + (i+1));
            List<List<Cell>> matrix = game.getBoardsStates().get(i).getMatrix();
            for (int j = 0; j < matrix.size(); j++) {
                for (int k = 0; k < matrix.size(); k++) {
                    matrix.get(j).get(k).displayCell();
                }
                System.out.println();
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }System.out.println("Winner : "+ winner);
    }

    public void saveBoard(Game game) {
        Board newBoard = game.getCurrentBoard().deepCopy(game.getCurrentBoard());
        game.getBoardsStates().add(newBoard);
    }
}
