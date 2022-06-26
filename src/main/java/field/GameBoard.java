package field;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {

    private List<List<Cell>> worldMap;

    public GameBoard() {
        worldMap = new ArrayList<>();
    }

    public void initWorldMap(int maxRowCount, int maxColCount) {
        for (int row = 0; row < maxRowCount; row++) {
            worldMap.add(new ArrayList<>());
            for (int col = 0; col < maxColCount; col++) {
                worldMap.get(row).add(new Cell(new Position(row, col)));
            }
        }
    }

    public List<List<Cell>> getWorldMap() {
        return worldMap;
    }

    public Cell getCell(Position position){
        return worldMap.get(position.getRow()).get(position.getColumn());
    }
}
