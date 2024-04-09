package Model;

import Model.ENUM.BotDifficultyLevel;
import Model.ENUM.PlayerType;

public class Bot extends Player{

    BotDifficultyLevel level;

    public Bot(BotDifficultyLevel level) {
        super("JARVIS", '@', PlayerType.BOT, 0 );
        this.level = level;
    }

    @Override
    public Move makeMove(Board board) {
        return super.makeMove(board);
    }
}
