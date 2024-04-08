package Model;

import Model.ENUM.BotDifficultyLevel;

public class Bot extends Player{

    BotDifficultyLevel level;

    public Bot(BotDifficultyLevel level) {
        this.level = level;
    }

    @Override
    public Move makeMove(Board board) {
        return super.makeMove(board);
    }
}
