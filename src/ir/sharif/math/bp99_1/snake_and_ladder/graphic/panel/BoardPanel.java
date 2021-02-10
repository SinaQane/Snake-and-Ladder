package ir.sharif.math.bp99_1.snake_and_ladder.graphic.panel;

import ir.sharif.math.bp99_1.snake_and_ladder.graphic.GraphicalAgent;
import ir.sharif.math.bp99_1.snake_and_ladder.graphic.Listeners.BoardMouseListener;
import ir.sharif.math.bp99_1.snake_and_ladder.graphic.models.GraphicalBoard;
import ir.sharif.math.bp99_1.snake_and_ladder.util.Config;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {
    protected final GraphicalBoard board;
    protected final GraphicalAgent agent;


    public BoardPanel(GraphicalBoard board, GraphicalAgent agent) {
        this.board = board;
        this.agent = agent;
        config();
        addMouseListener(new BoardMouseListener(agent));
        requestFocus();
    }

    private void config() {
        Config config = Config.getConfig("boardPanel");
        setLayout(null);
        setBounds(config.getProperty(Integer.class, "x"), config.getProperty(Integer.class, "y")
                , config.getProperty(Integer.class, "width"), config.getProperty(Integer.class, "height"));
//        setPreferredSize(new Dimension(config.getProperty(Integer.class, "width"), config.getProperty(Integer.class, "height")));
    }

    @Override
    protected void paintComponent(Graphics gs) {
        super.paintComponent(gs);
        Graphics2D g = (Graphics2D) gs;
        synchronized (agent.getPaintLock()) {
            board.paint(g);
        }
    }

}
