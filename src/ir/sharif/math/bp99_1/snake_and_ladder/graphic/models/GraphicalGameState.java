package ir.sharif.math.bp99_1.snake_and_ladder.graphic.models;


public class GraphicalGameState {
    private final GraphicalBoard board;
    private final GraphicalPlayer player1, player2;
    private boolean started;

    public GraphicalGameState(GraphicalBoard board, GraphicalPlayer player1, GraphicalPlayer player2) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
    }

    public GraphicalBoard getBoard() {
        return board;
    }

    public GraphicalPlayer getPlayer1() {
        return player1;
    }

    public GraphicalPlayer getPlayer2() {
        return player2;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
}
