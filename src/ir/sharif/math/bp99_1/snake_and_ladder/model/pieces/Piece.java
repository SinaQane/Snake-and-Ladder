package ir.sharif.math.bp99_1.snake_and_ladder.model.pieces;

import ir.sharif.math.bp99_1.snake_and_ladder.model.*;

public class Piece
{
    protected Cell currentCell;
    private final Color color;
    private final Player player;
    private boolean isSelected;
    protected boolean isAlive;
    private boolean isActivated;
    private String type;

    public Piece(Player player, Color color)
    {
        this.color = color;
        this.player = player;
        this.isAlive = true;
        this.isActivated = false;
        if (color==Color.BLUE)
            type = "Healer";
        else if (color==Color.GREEN)
            type = "Bomber";
        else if (color==Color.YELLOW)
            type = "Thief";
        else if (color==Color.RED)
            type = "Sniper";
    }

    public String getType()
    {
        return type;
    }

    public Player getPlayer()
    {
        return player;
    }

    public Color getColor()
    {
        return color;
    }

    public Cell getCurrentCell()
    {
        return currentCell;
    }

    public boolean getIsAlive()
    {
        return isAlive;
    }

    public void setIsAlive(boolean isAlive)
    {
        if (!isAlive)
            this.setIsActivated(false);
        this.isAlive = isAlive;
    }

    public boolean getIsActivated()
    {
        return isActivated;
    }

    public void setIsActivated(boolean isActive)
    {
        if (isActive)
            System.out.println(this.getPlayer().getName() + "'s " + this.getType() + " activated.\n");
        else
            System.out.println(this.getPlayer().getName() + "'s " + this.getType() + " deactivated.\n");
        this.isActivated = isActive;
    }

    public boolean isSelected()
    {
        return isSelected;
    }

    public void setSelected(boolean selected)
    {
        isSelected = selected;
    }

    public void setCurrentCell(Cell currentCell)
    {
        this.currentCell = currentCell;
    }

    /**
     * @return "true" if your movement is valid  , else return " false"
     * <p>
     * In this method, you should check if the movement is valid of not.
     * <p>
     * You can use some methods ( they are recommended )
     * <p>
     * 1) "canEnter" method in class "Cell"
     * <p>
     * if your movement is valid, return "true" , else return " false"
     */
    public boolean isValidMove(Cell destination, int diceNumber)
    { // Dice number match && No walls
        boolean f = (currentCell.getX()==destination.getX()
                && (Math.abs(currentCell.getY()-destination.getY())==diceNumber))
                ||(currentCell.getY()==destination.getY()
                && (Math.abs(currentCell.getX()-destination.getX())==diceNumber));
        if (f)
        { // If destination is on the same horizontal or vertical line as current cell
            if (destination.canEnter(currentCell.getPiece()))
            { // If Cell is empty && Color matches
                return this.getCurrentCell().canGoTo(destination); // If there's a wall between current cell and destination
            }
        }
        return false;
    }

    /**
     * @param destination move selected piece from "currentCell" to "destination"
     */
    public void moveTo(Cell destination)
    {
        if (isValidMove(destination, this.getPlayer().getMoveLeft()))
        {
            if (this.getColor().equals(destination.getColor()))
                this.getPlayer().applyOnScore(4);
            if(destination.getPrize()!=null)
                this.getPlayer().usePrize(destination.getPrize());
            Cell begin = this.getCurrentCell();
            this.setCurrentCell(destination);
            begin.setPiece(null);
            destination.setPiece(this);
        }
    }
}