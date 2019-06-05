package Model.algorithms.mazeGenerators;

import java.io.Serializable;

/**
 * @author Serfati
 * @version 1.0
 * @since 20-03-2019
 */
public class Position implements Serializable {
    private int row;
    private int column;

    public Position(int x, int y) {
        this.row = x;
        this.column = y;
    }

    public int getRowIndex() {
        return row;
    }

    public int getColumnIndex() {
        return column;
    }

    @Override
    public String toString() {
        return "{" + row + "," + column + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row &&
                column == position.column;
    }
}