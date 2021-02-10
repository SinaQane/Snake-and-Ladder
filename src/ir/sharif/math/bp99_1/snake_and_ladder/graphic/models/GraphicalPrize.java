package ir.sharif.math.bp99_1.snake_and_ladder.graphic.models;

import ir.sharif.math.bp99_1.snake_and_ladder.graphic.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GraphicalPrize extends GraphicalModel {
    private final String name;
    private BufferedImage image;

    public GraphicalPrize(String name) {
        this.name = name;
        if (name != null)
            image = ImageLoader.getImage("prize");
    }

    @Override
    public void paint(Graphics2D graphics2D) {
        if (image != null)
            graphics2D.drawImage(image, 0, 0, GraphicalCell.CELL_SIZE, GraphicalCell.CELL_SIZE, null);
    }
}
