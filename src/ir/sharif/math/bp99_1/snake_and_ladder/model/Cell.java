package ir.sharif.math.bp99_1.snake_and_ladder.model;

import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;
import ir.sharif.math.bp99_1.snake_and_ladder.model.prizes.Prize;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.Transmitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cell {
    private Color color;
    private final int x, y;
    private Transmitter transmitter;
    private Prize prize;
    private Piece piece;
    private final List<Cell> adjacentOpenCells;
    private final List<Cell> adjacentCells;
    private final List<Cell> allCells;

    public Cell(Color color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.transmitter = null;
        this.prize = null;
        this.piece = null;
        this.adjacentOpenCells = new ArrayList<>();
        this.adjacentCells = new ArrayList<>();
        this.allCells = new ArrayList<>();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }

    public void addToAdjacentCells(Cell newCell) {
        adjacentCells.add(newCell);
    }

    public void addToAdjacentOpenCells(Cell newCell) {
        adjacentOpenCells.add(newCell);
    }

    public void removeFromAdjacentOpenCells(Cell newCell) {
        adjacentOpenCells.remove(newCell);
    }

    public boolean canGoTo(Cell destination){ // Checks if there's any wall between this cell and destination
        if (this.getX()==destination.getX()){
            Cell down;
            Cell up;
            int minY = Math.min(this.getY(), destination.getY());
            int maxY = Math.max(this.getY(), destination.getY());
            if (this.getY()>destination.getY()){
                down = destination;
                up = this;
            } else {
                up = destination;
                down = this;
            }
            if (minY==maxY-1){
                for (Cell tempCell : down.getAdjacentOpenCells()){
                    if (tempCell.equals(up))
                        return true;
                }
                return false;
            }
            for (Cell tempCell : down.getAdjacentOpenCells()) {
                if (tempCell.getY() == minY + 1) {
                    return tempCell.canGoTo(up);
                }
            }
            return false;
        } else if (this.getY()==destination.getY()){
            Cell right;
            Cell left;
            int minX = Math.min(this.getX(), destination.getX());
            int maxX = Math.max(this.getX(), destination.getX());
            if (this.getX()>destination.getX()){
                left = destination;
                right = this;
            } else {
                right = destination;
                left = this;
            }
            if (minX==maxX-1){
                for (Cell tempCell : left.getAdjacentOpenCells()){
                    if (tempCell.equals(right))
                        return true;
                }
                return false;
            }
            for (Cell tempCell : left.getAdjacentOpenCells()) {
                if (tempCell.getX() == minX + 1) {
                    return tempCell.canGoTo(right);
                }
            }
            return false;
        } else {
            return false;
        }
    }

    public List<Cell> getAdjacentCells()
    {
        return adjacentCells;
    }

    public List<Cell> getAdjacentOpenCells()
    {
        return adjacentOpenCells;
    }

    public Piece getPiece()
    {
        return piece;
    }

    public Prize getPrize()
    {
        return prize;
    }

    public boolean hasPrize()
    {
        return prize != null;
    }

    public Transmitter getTransmitter()
    {
        return transmitter;
    }

    public void setPiece(Piece piece)
    {
        this.piece = piece;
    }

    public void setPrize(Prize prize)
    {
        this.prize = prize;
    }

    public void setTransmitter(Transmitter transmitter)
    {
        this.transmitter = transmitter;
    }

    /**
     * @return true if piece can enter this cell, else return false
     */
    public boolean canEnter(Piece piece)
    { // Cell empty && Color match
        if (piece.getType().equals("Thief"))
        {
            return this.getPiece() == null;
        }
        if (piece.getColor().equals(this.getColor()) || this.getColor().equals(Color.WHITE))
            return this.getPiece() == null;
        return false;
    }

    /**
     * DO NOT CHANGE FOLLOWING METHODS.
     */
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return x == cell.x && y == cell.y;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(x, y);
    }

    public List<Cell> getAllCells()
    {
        return allCells;
    }

    public void addToAllCells(Cell cell)
    {
        allCells.add(cell);
    }

    public void turnBlack()
    {
        this.color = Color.BLACK;
    }
}