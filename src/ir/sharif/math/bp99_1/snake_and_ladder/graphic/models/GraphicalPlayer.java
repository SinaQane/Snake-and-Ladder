package ir.sharif.math.bp99_1.snake_and_ladder.graphic.models;


import java.awt.*;
import java.util.List;

public class GraphicalPlayer extends GraphicalModel {
    private String name;
    private int score;
    private final List<GraphicalPiece> pieces;
    private boolean isReady;
    private int diceNumber;
    private boolean itsTurn;

    public GraphicalPlayer(String name, int score, List<GraphicalPiece> pieces) {
        this.name = name;
        this.score = score;
        this.pieces = pieces;
        this.diceNumber = 0;
    }

    public List<GraphicalPiece> getPieces() {
        return pieces;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public int getDiceNumber() {
        return diceNumber;
    }

    public void setDiceNumber(int diceNumber) {
        this.diceNumber = diceNumber;
    }

    public boolean isItsTurn() {
        return itsTurn;
    }

    public void setItsTurn(boolean itsTurn) {
        this.itsTurn = itsTurn;
    }

    @Override
    public void paint(Graphics2D graphics2D) {

    }
}
