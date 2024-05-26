package Model;

import Model.ENUM.CellState;

public class Cell {
    private int row;
    private int col;
    private CellState cellState;
    private Player player;

    public Cell(int i, int j) {
        this.row = i;
        this.col = j;
        this.cellState = CellState.EMPTY;
        this.player = null;
    }

    public void displayCell(){
        if (player == null) {
            System.out.print("| |");
        } else {
            System.out.print("|" + player.getSymbol() + "|");
        }
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }


    public CellState getCellState() {
        return cellState;
    }

    public void setCellState(CellState cellState) {
        this.cellState = cellState;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
