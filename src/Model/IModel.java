package Model;

import algorithms.search.Solution;
import javafx.scene.input.KeyCode;

import java.io.File;
public interface IModel {

    Solution getMazeSolution();
    void generateMaze(int width, int height);
    void moveCharacter(KeyCode movement);
    void generateSolution();
    char[][] getMaze();
    int getMainCharacterPositionRow();
    int getMainCharacterPositionColumn();
    String getMainCharacterDirection();
    void closeModel();
    boolean isAtTheEnd();
    int[][] getSolution();
    void saveCurrentMaze(File file, String name);
    void saveOriginalMaze(File file, String name);
    void loadMaze(File file);
    MazeCharacter getLoadedCharacter();
}
