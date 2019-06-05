package Model.algorithms.search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

/**
 * Depth-first algorithms.search (DFS) is an algorithm for traversing
 * or searching tree or graph data structures.
 *
 * @author Serfati
 * @version 1.5
 * @since 27-Mar-19
 */
public class DepthFirstSearch extends ASearchingAlgorithm {
    private Stack<AState> openList; //A stack of stated to be evaluated
    /**
     * A set of all completed states -
     * instead of using colors as in the usual algorithms,
     * this mode comes to handle gray and black mode.
     */
    private HashSet<AState> closedSet;

    public Solution solve(ISearchable searchable) {
        long startTime = System.currentTimeMillis();
        initialStructs();
        visitedNodes = 0;
        AState startState = searchable.getInitialState();
        AState endState = searchable.getGoalState();

        if ( startState == null || endState == null ) return null;

        visitedNodes++;
        openList.push(startState);

        while(!openList.isEmpty()) {

            AState state = openList.pop();
            closedSet.add(state);

            if (state.equals(searchable.getGoalState())) {
                searchable.reInitialize();
                return this.backTrace(state, startTime);
            }

            ArrayList<AState> successors = searchable.getAllPossibleStates(state);
            for(AState child : successors)
                if (child != null) {
                    if (!closedSet.contains(child) && !openList.contains(child)) {
                        child.setCost(state.getCost()+1);
                        child.setCameFrom(state);
                        visitedNodes++;
                        openList.push(child);
                    } else if (oldCost(child) > state.getCost()+1)
                        changeOldCost(child, state.getCost()+1, state);
                    child.setCost(state.getCost()+1);
                }
        }
        return null;
    }

    /**
     * @param pState - this state
     * @return the old cost
     */
    private double oldCost(AState pState) {
        for (AState s : closedSet)
            if (s.equals(pState)) return s.getCost();

        for(AState s : openList)
            if (s.equals(pState)) return s.getCost();

        return Double.NaN;
    }

    private void changeOldCost(AState pState, double pCost, AState pCameFrom) {
        if ( closedSet.contains(pState) ) {
            for (AState s : closedSet)
                if ( s.equals(pState) ) {
                    s.setCost(pCost);
                    s.setCameFrom(pCameFrom);
                }
        } else if (openList.contains(pState)) {
            for(AState s : openList)
                if ( s.equals(pState) ) {
                    s.setCost(pCost);
                    s.setCameFrom(pCameFrom);
                }
        }
    }

    @Override
    public String getName() {
        return "Depth First Search";
    }

    private void initialStructs() {
        visitedNodes = 0;
        openList = new Stack<>();
        closedSet = new HashSet<>();
    }
}