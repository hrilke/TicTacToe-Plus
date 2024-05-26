package Service.BotPlayingStrategy;

import Model.ENUM.BotDifficultyLevel;

public class BotPlayingStrategyFactory {
    public static BotPlayingStrategy getBotPlayingStrategy(BotDifficultyLevel level) {
        switch (level) {
            case EASY -> {
                return new EasyBotPlayingStrategy();
            }
            case MEDIUM -> {
                return new MediumBotPlayingStrategy();
            }
            case HARD -> {
                return new HardBotPlayingStrategy();
            }
        }
        return null;
    }
}
