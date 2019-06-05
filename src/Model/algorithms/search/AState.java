package Model.algorithms.search;

import java.io.Serializable;
import java.util.Comparator;

/**
 * General State
 *
 * @author Serfati
 * @version 1.0
 * @since 20-03-2019
 */
public abstract class AState implements Comparator<AState>, Comparable<AState>, Serializable {
    private String mState; // the mState represented by string
    private double mCost; // mCost to reach this mState
    private AState cameFrom; //parent state

    /**
     * CTOR
     *
     * @param mState-
     * @param mCost-
     * @param cameFrom-
     */
    public AState(String mState, double mCost, AState cameFrom) {
        if(mState !=null ) {
            this.mState = mState;
            this.mCost = mCost;
            this.cameFrom = cameFrom;
        }
    }

    @Override
    public boolean equals(Object aState) {
        return aState instanceof AState && mState.equals(((AState) aState).mState);
    }

    public double getCost() {
        return mCost;
    }

    void setCost(double cost) {
        this.mCost = cost;
    }

    public AState getCameFrom() {
        return cameFrom;
    }

    void setCameFrom(AState state) {
        this.cameFrom = state;
    }

    @Override
    public int compare(AState o1, AState o2) {
        return Double.compare(o1.getCost(), o2.getCost());
    }

    @Override
    public int compareTo(AState o) {
        return compare(this, o);
    }
}