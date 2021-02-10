package ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;

public class Transmitter
{
    protected final Cell firstCell, lastCell;
    protected Color color = Color.BLUE;

    public Transmitter(Cell firstCell, Cell lastCell)
    {
        this.firstCell = firstCell;
        this.lastCell = lastCell;
    }

    public Color getColor()
    {
        return color;
    }

    public Cell getFirstCell()
    {
        return firstCell;
    }

    public Cell getLastCell()
    {
        return lastCell;
    }

    /**
     * transmit piece to lastCell
     */
    public void transmit(Piece piece)
    {
        if (lastCell.canEnter(piece))
        {
            Cell begin = piece.getCurrentCell();
            piece.setCurrentCell(this.lastCell);
            begin.setPiece(null);
            this.lastCell.setPiece(piece);
            if (lastCell.hasPrize())
                lastCell.getPrize().using(piece);
            if (lastCell.getColor().equals(piece.getColor()))
                piece.getPlayer().applyOnScore(4);
            if(lastCell.getPrize()!=null)
                piece.getPlayer().usePrize(lastCell.getPrize());
        }
        piece.getPlayer().applyOnScore(-3);
    }

    public int getKindNumber()
    {
        return 0;
    }
}