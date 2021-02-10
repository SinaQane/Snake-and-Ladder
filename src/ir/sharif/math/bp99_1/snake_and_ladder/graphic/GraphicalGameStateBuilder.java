package ir.sharif.math.bp99_1.snake_and_ladder.graphic;

import ir.sharif.math.bp99_1.snake_and_ladder.graphic.models.*;
import ir.sharif.math.bp99_1.snake_and_ladder.model.*;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;
import ir.sharif.math.bp99_1.snake_and_ladder.model.prizes.Prize;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.Transmitter;
import ir.sharif.math.bp99_1.snake_and_ladder.util.Loop;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GraphicalGameStateBuilder {
    private final GameState logicalGameState;


    public GraphicalGameStateBuilder(GameState gameState) {
        this.logicalGameState = gameState;
    }

    public GraphicalGameState build() {
        GraphicalPlayer p1 = createGPlayer(logicalGameState.getPlayer(1));
        GraphicalPlayer p2 = createGPlayer(logicalGameState.getPlayer(2));
        GraphicalBoard b = createBoard(logicalGameState.getBoard());
        return new GraphicalGameState(b, p1, p2);
    }

    public void update(GraphicalGameState graphicalGameState) {
        updateGPlayer(logicalGameState.getPlayer1(), graphicalGameState.getPlayer1());
        updateGPlayer(logicalGameState.getPlayer2(), graphicalGameState.getPlayer2());
        updateBoard(logicalGameState.getBoard(), graphicalGameState.getBoard());
        graphicalGameState.setStarted(logicalGameState.isStarted());
    }


    private GraphicalPlayer createGPlayer(Player p) {
        List<GraphicalPiece> gp = convertPieces(p.getPieces());
        return new GraphicalPlayer(p.getName(), p.getScore(), gp);
    }

    private void updateGPlayer(Player player, GraphicalPlayer graphicalPlayer) {
        List<GraphicalPiece> graphicalPieces = convertPieces(player.getPieces());
        graphicalPlayer.setName(graphicalPlayer.getName());
        setList(graphicalPlayer.getPieces(), graphicalPieces);
        graphicalPlayer.setScore(player.getScore());
        graphicalPlayer.setItsTurn(player.equals(logicalGameState.getCurrentPlayer()));
        graphicalPlayer.setDiceNumber(player.getMoveLeft());
        graphicalPlayer.setReady(player.isReady());
    }

    private GraphicalBoard createBoard(Board board) {
        GraphicalBoard graphicalBoard = new GraphicalBoard(convertCells(board.getCells()), convertTransmitter(board.getTransmitters())
                , convertWalls(board.getWalls()));
        List<Transmitter> transmitters = new LinkedList<>(board.getTransmitters());
        setList(graphicalBoard.getGraphicalTransmitters(), convertTransmitter(transmitters));
        return graphicalBoard;
    }

    private void updateBoard(Board board, GraphicalBoard graphicalBoard) {
        setList(graphicalBoard.getGraphicalCells(), convertCells(board.getCells()));
        setList(graphicalBoard.getGraphicalTransmitters(), convertTransmitter(board.getTransmitters()));
        setList(graphicalBoard.getGraphicalWalls(), convertWalls(board.getWalls()));
    }


    private List<GraphicalWall> convertWalls(List<Wall> list) {
        List<GraphicalWall> graphicalWalls = new LinkedList<>();
        for (Wall wall : list) {
            if (wall.getCell1().getX() < wall.getCell2().getX() && wall.getCell1().getY() == wall.getCell2().getY()) {
                graphicalWalls.add(new GraphicalWall(wall.getCell2().getY(), wall.getCell2().getX()
                        , wall.getCell2().getY() + 1, wall.getCell2().getX()));
            }
            if (wall.getCell1().getX() > wall.getCell2().getX() && wall.getCell1().getY() == wall.getCell2().getY()) {
                graphicalWalls.add(new GraphicalWall(wall.getCell1().getY(), wall.getCell1().getX()
                        , wall.getCell1().getY() + 1, wall.getCell1().getX()));
            }
            if (wall.getCell1().getX() == wall.getCell2().getX() && wall.getCell1().getY() < wall.getCell2().getY()) {
                graphicalWalls.add(new GraphicalWall(wall.getCell2().getY(), wall.getCell2().getX()
                        , wall.getCell2().getY(), wall.getCell2().getX() + 1));
            }
            if (wall.getCell1().getX() == wall.getCell2().getX() && wall.getCell1().getY() > wall.getCell2().getY()) {
                graphicalWalls.add(new GraphicalWall(wall.getCell1().getY(), wall.getCell1().getX()
                        , wall.getCell1().getY(), wall.getCell1().getX() + 1));
            }
        }
        return graphicalWalls;
    }

    private List<GraphicalCell> convertCells(List<Cell> cells) {
        List<GraphicalCell> graphicalCells = new LinkedList<>();
        for (Cell cell : cells) {
            graphicalCells.add(new GraphicalCell(getColor(cell.getColor()), convertPrize(cell.getPrize())
                    , convertPiece(cell.getPiece()), cell.getX(), cell.getY()));
        }
        return graphicalCells;
    }

    private List<GraphicalTransmitter> convertTransmitter(List<Transmitter> l) {
        List<GraphicalTransmitter> graphicalTransmitters = new LinkedList<>();
        for (Transmitter transmitter : l) {
            if (transmitter == null) {
                graphicalTransmitters.add(null);
            } else {
                int y1 = (transmitter.getFirstCell().getX() - 1) * GraphicalCell.CELL_SIZE + GraphicalCell.CELL_SIZE / 2;
                int x1 = (transmitter.getFirstCell().getY() - 1) * GraphicalCell.CELL_SIZE + GraphicalCell.CELL_SIZE / 2;
                int y2 = (transmitter.getLastCell().getX() - 1) * GraphicalCell.CELL_SIZE + GraphicalCell.CELL_SIZE / 2;
                int x2 = (transmitter.getLastCell().getY() - 1) * GraphicalCell.CELL_SIZE + GraphicalCell.CELL_SIZE / 2;
                graphicalTransmitters.add(new GraphicalTransmitter(new Snake.SnakeBuilder().setStart(x1, y1).setEnd(x2, y2)
                        .setColor(transmitter.getColor()).build()));
            }
        }
        return graphicalTransmitters;
    }

    private GraphicalColor getColor(Color c) {
        if (c.equals(Color.BLACK)) return GraphicalColor.BLACK;
        else if (c.equals(Color.WHITE)) return GraphicalColor.WHITE;
        else if (c.equals(Color.RED)) return GraphicalColor.RED;
        else if (c.equals(Color.BLUE)) return GraphicalColor.BLUE;
        else if (c.equals(Color.GREEN)) return GraphicalColor.GREEN;
        else return GraphicalColor.YELLOW;
    }

    private GraphicalPiece convertPiece(Piece piece) {
        if (piece == null) {
            return null;
        }
        return new GraphicalPiece(getColor(piece.getColor()), piece.getPlayer().getPlayerNumber(), piece.isSelected());
    }

    private GraphicalPrize convertPrize(Prize prize) {
        if (prize == null) {
            return new GraphicalPrize(null);
        }
        return new GraphicalPrize("prize");
    }

    private List<GraphicalPiece> convertPieces(List<Piece> pieces) {
        List<GraphicalPiece> graphicalPieces = new ArrayList<>();
        for (Piece piece : pieces) {
            if (piece == null) {
                graphicalPieces.add(null);
            } else
                graphicalPieces.add(new GraphicalPiece(getColor(piece.getColor()), piece.getPlayer().getPlayerNumber(), piece.isSelected()));
        }
        return graphicalPieces;
    }

    private <T> void setList(List<? super T> target, List<? extends T> values) {
        target.clear();
        target.addAll(values);
    }
}
