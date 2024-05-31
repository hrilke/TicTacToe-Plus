package Controller;
import Model.Board;
import Model.ENUM.CellState;
import Model.ENUM.GameStatus;
import Model.Game;
import Model.Move;
import Model.Player;
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

    public void replayGame(Game game) {

    }

}
