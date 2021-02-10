package ir.sharif.math.bp99_1.snake_and_ladder.model.pieces;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;

public class Sniper extends Piece
{
    public Sniper(Player player, Color color)
    {
        super(player, color);
    }

    public void Kill()
    {
        for (Cell cell : this.getCurrentCell().getAdjacentCells())
        {
            if (this.getIsActivated())
            {
                if (cell.getPiece() != null)
                {
                    if (cell.getPiece().getPlayer().equals(this.getPlayer().getRival())
                            && !(cell.getPiece() instanceof Healer)
                            && cell.getPiece().getIsAlive())
                    {
                        cell.getPiece().setIsAlive(false);
                        cell.getPiece().setIsActivated(false);
                        System.out.println("Piece in cell x=" + cell.getX() + " and y=" + cell.getY() + " was shoot.\n");
                        this.setIsActivated(false);
                    }
                }
            }
        }
    }
}