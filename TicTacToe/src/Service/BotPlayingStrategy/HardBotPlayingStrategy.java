package Service.BotPlayingStrategy;
import Model.Board;
import Model.Cell;

import Model.ENUM.CellState;
import Model.Move;
import Model.Player;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class HardBotPlayingStrategy implements BotPlayingStrategy{
    private static int matrixSize;

    private static List<HashMap<Character,Integer>> rowHashMapList;

    private static List<HashMap<Character, Integer>> colHashMapList;

    private static HashMap<Character, Integer> leftDiagonalHashMap;

    private static HashMap<Character,Integer> rightDiagonalHashMap;

    private static   HashMap<Character, Integer> cornersHashMap;


    public static void initializeHashMaps(int dimension) {
        matrixSize = dimension;
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
    public Move makeMove(Board board, Player jarvis) {
        List<List<Cell>> matrix = board.getMatrix();

        if (checkFirstMove(matrix)) {
            return getFirstMove(jarvis,matrix.size());
        }


        HardBotPlayingStrategy.initializeHashMaps(matrix.size());
        return null;
    }
    public boolean checkFirstMove(List<List<Cell>> matrix) {
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.size(); j++) {
                if (matrix.get(i).get(i).getCellState().equals(CellState.FILLED)) return false;
            }
        }
        return true;
    }

    public Move getFirstMove(Player jarvis, int size) {
        ArrayList<Move> moves = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if ((i == j || i+j == size-1) && !(i == j && i+j == size-1)) {
                    Cell cell = new Cell(i, j);
                    moves.add(new Move(cell, jarvis));
                }
            }
        }
        Collections.shuffle(moves);
        return moves.get(0);
    }
}
