package ir.sharif.math.bp99_1.snake_and_ladder.graphic.models;

import java.awt.*;

public enum GraphicalColor {
    WHITE(Color.white),
    BLACK(Color.LIGHT_GRAY),
    RED(new Color(252, 163, 163, 255)),
    BLUE(new Color(0, 61, 252)),
    GREEN(new Color(157, 255, 140, 226)),
    YELLOW(new Color(255, 238, 175));

    private final Color color;

    GraphicalColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
