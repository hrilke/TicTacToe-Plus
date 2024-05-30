package Service.WinningStrategy;

import Model.Board;

import Model.Move;
import Model.Player;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderOneWinningStrategy implements WinningStrategy {

    private final int dimension;

    private final List<HashMap<Character,Integer>> rowHashMapList;

    private final List<HashMap<Character, Integer>> colHashMapList;

    private final HashMap<Character, Integer> leftDiagonalHashMap;

    private final HashMap<Character,Integer> rightDiagonalHashMap;

    private final HashMap<Character, Integer> cornersHashMap;

    public OrderOneWinningStrategy(int dimension) {
        this.dimension = dimension;
        rowHashMapList = new ArrayList<>();
        colHashMapList = new ArrayList<>();
        leftDiagonalHashMap = new HashMap<>();
        rightDiagonalHashMap = new HashMap<>();
        cornersHashMap = new HashMap<>();

        for (int i =0; i < dimension; i++) {
            rowHashMapList.add(new HashMap<>());
            colHashMapList.add(new HashMap<>());
        }
    }

    @Override
    public Player checkWinner(Board board, Move move) {
        Player player = move.getPlayer();
        char symbol = move.getPlayer().getSymbol();
        int row = move.getCell().getRow();
        int col = move.getCell().getCol();

        boolean WinnerResult = (isMoveOnLeftDiagonal(row,col) && checkHashMap( leftDiagonalHashMap,symbol))
                || (isMoveOnRightDiagonal(row,col) && checkHashMap( rightDiagonalHashMap,symbol))
                || (isMoveInCorners(row,col) && checkCorners(symbol))
                || checkHashMap(rowHashMapList.get(row),symbol)
                || checkHashMap(colHashMapList.get(col),symbol);

        if (WinnerResult) return player;
        return null;
    }
    private boolean checkHashMap(HashMap<Character,Integer> map, char symbol) {
        int count = map.compute(symbol, (key, value) -> (value == null) ? 1 : value + 1);
        return count == dimension;
    }

    private boolean checkCorners(char symbol) {
        int count = cornersHashMap.compute(symbol, (key,value) -> (value == null) ? 1 : value + 1);
        return count == 4;
    }

    private boolean isMoveInCorners(int row, int col) {
        return (row == 0 && col == 0)
            || (row == dimension-1 && col == 0)
            || (col == dimension-1 && row == 0)
            || (row == dimension-1 && col == dimension -1);
    }

    private boolean isMoveOnRightDiagonal(int row, int col) {
        return row + col == dimension - 1;
    }

    private boolean isMoveOnLeftDiagonal(int row, int col) {
        return row == col;
    }


}
