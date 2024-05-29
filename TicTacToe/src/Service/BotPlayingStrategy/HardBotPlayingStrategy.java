package Service.BotPlayingStrategy;
import Exceptions.GameOverException;
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

    private static  HashMap<Character, Integer> cornersHashMap;

    private static Cell possibleWinner;

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
    private static void updateCheckHashMap(List<List<Cell>> matrix,Move lastMove) {
        int row = lastMove.getCell().getRow();
        int col = lastMove.getCell().getCol();
        char symbol = lastMove.getPlayer().getSymbol();

        while (possibleWinner != null) {
            if (isMoveInCorners(row, col)) {
                int count = cornersHashMap.compute(symbol, (key, value) -> (value == null) ? 1 : value + 1);
                if (count == 3) {
                    cornerMove(matrix);
                    break;
                }
            } else if (isMoveOnRightDiagonal(row, col)) {
                int count = rightDiagonalHashMap.compute(symbol, (key, value) -> (value == null) ? 1 : value + 1);
                if (count == matrixSize - 1) {
                    rightDiagonalMove(matrix);
                    break;
                }
            } else if (isMoveOnLeftDiagonal(row, col)) {
                int count = leftDiagonalHashMap.compute(symbol, (key, value) -> (value == null) ? 1 : value + 1);
                if (count == matrixSize - 1) {
                    leftDiagonalMove(matrix);
                    break;
                }
            } else {
                HashMap<Character,Integer> rowMap = rowHashMapList.get(row);
                int rowCount = rowMap.compute(symbol, (key, value) -> (value == null) ? 1 : value + 1);
                if (rowCount == matrixSize - 1) {
                    checkRowHashMap(row,matrix);
                }
                HashMap<Character,Integer> colMap = colHashMapList.get(row);
                int colCount = rowMap.compute(symbol, (key, value) -> (value == null) ? 1 : value + 1);
                if (colCount == matrixSize - 1) {
                    checkColHashMap(col,matrix);
                }
            }
        }
    }
    private static void checkRowHashMap(int row, List<List<Cell>> matrix) {
        for (int j = 0; j < matrixSize; j++) {
            if (matrix.get(row).get(j).getCellState().equals(CellState.EMPTY)) {
                possibleWinner = new Cell(row, j);
                break;
            }
        }
    }
    private static void checkColHashMap(int col, List<List<Cell>> matrix) {
        for (int i = 0 ; i < matrixSize; i++) {
            if (matrix.get(i).get(col).getCellState().equals(CellState.EMPTY)) {
                possibleWinner = new Cell(i, col);
                break;
            }
        }
    }
    @Override
    public Move makeMove(Board board, Player jarvis) {
        List<List<Cell>> matrix = board.getMatrix();
        if (!checkFirstMove(matrix)) {
            HardBotPlayingStrategy.updateCheckHashMap(matrix,board.getLastMove());
        }
        if (checkFirstMove(matrix)) {
            HardBotPlayingStrategy.initializeHashMaps(matrix.size());
            Cell selectedCell = getFirstMove(matrix.size());
            board.getMatrix().get(selectedCell.getRow()).get(selectedCell.getCol()).setCellState(CellState.FILLED);
            board.getMatrix().get(selectedCell.getRow()).get(selectedCell.getCol()).setPlayer(jarvis);
            return new Move(board.getMatrix().get(selectedCell.getRow()).get(selectedCell.getCol()), jarvis);
        }
        else if (possibleWinner != null) {
            board.getMatrix().get(possibleWinner.getRow()).get(possibleWinner.getCol()).setCellState(CellState.FILLED);
            board.getMatrix().get(possibleWinner.getRow()).get(possibleWinner.getCol()).setPlayer(jarvis);
            return new Move(board.getMatrix().get(possibleWinner.getRow()).get(possibleWinner.getCol()), jarvis);
        }
        else {
            for (int i = 0; i < matrix.size(); i++) {
                for (int j = 0; j < matrix.size(); j++) {
                    if(matrix.get(i).get(j).getCellState().equals(CellState.EMPTY)) {
                        board.getMatrix().get(i).get(j).setCellState(CellState.FILLED);
                        board.getMatrix().get(i).get(j).setPlayer(jarvis);
                        return new Move(board.getMatrix().get(i).get(j), jarvis);
                    }
                }
            }
        }
        throw new GameOverException("No target cell left to play");
    }
    public boolean checkFirstMove(List<List<Cell>> matrix) {
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.size(); j++) {
                if (matrix.get(i).get(i).getCellState().equals(CellState.FILLED)) return false;
            }
        }
        return true;
    }
    public Cell getFirstMove(int size) {
        ArrayList<Cell> cells = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if ((i == j || i+j == size-1) && !(i == j && i+j == size-1)) {
                    cells.add(new Cell(i,j));
                }
            }
        }
        Collections.shuffle(cells);
        return cells.get(0);
    }
    private static void leftDiagonalMove(List<List<Cell>> matrix) {
        for (int i = 0 ; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; i++) {
                if (i == j) {
                    if (matrix.get(i).get(j).getCellState().equals(CellState.EMPTY)) {
                        possibleWinner = new Cell(i,j);
                        break;
                    }
                }
            }
        }
    }
    private static void rightDiagonalMove(List<List<Cell>> matrix) {
        for (int i = 0 ; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; i++) {
                if (i + j == matrixSize-1) {
                    if (matrix.get(i).get(j).getCellState().equals(CellState.EMPTY)) {
                        possibleWinner = new Cell(i,j);
                        break;
                    }
                }
            }
        }
    }
    private static void cornerMove(List<List<Cell>> matrix) {
        int n = matrixSize;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((i == 0 && j == 0)
                        || (i == n-1 && j == 0)
                        || (j == n-1 && i == 0)
                        || (i == n-1 && j == n-1)) {
                    if (matrix.get(i).get(j).getCellState().equals(CellState.EMPTY)) {
                        possibleWinner = new Cell(i,j);
                        break;
                    }
                }
            }
        }
    }
    private static boolean isMoveInCorners(int row, int col) {
        return (row == 0 && col == 0)
                || (row == matrixSize-1 && col == 0)
                || (col == matrixSize-1 && row == 0)
                || (row == matrixSize-1 && col == matrixSize -1);
    }
    private static boolean isMoveOnRightDiagonal(int row, int col) {
        return row + col == matrixSize - 1;
    }
    private static boolean isMoveOnLeftDiagonal(int row, int col) {
        return row == col;
    }
}
