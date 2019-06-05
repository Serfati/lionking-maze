package Model;

import Model.algorithms.mazeGenerators.Maze;

import java.io.Serializable;

public class MazeDisplayState implements Serializable {

    private Maze maze;
    private MazeCharacter mazeCharacter;

    MazeDisplayState(Maze newMaze, MazeCharacter newMazeCharacter) {
        this.maze = newMaze;
        this.mazeCharacter = newMazeCharacter;
    }

    public Maze getMaze() {
        return maze;
    }

    MazeCharacter getMazeCharacter() {
        return mazeCharacter;
    }
}
