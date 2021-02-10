package ir.sharif.math.bp99_1.snake_and_ladder.model.pieces;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;

public class Healer extends Piece
{
    public Healer(Player player, Color color)
    {
        super(player, color);
    }

    @Override
    public void setIsAlive(boolean isAlive)
    {
        super.setIsAlive(true);
    }

    public void Heal()
    {
        for (Cell cell : this.getCurrentCell().getAdjacentCells())
        {
            if (this.getIsActivated())
            {
                if (cell.getPiece() != null)
                {
                    if (cell.getPiece().getPlayer().equals(this.getPlayer())
                            && !cell.getPiece().getIsAlive())
                    {
                        cell.getPiece().setIsAlive(true);
                        System.out.println("Piece in cell x=" + cell.getX() + " and y=" + cell.getY() + " was healed.\n");
                        this.setIsActivated(false);
                    }
                }
            }
        }
    }
}