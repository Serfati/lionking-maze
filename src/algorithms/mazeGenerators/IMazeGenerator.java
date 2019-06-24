package Model.algorithms.mazeGenerators;

/**
 * An interface of algorithms.mazeGenerators.Maze Generator algorithms
 * that provides a common functions.
 *
 * @author Serfati
 * @version 1.0
 * @since 20-03-2019
 */
public interface IMazeGenerator {

    /**
     * This method create a new maze by the row and column parameters.
     * @param row is the numbers of row requested in the maze
     * @param column is the numbers of column requested in the maze
     * @return A Maze
     */
    Maze generate(int row, int column);

    /**
     * This method measures the time it takes to build the maze using the
     * generate method.
     * @param row is the numbers of row requested in the maze
     * @param column is the numbers of column requested in the maze
     * @return long the time that take to create a maze by the generate method.
     */
    long measureAlgorithmTimeMillis(int row, int column);
}
