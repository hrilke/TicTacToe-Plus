import Controller.GameController;
import Model.*;
import Model.ENUM.BotDifficultyLevel;
import Model.ENUM.GameStatus;
import Model.ENUM.PlayerType;
import Service.WinningStrategy.WinningStrategyName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GameController gameController = new GameController();
        List<Player> players = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to TicTacToe-PLUS");
        System.out.println("Please enter the BOARD SIZE");
        int dimension = sc.nextInt();
        int noOfPlayers = dimension - 1;
        System.out.println(" Do you want Bot int the game Y or N");
        String botAns = sc.next();

        if (botAns.equalsIgnoreCase("Y")){
            System.out.printf("Select and Type Number for Bot Difficulty" +
                                "%n1 : EASY" +
                                "%n2 : MEDIUM" +
                                "%n3 : HARD");
            System.out.println();
            int level = sc.nextInt();
            Bot bot = new Bot();
            switch (level) {
                case 1 -> bot.setLevel(BotDifficultyLevel.EASY);
                case 2 -> bot.setLevel(BotDifficultyLevel.MEDIUM);
                case 3 -> bot.setLevel(BotDifficultyLevel.HARD);
            }
            players.add(bot);
            noOfPlayers--;
        }
        while(noOfPlayers > 0) {
            System.out.println("Enter player name");
            String playerName = sc.next();
            System.out.println("Enter player Symbol");
            char Symbol =sc.next().charAt(0);
            Player newPlayer = new Player(playerName, Symbol, PlayerType.HUMAN, noOfPlayers);
            players.add(newPlayer);
            noOfPlayers--;
        }
        Collections.shuffle(players);
        System.out.printf("Select and Type Number for Winning Strategy Algorithm" +
                "%n1 : O(1) Time Complexity" +
                "%n2 : O(N) Time Complexity");
        int winStrategy = sc.nextInt();
        Game game;
        if (winStrategy == 1) game = gameController.createGame(dimension,players, WinningStrategyName.ORDER_1_WINNINGSTRATEGY);
        else game = gameController.createGame(dimension,players, WinningStrategyName.ORDER_N_WINNINGSTRATEGY);

        int playerIndex = -1;
        boolean boardDisplay = true;
        while(game.getGameStatus().equals(GameStatus.IN_PROGRESS)) {
            if (boardDisplay) {
                System.out.println("Current Board Status");
                gameController.displayBoard(game);
                boardDisplay = false;
            }
            playerIndex++;
            playerIndex = playerIndex % players.size();
            Move movePlayed = new Move();
            System.out.println("Your Turn "+players.get(playerIndex).getName());
            if (players.get(playerIndex).getPlayerType().equals(PlayerType.BOT)) {
                movePlayed = gameController.executeMove(game, players.get(playerIndex));
                game.getCurrentBoard().setLastMove(movePlayed);
                game.getMoves().add(movePlayed);
            }

            if (players.get(playerIndex).getPlayerType().equals(PlayerType.HUMAN)) {
                movePlayed = gameController.executeMove(game, players.get(playerIndex));
                System.out.println("Board after move");
                gameController.displayBoard(game);
                System.out.printf("Select whether you want to UNDO or CONTINUE" +
                        "%n1 : UNDO" +
                        "%n2 : CONTINUE");
                System.out.println();
                int x = sc.nextInt();
                if (x == 1) {
                    gameController.undoMove(game, movePlayed);
                    playerIndex--;
                }
                game.getCurrentBoard().setLastMove(movePlayed);
                game.getMoves().add(movePlayed);
            }
            System.out.println("Board after move");
            gameController.displayBoard(game);
            gameController.saveBoard(game);
            Player winner = gameController.checkWinner(game, movePlayed);
            if (winner != null) {
                System.out.println("Winner is :" + winner.getName());
                System.out.println("final board ");
                gameController.displayBoard(game);
                System.out.printf("Would you like to replay the game ?" +
                        "%n1 : WATCH REPLAY" +
                        "%n2 : CONTINUE");
                System.out.println();
                int replay = sc.nextInt();
                String name = winner.getName();
                if (replay == 1) gameController.replayGame(game, name);
                break;
            } if (winner == null && game.checkIsEmpty()) {
                System.out.println("ITS A TIE");
                System.out.println("final board ");
                gameController.displayBoard(game);
                System.out.printf("Would you like to replay the game ?" +
                        "%n1 : WATCH REPLAY" +
                        "%n2 : CONTINUE");
                System.out.println();
                int replay = sc.nextInt();
                String tie = "NOBODY Its a TIE";
                if (replay == 1) gameController.replayGame(game, tie);
                break;
            }
        }
    }
}