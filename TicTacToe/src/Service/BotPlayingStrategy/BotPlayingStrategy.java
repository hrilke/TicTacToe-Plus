package Service.BotPlayingStrategy;

import Model.Board;
import Model.Move;
import Model.Player;

public interface BotPlayingStrategy {
    Move makeMove(Board board, Player player);
}
