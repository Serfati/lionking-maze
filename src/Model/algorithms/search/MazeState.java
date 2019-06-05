package Model.algorithms.search;

import Model.algorithms.mazeGenerators.Position;

/**
 * State for maze
 *
 * @author Serfati
 * @version 1.0
 * @since 20-03-2019
 */
public class MazeState extends AState {
    private Position position;

    public MazeState(double mCost, AState mCameFrom, Position position) {
        super(position.toString(), mCost, mCameFrom);
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "{Position="+position.toString()+'}';
    }
}
