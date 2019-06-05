package Model.Server;

import Model.algorithms.mazeGenerators.IMazeGenerator;
import Model.algorithms.mazeGenerators.MyMazeGenerator;
import Model.algorithms.mazeGenerators.SimpleMazeGenerator;
import Model.algorithms.search.BestFirstSearch;
import Model.algorithms.search.BreadthFirstSearch;
import Model.algorithms.search.DepthFirstSearch;
import Model.algorithms.search.ISearchingAlgorithm;

import java.io.*;
import java.util.Objects;
import java.util.Properties;


public final class Configurations {
    private static Properties properties = new Properties();

    public synchronized static void runConf() {
        try {
            File f = new File("resources/config.properties");
            if (!f.exists()) createConf();
            InputStream input = new FileInputStream("resources/config.properties");
            properties.load(input);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static void createConf() {
        OutputStream output;
        try {
            output = new FileOutputStream("resources/config.properties");
            // set the ProjectProperties value
            properties.setProperty("MazeGenerator", "MyMazeGenerator");
            properties.setProperty("NumberOfThreads", "7");
            properties.setProperty("SearchingAlgorithm", "BFS");
            // save ProjectProperties to project root folder
            properties.store(output, null);

        } catch(IOException io) {
            io.printStackTrace();
        }
    }

    static int getNumberOfThreads() {
        String value = properties.getProperty("NumberOfThreads");
        int threadNum = Objects.equals(value, "") ? 10 : Integer.parseInt(value);
        return threadNum > 0 ? threadNum : 10;
    }

    static IMazeGenerator getMazeGenerator() {
        String value = properties.getProperty("MazeGenerator");
        return Objects.equals(value, "SimpleMazeGenerator") ? new SimpleMazeGenerator() : new MyMazeGenerator();
    }

    static ISearchingAlgorithm getSearchingAlgorithm() {
        String value = properties.getProperty("SearchingAlgorithm");
        if (Objects.equals(value, "DepthFirstSearch"))
            return new DepthFirstSearch();
        else if (Objects.equals(value, "BestFirstSearch"))
            return new BestFirstSearch();
        else
            return new BreadthFirstSearch();
    }
}
