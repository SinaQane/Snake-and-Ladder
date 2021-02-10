package ir.sharif.math.bp99_1.snake_and_ladder.logic;

import ir.sharif.math.bp99_1.snake_and_ladder.graphic.GraphicalAgent;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Board;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.GameState;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.Transmitter;

import java.util.Map;

/**
 * This class is an interface between logic and graphic.
 * some methods of this class, is called from graphic.
 * DO NOT CHANGE ANY PART WHICH WE MENTION.
 */
public class LogicalAgent
{
    private final ModelLoader modelLoader;
    private final GraphicalAgent graphicalAgent;
    private final GameState gameState;

    /**
     * DO NOT CHANGE CONSTRUCTOR.
     */
    public LogicalAgent()
    {
        this.graphicalAgent = new GraphicalAgent(this);
        this.modelLoader = new ModelLoader();
        this.gameState = loadGameState();
    }

    /**
     * NO CHANGES NEEDED.
     */
    private GameState loadGameState()
    {
        Player player1 = modelLoader.loadPlayer(graphicalAgent.getPlayerNames(1), 1);
        Player player2;
        do
        {
            player2 = modelLoader.loadPlayer(graphicalAgent.getPlayerNames(2), 2);
        } while (player1.equals(player2));
        player1.setRival(player2);
        player2.setRival(player1);

        if (modelLoader.checkForOldGame(player1, player2))
        {
            GameState gameState = modelLoader.loadGame(player1, player2);
            return gameState;
        }
        Board board = modelLoader.loadBord();
        return new GameState(board, player1, player2, 0);
    }

    /**
     * NO CHANGES NEEDED.
     */
    public void initialize()
    {
        graphicalAgent.initialize(gameState);
    }

    /**
     * Give a number from graphic,( which is the playerNumber of a player
     * who clicks "ReadyButton".) you should somehow change that player state.
     * if both players are ready. then start the game.
     */
    public void readyPlayer(int playerNumber)
    {
        gameState.getPlayer(playerNumber).setReady(!gameState.getPlayer(playerNumber).isReady());
        if (gameState.getPlayer(1).isReady()&&gameState.getPlayer(2).isReady())
        {
            for (Map.Entry<Cell, Integer> entry : gameState.getBoard().getStartingCells().entrySet())
            {
                if (entry.getValue() == 1)
                {
                    for (Piece piece : gameState.getPlayer1().getPieces())
                    {
                        if (piece.getCurrentCell() == null)
                        {
                            if (piece.getColor().equals(entry.getKey().getColor()))
                            {
                                piece.setCurrentCell(entry.getKey());
                                entry.getKey().setPiece(piece);
                                break;
                            }
                        }
                    }
                }
                else if (entry.getValue() == 2)
                {
                    for (Piece piece : gameState.getPlayer2().getPieces())
                    {
                        if (piece.getCurrentCell() == null)
                        {
                            if (piece.getColor().equals(entry.getKey().getColor()))
                            {
                                piece.setCurrentCell(entry.getKey());
                                entry.getKey().setPiece(piece);
                                break;
                            }
                        }
                    }
                }
            }
            gameState.nextTurn();
            modelLoader.saveGame(gameState);
        }
        // don't touch this line
        graphicalAgent.update(gameState);
    }

    /**
     * give x,y (coordinates of a cell) :
     * you should handle if user want to select a piece
     * or already selected a piece and now want to move it to a new cell
     */
    // ***
    public void selectCell(int x, int y)
    {
        if (gameState.isStarted())
        {
            Cell cell = this.gameState.getBoard().getCell(x, y);
            if (cell.getPiece() == null)
            {
                if (this.gameState.getCurrentPlayer().getSelectedPiece() != null)
                {
                    if (this.gameState.getCurrentPlayer().isDicePlayedThisTurn())
                    {
                        if (this.gameState.getCurrentPlayer().getSelectedPiece().isValidMove(cell, this.gameState.getCurrentPlayer().getMoveLeft()))
                        {
                            this.gameState.getCurrentPlayer().getSelectedPiece().moveTo(cell);

                            gameState.getBoard().getCell(x, y).getPiece().setSelected(false); // Move Piece

                            for (Transmitter snake : this.gameState.getBoard().getTransmitters())
                            {
                                if (snake.getFirstCell().equals(cell))
                                {
                                    if(this.gameState.getCurrentPlayer().getSelectedPiece().getType().equals("Thief"))
                                    {
                                        this.gameState.getCurrentPlayer().getThief().losePrize();
                                    }
                                    snake.transmit(this.gameState.getCurrentPlayer().getSelectedPiece()); // Snake
                                }
                            }

                            this.gameState.getCurrentPlayer().getSniper().Kill();
                            this.gameState.getCurrentPlayer().getRival().getSniper().Kill();
                            this.gameState.getCurrentPlayer().getHealer().Heal();
                            this.gameState.getCurrentPlayer().getRival().getHealer().Heal();

                            this.gameState.getCurrentPlayer().setSelectedPiece(null);
                            this.gameState.nextTurn();
                            modelLoader.saveGame(gameState);
                        }
                    }
                }
            }
            else
            {
                if (cell.getPiece().equals(this.gameState.getCurrentPlayer().getSelectedPiece()))
                {
                    if(this.gameState.getCurrentPlayer().getSelectedPiece().getType().equals("Thief")
                            && this.gameState.getCurrentPlayer().getSelectedPiece().getIsActivated())
                    {
                        if(this.gameState.getCurrentPlayer().getThief().getCurrentPrize()!=null)
                        {
                            this.gameState.getCurrentPlayer().getThief().dropPrize(this.gameState.getCurrentPlayer().getSelectedPiece().getCurrentCell());
                            this.gameState.nextTurn();
                            modelLoader.saveGame(gameState);
                        }
                        else
                        {
                            if(this.gameState.getCurrentPlayer().getSelectedPiece().getCurrentCell().hasPrize())
                            {
                                this.gameState.getCurrentPlayer().getThief().takePrize();
                                this.gameState.nextTurn();
                                modelLoader.saveGame(gameState);
                            }
                        }
                    }
                    else if (this.gameState.getCurrentPlayer().getSelectedPiece().getType().equals("Bomber")
                            && this.gameState.getCurrentPlayer().getSelectedPiece().getIsActivated())
                    {
                        this.gameState.getCurrentPlayer().getBomber().Boom();
                        this.gameState.nextTurn();
                        modelLoader.saveGame(gameState);
                    }

                    this.gameState.getCurrentPlayer().setSelectedPiece(null); // Deselect
                    gameState.getBoard().getCell(x, y).getPiece().setSelected(false);
                }
                else
                {
                    if (cell.getPiece().getPlayer().equals(this.gameState.getCurrentPlayer()))
                    {
                        if (cell.getPiece().getIsAlive())
                        {
                            try
                            {
                                this.gameState.getCurrentPlayer().getSelectedPiece().setSelected(false);
                            }
                            catch (RuntimeException ignored) {}
                            this.gameState.getCurrentPlayer().setSelectedPiece(cell.getPiece()); // Select
                            gameState.getBoard().getCell(x, y).getPiece().setSelected(true);
                        }
                    }
                }

            }
        }
        modelLoader.saveGame(gameState);
        // don't touch this line
        graphicalAgent.update(gameState);
        checkForEndGame();
    }

    /**
     * check for endgame and specify winner
     * if player one in winner set winner variable to 1
     * if player two in winner set winner variable to 2
     * If the game is a draw set winner variable to 3
     */
    private void checkForEndGame()
    {
        if (gameState.getTurn()==41)
        {
            // game ends
            int winner;
            if (gameState.getPlayer1().getScore()==gameState.getPlayer2().getScore())
                winner = 3;
            else
                winner = gameState.getPlayer1().getScore()>gameState.getPlayer2().getScore() ? 1 : 2;

            // don't touch it
            graphicalAgent.playerWin(winner);
            /* save players*/
            modelLoader.savePlayer(gameState.getPlayer1());
            modelLoader.savePlayer(gameState.getPlayer2());
            modelLoader.archive(gameState.getPlayer1(), gameState.getPlayer2());
            modelLoader.deleteSaveGame(gameState);
            LogicalAgent logicalAgent = new LogicalAgent();
            logicalAgent.initialize();
        }
    }

    public void resign(int playerNumber)
    {
        gameState.getPlayer(playerNumber%2+1).applyOnScore(1000);
        graphicalAgent.playerWin(playerNumber %2+1);
        /* save players*/
        modelLoader.savePlayer(gameState.getPlayer1());
        modelLoader.savePlayer(gameState.getPlayer2());
        modelLoader.archive(gameState.getPlayer1(), gameState.getPlayer2());
        modelLoader.deleteSaveGame(gameState);
        LogicalAgent logicalAgent = new LogicalAgent();
        logicalAgent.initialize();
    }

    /**
     * Give a number from graphic,( which is the playerNumber of a player
     * who left clicks "dice button".) you should roll his/her dice
     * and update *****************
     */
    public void rollDice(int playerNumber)
    {
        if (gameState.isStarted())
        {
            if (gameState.getCurrentPlayer().equals(gameState.getPlayer(playerNumber)))
            {
                if (!gameState.getPlayer(playerNumber).isDicePlayedThisTurn())
                {
                    int diceNumber = gameState.getPlayer(playerNumber).getDice().roll();

                    if (diceNumber == 1 && gameState.getPlayer(playerNumber).getHealer().getIsAlive())
                        gameState.getPlayer(playerNumber).getHealer().setIsActivated(true);
                    else if (diceNumber == 3 && gameState.getPlayer(playerNumber).getBomber().getIsAlive())
                        gameState.getPlayer(playerNumber).getBomber().setIsActivated(true);
                    else if (diceNumber == 5 && gameState.getPlayer(playerNumber).getSniper().getIsAlive())
                        gameState.getPlayer(playerNumber).getSniper().setIsActivated(true);
                    else if (diceNumber == 6)
                        gameState.getPlayer(playerNumber).applyOnScore(4);

                    gameState.getPlayer(playerNumber).setMoveLeft(diceNumber);
                    gameState.getPlayer(playerNumber).setDicePlayedThisTurn(true);
                    if (gameState.getCurrentPlayer().getMoveLeft() != 0)
                    {
                        if (!gameState.getPlayer(playerNumber).hasMove(gameState.getBoard(), gameState.getPlayer(playerNumber).getMoveLeft()))
                        {
                            gameState.getPlayer(playerNumber).applyOnScore(-3);
                            gameState.nextTurn();
                        }
                        else
                        {
                            System.out.println(gameState.getPlayer(playerNumber).getStatus());
                        }
                    }
                }
            }
        }
        // don't touch this line
        modelLoader.saveGame(gameState);
        graphicalAgent.update(gameState);
    }

    public String getCellDetails(int x, int y)
    {
        return "cell at" + x + "," + y;
    }

    /**
     * Give a number from graphic,( which is the playerNumber of a player
     * who right clicks "dice button".) you should return the dice detail of that player.
     * you can use method "getDetails" in class "Dice"(not necessary, but recommended )
     */
    public String getDiceDetail(int playerNumber)
    {
        return gameState.getPlayer(playerNumber).getDice().getDetails();
    }
}