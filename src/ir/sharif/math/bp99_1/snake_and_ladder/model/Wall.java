package ir.sharif.math.bp99_1.snake_and_ladder.model;

public class Wall {
    private final Cell cell1, cell2;

    public Wall(Cell cell1, Cell cell2) {
        this.cell1 = cell1;
        this.cell2 = cell2;
    }

    public Cell getCell1() {
        return cell1;
    }

    public Cell getCell2() {
        return cell2;
    }

    @Override
    public String toString() {
        return "Wall{" +
                "cell1=" + cell1 +
                ", cell2=" + cell2 +
                '}';
    }
}