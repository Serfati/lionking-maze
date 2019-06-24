package Model.algorithms.mazeGenerators;

/**
 * An abstract class of maze generation
 * that provides a common functions.
 *
 * @author Serfati
 * @version 1.0
 * @since 1-Apr-19
 */

public abstract class AMazeGenerator implements IMazeGenerator {
    private int mRow;
    private int mColumn;

    static int rangeRandom() {
        return (int) ( Math.random() * 2 );
    }

    @Override
    public abstract Maze generate(int row, int column);

    @Override
    public long measureAlgorithmTimeMillis(int row, int column) {
        long before = System.currentTimeMillis();
        generate(row, column);
        long after = System.currentTimeMillis();
        return (after - before);
    }

    int getRow() {
        return mRow;
    }

    void setRow(int row) {
        this.mRow = row;
    }

    int getColumn() {
        return mColumn;
    }

    void setColumn(int col) {
        this.mColumn = col;
    }
}