package Model.Server;

import Model.algorithms.mazeGenerators.Maze;
import Model.algorithms.search.SearchableMaze;
import Model.algorithms.search.Solution;
import sun.awt.Mutex;

import java.io.*;

/**
 * This class is the implementation of the maze solution strategy
 */
public class ServerStrategySolveSearchProblem implements IServerStrategy {
    private String tempDirectoryPath = System.getProperty("/tmp"); //java.io.tmpdir
    private static Mutex m = new Mutex();

    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) throws IOException {
        Solution solution;
        ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
        ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
        toClient.flush();
        try {
            Maze maze = (Maze) fromClient.readObject();
            String tempPath = tempDirectoryPath+"maze"+maze.toString().hashCode();
            m.lock();
            File f = new File(tempPath);
            if (!f.exists()) { //we need to solve the problem
                m.unlock();
                SearchableMaze searchableMaze = new SearchableMaze(maze);
                solution = Configurations.getSearchingAlgorithm().solve(searchableMaze);
                m.lock();
                final boolean newFile = f.createNewFile();
                FileOutputStream fout = new FileOutputStream(tempPath);
                fout.flush();
                ObjectOutputStream oout = new ObjectOutputStream(fout);
                oout.flush();
                oout.writeObject(solution);
                oout.flush();
                m.unlock();
            } else { // the file exists, we don't need to solve again. only take what exists.
                FileInputStream fin = new FileInputStream(tempPath);
                ObjectInputStream oin = new ObjectInputStream(fin);
                solution = (Solution) oin.readObject();
                fin.close();
                oin.close();
                m.unlock();
            }
            toClient.writeObject(solution);
            toClient.flush();
        } catch(Exception e) {
            System.out.println("Exception in ServerStrategySolveSearchProblem");
        }
    }
}
