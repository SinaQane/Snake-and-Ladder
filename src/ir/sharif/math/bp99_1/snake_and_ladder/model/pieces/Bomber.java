package ir.sharif.math.bp99_1.snake_and_ladder.model.pieces;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;

public class Bomber extends Piece
{
    public Bomber(Player player, Color color)
    {
        super(player, color);
    }

    public Cell findCell(int x, int y)
    {
        for (Cell cell : this.getCurrentCell().getAllCells())
        {
            if (cell.getY()==y && cell.getX()==x)
                return cell;
        }
        return null;
    }

    @Override
    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public void Boom()
    {
        if (this.getIsActivated())
        {
            int x = this.getCurrentCell().getX();
            int y = this.getCurrentCell().getY();

            for (int i = x - 1; i <= x + 1; i++)
            {
                for (int j = y - 1; j <= y + 1; j++)
                {

                    Cell cell = findCell(i, j);
                    if (cell != null)
                    {
                        if (cell.getPiece() != null)
                        {
                            System.out.println("Piece in cell x=" + i + " and y=" + j + " died.");
                            cell.getPiece().setIsAlive(false);
                        }
                        if (cell.hasPrize())
                        {
                            System.out.println("Prize in cell x=" + i + " and y=" + j + " destroyed.");
                            cell.setPrize(null);
                        }
                    }
                }
            }
            System.out.println();
            this.getCurrentCell().turnBlack();
            this.setIsActivated(false);
        }
    }
}
