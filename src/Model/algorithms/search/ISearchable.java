package Model.algorithms.search;

import java.util.ArrayList;

/**
 * An interface of algorithms.search structs
 * that provides a common functions.
 *
 * @author Serfati
 * @version 1.0
 * @since 20-03-2019
 */
public interface ISearchable {

    /**
     * This method return the initial situation from which we want to begin to algorithms.search.
     *
     * @return algorithms.search.AState
     */
    AState getInitialState();

    /**
     * This method return the goal situation we want to reach.
     * @return algorithms.search.AState
     */

    AState getGoalState();

    /**
     * This method return a list of legal states that can be reached from the current state.
     * @param state is the current state.
     * @return ArrayList<algorithms.search.AState>
     */
    ArrayList<AState> getAllPossibleStates(AState state);

    /**
     * ----------------------------------------------------------------------
     * This method reInitialize all data structs used to adapt the problem
     * to a search able problem.
     * In out case re initialize the matrix of all states to short run-time.
     * only on version 2.5 and above
     * @since 3-Apr-19
     * ----------------------------------------------------------------------
     */
    void reInitialize();
}

