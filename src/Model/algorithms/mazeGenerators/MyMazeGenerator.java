package Model.algorithms.mazeGenerators;

import Model.algorithms.search.MazeState;

import java.util.ArrayList;

/******************************************************************************
 * ~-=-=-~-~=~-=-=-~=~-=-=-~= Prim's algorithm  ~-==-=-~=~-=-=~-=-=-~=-~=     *
 ******************************************************************************
 * This class inherit the abstract class AMazeGenerator that implements the   *
 * IMazeGenerator interface. This algorithm is a greedy algorithm that finds  *
 * a minimum spanning tree (MST) for a weighted undirected graph              *
 * This class creates random Maze with Prim's algorithm for generating mazes. *
 *                                                                            *
 *  @author Serfati                                                           *
 *  @version 1.7                                                              *
 *  @since 31-Mar-19                                                          *
 *  @see Model.algorithms.mazeGenerators                                            *
 *                                                                            *
 ******************************************************************************/

public class MyMazeGenerator extends AMazeGenerator {

    /**
     * The generate function being overwritten. Creates the maze.
     * {@inheritDoc}
     */
    @Override
    public Maze generate(int row, int column) {
        if (row < 2 || column < 2) return null;

        setRow(row);
        setColumn(column);

        // Build maze and initialize with only walls (1 - Ones)
        int[][] mMazeArray = new int[row][column];
        for(int i = 0; i < getRow(); i++)
            for(int j = 0; j < getColumn(); j++)
                mMazeArray[i][j] = 1;

        return runPRIMs(mMazeArray); // first run of algorithm
    }

    /**
     * The actual algorithm. Receives the maze
     *
     * @param pMaze - 2D integer array / represent a maze
     * @return A generated Maze by Prim's Algorithm, null on fail
     */
    private Maze runPRIMs(int[][] pMaze) {
        // Select random point and open as start node
        Position goalPosition = null;
        Position startPosition = new Position((int) (Math.random() * getRow()), 0);
        MazeState START = new MazeState(0, null, startPosition);
        // Iterate through direct neighbors of node
        ArrayList<MazeState> frontier = new ArrayList<>();
        iterate(pMaze, START, frontier);

        MazeState last = null;
        try {
            while(!frontier.isEmpty()) {

                // Pick current node at random
                MazeState curState = frontier.remove((int) (Math.random() * frontier.size()));
                MazeState opoState = opposite(curState);
                try {
                    // If both node and its opposite are walls
                    if (pMaze[curState.getPosition().getRowIndex()][curState.getPosition().getColumnIndex()] == 1) {
                        assert opoState != null;
                        if (pMaze[opoState.getPosition().getRowIndex()][opoState.getPosition().getColumnIndex()] == 1) {
                            // Open path between the nodes
                            pMaze[curState.getPosition().getRowIndex()][curState.getPosition().getColumnIndex()] = 0;
                            pMaze[opoState.getPosition().getRowIndex()][opoState.getPosition().getColumnIndex()] = 0;
                            // Store last node in order to mark it later
                            last = opoState;
                            // Iterate through direct neighbors of node, same as earlier
                            iterate(pMaze, opoState, frontier);
                        }
                    }
                } catch(Exception ignored) {
                } // Ignore NullPointer and ArrayIndexOutOfBounds
                // If algorithm has resolved, mark end node
                if (frontier.isEmpty()) {
                    assert last != null;
                    goalPosition = new Position(last.getPosition().getRowIndex(), last.getPosition().getColumnIndex());
                }
            }
            return new Maze(pMaze, startPosition, goalPosition);
        } catch(Exception e) {
            return null;
        }
    }

    /**
     * Iterate through direct neighbors of node
     *
     * @param pMaze    - 2D integer array / represent a maze
     * @param pState   - the state we stand on
     * @param frontier - ArrayList will contain all states we can move on
     */
    private void iterate(int[][] pMaze, MazeState pState, ArrayList<MazeState> frontier) {
        try {
            for(int x = -1; x <= 1; x++)
                for(int y = -1; y <= 1; y++) {
                    if (x == 0 && y == 0 || x != 0 && y != 0) continue;
                    try {
                        if (pMaze[pState.getPosition().getRowIndex()+x][pState.getPosition().getColumnIndex()+y] == 0)
                            continue;
                    } catch(Exception e) {
                        continue;
                    } // ignore ArrayIndexOutOfBounds
                    // Add eligible points to frontier
                    frontier.add(new MazeState(pState.getCost(), pState, new Position(pState.getPosition().getRowIndex()+x, pState.getPosition().getColumnIndex()+y)));
                }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * [1][a][1]
     * [1][b][1]
     * [1][c][1]
     * In this example:
     * 'c' is the Parent of 'b'
     * => 'a' is the Opposite of 'b'
     * "Opposite from the Parent's side"
     *
     * @param current - current state we stand on the maze
     * @return the opposite state according to @current position
     * @see MazeState
     */
    private MazeState opposite(MazeState current) {
        try {
            MazeState parent = (MazeState) current.getCameFrom();
            Position myPosition = current.getPosition();
            Position pParent = parent.getPosition();
            if (current.getCameFrom() != null) {
                if (myPosition.getRowIndex() != pParent.getRowIndex())
                    return new MazeState(current.getCost(), current, new Position(myPosition.getRowIndex()+Integer.compare(myPosition.getRowIndex(), pParent.getRowIndex()), myPosition.getColumnIndex()));
                if (myPosition.getColumnIndex() != pParent.getColumnIndex())
                    return new MazeState(current.getCost(), current, new Position(myPosition.getRowIndex(), myPosition.getColumnIndex()+Integer.compare(myPosition.getColumnIndex()+myPosition.getColumnIndex(), pParent.getColumnIndex())));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}