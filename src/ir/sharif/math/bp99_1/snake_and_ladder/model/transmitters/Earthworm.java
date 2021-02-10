package ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;

import java.util.Random;

public class Earthworm extends Transmitter
{
    public Earthworm(Cell firstCell, Cell lastCell)
    {
        super(firstCell, lastCell);
        this.color = Color.BLACK;
    }

    public Cell findCell(int x, int y)
    {
        for (Cell cell : firstCell.getAllCells())
        {
            if (cell.getY()==y && cell.getX()==x)
                return cell;
        }
        return null;
    }

    @Override
    public void transmit(Piece piece)
    {
        Random random = new Random();
        Cell answer;
        while (true)
        {
            int y = random.nextInt(16) + 1;
            int x = random.nextInt(7) + 1;
            if (findCell(x, y).canEnter(piece))
            {
                answer = findCell(x, y);
                break;
            }
        }
        if (answer.canEnter(piece))
        {
            Cell begin = piece.getCurrentCell();
            piece.setCurrentCell(answer);
            begin.setPiece(null);
            answer.setPiece(piece);
            if (answer.hasPrize())
                answer.getPrize().using(piece);
            if (answer.getColor().equals(piece.getColor()))
                piece.getPlayer().applyOnScore(4);
            if(answer.getPrize()!=null)
                piece.getPlayer().usePrize(answer.getPrize());
        }
        piece.getPlayer().applyOnScore(-3);
    }

    @Override
    public int getKindNumber() {
        return 3;
    }
}