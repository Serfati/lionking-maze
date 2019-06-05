package Model.algorithms.mazeGenerators;

import java.util.Random;

/**
 * A simple way to create a maze
 *
 * @author Serfati
 * @version 1.0
 * @since 25-Mar-19
 */

public class SimpleMazeGenerator extends AMazeGenerator {

    /**
     *     This method receives int [][] "pMazeArray" and a startPosition
     *     It builds a random path filled with '0's
     *     Returns the goalPosition of this path
     *     Building stairs :
     * @return - Goal position of the maze
     * @param pMazeArray - 2D integer array / represent a maze
     * @param startPosition - startPosition
     */
    private static Position digg(int[][] pMazeArray, Position startPosition) {
        Position goalPosition;
        int currentRow = startPosition.getRowIndex();
        int currentCol = 1;
        int maxRow = pMazeArray.length - 2;
        int maxCol = pMazeArray[ 0 ].length - 2;

        // Checks if path runs Up or Down
        int direction = 1; // "Up-Right"
        if ( currentRow > ( maxRow / 2 ) )
            direction = -1; // "Down-Right"

        while (currentCol <= maxCol && currentRow <= maxRow && currentRow >= 2) {
            pMazeArray[ currentRow ][ currentCol ] = 0;
            currentRow = currentRow + direction;
            pMazeArray[ currentRow ][ currentCol ] = 0;
            currentCol++;
        }
        goalPosition = new Position(currentRow, currentCol);
        return goalPosition;
    }

    /**
     * This method create a new solvable maze by random injection of walls.
     * {@inheritDoc}
     */
    @Override
    public Maze generate(int row, int column) {
        if (row < 2 || column < 2) return null;

        int[][] mMazeArray = new int[ row ][ column ];
        // Init maze ( 2D char array ) with random '0's and '1's
        for (int i = 0; i < row; i++)
            for (int j = 0; j < column; j++)
                mMazeArray[ i ][ j ] = rangeRandom();

        // Select random point and open as start node
        Random rg = new Random();
        int x = rg.nextInt(row);
        // Avoid the Random of x = 0 / 1 / row-1
        if ( x > row / 2 ) {
            x -= 2;
        } else {
            x += 2;
        }
        // Set startPosition,goalPosition
        Position startPosition = new Position(x, 0);
        Position goalPosition = digg(mMazeArray, startPosition);

        try {
            return new Maze(mMazeArray, startPosition, goalPosition);
        } catch (Exception e) {
            return null;
        }
    }
}