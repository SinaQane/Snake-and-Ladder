package ir.sharif.math.bp99_1.snake_and_ladder.model.pieces;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;
import ir.sharif.math.bp99_1.snake_and_ladder.model.prizes.Prize;

public class Thief extends Piece
{
    private Prize currentPrize;
    public Thief(Player player, Color color)
    {
        super(player, color);
        this.setIsActivated(true);
        this.currentPrize = null;
    }

    public void takePrize()
    {
        this.currentPrize = this.getCurrentCell().getPrize();
        this.getCurrentCell().setPrize(null);
        System.out.println("Thief took the prize");
    }

    public void dropPrize(Cell cell)
    {
        cell.setPrize(this.currentPrize);
        this.currentPrize = null;
        System.out.println("Thief dropped the prize");
    }

    public Prize getCurrentPrize()
    {
        return this.currentPrize;
    }

    public void setCurrentPrize(Prize currentPrize) {
        this.currentPrize = currentPrize;
    }

    public void losePrize()
    {
        this.currentPrize = null;
        System.out.println("Thief lost the prize");
    }

    @Override
    public boolean isValidMove(Cell destination, int diceNumber) {
        // Dice number match
        boolean f = (currentCell.getX()==destination.getX()
                && (Math.abs(currentCell.getY()-destination.getY())==diceNumber))
                ||(currentCell.getY()==destination.getY()
                && (Math.abs(currentCell.getX()-destination.getX())==diceNumber));
        if (f) // If destination is on the same horizontal or vertical line as current cell
        {
            return destination.canEnter(currentCell.getPiece()); // If Cell is empty && Color matches
        }
        return false;
    }

    @Override
    public void moveTo(Cell destination) {
        if (isValidMove(destination, this.getPlayer().getMoveLeft()))
        {
            if (this.getColor().equals(destination.getColor()))
                this.getPlayer().applyOnScore(4);
            if(destination.getPrize()!=null && this.currentPrize==null)
                this.getPlayer().usePrize(destination.getPrize());
            Cell begin = this.getCurrentCell();
            this.setCurrentCell(destination);
            begin.setPiece(null);
            destination.setPiece(this);
        }
    }

    @Override
    public void setIsActivated(boolean isActive)
    {
        super.setIsActivated(true);
    }
}