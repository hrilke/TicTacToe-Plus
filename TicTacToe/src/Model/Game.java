package Model;

import Exceptions.InvalidBoardSizeException;
import Exceptions.InvalidBotCountException;
import Exceptions.InvalidPlayerCountException;
import Exceptions.InvalidPlayerSymbolsException;
import Model.ENUM.CellState;
import Model.ENUM.GameStatus;
import Model.ENUM.PlayerType;
import Service.WinningStrategy.WinningStrategy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Game {
    private Board currentBoard;
    private List<Player> players;
    private List<Move> moves;
    private final List<Board> boardsStates;
    private GameStatus gameStatus;
    private WinningStrategy winningStrategy;

    private Game(Board currentBoard, List<Player> players, WinningStrategy winningStrategy) {
        this.currentBoard = currentBoard;
        this.players = players;
        this.moves = new ArrayList<>();
        this.boardsStates = new ArrayList<>();
        this.gameStatus = GameStatus.IN_PROGRESS;
        this.winningStrategy = winningStrategy;

    }

    public static Builder builder() {
        return new Builder();
    }

    public Board getCurrentBoard() {
        return currentBoard;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public List<Board> getBoardsStates() {
        return boardsStates;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public WinningStrategy getWinningStrategy() {
        return winningStrategy;
    }

    public boolean checkIsEmpty() {
        List<List<Cell>> matrix = this.getCurrentBoard().getMatrix();
        for (int i =0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.size(); j++) {
                if (matrix.get(i).get(j).getCellState().equals(CellState.EMPTY)) return false;
            }
        }
        return true;
    }

    public static class Builder {
        private int dimension;
        private Board currentBoard;
        private List<Player> players;
        private WinningStrategy winningStrategy;


        public Builder setDimension(int dimension){
            this.dimension = dimension;
            return this;
        }
        public Builder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }
        public Builder setWinningStrategy(WinningStrategy winningStrategy) {
            this.winningStrategy = winningStrategy;
            return this;
        }


        public Game build() {
            validate();
            return new Game(new Board(dimension), players, winningStrategy);
        }

        private void validate() {
            validatePlayerCount();
            validateBotCount();
            validateSymbols();
            validateDimension();
        }
        private void validateDimension() {
            if (dimension < 3 || dimension > 10 ){
                throw new InvalidBoardSizeException("Dimension for board is not valid");
            }
        }

        private void validateSymbols() {
            HashSet<Character> symbols = new HashSet<>();
            for (int i = 0; i < players.size(); i++) {
                symbols.add(players.get(i).getSymbol());
            }
            if (symbols.size() != players.size()) {
                throw new InvalidPlayerSymbolsException("This symbol is already taken by other player");
            }
        }

        private void validateBotCount() {
            int bot = 0;
            for (Player player : players) {
                if(player.getPlayerType().equals(PlayerType.BOT)) bot++;
            }

            if (bot < 0 || bot > 1) {
                throw new InvalidBotCountException("Bot count is Invalid");
            }
        }

        private void validatePlayerCount() {
            // if bot present valid player count will be n-2
            // if bot not present, valid player count will be n-1
            if (players.size() < dimension- 2 || players.size() >= dimension) {
                throw new InvalidPlayerCountException("Player count should be n-1 || n-2 as per board size");
            }

        }

    }
}
