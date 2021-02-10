package ir.sharif.math.bp99_1.snake_and_ladder.logic;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Board;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Wall;
import ir.sharif.math.bp99_1.snake_and_ladder.model.prizes.Prize;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.*;

import java.util.*;

public class BoardBuilder {
    String source;
    public BoardBuilder(String src) {
        this.source = src;
    }

    /**
     * give you a string in constructor.
     * <p>
     * you should read the string and create a board according to it.
     */
    public Board build()
    {
        String[] types = this.source.split("!");
        List<Cell> cells = new LinkedList<>();
        List<Transmitter> transmitters = new LinkedList<>();
        List<Wall> walls = new LinkedList<>();
        Map<Cell, Integer> startingCells = new HashMap<>();
        String[] cellsArray = types[0].split("/");

        for (String cell : cellsArray)
        {
            String[] cellArray = cell.split("#");

            Color color = null;
            if (cellArray[2].equals("RED"))
            {
                 color = Color.RED;
            }
            else if (cellArray[2].equals("BLUE"))
            {
                 color = Color.BLUE;
            }
            else if (cellArray[2].equals("GREEN"))
            {
                 color = Color.GREEN;
            }
            else if (cellArray[2].equals("YELLOW"))
            {
                 color = Color.YELLOW;
            }
            else if (cellArray[2].equals("WHITE"))
            {
                 color = Color.WHITE;
            }
            else if (cellArray[2].equals("BLACK"))
            {
                 color = Color.BLACK;
            }
            Cell newCell = new Cell(color, Integer.parseInt(cellArray[0]),  Integer.parseInt(cellArray[1]));
            cells.add(newCell);
        }
        String[] startingCellsArray = types[1].split("/");
        for (String startingCell : startingCellsArray) {
            String[] startingCellArray = startingCell.split("#");
            Cell newStartingCell = null;
            for (Cell searchCell : cells){
                if (searchCell.getX()==Integer.parseInt(startingCellArray[0])&&searchCell.getY()==Integer.parseInt(startingCellArray[1]))
                    newStartingCell = searchCell;
            }
            startingCells.put(newStartingCell, Integer.parseInt(startingCellArray[2]));
        }
        for (Cell tempCell : cells)
        {
            for (Cell tempCell2 : cells)
            {
                if ((Math.abs(tempCell.getX() - tempCell2.getX()) == 1 && tempCell.getY() == tempCell2.getY())
                        || (Math.abs(tempCell.getY() - tempCell2.getY()) == 1 && tempCell.getX() == tempCell2.getX()))
                {
                    tempCell.addToAdjacentCells(tempCell2);
                    tempCell.addToAdjacentOpenCells(tempCell2);
                }
            }
        }
        for (Cell tempCell : cells)
        {
            for (Cell tempCell2 : cells)
            {
                tempCell.addToAllCells(tempCell2);
            }
        }
        String[] wallsArray = types[2].split("/");
        for (String wallString : wallsArray)
        {
            String[] wallArray = wallString.split("#");
            Cell startWall = null;
            Cell endWall = null;
            for (Cell searchCell : cells)
            {
                if (searchCell.getX()==Integer.parseInt(wallArray[0])&&searchCell.getY()==Integer.parseInt(wallArray[1]))
                    startWall = searchCell;
            }
            for (Cell searchCell : cells)
            {
                if (searchCell.getX()==Integer.parseInt(wallArray[2])&&searchCell.getY()==Integer.parseInt(wallArray[3]))
                    endWall = searchCell;
            }
            assert startWall != null;
            startWall.removeFromAdjacentOpenCells(endWall);
            assert endWall != null;
            endWall.removeFromAdjacentOpenCells(startWall);
            Wall newWall = new Wall(startWall, endWall);
            walls.add(newWall);
        }

        String[] transmittersArray = types[3].split("/");
        for (String transmitterString : transmittersArray)
        {
            String[] transmitterArray = transmitterString.split("#");
            Cell startTransmitter = null;
            Cell endTransmitter = null;

            for (Cell searchCell : cells)
            {
                if (searchCell.getX()==Integer.parseInt(transmitterArray[0])&&searchCell.getY()==Integer.parseInt(transmitterArray[1]))
                    startTransmitter = searchCell;
            }

            for (Cell searchCell : cells)
            {
                if (searchCell.getX() == Integer.parseInt(transmitterArray[2]) && searchCell.getY() == Integer.parseInt(transmitterArray[3]))
                    endTransmitter = searchCell;
            }
            if (Integer.parseInt(transmitterArray[4])==0)
            {
                Normal newTransmitter = new Normal(startTransmitter, endTransmitter);
                transmitters.add(newTransmitter);
            }
            else if (Integer.parseInt(transmitterArray[4])==1)
            {
                Magical newTransmitter = new Magical(startTransmitter, endTransmitter);
                transmitters.add(newTransmitter);
            }
            else if (Integer.parseInt(transmitterArray[4])==2)
            {
                Deadly newTransmitter = new Deadly(startTransmitter, endTransmitter);
                transmitters.add(newTransmitter);
            }
            else if (Integer.parseInt(transmitterArray[4])==3)
            {
                Earthworm newTransmitter = new Earthworm(startTransmitter, endTransmitter);
                transmitters.add(newTransmitter);
            }
        }
        String[] prizesArray = types[4].split("/");
        for (String prizeString : prizesArray)
        {
            String[] prizeArray = prizeString.split("#");
            for (Cell searchCell : cells)
            {
                if (searchCell.getX()==Integer.parseInt(prizeArray[0])&&searchCell.getY()==Integer.parseInt(prizeArray[1]))
                {
                    Prize newPrize = new Prize(searchCell, Integer.parseInt(prizeArray[2]), Integer.parseInt(prizeArray[3]), Integer.parseInt(prizeArray[4]));
                    searchCell.setPrize(newPrize);
                }
            }
        }
        return new Board(cells, transmitters, walls, startingCells);
    }
}