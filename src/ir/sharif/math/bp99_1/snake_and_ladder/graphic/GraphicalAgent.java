package ir.sharif.math.bp99_1.snake_and_ladder.graphic;


import ir.sharif.math.bp99_1.snake_and_ladder.graphic.models.GraphicalGameState;
import ir.sharif.math.bp99_1.snake_and_ladder.graphic.panel.BoardPanel;
import ir.sharif.math.bp99_1.snake_and_ladder.graphic.panel.MainPanel;
import ir.sharif.math.bp99_1.snake_and_ladder.graphic.panel.PlayerInfoPanel;
import ir.sharif.math.bp99_1.snake_and_ladder.logic.LogicalAgent;
import ir.sharif.math.bp99_1.snake_and_ladder.model.GameState;

import javax.swing.*;

/**
 * this class is a connection between the graphic and logics
 */
public class GraphicalAgent {
    private final LogicalAgent logicalAgent;
    private final Object paintLock;
    private GraphicalGameState graphicalGameState;
    private Frame frame;

    public GraphicalAgent(LogicalAgent logicalAgent) {
        this.logicalAgent = logicalAgent;
        this.paintLock = new Object();
    }

    /**
     * this method get game state and build or update a graphical models
     * and save this models somewhere
     */
    public void update(GameState gameState) {
        synchronized (paintLock) {
            new GraphicalGameStateBuilder(gameState).update(this.graphicalGameState);
        }
    }

    public void initialize(GameState gameState) {
        this.graphicalGameState = new GraphicalGameStateBuilder(gameState).build();
        this.frame = initializePanels();
    }

    private Frame initializePanels() {
        PlayerInfoPanel player1Info = new PlayerInfoPanel(graphicalGameState.getPlayer1(), this, 1);
        PlayerInfoPanel player2Info = new PlayerInfoPanel(graphicalGameState.getPlayer2(), this, 2);
        BoardPanel boardPanel = new BoardPanel(graphicalGameState.getBoard(), this);
        MainPanel mainPanel = new MainPanel(boardPanel, player1Info, player2Info);
        Frame frame = Frame.getInstance();
        frame.setContentPane(mainPanel);
        return frame;
    }

    public void diceRequest(int playerNumber) {
        logicalAgent.rollDice(playerNumber);
    }

    public void showDiceDetails(int playerNumber) {
        String details = logicalAgent.getDiceDetail(playerNumber);
        JOptionPane.showMessageDialog(frame, details, "dice details", JOptionPane.INFORMATION_MESSAGE);
    }

    public void clickRequest(int x, int y) {
        logicalAgent.selectCell(x, y);
        /*
         *  TO DO
         *  WE SEND X , Y TO LOGIC, LOGIC SHOULD DECIDE WEATHER WE CHOOSE
         *  A PIECE , OR WE WANT TO MOVE A (ALREADY) CHOSEN PIECE TO A NEW CORDINATES.
         *
         *  I SUGGEST LOGIC SEND BACK A BOOLEAN (OR STRING ), SO WE UNDERSTAND IF
         *  OUR REQUEST IS DONE OR NOT .
         *
         *
         */
    }

    public void clickRightOnCell(int x, int y) {
        String details = logicalAgent.getCellDetails(x, y);
        JOptionPane.showMessageDialog(frame, details, "cell details", JOptionPane.INFORMATION_MESSAGE);
    }

    public void changeColorRequest(int player, int piece, String color) {
        /*
         * TO DO
         * SEND A REQUEST TO LOGIC THAT PLAYER # WANT TO CHANGE THE COLOR OF
         * ONE OF HIS PIECES .
         *
         *
         * NOT FORGOT TO UPDATE THE GRAPHIC AFTER THIS REQUEST.
         */
    }

    public void requestStart(int playerNumber) {
        logicalAgent.readyPlayer(playerNumber);
    }

    public void requestEndTurn(int playerNumber) {
        logicalAgent.resign(playerNumber);
        /*
         * TO DO
         * SEND A REQUEST TO LOGIC THAT PLAYER # Turn is ended and switch the turns
         *
         * NOT FORGOT TO UPDATE THE GRAPHIC AFTER THIS REQUEST.
         **/
    }

    public Object getPaintLock() {
        return paintLock;
    }

    public String getPlayerNames(int number) {
        String[] s = new String[]{"first", "second"};
        String result;
        do {
            result = JOptionPane.showInputDialog(frame, "Enter " + s[number - 1] + " player name ");
        } while (result == null || result.length() == 0);
        return result;
    }

    public void playerWin(int playerNumber) {
        String[] s = new String[]{"one", "two"};
        String message;
        if (playerNumber == 3) message = "draw";
        else message = "player " + s[playerNumber - 1] + " wins";
        JOptionPane.showMessageDialog(frame, message);
    }

    public GraphicalGameState getGraphicalGameState() {
        return graphicalGameState;
    }
}
