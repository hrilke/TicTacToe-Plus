package Model;

import Exceptions.InvalidTargetException;
import Exceptions.TargetOutOfBoundException;
import Model.ENUM.CellState;
import Model.ENUM.PlayerType;

import java.util.Scanner;

public class Player {
    private String name;
    private char symbol;
    private PlayerType playerType;
    private int id;

    public Player(){}
    public Player(String name, char symbol, PlayerType playerType, int id) {
        this.name = name;
        this.symbol = symbol;
        this.playerType = playerType;
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public char getSymbol() {
        return symbol;
    }
    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }
    public PlayerType getPlayerType() {
        return playerType;
    }
    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }


    public Move makeMove(Board board) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the Row for the target cell");
        int row = sc.nextInt();
        System.out.println("Enter the column for the target cell");
        int col = sc.nextInt();

        if (row > board.getSize() || row < 0 || col > board.getSize() || col < 0) {
            throw new TargetOutOfBoundException(" Please enter valid target row and col");
        }
        Cell cell = board.getMatrix().get(row).get(col);
        if (cell.getCellState().equals(CellState.FILLED)) {
            throw new InvalidTargetException("The target i already filled ");
        }

        cell.setCellState(CellState.FILLED);
        cell.setPlayer(this);

        return new Move(cell, this);

    }



}
