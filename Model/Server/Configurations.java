package Server;

import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.SimpleMazeGenerator;
import algorithms.search.BestFirstSearch;
import algorithms.search.BreadthFirstSearch;
import algorithms.search.DepthFirstSearch;
import algorithms.search.ISearchingAlgorithm;

import java.io.*;
import java.util.Objects;
import java.util.Properties;


final class Configurations {
    private static Properties properties = new Properties();

    synchronized static void runConf() {
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
