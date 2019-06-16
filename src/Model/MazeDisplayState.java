package Model;

import algorithms.mazeGenerators.Maze;

import java.io.Serializable;

class MazeDisplayState implements Serializable {

    private Maze maze;
    private MazeCharacter mazeCharacter;

    MazeDisplayState(Maze newMaze, MazeCharacter newMazeCharacter) {
        this.maze = newMaze;
        this.mazeCharacter = newMazeCharacter;
    }

    Maze getMaze() {
        return maze;
    }

    MazeCharacter getMazeCharacter() {
        return mazeCharacter;
    }
}
