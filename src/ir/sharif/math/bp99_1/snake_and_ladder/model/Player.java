package ir.sharif.math.bp99_1.snake_and_ladder.model;

import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.*;
import ir.sharif.math.bp99_1.snake_and_ladder.model.prizes.Prize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Player {
    private final String name;
    private int score;
    private final List<Piece> pieces;
    private final Dice dice;
    private Player rival;
    private final int id;
    private int playerNumber;
    private boolean isReady;
    private boolean dicePlayedThisTurn;
    private int moveLeft;
    private Piece selectedPiece;
    private final Sniper sniper = new Sniper(this, Color.RED);
    private final Healer healer = new Healer(this, Color.BLUE);
    private final Bomber bomber = new Bomber(this, Color.GREEN);
    private final Thief thief = new Thief(this, Color.YELLOW);

    public Player(String name, int score, int id, int playerNumber) {
        this.name = name;
        this.score = score;
        this.id = id;
        this.playerNumber = playerNumber;
        this.dice = new Dice();
        this.pieces = new ArrayList<>();
        this.pieces.add(sniper);
        this.pieces.add(healer);
        this.pieces.add(bomber);
        this.pieces.add(thief);
        this.moveLeft = 0;
        this.selectedPiece = null;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Dice getDice() {
        return dice;
    }

    public int getScore() {
        return score;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public Player getRival() {
        return rival;
    }

    public int getMoveLeft() {
        return moveLeft;
    }

    public Piece getSelectedPiece() {
        return selectedPiece;
    }

    public boolean isDicePlayedThisTurn() {
        return dicePlayedThisTurn;
    }

    public void setDicePlayedThisTurn(boolean dicePlayedThisTurn) {
        this.dicePlayedThisTurn = dicePlayedThisTurn;
    }

    public void setSelectedPiece(Piece selectedPiece) {
        this.selectedPiece = selectedPiece;
    }

    public void setMoveLeft(int moveLeft) {
        this.moveLeft = moveLeft;
    }

    public void setRival(Player rival) {
        this.rival = rival;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void applyOnScore(int score) {
        this.score += score;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    /**
     * @param prize according to input prize , apply necessary changes to score and dice chance
     *              <p>
     *              you can use method "addChance" in class "Dice"(not necessary, but recommended)
     */

    public void usePrize(Prize prize) {
        this.applyOnScore(prize.getPoint());
        this.dice.addChance(prize.getDiceNumber(), prize.getChance());
    }

    /**
     * check if any of player pieces can move to another cell.
     * @return true if at least 1 piece has a move , else return false
     * <p>
     * you can use method "isValidMove" in class "Piece"(not necessary, but recommended)
     */
    public boolean hasMove(Board board, int diceNumber)
    {
        for (Piece piece : this.getPieces())
        {
            if (piece.getIsAlive())
            {
                for (Cell cell : board.getCells())
                {
                    if (piece.isValidMove(cell, diceNumber))
                    {
                        return true;
                    }
                }
            }
        }
        if (this.getBomber().getIsActivated())
            return true;
        if (this.getThief().getIsActivated() && this.getThief().getCurrentPrize()!=null)
            return true;
        if (this.getThief().getIsActivated() && this.getThief().getCurrentPrize()==null && this.getThief().getCurrentCell().hasPrize())
            return true;
        return false;
    }

    /**
     * Deselect selectedPiece and make some changes in this class fields.
     */
    // **

    public void endTurn()
    {
        try
        {
            this.selectedPiece.setSelected(false);
        }
        catch (RuntimeException ignored){}

        this.selectedPiece = null;
        this.dicePlayedThisTurn = false;
        this.moveLeft = 0;
        this.setDicePlayedThisTurn(false);
    }

    public Sniper getSniper()
    {
        return sniper;
    }

    public Healer getHealer()
    {
        return healer;
    }

    public Bomber getBomber()
    {
        return bomber;
    }

    public Thief getThief()
    {
        return thief;
    }

    public String getStatus(){
        StringBuilder details = new StringBuilder();
        details.append("Player " + playerNumber + ":\n");
        String temp = "";
        for (Piece piece : pieces)
        {
            temp += "Piece " + piece.getType();
            if (piece.getIsAlive())
                temp += " is Alive and ";
            else
                temp += " is Dead and ";
            if (piece.getIsActivated())
                temp += "Activated.\n";
            else
                temp += "Deactivated.\n";
            details.append(temp);
            temp = "";
        }
        return details.toString();
    }

    /**
     * DO NOT CHANGE FOLLOWING METHODS.
     */

    /*
    * Okay
    */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}