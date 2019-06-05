package Model.Server;

import Model.IO.MyCompressorOutputStream;
import Model.algorithms.mazeGenerators.IMazeGenerator;
import Model.algorithms.mazeGenerators.Maze;

import java.io.*;

/**
 * This class is the implementation of the Generate maze strategy
 */
public class ServerStrategyGenerateMaze implements IServerStrategy {

    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) throws IOException {
        ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
        ObjectOutputStream toClientObject = new ObjectOutputStream(outToClient);
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        MyCompressorOutputStream toClient = new MyCompressorOutputStream(byteOut);

        int[] mazeDimensions;

        try {
            mazeDimensions = (int[]) fromClient.readObject();
            IMazeGenerator mg = Configurations.getMazeGenerator();
            Maze maze = mg.generate(mazeDimensions[0], mazeDimensions[1]);
            byte[] mazeByteArray = maze.toByteArray();
            toClient.write(mazeByteArray);
            toClientObject.writeObject(byteOut.toByteArray());
            toClientObject.flush();
        } catch(Exception e) {
            System.out.println("Exception in ServerStrategyGenerateMaze");
            e.printStackTrace();
        }
    }
}
