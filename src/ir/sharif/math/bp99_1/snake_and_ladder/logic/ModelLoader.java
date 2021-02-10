package ir.sharif.math.bp99_1.snake_and_ladder.logic;

import ir.sharif.math.bp99_1.snake_and_ladder.model.*;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.*;
import ir.sharif.math.bp99_1.snake_and_ladder.model.prizes.Prize;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.*;
import ir.sharif.math.bp99_1.snake_and_ladder.util.Config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.*;

public class ModelLoader
{
    private final File boardFile, playersDirectory, archiveFile;
    private final File gamesDirectory;

    /**
     * DO NOT CHANGE ANYTHING IN CONSTRUCTOR.
     */
    public ModelLoader()
    {
        boardFile = Config.getConfig("mainConfig").getProperty(File.class, "board");
        playersDirectory = Config.getConfig("mainConfig").getProperty(File.class, "playersDirectory");
        gamesDirectory = Config.getConfig("mainConfig").getProperty(File.class, "gamesDirectory");
        archiveFile = Config.getConfig("mainConfig").getProperty(File.class, "archive");
        if (!playersDirectory.exists()) playersDirectory.mkdirs();
        if (!gamesDirectory.exists()) gamesDirectory.mkdirs();
    }

    /**
     * read file "boardFile" and create a Board
     * <p>
     * you can use "BoardBuilder" class for this purpose.
     * <p>
     * pay attention add your codes in "try".
     */
    public Board loadBord()
    {
        try
        {
            Scanner scanner = new Scanner(boardFile);
            StringBuilder data = new StringBuilder();
            while (scanner.hasNextLine())
            {
                String tempString = scanner.nextLine();
                tempString = tempString + "     ";
                switch (tempString.substring(0, 5))
                {
                    case "CELLS":
                        String[] tempArray = tempString.split(" ");
                        for (int i = 0; i < Integer.parseInt(tempArray[1]); i++)
                        {
                            String cellRowString = scanner.nextLine();
                            String[] cellRowArray = cellRowString.split(" ");
                            for (int j = 0; j < Integer.parseInt(tempArray[2]); j++)
                            {
                                data.append(String.valueOf(i + 1)).append("#").append(String.valueOf(j + 1)).append("#").append(cellRowArray[j]).append("/");
                            }
                        }
                        data.append("!");
                        break;
                    case "START":
                    case "PRIZE":
                    case "WALLS":
                    case "TRANS":
                        String[] tempArray2 = tempString.split(" ");
                        for (int i = 0; i < Integer.parseInt(tempArray2[1]); i++)
                        {
                            String startingCellsString = scanner.nextLine().replace(" ", "#").replace("\n", "");
                            data.append(startingCellsString).append("/");
                        }
                        data.append("!");
                        break;
                }
            }
            scanner.close();
            BoardBuilder builder = new BoardBuilder(data.toString());
            return builder.build();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            System.err.println("could not find board file");
            System.exit(-2);
        }
        return null;
    }

    /**
     * load player.
     * if no such a player exist, create an account(file) for him/her.
     * <p>
     * you can use "savePlayer" method of this class for that purpose.
     * <p>
     * add your codes in "try" block .
     */
    public Player loadPlayer(String name, int playerNumber)
    {
        try
        {
            File playerFile = getPlayerFile(name);
            Player player;
            int id = 0;
            if (playerFile == null)
            {
                if (playersDirectory.listFiles() != null)
                {
                    for (File file : Objects.requireNonNull(playersDirectory.listFiles()))
                    {
                        Scanner scanner = new Scanner(file);
                        id = Math.max(id, scanner.nextInt());
                        scanner.close();
                    }
                }
                player = new Player(name, 0, id + 1, playerNumber);
                savePlayer(player);
            }
            else
            {
                Scanner scanner = new Scanner(playerFile);
                id = scanner.nextInt();
                player = new Player(name, 0, id, playerNumber);
                scanner.close();
            }
            return player;
        }
        catch (FileNotFoundException | IllegalArgumentException e)
        {
            e.printStackTrace();
            System.err.println("could not find player file");
            System.exit(-2);
        }
        return null;
    }

    /**
     * if player does not have a file, create one.
     * <p>
     * else update his/her file.
     * <p>
     * add your codes in "try" block .
     */
    public void savePlayer(Player player)
    {
        try
        {
            // add your codes in this part
            File file = getPlayerFile(player.getName());
            if (file == null)
            {
                File newPlayerFile = new File(playersDirectory + "/" + player.getName());
                PrintStream printStream = new PrintStream(new FileOutputStream(newPlayerFile));
                printStream.println(player.getId() + " " + 0);
                printStream.flush();
                printStream.close();
            }
            else
            {
                Scanner scanner = new Scanner(file);
                scanner.next();
                int score = scanner.nextInt();
                scanner.close();
                if (file.delete())
                {
                    File newFile = new File(playersDirectory.getPath() + "/" + player.getName());
                    PrintStream printStream = new PrintStream(new FileOutputStream(newFile));
                    printStream.println(player.getId() + " " + (player.getScore() + score));
                    printStream.flush();
                    printStream.close();
                }
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            System.err.println("could not find player file");
            System.exit(-2);
        }
    }

    /**
     * give you a name (player name), search for its file.
     * return the file if exist.
     * return null if not.
     */
    private File getPlayerFile(String name)
    {
        for (File file : Objects.requireNonNull(playersDirectory.listFiles()))
        {
            if (file.getName().equals(name))
                return file;
        }
        return null;
    }

    /**
     * at the end of the game save game details
     */
    public void archive(Player player1, Player player2)
    {
        try
        {
            // add your codes in this part
            PrintStream printStream = new PrintStream(new FileOutputStream(archiveFile, true));
            String winner = "", output = "";
            if (player1.getScore() != player2.getScore())
            {
                if (player1.getScore() > player2.getScore())
                    winner = player1.getName();
                else
                    winner = player2.getName();
            }
            else
            {
                winner = "It's a tie";
            }
            output = String.format("Player 1: %s, Score: %d, Player 2: %s, Score: %d, Winner: %s",
                    player1.getName(), player1.getScore(), player2.getName(), player2.getScore(), winner);
            printStream.println(output);
            printStream.flush();
            printStream.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public void deleteSaveGame(GameState gameState)
    {
        String gameName = gameState.getPlayer1().getName() + "&" + gameState.getPlayer2().getName();
        File gameFile = getGameFile(gameName);
        if(gameFile.delete())
        {
            System.out.println("Game Over!");
        }
        else
        {
            System.out.println("Game Over! But the file remains...");
        }
    }

    public void saveGame(GameState gameState)
    {
        try
        {
            String gameName = gameState.getPlayer1().getName() + "&" + gameState.getPlayer2().getName();
            File gameFile = getGameFile(gameName);
            StringBuilder toSave = new StringBuilder();

            toSave.append("CELLS[ 7 16 ]:\n");
            for (int i = 1; i<=7; i++)
            {
                for (int j = 1; j<=16; j++)
                {
                    Color cellColor = gameState.getBoard().getCell(i, j).getColor();
                    String cellColorString = "";
                    if (cellColor.equals(Color.WHITE))
                        cellColorString = "WHITE";
                    else if (cellColor.equals(Color.BLACK))
                        cellColorString = "BLACK";
                    else if (cellColor.equals(Color.BLUE))
                        cellColorString = "BLUE";
                    else if (cellColor.equals(Color.GREEN))
                        cellColorString = "GREEN";
                    else if (cellColor.equals(Color.RED))
                        cellColorString = "RED";
                    else if (cellColor.equals(Color.YELLOW))
                        cellColorString = "YELLOW";
                    toSave.append(cellColorString).append(" ");
                }
                toSave.append("\n");
            }
            toSave.append("\n");

            toSave.append("PIECES[ 8 ]:\n");
            for (Piece piece : gameState.getPlayer1().getPieces())
            {
                toSave.append(piece.getCurrentCell().getX()).append(" ").append(piece.getCurrentCell().getY()).append(" ").append(1).append(" ");
                if (piece.getIsAlive())
                    toSave.append(1).append(" ");
                else
                    toSave.append(0).append(" ");
                if (piece.getIsActivated())
                    toSave.append(1).append(" ");
                else
                    toSave.append(0).append(" ");
                toSave.append(piece.getType()).append("\n");
            }
            for (Piece piece : gameState.getPlayer2().getPieces())
            {
                toSave.append(piece.getCurrentCell().getX()).append(" ").append(piece.getCurrentCell().getY()).append(" ").append(2).append(" ");
                if (piece.getIsAlive())
                    toSave.append(1).append(" ");
                else
                    toSave.append(0).append(" ");
                if (piece.getIsActivated())
                    toSave.append(1).append(" ");
                else
                    toSave.append(0).append(" ");
                toSave.append(piece.getType()).append("\n");
            }
            toSave.append("\n");

            toSave.append("WALLS[ 50 ]:\n");
            for (Wall wall : gameState.getBoard().getWalls())
            {
                toSave.append(wall.getCell1().getX()).append(" ").append(wall.getCell1().getY()).append(" ").append(wall.getCell2().getX()).append(" ").append(wall.getCell2().getY()).append("\n");
            }
            toSave.append("\n");

            toSave.append("TRANSMITTERS[ 12 ]:\n");
            for (Transmitter snake : gameState.getBoard().getTransmitters())
            {
                toSave.append(snake.getFirstCell().getX()).append(" ").append(snake.getFirstCell().getY()).append(" ").append(snake.getLastCell().getX()).append(" ").append(snake.getLastCell().getY()).append(" ").append(snake.getKindNumber()).append("\n");
            }
            toSave.append("\n");

            ArrayList<Prize> availablePrizes = new ArrayList<>();
            for (Cell cell : gameState.getBoard().getCells())
            {
                if (cell.getPrize()!=null)
                    availablePrizes.add(cell.getPrize());
            }
            toSave.append("PRIZES[ ").append(availablePrizes.size()).append(" ]:\n");
            for (Prize prize : availablePrizes)
            {
                toSave.append(prize.getCell().getX()).append(" ").append(prize.getCell().getY()).append(" ").append(prize.getPoint()).append(" ").append(prize.getChance()).append(" ").append(prize.getDiceNumber()).append("\n");
            }
            toSave.append("\n");

            toSave.append("PLAYERS_INFO[ 2 ]:\n");
            toSave.append(gameState.getPlayer1().getScore()).append(" ");
            if (gameState.getPlayer1().getThief().getCurrentPrize()!=null)
            {
                Prize thiefPrize = gameState.getPlayer1().getThief().getCurrentPrize();
                toSave.append(thiefPrize.getCell().getX()).append(" ").append(thiefPrize.getCell().getY()).append(" ").append(thiefPrize.getPoint()).append(" ").append(thiefPrize.getChance()).append(" ").append(thiefPrize.getDiceNumber()).append(" ");
            }
            else
            {
                toSave.append("0 0 0 0 0 ");
            }
            Dice player1Dice = gameState.getPlayer1().getDice();
            for (int i = 1; i<=6; i++)
            {
                toSave.append(player1Dice.getChance(i)).append(" ");
            }
            toSave.append("\n");
            toSave.append(gameState.getPlayer2().getScore()).append(" ");
            if (gameState.getPlayer2().getThief().getCurrentPrize()!=null)
            {
                Prize thiefPrize = gameState.getPlayer2().getThief().getCurrentPrize();
                toSave.append(thiefPrize.getCell().getX()).append(" ").append(thiefPrize.getCell().getY()).append(" ").append(thiefPrize.getPoint()).append(" ").append(thiefPrize.getChance()).append(" ").append(thiefPrize.getDiceNumber()).append(" ");
            }
            else
            {
                toSave.append("0 0 0 0 0 ");
            }
            Dice player2Dice = gameState.getPlayer2().getDice();
            for (int i = 1; i<=6; i++)
            {
                toSave.append(player2Dice.getChance(i)).append(" ");
            }
            toSave.append("\n\n");

            toSave.append("OTHER_INFO[ 3 ]:\n");
            toSave.append(gameState.getTurn()).append("\n");
            if (gameState.getCurrentPlayer().isDicePlayedThisTurn())
            {
                toSave.append(1).append("\n");
                toSave.append(gameState.getCurrentPlayer().getMoveLeft());
            }
            else
            {
                toSave.append(0).append("\n");
                toSave.append(0);
            }
            toSave.append("\n");
            toSave.append(player1Dice.getLastDice()).append("\n").append(player2Dice.getLastDice());

            if (gameFile == null)
            {
                File newGameFile = new File(gamesDirectory + "/" + gameName);
                PrintStream printStream = new PrintStream(new FileOutputStream(newGameFile, false));
                printStream.println(toSave);
                printStream.flush();
                printStream.close();
            }
            else
            {
                PrintStream printStream = new PrintStream(new FileOutputStream(gameFile, false));
                printStream.println(toSave);
                printStream.flush();
                printStream.close();
            }

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            System.err.println("could not find player file");
            System.exit(-2);
        }
    }

    private File getGameFile(String name)
    {
        for (File file : Objects.requireNonNull(gamesDirectory.listFiles()))
        {
            if (file.getName().equals(name))
                return file;
        }
        return null;
    }

    public boolean checkForOldGame(Player player1, Player player2)
    {
        String gameName = player1.getName() + "&" + player2.getName();
        if (getGameFile(gameName)!=null)
            return true;
        return false;
    }

    public GameState loadGame(Player player1, Player player2)
    {
        try
        {
            Scanner scanner = new Scanner(Objects.requireNonNull(getGameFile(player1.getName() + "&" + player2.getName())));

            List<Cell> cells = new LinkedList<>();
            List<Transmitter> transmitters = new LinkedList<>();
            List<Wall> walls = new LinkedList<>();
            Map<Cell, Integer> startingCells = new HashMap<>();

            scanner.nextLine();
            for (int i = 0; i<7; i++)
            {
                String[] cellsString = scanner.nextLine().replace("\n", "").split(" ");
                for (int j = 0; j<16; j++)
                {
                    Color color = null;
                    if (cellsString[j].equals("RED"))
                    {
                        color = Color.RED;
                    }
                    else if (cellsString[j].equals("BLUE"))
                    {
                        color = Color.BLUE;
                    }
                    else if (cellsString[j].equals("GREEN"))
                    {
                        color = Color.GREEN;
                    }
                    else if (cellsString[j].equals("YELLOW"))
                    {
                        color = Color.YELLOW;
                    }
                    else if (cellsString[j].equals("WHITE"))
                    {
                        color = Color.WHITE;
                    }
                    else if (cellsString[j].equals("BLACK"))
                    {
                        color = Color.BLACK;
                    }
                    Cell newCell = new Cell(color, i+1, j+1);
                    cells.add(newCell);
                }
            }
            scanner.nextLine();

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

            scanner.nextLine();
            for (int i = 1; i<=8; i++)
            {
                String[] pieceString = scanner.nextLine().replace("\n", "").split(" ");
                Cell newStartingCell = null;
                for (Cell searchCell : cells)
                {
                    if ((searchCell.getX()+"").equals(pieceString[0]) && (searchCell.getY()+"").equals(pieceString[1]))
                    {
                        if (pieceString[2].equals("1"))
                        {
                            switch (pieceString[5])
                            {
                                case "Sniper":
                                    player1.getSniper().setCurrentCell(searchCell);
                                    searchCell.setPiece(player1.getSniper());
                                    if (pieceString[3].equals("0"))
                                        player1.getSniper().setIsAlive(false);
                                    else if (pieceString[3].equals("1"))
                                        player1.getSniper().setIsAlive(true);

                                    if (pieceString[4].equals("0"))
                                        player1.getSniper().setIsActivated(false);
                                    else if (pieceString[4].equals("1"))
                                        player1.getSniper().setIsActivated(true);
                                    break;
                                case "Thief":
                                    player1.getThief().setCurrentCell(searchCell);
                                    searchCell.setPiece(player1.getThief());
                                    if (pieceString[3].equals("0"))
                                        player1.getThief().setIsAlive(false);
                                    else if (pieceString[3].equals("1"))
                                        player1.getThief().setIsAlive(true);

                                    if (pieceString[4].equals("0"))
                                        player1.getThief().setIsActivated(false);
                                    else if (pieceString[4].equals("1"))
                                        player1.getThief().setIsActivated(true);
                                    break;
                                case "Bomber":
                                    player1.getBomber().setCurrentCell(searchCell);
                                    searchCell.setPiece(player1.getBomber());
                                    if (pieceString[3].equals("0"))
                                        player1.getBomber().setIsAlive(false);
                                    else if (pieceString[3].equals("1"))
                                        player1.getBomber().setIsAlive(true);

                                    if (pieceString[4].equals("0"))
                                        player1.getBomber().setIsActivated(false);
                                    else if (pieceString[4].equals("1"))
                                        player1.getBomber().setIsActivated(true);
                                    break;
                                case "Healer":
                                    player1.getHealer().setCurrentCell(searchCell);
                                    searchCell.setPiece(player1.getHealer());
                                    if (pieceString[3].equals("0"))
                                        player1.getHealer().setIsAlive(false);
                                    else if (pieceString[3].equals("1"))
                                        player1.getHealer().setIsAlive(true);

                                    if (pieceString[4].equals("0"))
                                        player1.getHealer().setIsActivated(false);
                                    else if (pieceString[4].equals("1"))
                                        player1.getHealer().setIsActivated(true);
                                    break;
                            }
                        }
                        else if (pieceString[2].equals("2"))
                        {
                            switch (pieceString[5])
                            {
                                case "Sniper":
                                    player2.getSniper().setCurrentCell(searchCell);
                                    searchCell.setPiece(player2.getSniper());
                                    if (pieceString[3].equals("0"))
                                        player2.getSniper().setIsAlive(false);
                                    else if (pieceString[3].equals("1"))
                                        player2.getSniper().setIsAlive(true);

                                    if (pieceString[4].equals("0"))
                                        player2.getSniper().setIsActivated(false);
                                    else if (pieceString[4].equals("1"))
                                        player2.getSniper().setIsActivated(true);
                                    break;
                                case "Thief":
                                    player2.getThief().setCurrentCell(searchCell);
                                    searchCell.setPiece(player2.getThief());
                                    if (pieceString[3].equals("0"))
                                        player2.getThief().setIsAlive(false);
                                    else if (pieceString[3].equals("1"))
                                        player2.getThief().setIsAlive(true);

                                    if (pieceString[4].equals("0"))
                                        player2.getThief().setIsActivated(false);
                                    else if (pieceString[4].equals("1"))
                                        player2.getThief().setIsActivated(true);
                                    break;
                                case "Bomber":
                                    player2.getBomber().setCurrentCell(searchCell);
                                    searchCell.setPiece(player2.getBomber());
                                    if (pieceString[3].equals("0"))
                                        player2.getBomber().setIsAlive(false);
                                    else if (pieceString[3].equals("1"))
                                        player2.getBomber().setIsAlive(true);

                                    if (pieceString[4].equals("0"))
                                        player2.getBomber().setIsActivated(false);
                                    else if (pieceString[4].equals("1"))
                                        player2.getBomber().setIsActivated(true);
                                    break;
                                case "Healer":
                                    player2.getHealer().setCurrentCell(searchCell);
                                    searchCell.setPiece(player2.getHealer());
                                    if (pieceString[3].equals("0"))
                                        player2.getHealer().setIsAlive(false);
                                    else if (pieceString[3].equals("1"))
                                        player2.getHealer().setIsAlive(true);

                                    if (pieceString[4].equals("0"))
                                        player2.getHealer().setIsActivated(false);
                                    else if (pieceString[4].equals("1"))
                                        player2.getHealer().setIsActivated(true);
                                    break;
                            }
                        }
                    }
                    newStartingCell = searchCell;
                }
                startingCells.put(newStartingCell, Integer.parseInt(pieceString[2]));
            }
            scanner.nextLine();

            scanner.nextLine();
            for (int i = 0; i<50; i++)
            {
                String[] wallString = scanner.nextLine().replace("\n", "").split(" ");
                Cell startWallCell = null;
                for (Cell searchCell : cells)
                {
                    if ((searchCell.getX()+"").equals(wallString[0]) && (searchCell.getY()+"").equals(wallString[1]))
                        startWallCell = searchCell;
                }
                Cell endWallCell = null;
                for (Cell searchCell : cells)
                {
                    if ((searchCell.getX()+"").equals(wallString[2]) && (searchCell.getY()+"").equals(wallString[3]))
                        endWallCell = searchCell;
                }
                assert startWallCell != null;
                startWallCell.removeFromAdjacentOpenCells(endWallCell);
                assert endWallCell != null;
                endWallCell.removeFromAdjacentOpenCells(startWallCell);
                Wall newWall = new Wall(startWallCell, endWallCell);
                walls.add(newWall);
            }
            scanner.nextLine();

            scanner.nextLine();
            for (int i = 0; i < 12; i++)
            {
                String[] snakeString = scanner.nextLine().replace("\n", "").split(" ");

                Cell startTransmitter = null;
                Cell endTransmitter = null;

                for (Cell searchCell : cells)
                {
                    if (searchCell.getX()==Integer.parseInt(snakeString[0])&&searchCell.getY()==Integer.parseInt(snakeString[1]))
                        startTransmitter = searchCell;
                }
                for (Cell searchCell : cells)
                {
                    if (searchCell.getX() == Integer.parseInt(snakeString[2]) && searchCell.getY() == Integer.parseInt(snakeString[3]))
                        endTransmitter = searchCell;
                }

                if (Integer.parseInt(snakeString[4])==0)
                {
                    Normal newTransmitter = new Normal(startTransmitter, endTransmitter);
                    transmitters.add(newTransmitter);
                }
                else if (Integer.parseInt(snakeString[4])==1)
                {
                    Magical newTransmitter = new Magical(startTransmitter, endTransmitter);
                    transmitters.add(newTransmitter);
                }
                else if (Integer.parseInt(snakeString[4])==2)
                {
                    Deadly newTransmitter = new Deadly(startTransmitter, endTransmitter);
                    transmitters.add(newTransmitter);
                }
                else if (Integer.parseInt(snakeString[4])==3)
                {
                    Earthworm newTransmitter = new Earthworm(startTransmitter, endTransmitter);
                    transmitters.add(newTransmitter);
                }
            }
            scanner.nextLine();

            String[] tempString  = scanner.nextLine().replace("\n", "").split(" ");
            for (int i = 0; i < Integer.parseInt(tempString[1]); i++)
            {
                String[] prizeArray = scanner.nextLine().replace("\n", "").split(" ");
                for (Cell searchCell : cells)
                {
                    if (searchCell.getX()==Integer.parseInt(prizeArray[0]) && searchCell.getY()==Integer.parseInt(prizeArray[1]))
                    {
                        Prize newPrize = new Prize(searchCell, Integer.parseInt(prizeArray[2]), Integer.parseInt(prizeArray[3]), Integer.parseInt(prizeArray[4]));
                        searchCell.setPrize(newPrize);
                    }
                }
            }
            scanner.nextLine();

            scanner.nextLine();
            String[] player1Info = scanner.nextLine().replace("\n", "").split(" ");
            String[] player2Info = scanner.nextLine().replace("\n", "").split(" ");
            player1.setScore(Integer.parseInt(player1Info[0]));
            player2.setScore(Integer.parseInt(player2Info[0]));
            if (player1Info[1].equals("0") && player1Info[2].equals("0") && player1Info[3].equals("0") && player1Info[4].equals("0") && player1Info[5].equals("0"))
                player1.getThief().setCurrentPrize(null);
            else
            {
                for (Cell searchCell : cells)
                {
                    if (searchCell.getX()==Integer.parseInt(player1Info[1])&&searchCell.getY()==Integer.parseInt(player1Info[2]))
                    {
                        Prize newPrize = new Prize(searchCell, Integer.parseInt(player1Info[3]), Integer.parseInt(player1Info[4]), Integer.parseInt(player1Info[5]));
                        player1.getThief().setCurrentPrize(newPrize);
                    }
                }
            }
            if (player2Info[1].equals("0") && player2Info[2].equals("0") && player2Info[3].equals("0") && player2Info[4].equals("0") && player2Info[5].equals("0"))
                player2.getThief().setCurrentPrize(null);
            else
            {
                for (Cell searchCell : cells)
                {
                    if (searchCell.getX()==Integer.parseInt(player2Info[1])&&searchCell.getY()==Integer.parseInt(player2Info[2]))
                    {
                        Prize newPrize = new Prize(searchCell, Integer.parseInt(player2Info[3]), Integer.parseInt(player2Info[4]), Integer.parseInt(player2Info[5]));
                        player2.getThief().setCurrentPrize(newPrize);
                    }
                }
            }
            for (int i = 6; i<12; i++)
            {
                player1.getDice().setChance(i-5, Integer.parseInt(player1Info[i]));
                player2.getDice().setChance(i-5, Integer.parseInt(player2Info[i]));
            }
            scanner.nextLine();

            Board board = new Board(cells, transmitters, walls, startingCells);

            scanner.nextLine();

            int turn = scanner.nextInt();
            GameState gameState = new GameState(board, player1, player2, turn);

            int isDicePlayedNumber = scanner.nextInt();
            boolean isDicePlayed = isDicePlayedNumber != 0;
            int diceNumber = scanner.nextInt();
            int player1LastDice = scanner.nextInt();
            int player2LastDice = scanner.nextInt();
            player1.getDice().setLastDice(player1LastDice);
            player2.getDice().setLastDice(player2LastDice);

            gameState.getCurrentPlayer().setDicePlayedThisTurn(isDicePlayed);
            if (isDicePlayed)
                gameState.getCurrentPlayer().setMoveLeft(diceNumber);

            scanner.close();
            return gameState;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            System.err.println("could not find board file");
            System.exit(-2);
        }
        return null;
    }
}