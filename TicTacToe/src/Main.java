import Controller.GameController;
import Model.Bot;
import Model.ENUM.BotDifficultyLevel;
import Model.ENUM.GameStatus;
import Model.ENUM.PlayerType;
import Model.Game;
import Model.Move;
import Model.Player;
import Service.BotPlayingStrategy.EasyBotPlayingStrategy;
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
        System.out.println("Please enter the dimension. ");
        int dimension = sc.nextInt();
        int noOfPlayers = dimension - 1;
        System.out.println(" Do you want Bot int the game Y or N");
        String botAns = sc.next();
        if (botAns.equalsIgnoreCase("Y")){
            Player bot = new Bot(BotDifficultyLevel.EASY);
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
        Game game = gameController.createGame(dimension,players, WinningStrategyName.ORDER_1_WINNINGSTRATEGY);
        int playerIndex = -1;
        while(game.getGameStatus().equals(GameStatus.IN_PROGRESS)) {
            System.out.println("Current Board Status");
            gameController.displayBoard(game);
            playerIndex++;
            playerIndex = playerIndex % players.size();
            System.out.println("Your Turn "+players.get(playerIndex).getName());
            Move movePlayed = gameController.executeMove(game,players.get(playerIndex));
            Player winner = gameController.checkWinner(game, movePlayed);
            if (winner != null) {
                System.out.println("Winner is :" + winner.getName());
                break;
            }
        }
        System.out.println("final board ");
        gameController.displayBoard(game);
    }
}