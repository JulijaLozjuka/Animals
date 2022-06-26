package settings;

public class GameSettings {
    private int rows;
    private int columns;

    private int gameTurns;

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getGameTurns() {
        return gameTurns;
    }

    public void setGameTurns(int gameTurns) {
        this.gameTurns = gameTurns;
    }
}
