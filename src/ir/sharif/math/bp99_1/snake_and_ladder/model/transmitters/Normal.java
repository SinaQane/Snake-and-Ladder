package ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;

public class Normal extends Transmitter
{
    public Normal(Cell firstCell, Cell lastCell)
    {
        super(firstCell, lastCell);
        this.color = Color.BLUE;
    }
}
