package Model.algorithms.search;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class BestFirstSearch extends BreadthFirstSearch {

    /**
     * Constructor that will initialize what it need to solve the searchable problem.
     */
    public BestFirstSearch() {
        super();
        Comparator<AState> comparator = Comparator.comparingDouble(AState::getCost);
        visitedNodes = 0;
        openSet = new PriorityQueue<>(comparator);
        closedSet = new HashSet<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "Best First Search";
    }
}