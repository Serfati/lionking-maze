package Model.algorithms.search;

/**
 * An interface of algorithms.search algorithms
 * that provides a common functions.
 *
 * @author Serfati
 * @version 1.0
 * @since 20-03-2019
 */
public interface ISearchingAlgorithm {

    /**
     * This method algorithms.search for a legal sequence of states from start state to goal state on domain.
     * @param searchable is a searchable object on which we will run a algorithms.search
     * @return the solution - algorithms.search.Solution
     * @see Solution
     */
    Solution solve(ISearchable searchable);

    /**
     * This method return the numbers of nodes that have been evaluated during the algorithms.search.
     * @return int
     */
    int getNumberOfNodesEvaluated();

    /**
     * @return the name of the algorithm
     */
    String getName();

    /**
     * @return the runtime of the algorithm
     */
    double getSolvingTimeMiliseconds(ISearchable searchAble);
}
