package Model;

import Exceptions.InvalidBotCountException;
import Exceptions.InvalidPlayerCountException;
import Exceptions.InvalidPlayerSymbolsException;
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
    private List<Board> boardsStates;
    private int numberOfSymbols;
    private GameStatus gameStatus;
    private WinningStrategy winningStrategy;


    private Game(Board currentBoard, List<Player> players, WinningStrategy winningStrategy {
        this.currentBoard = currentBoard;
        this.players = players;
        this.moves = new ArrayList<>();
        this.boardsStates = new ArrayList<>();
        this.numberOfSymbols = players.size();
        this.gameStatus = GameStatus.IN_PROGRESS;
        this.winningStrategy = winningStrategy;

    }

    public Builder builder() {
        return new Builder();
    }

    public Board getCurrentBoard() {
        return currentBoard;
    }

    public void setCurrentBoard(Board currentBoard) {
        this.currentBoard = currentBoard;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public WinningStrategy getWinningStrategy() {
        return winningStrategy;
    }

    public void setWinningStrategy(WinningStrategy winningStrategy) {
        this.winningStrategy = winningStrategy;
    }



    public static class Builder {

        private Board currentBoard;

        private List<Player> players;

        private WinningStrategy winningStrategy;





        public Builder setCurrentBoard(Board currentBoard) {
            this.currentBoard = currentBoard;
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
            return null;
        }

        private void validate() {
            validatePlayerCount();
            validateBotCount();
            validateSymbols();
        }

        private void validateSymbols() {
            HashSet<Character> symbols = new HashSet<>();
            for (int i = 0; i < players.size(); i++) {
                symbols.add(players.get(i).getSymbol());
            }
            if (symbols.size() != players.size()) {
                throw new InvalidPlayerSymbolsException("Symbol for each player has to be UNIQUE");
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
            if (players.size() < currentBoard.getSize()- 2 || players.size() >= currentBoard.getSize()) {
                throw new InvalidPlayerCountException("Player count should be n-1 || n-2 as per board size");
            }

        }

    }
}
