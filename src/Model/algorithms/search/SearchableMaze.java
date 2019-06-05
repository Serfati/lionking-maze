package Model.algorithms.search;

import Model.algorithms.mazeGenerators.Maze;
import Model.algorithms.mazeGenerators.Position;

import java.util.ArrayList;

/**
 * Searchable algorithms.mazeGenerators.Maze class
 * Used as Object Adapter
 *
 * @author Serfati
 * @version 1.0
 * @since 20-03-2019
 */
public class SearchableMaze implements ISearchable {
    private Maze maze;
    private MazeState[][] myMazeSra;

    /**
     * Constructor
     *
     * @param maze A Maze.
     */
    public SearchableMaze(Maze maze) {
        if (maze == null || maze.getStartPosition() == null) return;

        this.myMazeSra =new MazeState[maze.mMaze.length][maze.mMaze[0].length];
        for(int i = 0; i < maze.mMaze.length; i++)
            for(int j = 0; j < maze.mMaze[0].length; j++)
                this.myMazeSra[i][j]= new MazeState( Double.POSITIVE_INFINITY ,null , new Position(i, j));

        this.maze = maze;
        myMazeSra[maze.getStartPosition().getColumnIndex()][maze.getStartPosition().getRowIndex()].setCost(0);
    }

    @Override
    public void reInitialize() {
        for(int i = 0; i < myMazeSra.length; i++)
            for(int j = 0; j < myMazeSra[0].length; j++)
                this.myMazeSra[i][j] = new MazeState(Double.POSITIVE_INFINITY, null, new Position(i, j));
    }

    @Override
    public ArrayList<AState> getAllPossibleStates(AState currentState) {
        if ( !( currentState instanceof MazeState ) ) return null;
        ArrayList<AState> possibleStates = new ArrayList<>();
        MazeState curMazeState = (MazeState) currentState;
        Position curPos = curMazeState.getPosition();
        int curRow = curPos.getRowIndex();
        int curCol = curPos.getColumnIndex();

        for(int x = -1; x <= 1; x++)
            for(int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0) continue; //same location
                if (0 != x && 0 != y) {
                    if (mazeCheck(curRow+x, curCol+y)) if (mazeCheck(curRow, curCol+y))
                        addAState(curCol, x, curRow, y, possibleStates, currentState, 1.5);
                    else // checks if at the same col we have 0
                        if (mazeCheck(curRow+x, curCol))
                            addAState(curCol, x, curRow, y, possibleStates, currentState, 1.5);
                }
                else // if its not slant, same location and it have '0'
                    if (mazeCheck(curRow+x, curCol+y))
                        addAState(curCol, x, curRow, y, possibleStates, currentState, 1);
            }
        return possibleStates;
    }

    /**
     * @param curCol-
     * @param x-
     * @param curRow-
     * @param y-
     * @param possibleStates-
     * @param currentState-
     * @param cost-
     */
    private void addAState(int curCol, int x, int curRow, int y, ArrayList<AState> possibleStates, AState currentState, double cost) {
        MazeState newMazeState=  (MazeState) currentState;
        if(currentState.getCameFrom() != null) {
            MazeState cameNewMazeState= (MazeState)currentState.getCameFrom();
            if (curRow+x == cameNewMazeState.getPosition().getRowIndex() && curCol+y == cameNewMazeState.getPosition().getColumnIndex())
                return;
        }
        if (curRow+x == this.maze.getStartPosition().getRowIndex() && curCol+y == this.maze.getStartPosition().getColumnIndex())
            return;
            if (this.myMazeSra[curRow + x][curCol + y].getCost() > newMazeState.getCost() + cost) {
                double Addcost = currentState.getCost() + cost;
                this.myMazeSra[curRow + x][curCol + y].setCameFrom(currentState);
                this.myMazeSra[curRow + x][curCol + y].setCost(Addcost);
                possibleStates.add(this.myMazeSra[curRow + x][curCol + y]);
            }

    }

    /**
     * Checks if the given parameters represents a valid cell to move to in the maze.
     *
     * @param row The Row it checks.
     * @param col The Column it checks.
     * @return true given parameters represent a cell open in the maze, false otherwise.
     */
    private boolean mazeCheck(int row, int col) {
        try {
            Position curr = new Position(row, col);
            Position start = maze.getStartPosition();
            Position end = maze.getGoalPosition();
            return (maze.mMaze[row][col] == 0 || start.equals(curr) || end.equals(curr));
        } catch(Exception e) {
            return false;
        }
    }

    @Override
    public AState getInitialState() {
        return maze.getStartPosition() == null ? null : new MazeState(0, null, maze.getStartPosition());
    }

    @Override
    public AState getGoalState() {
        return maze.getGoalPosition() == null ? null : new MazeState(0, null, maze.getGoalPosition());
    }
}