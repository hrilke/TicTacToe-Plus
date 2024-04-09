package Service.WinningStrategy;

import Model.Board;
import Model.Move;
import Model.Player;

public interface WinningStrategy {
    public Player checkWinner(Board board, Move move);

}
