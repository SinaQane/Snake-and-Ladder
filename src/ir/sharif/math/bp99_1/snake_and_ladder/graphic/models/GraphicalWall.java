package ir.sharif.math.bp99_1.snake_and_ladder.graphic.models;

import java.awt.*;

public class GraphicalWall extends GraphicalModel {
    private final int startX, startY, endX, endY;

    public GraphicalWall(int startX, int startY, int endX, int endY) {
        this.startX = (startX - 1) * GraphicalCell.CELL_SIZE;
        this.startY = (startY - 1) * GraphicalCell.CELL_SIZE;
        this.endX = (endX - 1) * GraphicalCell.CELL_SIZE;
        this.endY = (endY - 1) * GraphicalCell.CELL_SIZE;
    }

    @Override
    public void paint(Graphics2D graphics2D) {
        Stroke s = graphics2D.getStroke();
        graphics2D.setColor(Color.black.darker());
        graphics2D.setStroke(new BasicStroke(6));
        graphics2D.drawLine(startX, startY, endX, endY);
        graphics2D.setStroke(s);
        graphics2D.setColor(Color.BLACK);
    }
}
