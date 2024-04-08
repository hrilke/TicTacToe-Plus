package Model;

import Model.ENUM.PlayerType;

public class Player {
    private String name;
    private char symbol;
    private PlayerType playerType;


    public Move makeMove(Board board) {
        //INCOMPLETE
        return null;
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
}
