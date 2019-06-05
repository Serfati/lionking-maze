package Model.algorithms.mazeGenerators;

/**
 * A simple way to create a maze
 *
 * @author MoranChery
 * @version 1.0
 * @since 25-Mar-19
 */

public class EmptyMazeGenerator extends AMazeGenerator {

    @Override
    public Maze generate(int row, int column) {
        if (row < 2 || column < 2) return null;
        int[][] emptyMaze = new int[ row ][ column ];
        Position start = new Position(0, 0);
        Position end = new Position(row - 1, column - 1);
        return new Maze(emptyMaze , start , end);
    }
}
