package Model.algorithms.search;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * An abstract class of algorithms.search algorithms
 * that provides a common functions.
 *
 * @author Serfati
 * @version 3.0
 * @since 27-Mar-19
 */
public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {
    int visitedNodes; //how many nodes were evaluated by the algorithm
    private long solTekTime = 0;
    /**
     * A set of all completed states -
     * instead of using colors as in the usual algorithms,
     * this mode comes to handle gray and black mode.
     */
    HashSet<Integer> closedSet;

    public abstract Solution solve(ISearchable searchable);

    /**
     * Create a solution from a given 'AState'
     *
     * @param goal An 'AState' representing the goalState after the algorithm found it.
     * @return The solution
     */
    Solution backTrace(AState goal, long startTime) {
        AState parenState = goal;
        ArrayList<AState> solPath = new ArrayList<>();
        while (parenState != null) {
            solPath.add(0, parenState);
            parenState = parenState.getCameFrom();
        }
        this.solTekTime = System.currentTimeMillis()-startTime;
        return new Solution(solPath);
    }
    @Override
    public int getNumberOfNodesEvaluated() {
        return visitedNodes;
    }

    @Override
    public double getSolvingTimeMiliseconds(ISearchable searchAble) {
        return solTekTime;
    }
}