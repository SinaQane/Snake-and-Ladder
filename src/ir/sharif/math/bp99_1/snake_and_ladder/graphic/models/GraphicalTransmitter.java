package ir.sharif.math.bp99_1.snake_and_ladder.graphic.models;

import java.awt.*;

public class GraphicalTransmitter extends GraphicalModel {
    private final Snake snake;

    public GraphicalTransmitter(Snake snake) {
        this.snake = snake;
    }

    @Override
    public void paint(Graphics2D graphics2D) {
        snake.paint(graphics2D);
    }
}
