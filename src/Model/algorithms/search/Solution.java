package Model.algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class defines result of a algorithms.search in a way
 * that we can save and go over the states that bring us
 * to the goal state.
 * A legal sequence of states from start state to goal state.
 *
 * @author Serfati
 * @version 1.0
 * @since 20-03-2019
 */
public class Solution implements Serializable {

    private ArrayList<AState> path;

    Solution(ArrayList<AState> path) {
        this.path = path;
    }

    public ArrayList<AState> getSolutionPath() {
        return this.path == null ? new ArrayList<>() : this.path;
    }
}
