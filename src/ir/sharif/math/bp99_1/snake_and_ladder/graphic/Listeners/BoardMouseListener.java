package ir.sharif.math.bp99_1.snake_and_ladder.graphic.Listeners;

import ir.sharif.math.bp99_1.snake_and_ladder.graphic.GraphicalAgent;
import ir.sharif.math.bp99_1.snake_and_ladder.graphic.models.GraphicalCell;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class BoardMouseListener implements DummyListener {
    private final GraphicalAgent graphicalAgent;

    public BoardMouseListener(GraphicalAgent graphicalAgent) {
        this.graphicalAgent = graphicalAgent;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int xa = (e.getX() / GraphicalCell.CELL_SIZE) + 1;
        int ya = (e.getY() / GraphicalCell.CELL_SIZE) + 1;
        if (SwingUtilities.isLeftMouseButton(e))
            graphicalAgent.clickRequest(ya, xa);
        if (SwingUtilities.isRightMouseButton(e))
            graphicalAgent.clickRightOnCell(ya, xa);

    }
}
