package View;

import Model.MazeCharacter;
import algorithms.mazeGenerators.Position;
import algorithms.search.AState;
import algorithms.search.MazeState;
import algorithms.search.Solution;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.util.ArrayList;

public class MazeDisplayer extends Canvas {

    private MazeCharacter mainCharacter = new MazeCharacter("Simba_", 0, 0);

    private char[][] mazeCharArr;

    private int goalPositionRow;
    private int goalPositionColumn;
    private int rowMazeSize;
    private int colMazeSize;
    private int oldMainCharacterRow;
    private int oldMainCharacterCol;
    private boolean hint;

    private Image solutionImage;
    private Image goalImage;
    private Image mainCharacterImage;
    private Image backGroundImage;
    private Image wallImage;


    private void setImages() {
        String mainCharacterName = mainCharacter.getCharacterName();
        try {
            solutionImage = new Image(new FileInputStream("Resources/Images/"+mainCharacterName+"showSolution.png"));
            mainCharacterImage = new Image(new FileInputStream("Resources/Characters/"+mainCharacterName+mainCharacter.getCharacterDirection()+".png"));
            goalImage = new Image(new FileInputStream("Resources/Characters/"+mainCharacterName+"goal.png"));
            wallImage = new Image(new FileInputStream("Resources/Images/"+mainCharacterName+"wall.png"));
            backGroundImage = new Image(new FileInputStream("Resources/Images/"+mainCharacterName+"backGround.png"));
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    private void setCharactersImage() {
        String mainCharacterName = mainCharacter.getCharacterName();
        try {
            mainCharacterImage = new Image(new FileInputStream("Resources/Characters/"+mainCharacterName+mainCharacter.getCharacterDirection()+".png"));
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    private int getMainCharacterRow() {
        return mainCharacter.getCharacterRow();
    }
    private int getMainCharacterColumn() {
        return mainCharacter.getCharacterCol();
    }

    void setMaze(char[][] maze) {
        mazeCharArr = maze;
        rowMazeSize = maze.length;
        colMazeSize = maze[0].length;
    }

    void setMainCharacterDirection(String direction) {
        mainCharacter.setCharacterDirection(direction);
    }

    void setMainCharacterPosition(int row, int column) {
        oldMainCharacterRow = mainCharacter.getCharacterRow();
        oldMainCharacterCol = mainCharacter.getCharacterCol();

        mainCharacter.setCharacterRow(row);
        mainCharacter.setCharacterCol(column);
    }

    void setMainCharacterName(String name) {
        mainCharacter.setCharacterName(name);
        setImages();
    }

     void redraw() {
        if (mazeCharArr != null) {
            this.setHeight(this.getScene().getHeight() - 80 /*ToolBar*/ - 105 /*LowerBar*/ );
            this.setWidth( this.getScene().getWidth() * 19/20);
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            double maxSize = Math.max(colMazeSize,rowMazeSize);
            double cellHeight = canvasHeight / maxSize;
            double cellWidth = canvasWidth / maxSize;
            double startRow = (canvasHeight/2 - (cellHeight * rowMazeSize / 2)) / cellHeight;
            double startCol = (canvasWidth/2 - (cellWidth * colMazeSize / 2)) / cellWidth;

            try {
                GraphicsContext graphicsContext2D = getGraphicsContext2D();
                graphicsContext2D.clearRect(0, 0, getWidth(), getHeight()); //Clears the canvas

                //Draw Maze
                drawMazeIteration();

                //Draw Character
                graphicsContext2D.drawImage(mainCharacterImage, (startCol + getMainCharacterColumn()) * cellWidth, (startRow + getMainCharacterRow()) * cellHeight, cellWidth, cellHeight);

                if (mainCharacter.getCharacterRow() != goalPositionRow || mainCharacter.getCharacterCol() != goalPositionColumn)
                    graphicsContext2D.drawImage(goalImage, (startCol + goalPositionColumn) * cellWidth, (startRow + goalPositionRow) * cellHeight, cellWidth, cellHeight);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void drawSolution(Solution mazeSolution) {
        if (hint) {
            drawPartSolution(mazeSolution);
            hint = false;
            return;
        }
        try {
            double width = getWidth();
            double height = getHeight();
            double wid = width / mazeCharArr[0].length;
            double hig = height / mazeCharArr.length;

            int[][] grid = new int[mazeCharArr.length][mazeCharArr[0].length];
            for(int i = 0; i < mazeCharArr.length; i++)
                for(int j = 0; j < mazeCharArr[0].length; j++)
                    grid[i][j] = Character.getNumericValue(mazeCharArr[i][j]);
            GraphicsContext graphicsContext = getGraphicsContext2D();
            graphicsContext.clearRect(0, 0, getWidth(), getHeight());
            ArrayList<AState> path = mazeSolution.getSolutionPath();
            for(int i = 0; i < grid.length; i++)
                for(int j = 0; j < grid[i].length; j++) {
                    if (grid[i][j] == 1)
                        graphicsContext.drawImage(wallImage, j * wid, i * hig, wid, hig);
                    else if (grid[i][j] == 0)
                        graphicsContext.drawImage(backGroundImage, j * wid, i * hig, wid, hig);
                    else
                        graphicsContext.drawImage(backGroundImage, j * wid, i * hig, wid, hig);
                    AState p = new MazeState(0, null, new Position(i, j));
                    if (path.contains(p))
                        graphicsContext.drawImage(solutionImage, j * wid, i * hig, wid, hig);
                }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void drawPartSolution(Solution mazeSolution) {
        try {

            double width = getWidth();
            double height = getHeight();
            double wid = width / mazeCharArr[0].length;
            double hig = height / mazeCharArr.length;
            int solLength;
            int[][] grid = new int[mazeCharArr.length][mazeCharArr[0].length];
            for(int i = 0; i < mazeCharArr.length; i++)
                for(int j = 0; j < mazeCharArr[0].length; j++)
                    grid[i][j] = Character.getNumericValue(mazeCharArr[i][j]);

            GraphicsContext graphicsContext = getGraphicsContext2D();
            graphicsContext.clearRect(0, 0, getWidth(), getHeight());
            ArrayList<AState> mazeSolutionArr = mazeSolution.getSolutionPath();
            solLength = mazeSolutionArr.size();

            if (solLength != 1 && (int) Math.sqrt(solLength) == 1)
                solLength = 2;
            else
                solLength = (int) Math.sqrt(solLength);
            int count = 0;
            for(int i = 0; i < grid.length; i++)
                for(int j = 0; j < grid[i].length; j++) {
                    if (grid[i][j] == 1)
                        graphicsContext.drawImage(wallImage, j * wid, i * hig, wid, hig);
                    else if (grid[i][j] == 0)
                        graphicsContext.drawImage(backGroundImage, j * wid, i * hig, wid, hig);
                    else
                        graphicsContext.drawImage(backGroundImage, j * wid, i * hig, wid, hig);
                    AState p = new MazeState(0, null, new Position(i, j));
                    if (mazeSolutionArr.contains(p) && count <= solLength) {
                        graphicsContext.drawImage(solutionImage, j * wid, i * hig, wid, hig);
                        count++;
                    }
                }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void drawMazeIteration(){
        if (mazeCharArr != null) {
            try {
                this.setHeight(this.getScene().getHeight() - 80 /*ToolBar*/ - 105 /*LowerBar*/ );
                this.setWidth( this.getScene().getWidth() * 19/20);
                double canvasHeight = getHeight();
                double canvasWidth = getWidth();
                double maxSize = Math.max(colMazeSize, rowMazeSize);
                double cellHeight = canvasHeight / maxSize;
                double cellWidth = canvasWidth / maxSize;
                double startRow = (canvasHeight / 2 - (cellHeight * rowMazeSize / 2)) / cellHeight;
                double startCol = (canvasWidth / 2 - (cellWidth * colMazeSize / 2)) / cellWidth;
                GraphicsContext graphicsContext2D = getGraphicsContext2D();
                graphicsContext2D.clearRect(0, 0, getWidth(), getHeight()); //Clears the canvas
                for (int i = 0; i < rowMazeSize; i++)
                    for (int j = 0; j < colMazeSize; j++) {
                        graphicsContext2D.drawImage(backGroundImage, (startCol + j) * cellWidth, (startRow + i) * cellHeight, cellWidth, cellHeight);
                        if (mazeCharArr[i][j] == '1')
                            graphicsContext2D.drawImage(wallImage, (startCol + j) * cellWidth, (startRow + i) * cellHeight, cellWidth, cellHeight);
                        else if (mazeCharArr[i][j] == 'E') {
                            goalPositionRow = i;
                            goalPositionColumn = j;
                        }
                    }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void redrawMaze() {
        if (mazeCharArr != null) {
            hint = false;
            drawMazeIteration();
        }
    }

    void setHint() {
        this.hint = true;
    }

    void redrawCharacter(){
        try {
            setCharactersImage();
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            double maxSize = Math.max(colMazeSize, rowMazeSize);
            double cellHeight = canvasHeight / maxSize;
            double cellWidth = canvasWidth / maxSize;
            double startRow = (canvasHeight / 2 - (cellHeight * rowMazeSize / 2)) / cellHeight;
            double startCol = (canvasWidth / 2 - (cellWidth * colMazeSize / 2)) / cellWidth;
            hint = false;
            GraphicsContext graphicsContext2D = getGraphicsContext2D();
            if(mazeCharArr[oldMainCharacterRow][oldMainCharacterCol] != '1')
                graphicsContext2D.drawImage(backGroundImage, (startCol + oldMainCharacterCol) * cellWidth, (startRow + oldMainCharacterRow) * cellHeight, cellWidth, cellHeight);
            graphicsContext2D.drawImage(backGroundImage, (startCol + getMainCharacterColumn()) * cellWidth, (startRow + getMainCharacterRow()) * cellHeight, cellWidth, cellHeight);
            graphicsContext2D.drawImage(mainCharacterImage, (startCol + getMainCharacterColumn()) * cellWidth, (startRow + getMainCharacterRow()) * cellHeight, cellWidth, cellHeight);

            if (mainCharacter.getCharacterRow() != goalPositionRow || mainCharacter.getCharacterCol() != goalPositionColumn) {
                graphicsContext2D.drawImage(backGroundImage, (startCol + goalPositionColumn) * cellWidth, (startRow + goalPositionRow) * cellHeight, cellWidth, cellHeight);
                graphicsContext2D.drawImage(goalImage, (startCol + goalPositionColumn) * cellWidth, (startRow + goalPositionRow) * cellHeight, cellWidth, cellHeight);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}