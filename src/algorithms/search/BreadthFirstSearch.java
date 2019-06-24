package Model.algorithms.search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Queue;
import java.util.concurrent.LinkedTransferQueue;

/**
 * Breadth-first algorithms.search (BFS) is an algorithm for traversing
 * or searching tree or graph data structures.
 *
 * @author Serfati
 * @version 1.5
 * @since 27-Mar-19
 */
public class BreadthFirstSearch extends ASearchingAlgorithm {
    Queue<AState> openSet; //A queue of stated to be evaluated

    /**
     * {@inheritDoc}
     * here we algorithms.search for the best path from start state to goal state.
     * Constructor that will initialize what it need to solve the searchable problem.
     */
    public BreadthFirstSearch() {
        visitedNodes = 0;
        openSet = new LinkedTransferQueue<>();
        closedSet = new HashSet<>();
    }

    @Override
    public Solution solve(ISearchable searchable) {
        return searchable == null ? null : backTrace(BFS(searchable), System.currentTimeMillis());
    }

    /**
     * The actual BFS algorithm.
     *
     * @param searchable -
     * @return the goal state after bfs
     */
    private AState BFS(ISearchable searchable) {
        if (searchable == null) return null;
        AState goalState = searchable.getGoalState();
        AState startState = searchable.getInitialState();
        if (startState == null) return null;

        startState.setCameFrom(null);

        visitedNodes++;
        openSet.add(startState);
        closedSet.add(startState.hashCode());

        while (!openSet.isEmpty()) {

            AState current = openSet.poll();

            if (current.equals(goalState)) {
                searchable.reInitialize();
                return current;
            }

            ArrayList<AState> neighboursList = searchable.getAllPossibleStates(current);
            for(AState neighbour : neighboursList)
                if (!closedSet.contains(neighbour.hashCode()) && !openSet.contains(neighbour)) {
                    visitedNodes++;
                    openSet.add(neighbour);
                    closedSet.add(neighbour.hashCode());
                }
        }
        return null;
    }

    @Override
    public String getName() {
        return "Breadth First Search";
    }
}
