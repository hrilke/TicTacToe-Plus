package Model;

import Exceptions.InvalidBotCountException;
import Exceptions.InvalidPlayerSymbolsException;
import Model.ENUM.GameStatus;
import Model.ENUM.PlayerType;

import java.util.HashSet;
import java.util.List;

public class Game {
    private Board currentBoard;

    private List<Player> players;

    private List<Move> moves;

    private List<Board> boards;

    private Player winner;

    private GameStatus gameStatus;

    public static class builder {

        private Board currentBoard;

        private List<Player> players;

        private List<Move> moves;

        private List<Board> boards;

        private Player winner;

        private GameStatus gameStatus;

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
                if(player.getPlayerType() == PlayerType.BOT) bot++;
            }

            if (bot < 0 || bot > 1) {
                throw new InvalidBotCountException("Bot count is Invalid");
            }
        }

        private void validatePlayerCount() {
            // if bot present valid player count will be n-2
            // if bot not present, valid player count will be n-1
            if (players.size() < currentBoard.getSize()- 1 || players.size() > currentBoard.getSize()- 2) {
                //INCOMPLETE
            }

        }
    }
}
