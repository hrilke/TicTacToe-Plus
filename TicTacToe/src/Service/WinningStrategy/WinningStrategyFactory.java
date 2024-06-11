package Service.WinningStrategy;

public class WinningStrategyFactory {
    public static WinningStrategy getWinningStrategy(WinningStrategyName name, int dimension) {
        switch (name) {
            case ORDER_1_WINNINGSTRATEGY -> {
                return new OrderOneWinningStrategy(dimension);
            }
            case ORDER_N_WINNINGSTRATEGY -> {
                return new OrderNWinningStrategy();
            }
        }
        return null;
    }
}
