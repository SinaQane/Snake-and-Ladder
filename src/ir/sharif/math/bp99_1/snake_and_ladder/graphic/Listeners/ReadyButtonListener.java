package ir.sharif.math.bp99_1.snake_and_ladder.graphic.Listeners;

import ir.sharif.math.bp99_1.snake_and_ladder.graphic.GraphicalAgent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReadyButtonListener implements ActionListener {
    private final GraphicalAgent graphicalAgent;
    private final int playerNumber;

    public ReadyButtonListener(GraphicalAgent graphicalAgent, int playerNumber) {
        this.graphicalAgent = graphicalAgent;
        this.playerNumber = playerNumber;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        graphicalAgent.requestStart(playerNumber);
    }
}
