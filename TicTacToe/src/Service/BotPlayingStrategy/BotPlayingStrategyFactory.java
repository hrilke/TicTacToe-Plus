package Service.BotPlayingStrategy;

import Model.Board;

public class BotPlayingStrategyFactory {
    public static BotPlayingStrategy getBotPlayingStrategy() {
        return new EasyBotPlayingStrategy();
    }
}
