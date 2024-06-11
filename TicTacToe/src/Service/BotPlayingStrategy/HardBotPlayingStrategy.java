package Service.BotPlayingStrategy;
import Exceptions.GameOverException;
import Model.Board;
import Model.Cell;
import Model.ENUM.CellState;
import Model.Move;
import Model.Player;
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
    private static void initializeHashMaps(int dimension) {
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
    private static void updateCheckHashMap(Board board) {
        Move lastMove = board.getLastMove();
        int row = lastMove.getCell().getRow();
        int col = lastMove.getCell().getCol();
        char symbol = lastMove.getPlayer().getSymbol();


            if (isMoveInCorners(row,col)) {
                int count = cornersHashMap.compute(symbol, (key, value) -> (value == null) ? 1 : value + 1);
                if (count == 3) {
                    cornerMove(board);
                }
            } else if (isMoveOnRightDiagonal(row, col)) {
                int count = rightDiagonalHashMap.compute(symbol, (key, value) -> (value == null) ? 1 : value + 1);
                if (count == matrixSize - 1) {
                    rightDiagonalMove(board);
                }
            } else if (isMoveOnLeftDiagonal(row, col)) {
                int count = leftDiagonalHashMap.compute(symbol, (key, value) -> (value == null) ? 1 : value + 1);
                if (count == matrixSize - 1) {
                    leftDiagonalMove(board);
                }
            }
                HashMap<Character,Integer> rowMap = rowHashMapList.get(row);
                int rowCount = rowMap.compute(symbol, (key, value) -> (value == null) ? 1 : value + 1);
                if (rowCount == matrixSize - 1) {
                    checkRowHashMap(row,board);
                }
                HashMap<Character,Integer> colMap = colHashMapList.get(col);
                int colCount = colMap.compute(symbol, (key, value) -> (value == null) ? 1 : value + 1);
                if (colCount == matrixSize - 1) {
                    checkColHashMap(col,board);
                }


    }
    private static void checkRowHashMap(int row, Board board) {
        List<List<Cell>> matrix = board.getMatrix();
        for (int j = 0; j < matrixSize; j++) {
            if (matrix.get(row).get(j).getCellState().equals(CellState.EMPTY)) {
                possibleWinner = new Cell(row, j);
                break;
            }
        }
    }
    private static void checkColHashMap(int col, Board board) {
        List<List<Cell>> matrix = board.getMatrix();
        for (int i = 0 ; i < matrixSize; i++) {
            if (matrix.get(i).get(col).getCellState().equals(CellState.EMPTY)) {
                possibleWinner = new Cell(i, col);
                break;
            }
        }
    }
    @Override
    public Move makeMove(Board board, Player jarvis) {
        possibleWinner = null;
        List<List<Cell>> matrix = board.getMatrix();
        if (colHashMapList == null) HardBotPlayingStrategy.initializeHashMaps(matrix.size());
        if (!checkFirstMove(board)) {
            HardBotPlayingStrategy.updateCheckHashMap(board);
        }
        if (checkFirstMove(board)) {
            Cell selectedCell = getFirstMove(board);
            board.getMatrix().get(selectedCell.getRow()).get(selectedCell.getCol()).setCellState(CellState.FILLED);
            board.getMatrix().get(selectedCell.getRow()).get(selectedCell.getCol()).setPlayer(jarvis);
            return new Move(board.getMatrix().get(selectedCell.getRow()).get(selectedCell.getCol()), jarvis);
        }
        else if (possibleWinner != null) {
            board.getMatrix().get(possibleWinner.getRow()).get(possibleWinner.getCol()).setCellState(CellState.FILLED);
            board.getMatrix().get(possibleWinner.getRow()).get(possibleWinner.getCol()).setPlayer(jarvis);
            return new Move(board.getMatrix().get(possibleWinner.getRow()).get(possibleWinner.getCol()), jarvis);
        }
        else if (possibleWinner == null) {
            for (int i = 0; i < matrixSize; i++) {
                for (int j = 0; j < matrixSize; j++) {
                    if ((i == j) && (i+j == matrixSize-1)) {
                        if (matrix.get(i).get(j).getCellState().equals(CellState.EMPTY)) {
                            board.getMatrix().get(i).get(j).setCellState(CellState.FILLED);
                            board.getMatrix().get(i).get(j).setPlayer(jarvis);
                            return new Move(board.getMatrix().get(i).get(j), jarvis);
                        }
                    }
                }
            }

            for (int i = 0; i < matrixSize; i++) {
                for (int j = 0; j < matrixSize; j++) {
                    if ((i == j) || (i+j == matrixSize-1)) {
                        if (matrix.get(i).get(j).getCellState().equals(CellState.EMPTY)) {
                            board.getMatrix().get(i).get(j).setCellState(CellState.FILLED);
                            board.getMatrix().get(i).get(j).setPlayer(jarvis);
                            return new Move(board.getMatrix().get(i).get(j), jarvis);
                        }
                    }
                }
            }


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
    private boolean checkFirstMove(Board board) {
        List<List<Cell>> matrix = board.getMatrix();
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.size(); j++) {
                if (matrix.get(i).get(i).getCellState().equals(CellState.FILLED)) return false;
            }
        }
        return true;
    }
    private Cell getFirstMove(Board board) {
        int size = board.getSize();
        List<List<Cell>> matrix = board.getMatrix();
        ArrayList<Cell> cells = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if ((i == j || i+j == size-1) && !(i == j && i+j == size-1)) {
                    if (matrix.get(i).get(j).getCellState().equals(CellState.EMPTY)) {
                        cells.add(new Cell(i,j));
                    }
                }
            }
        }
        Collections.shuffle(cells);
        return cells.get(0);
    }
    private static void leftDiagonalMove(Board board) {
        List<List<Cell>> matrix = board.getMatrix();
        for (int i = 0 ; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                if (i == j) {
                    if (matrix.get(i).get(j).getCellState().equals(CellState.EMPTY)) {
                        possibleWinner = new Cell(i,j);
                        break;
                    }
                }
            }
        }
    }
    private static void rightDiagonalMove(Board board) {
        List<List<Cell>> matrix = board.getMatrix();
        for (int i = 0 ; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                if (i + j == matrixSize-1) {
                    if (matrix.get(i).get(j).getCellState().equals(CellState.EMPTY)) {
                        possibleWinner = new Cell(i,j);
                        break;
                    }
                }
            }
        }
    }
    private static void cornerMove(Board board) {
        List<List<Cell>> matrix = board.getMatrix();
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
