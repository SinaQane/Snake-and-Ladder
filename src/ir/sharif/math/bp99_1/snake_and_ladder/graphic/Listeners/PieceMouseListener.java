package ir.sharif.math.bp99_1.snake_and_ladder.graphic.Listeners;

import ir.sharif.math.bp99_1.snake_and_ladder.graphic.GraphicalAgent;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class PieceMouseListener implements DummyListener {

    private final GraphicalAgent graphicalAgent;
    private final int player;
    private final int piece;

    public PieceMouseListener(GraphicalAgent graphicalAgent, int player, int piece) {
        this.graphicalAgent = graphicalAgent;
        this.player = player;
        this.piece = piece;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        try {
            String[] myColor = new String[]{"red", "blue", "green", "yellow"};
            String color = "";
            while (color.length() == 0) {
                color = (String) JOptionPane.showInputDialog(null, "select piece color ",
                        "select", JOptionPane.QUESTION_MESSAGE, null, myColor, myColor[0]);
                if (color == null) return;
            }

            graphicalAgent.changeColorRequest(player, piece, color);
            /*
             *  TO DO ...
             *
             *  send request to logic to chang color of piece
             *
             *
             */
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
