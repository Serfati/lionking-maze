package Model;

import javafx.scene.input.KeyCode;

import java.io.File;
import java.util.ArrayList;

public interface IModel {

    void generateMaze(int width, int height);

    void moveCharacter(KeyCode movement);

    void generateSolution();

    int[][] getMaze();

    int getMainCharacterPositionRow();

    int getMainCharacterPositionColumn();

    int getSecondCharacterPositionRow();

    int getSecondCharacterPositionColumn();

    String getMainCharacterDirection();

    String getSecondCharacterDirection();

    void closeModel();

    boolean isAtTheEnd();

    ArrayList getSolution();

    ArrayList getMazeSolutionArr();

    void saveCurrentMaze(File file, String name);

    void saveOriginalMaze(File file, String name);

    void loadMaze(File file);

    MazeCharacter getLoadedCharacter();


    void setMultiPlayerMode(boolean setMode);
}
