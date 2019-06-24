package Model.test;

import Model.IO.MyCompressorOutputStream;
import Model.IO.MyDecompressorInputStream;
import Model.algorithms.mazeGenerators.AMazeGenerator;
import Model.algorithms.mazeGenerators.Maze;
import Model.algorithms.mazeGenerators.MyMazeGenerator;

import java.io.*;
import java.util.Arrays;

/**
 * Created by Aviadjo on 3/26/2017.
 */
public class RunCompressDecompressMaze {
    public static void main(String[] args) {
        String mazeFileName = "savedMaze.maze";
        AMazeGenerator mazeGenerator = new MyMazeGenerator();
        Maze maze = mazeGenerator.generate(15, 15); //Generate new maze

        try {
            maze.print();
            System.out.println();
            // save maze to a file
            OutputStream out = new MyCompressorOutputStream(new FileOutputStream(mazeFileName));
            out.write(maze.toByteArray());
            out.flush();
            out.close();
        } catch(IOException e) {
            e.printStackTrace();
        }

        byte[] savedMazeBytes = new byte[0];
        try {
            //read maze from file
            InputStream in = new MyDecompressorInputStream(new FileInputStream(mazeFileName));
            savedMazeBytes = new byte[maze.toByteArray().length];
            in.read(savedMazeBytes);
            in.close();
        } catch(IOException e) {
            e.printStackTrace();
        }

        Maze loadedMaze = new Maze(savedMazeBytes);
        loadedMaze.print();
        boolean areMazesEquals = Arrays.equals(loadedMaze.toByteArray(), maze.toByteArray());
        System.out.println(String.format("Mazes equal: %s", areMazesEquals)); //maze should be equal to loadedMaze
    }
}