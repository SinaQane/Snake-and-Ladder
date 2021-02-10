package ir.sharif.math.bp99_1.snake_and_ladder.model;

import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.Transmitter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Board {
    private final List<Cell> cells;
    private final List<Transmitter> transmitters;
    private final List<Wall> walls;
    private final Map<Cell, Integer> startingCells;

    public Board( List<Cell> cells, List<Transmitter> transmitters,
                  List<Wall> walls, Map<Cell, Integer> startingCells) {
        this.cells = cells;
        this.transmitters = transmitters;
        this.walls = walls;
        this.startingCells = startingCells;
    }

    public List<Cell> getCells() { return cells; }

    public List<Wall> getWalls() { return walls; }

    public Map<Cell, Integer> getStartingCells() {
        return startingCells;
    }

    public List<Transmitter> getTransmitters() {
        return transmitters;
    }

    /**
     * give x,y , return a cell with that coordinates
     * return null if not exist.
     */
    public Cell getCell(int x, int y) {
        for (Cell cell : this.getCells()){
            if (cell.getX()==x && cell.getY()==y){
                return cell;
            }
        }
        return null;
    }
}