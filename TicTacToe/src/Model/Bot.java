package Model;

import Model.ENUM.BotDifficultyLevel;
import Model.ENUM.PlayerType;
import Service.BotPlayingStrategy.BotPlayingStrategyFactory;

public class Bot extends Player{

    BotDifficultyLevel level;

    public Bot() {
        super("JARVIS", '@', PlayerType.BOT, 0);
    }

    public void setLevel(BotDifficultyLevel level) {
        this.level = level;
    }

    @Override
    public Move makeMove(Board board) {
        return BotPlayingStrategyFactory.getBotPlayingStrategy(level).makeMove(board,this);
    }
}
