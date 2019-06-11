package Model;

import Model.Client.Client;
import Model.Client.IClientStrategy;
import Model.IO.MyDecompressorInputStream;
import Model.Server.Configurations;
import Model.Server.Server;
import Model.Server.ServerStrategyGenerateMaze;
import Model.Server.ServerStrategySolveSearchProblem;
import Model.algorithms.mazeGenerators.Maze;
import Model.algorithms.mazeGenerators.MyMazeGenerator;
import Model.algorithms.search.Solution;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyModel extends Observable implements IModel {
    private MazeCharacter mainCharacter = new MazeCharacter("Main_", 0, 0);
    private MazeCharacter secondCharacter = new MazeCharacter("Second_", 0, 0);
    private Maze maze;
    private Solution mazeSolution;
    private boolean isAtTheEnd;

    private Server serverMazeGenerator;
    private Server serverSolveMaze;

    private ExecutorService threadPool = Executors.newCachedThreadPool();

    public MyModel() {
        Configurations.runConf();
        isAtTheEnd = false;
        startServers();

    }

    private void startServers() {
        serverMazeGenerator = new Server(5400, 1000, new ServerStrategyGenerateMaze());
        serverSolveMaze = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
        serverMazeGenerator.start();
        serverSolveMaze.start();
    }

    private void closeServers() {
        serverMazeGenerator.stop();
        serverSolveMaze.stop();
    }

    @Override
    public void generateMaze(int row, int col) {
        try {
            Client clientMazeGenerator = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        int[] mazeDimensions = new int[]{row, col};
                        toServer.writeObject(mazeDimensions); //send maze dimensions to server
                        toServer.flush();
                        byte[] compressedMaze = (byte[]) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                        byte[] decompressedMaze = new byte[mazeDimensions[0] * mazeDimensions[1]+12 /*CHANGE SIZE ACCORDING TO YOU MAZE SIZE*/]; //allocating byte[] for the decompressed maze -
                        is.read(decompressedMaze); //Fill decompressedMaze with bytes
                        maze = new Maze(decompressedMaze);
                        toServer.close();
                        fromServer.close();
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            });
                clientMazeGenerator.communicateWithServer();
            MyMazeGenerator m = new MyMazeGenerator();
            this.maze = m.generate(row, col);
            int mazeRow = maze.getStartPosition().getRowIndex();
                int mazeCol = maze.getStartPosition().getColumnIndex();
            mainCharacter = new MazeCharacter("Main_", mazeRow, mazeCol);

                isAtTheEnd = false;
                setChanged();
                notifyObservers("Maze");
        } catch(UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void moveCharacter(KeyCode movement) {
        boolean legitKey = false;
        int mainCharacterPositionRow = mainCharacter.getCharacterRow();
        int mainCharacterPositionCol = mainCharacter.getCharacterCol();
        switch(movement) {
            case UP:
            case W:
            case NUMPAD8:
                legitKey = true;
                mainCharacter.setCharacterDirection("back");
                if (false)
                    secondCharacter.setCharacterDirection(mainCharacter.getCharacterDirection());
                if (isNotWall(mainCharacterPositionRow-1, mainCharacterPositionCol)) {
                    if (false) {
                        secondCharacter.setCharacterRow(mainCharacterPositionRow);
                        secondCharacter.setCharacterCol(mainCharacterPositionCol);
                    }
                    mainCharacter.setCharacterRow(mainCharacterPositionRow-1);

                }
                break;
            case DOWN:
            case X:
            case NUMPAD2:
                legitKey = true;
                mainCharacter.setCharacterDirection("front");
                if (false)
                    secondCharacter.setCharacterDirection(mainCharacter.getCharacterDirection());
                if (isNotWall(mainCharacterPositionRow+1, mainCharacterPositionCol)) {
                    if (false) {
                        secondCharacter.setCharacterRow(mainCharacterPositionRow);
                        secondCharacter.setCharacterCol(mainCharacterPositionCol);
                    }
                    mainCharacter.setCharacterRow(mainCharacterPositionRow+1);
                }
                break;
            case LEFT:
            case A:
            case NUMPAD4:
                legitKey = true;
                mainCharacter.setCharacterDirection("left");
                if (false)
                    secondCharacter.setCharacterDirection(mainCharacter.getCharacterDirection());
                if (isNotWall(mainCharacterPositionRow, mainCharacterPositionCol-1)) {
                    if (false) {
                        secondCharacter.setCharacterRow(mainCharacterPositionRow);
                        secondCharacter.setCharacterCol(mainCharacterPositionCol);
                    }
                    mainCharacter.setCharacterCol(mainCharacterPositionCol-1);
                }
                break;
            case RIGHT:
            case D:
            case NUMPAD6:
                legitKey = true;
                mainCharacter.setCharacterDirection("right");
                if (false)
                    secondCharacter.setCharacterDirection(mainCharacter.getCharacterDirection());
                if (isNotWall(mainCharacterPositionRow, mainCharacterPositionCol+1)) {
                    if (false) {
                        secondCharacter.setCharacterRow(mainCharacterPositionRow);
                        secondCharacter.setCharacterCol(mainCharacterPositionCol);
                    }
                    mainCharacter.setCharacterCol(mainCharacterPositionCol+1);
                }
                break;
            case Q:
            case NUMPAD7:
                legitKey = true;
                mainCharacter.setCharacterDirection("left");
                if (false)
                    secondCharacter.setCharacterDirection(mainCharacter.getCharacterDirection());
                if (isNotWall(mainCharacterPositionRow-1, mainCharacterPositionCol-1) && (isNotWall(mainCharacterPositionRow, mainCharacterPositionCol-1) || isNotWall(mainCharacterPositionRow-1, mainCharacterPositionCol))) {
                    if (false) {
                        secondCharacter.setCharacterRow(mainCharacterPositionRow);
                        secondCharacter.setCharacterCol(mainCharacterPositionCol);
                    }
                    mainCharacter.setCharacterRow(mainCharacterPositionRow-1);
                    mainCharacter.setCharacterCol(mainCharacterPositionCol-1);
                }
                break;
            case E:
            case NUMPAD9:
                legitKey = true;
                mainCharacter.setCharacterDirection("right");
                if (false)
                    secondCharacter.setCharacterDirection(mainCharacter.getCharacterDirection());
                if (isNotWall(mainCharacterPositionRow-1, mainCharacterPositionCol+1) && (isNotWall(mainCharacterPositionRow, mainCharacterPositionCol+1) || isNotWall(mainCharacterPositionRow-1, mainCharacterPositionCol))) {
                    if (false) {
                        secondCharacter.setCharacterRow(mainCharacterPositionRow);
                        secondCharacter.setCharacterCol(mainCharacterPositionCol);
                    }
                    mainCharacter.setCharacterRow(mainCharacterPositionRow-1);
                    mainCharacter.setCharacterCol(mainCharacterPositionCol+1);
                }
                break;
            case Z:
            case NUMPAD1:
                legitKey = true;
                mainCharacter.setCharacterDirection("left");
                if (false)
                    secondCharacter.setCharacterDirection(mainCharacter.getCharacterDirection());
                if (isNotWall(mainCharacterPositionRow+1, mainCharacterPositionCol-1) && (isNotWall(mainCharacterPositionRow, mainCharacterPositionCol-1) || isNotWall(mainCharacterPositionRow+1, mainCharacterPositionCol))) {
                    if (false) {
                        secondCharacter.setCharacterRow(mainCharacterPositionRow);
                        secondCharacter.setCharacterCol(mainCharacterPositionCol);
                    }
                    mainCharacter.setCharacterRow(mainCharacterPositionRow+1);
                    mainCharacter.setCharacterCol(mainCharacterPositionCol-1);
                }
                break;
            case C:
            case NUMPAD3:
                legitKey = true;
                mainCharacter.setCharacterDirection("right");
                if (false)
                    secondCharacter.setCharacterDirection(mainCharacter.getCharacterDirection());
                if (isNotWall(mainCharacterPositionRow+1, mainCharacterPositionCol+1) && (isNotWall(mainCharacterPositionRow, mainCharacterPositionCol+1) || isNotWall(mainCharacterPositionRow+1, mainCharacterPositionCol))) {
                    if (false) {
                        secondCharacter.setCharacterRow(mainCharacterPositionRow);
                        secondCharacter.setCharacterCol(mainCharacterPositionCol);
                    }
                    mainCharacter.setCharacterRow(mainCharacterPositionRow+1);
                    mainCharacter.setCharacterCol(mainCharacterPositionCol+1);
                }
                break;
            default:
                break;

        }

        if (maze.getCharAt(mainCharacter.getCharacterRow(), mainCharacter.getCharacterCol()) == 'E')
            isAtTheEnd = true;
        if (maze.getCharAt(secondCharacter.getCharacterRow(), secondCharacter.getCharacterCol()) == 'E')
            isAtTheEnd = true;

        if (legitKey) {
            setChanged();
            notifyObservers("Character");
        }

    }

    @Override
    public void generateSolution() {
        try {
            Client clientSolveMaze = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        toServer.writeObject(new Maze(maze, mainCharacter.getCharacterRow(), mainCharacter.getCharacterCol())); //send maze to server
                        toServer.flush();
                        mazeSolution = (Solution) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
                        toServer.close();
                        fromServer.close();
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            clientSolveMaze.communicateWithServer();
            setChanged();
            notifyObservers("Solution");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeModel() {
        System.out.println("Close Model");
        closeServers();
        threadPool.shutdown();
    }

    @Override
    public void saveOriginalMaze(File file, String name) {
        try {
            FileOutputStream fileWriter = null;
            fileWriter = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileWriter);
            MazeCharacter mazeCharacter = new MazeCharacter(name, maze.getStartPosition().getRowIndex(), maze.getStartPosition().getColumnIndex());
            MazeDisplayState mazeDisplayState = new MazeDisplayState(maze, mazeCharacter);
            objectOutputStream.writeObject(mazeDisplayState);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileWriter.close();
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void saveCurrentMaze(File file, String name) {
        try {
            FileOutputStream fileWriter;
            fileWriter = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileWriter);
            Maze currentMaze = getCurrentMaze();
            MazeCharacter mazeCharacter = new MazeCharacter(name, mainCharacter.getCharacterRow(), mainCharacter.getCharacterCol());
            MazeDisplayState mazeDisplayState = new MazeDisplayState(currentMaze, mazeCharacter);
            objectOutputStream.writeObject(mazeDisplayState);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileWriter.close();
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    private Maze getCurrentMaze() {
        try {
            return new Maze(maze, mainCharacter.getCharacterRow(), mainCharacter.getCharacterCol());
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public void loadMaze(File file) {
        try {
            FileInputStream fin = new FileInputStream(file);
            ObjectInputStream oin = new ObjectInputStream(fin);
            MazeDisplayState loadedMazeDisplayState = (MazeDisplayState) oin.readObject();
            if (loadedMazeDisplayState != null) {
                maze = loadedMazeDisplayState.getMaze();
                mainCharacter = loadedMazeDisplayState.getMazeCharacter();
                isAtTheEnd = false;
                setChanged();
                notifyObservers("Maze Load");
            }
            fin.close();
            oin.close();
        } catch(IOException | ClassNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Loaded Maze ERROR");
            alert.setHeaderText("Exception caught trying to load:\n"+file.getName());
            alert.setGraphic(null);
            alert.setContentText("Loaded file was not a saved maze!\nPlease load the right type of file");
            alert.show();
        }
    }

    private boolean isNotWall(int row, int col) {
        char c = maze.getCharAt(row, col);
        return (c == 'S' || c == '0' || c == 'E');
    }
    @Override
    public MazeCharacter getLoadedCharacter() {
        return mainCharacter;
    }

    @Override
    public int[][] getMazeInt() {
        return this.maze.mMaze;
    }

    @Override
    public char[][] getMaze() {
        return maze.getMaze();
    }

    @Override
    public int getMainCharacterPositionRow() {
        return mainCharacter.getCharacterRow();
    }

    @Override
    public int getMainCharacterPositionColumn() {
        return mainCharacter.getCharacterCol();
    }

    @Override
    public String getMainCharacterDirection() {
        return mainCharacter.getCharacterDirection();
    }

    @Override
    public int[][] getSolution() {
        return mazeSolution.getSolution();
    }

    @Override
    public boolean isAtTheEnd() {
        return isAtTheEnd;
    }
}
