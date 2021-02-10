package ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Healer;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;

public class Deadly extends Transmitter
{
    public Deadly(Cell firstCell, Cell lastCell)
    {
        super(firstCell, lastCell);
        this.color = Color.RED;
    }

    @Override
    public void transmit(Piece piece)
    {
        if (this.lastCell.canEnter(piece))
        {
            Cell begin = piece.getCurrentCell();
            piece.setCurrentCell(this.lastCell);
            begin.setPiece(null);
            this.lastCell.setPiece(piece);
            if (lastCell.hasPrize())
                lastCell.getPrize().using(piece);
            if (lastCell.getColor().equals(piece.getColor()))
                piece.getPlayer().applyOnScore(4);
            if (lastCell.getPrize() != null)
                piece.getPlayer().usePrize(lastCell.getPrize());
        }
        piece.getPlayer().applyOnScore(-3);
        if (!(piece instanceof Healer))
            piece.setIsAlive(false);
    }

    @Override
    public int getKindNumber() {
        return 2;
    }
}
