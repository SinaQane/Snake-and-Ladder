package ir.sharif.math.bp99_1.snake_and_ladder.graphic.models;

import java.awt.*;
import java.util.List;

public class GraphicalBoard extends GraphicalModel {
    private final List<GraphicalCell> graphicalCells;
    private final List<GraphicalTransmitter> graphicalTransmitters;
    private final List<GraphicalWall> graphicalWalls;

    public GraphicalBoard(List<GraphicalCell> graphicalCells, List<GraphicalTransmitter> graphicalTransmitters, List<GraphicalWall> graphicalWalls) {
        this.graphicalCells = graphicalCells;
        this.graphicalTransmitters = graphicalTransmitters;
        this.graphicalWalls = graphicalWalls;
    }

    public List<GraphicalWall> getGraphicalWalls() {
        return graphicalWalls;
    }

    public List<GraphicalCell> getGraphicalCells() {
        return graphicalCells;
    }

    public List<GraphicalTransmitter> getGraphicalTransmitters() {
        return graphicalTransmitters;
    }

    @Override
    public void paint(Graphics2D graphics2D) {
        graphicalCells.forEach(gt -> gt.paint(graphics2D));
        graphicalWalls.forEach(gt -> gt.paint(graphics2D));
        graphicalTransmitters.forEach(gt -> gt.paint(graphics2D));
    }
}
