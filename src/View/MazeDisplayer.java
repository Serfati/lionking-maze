package View;

import Model.MazeCharacter;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileInputStream;


public class MazeDisplayer extends Canvas {

    private MazeCharacter mainCharacter = new MazeCharacter("Crash_", 0, 0);
    private MazeCharacter secondCharacter = new MazeCharacter("Crash_Second_", 0, 0);
    private char[][] mazeCharArr;
    private int[][] mazeSolutionArr;
    private int goalPositionRow;
    private int goalPositionColumn;
    private int rowMazeSize;
    private int colMazeSize;
    private boolean hint;
    private int oldSecondCharacterRow;
    private int oldSecondCharacterCol;
    private int oldMainCharacterRow;
    private int oldMainCharacterCol;

    private Image solutionImage;
    private Image goalImage;
    private Image secondCharacterImage;
    private Image mainCharacterImage;
    private Image backGroundImage;
    private Image wallImage;


    private void setImages() {
        String mainCharacterName = mainCharacter.getCharacterName();
        try {
            solutionImage = new Image(new FileInputStream("Resources/Images/"+mainCharacterName+"showSolution.png"));
            secondCharacterImage = new Image(new FileInputStream("Resources/Characters/"+mainCharacterName+"Second_"+secondCharacter.getCharacterDirection()+".png"));
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
            secondCharacterImage = new Image(new FileInputStream("Resources/Characters/"+mainCharacterName+"Second_"+secondCharacter.getCharacterDirection()+".png"));
            mainCharacterImage = new Image(new FileInputStream("Resources/Characters/"+mainCharacterName+mainCharacter.getCharacterDirection()+".png"));
        } catch(Exception e) {
            e.printStackTrace();
        }

    }


    public int getMainCharacterRow() {
        return mainCharacter.getCharacterRow();
    }
    public int getMainCharacterColumn() {
        return mainCharacter.getCharacterCol();
    }

    public int getSecondCharacterRow() {
        return secondCharacter.getCharacterRow();
    }
    public int getSecondCharacterColumn() {
        return secondCharacter.getCharacterCol();
    }

    public void setMaze(char[][] maze) {
        mazeCharArr = maze;
        rowMazeSize = maze.length;
        colMazeSize = maze[0].length;
    }

    public void setGoalPosition(int row, int column) {
        goalPositionRow = row;
        goalPositionColumn = column;
    }


    public void setSecondCharacterDirection(String direction) {
        secondCharacter.setCharacterDirection(direction);
    }

    public void setSecondCharacterPosition(int row, int column) {

        oldSecondCharacterRow = secondCharacter.getCharacterRow();
        oldSecondCharacterCol = secondCharacter.getCharacterCol();

        secondCharacter.setCharacterRow(row);
        secondCharacter.setCharacterCol(column);

    }

    public void setMainCharacterDirection(String direction) {
        mainCharacter.setCharacterDirection(direction);
    }

    public void setMainCharacterPosition(int row, int column) {
        oldMainCharacterRow = mainCharacter.getCharacterRow();
        oldMainCharacterCol = mainCharacter.getCharacterCol();

        mainCharacter.setCharacterRow(row);
        mainCharacter.setCharacterCol(column);
    }

    public void setMainCharacterName(String name) {
        mainCharacter.setCharacterName(name);
        setImages();
    }

    public void setSecondCharacterName(String name) {
        secondCharacter.setCharacterName(name);
    }

    public void redraw() {
        if (mazeCharArr != null) {
            this.setHeight(this.getScene().getHeight()-80 /*ToolBar*/-105 /*LowerBar*/);
            this.setWidth(this.getScene().getWidth() * 19 / 20);
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            double maxSize = Math.max(colMazeSize, rowMazeSize);
            double cellHeight = canvasHeight / maxSize;
            double cellWidth = canvasWidth / maxSize;
            double startRow = (canvasHeight / 2-(cellHeight * rowMazeSize / 2)) / cellHeight;
            double startCol = (canvasWidth / 2-(cellWidth * colMazeSize / 2)) / cellWidth;

            try {

                GraphicsContext graphicsContext2D = getGraphicsContext2D();
                graphicsContext2D.clearRect(0, 0, getWidth(), getHeight()); //Clears the canvas

                //Draw Maze
                drawMazeIteration();

                //draw solution
                drawSolutionGeneric(solutionImage);

                //Draw Character

                graphicsContext2D.drawImage(mainCharacterImage, (startCol+getMainCharacterColumn()) * cellWidth, (startRow+getMainCharacterRow()) * cellHeight, cellWidth, cellHeight);

                if (!(secondCharacter.getCharacterRow() == mainCharacter.getCharacterRow() && secondCharacter.getCharacterCol() == mainCharacter.getCharacterCol())) {
                    //Image secondCharacterImage = new Image(new FileInputStream("Resources/Characters/" + secondCharacterName + secondCharacter.getCharacterDirection() + ".png"));
                    graphicsContext2D.drawImage(secondCharacterImage, (startCol+getSecondCharacterColumn()) * cellWidth, (startRow+getSecondCharacterRow()) * cellHeight, cellWidth, cellHeight);
                }

                if (mainCharacter.getCharacterRow() != goalPositionRow || mainCharacter.getCharacterCol() != goalPositionColumn) {
                    graphicsContext2D.drawImage(goalImage, (startCol+goalPositionColumn) * cellWidth, (startRow+goalPositionRow) * cellHeight, cellWidth, cellHeight);
                }

            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void drawMazeIteration() {
        if (mazeCharArr != null) {
            try {
                this.setHeight(this.getScene().getHeight()-80 /*ToolBar*/-105 /*LowerBar*/);
                this.setWidth(this.getScene().getWidth() * 19 / 20);
                double canvasHeight = getHeight();
                double canvasWidth = getWidth();
                double maxSize = Math.max(colMazeSize, rowMazeSize);
                double cellHeight = canvasHeight / maxSize;
                double cellWidth = canvasWidth / maxSize;
                double startRow = (canvasHeight / 2-(cellHeight * rowMazeSize / 2)) / cellHeight;
                double startCol = (canvasWidth / 2-(cellWidth * colMazeSize / 2)) / cellWidth;
                GraphicsContext graphicsContext2D = getGraphicsContext2D();
                graphicsContext2D.clearRect(0, 0, getWidth(), getHeight()); //Clears the canvas
                for(int i = 0; i < rowMazeSize; i++) {
                    for(int j = 0; j < colMazeSize; j++) {
                        graphicsContext2D.drawImage(backGroundImage, (startCol+j) * cellWidth, (startRow+i) * cellHeight, cellWidth, cellHeight);
                        if (mazeCharArr[i][j] == '1') {
                            graphicsContext2D.drawImage(wallImage, (startCol+j) * cellWidth, (startRow+i) * cellHeight, cellWidth, cellHeight);
                        } else if (mazeCharArr[i][j] == 'E') {
                            setGoalPosition(i, j);
                        }
                    }
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void redrawMaze() {
        if (mazeCharArr != null) {
            hint = false;
            mazeSolutionArr = null;
            drawMazeIteration();
        }
    }

    public void redrawCharacter() {
        try {
            setCharactersImage();
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            double maxSize = Math.max(colMazeSize, rowMazeSize);
            double cellHeight = canvasHeight / maxSize;
            double cellWidth = canvasWidth / maxSize;
            double startRow = (canvasHeight / 2-(cellHeight * rowMazeSize / 2)) / cellHeight;
            double startCol = (canvasWidth / 2-(cellWidth * colMazeSize / 2)) / cellWidth;
            hint = false;
            GraphicsContext graphicsContext2D = getGraphicsContext2D();
            if (mazeCharArr[oldMainCharacterRow][oldMainCharacterCol] != '1')
                graphicsContext2D.drawImage(backGroundImage, (startCol+oldMainCharacterCol) * cellWidth, (startRow+oldMainCharacterRow) * cellHeight, cellWidth, cellHeight);
            if (mazeCharArr[oldSecondCharacterRow][oldSecondCharacterCol] != '1')
                graphicsContext2D.drawImage(backGroundImage, (startCol+oldSecondCharacterCol) * cellWidth, (startRow+oldSecondCharacterRow) * cellHeight, cellWidth, cellHeight);

            graphicsContext2D.drawImage(backGroundImage, (startCol+getMainCharacterColumn()) * cellWidth, (startRow+getMainCharacterRow()) * cellHeight, cellWidth, cellHeight);
            graphicsContext2D.drawImage(mainCharacterImage, (startCol+getMainCharacterColumn()) * cellWidth, (startRow+getMainCharacterRow()) * cellHeight, cellWidth, cellHeight);

            if (!(secondCharacter.getCharacterRow() == mainCharacter.getCharacterRow() && secondCharacter.getCharacterCol() == mainCharacter.getCharacterCol())) {
                graphicsContext2D.drawImage(backGroundImage, (startCol+getSecondCharacterColumn()) * cellWidth, (startRow+getSecondCharacterRow()) * cellHeight, cellWidth, cellHeight);

                graphicsContext2D.drawImage(secondCharacterImage, (startCol+getSecondCharacterColumn()) * cellWidth, (startRow+getSecondCharacterRow()) * cellHeight, cellWidth, cellHeight);
            }

            if (mainCharacter.getCharacterRow() != goalPositionRow || mainCharacter.getCharacterCol() != goalPositionColumn) {
                graphicsContext2D.drawImage(backGroundImage, (startCol+goalPositionColumn) * cellWidth, (startRow+goalPositionRow) * cellHeight, cellWidth, cellHeight);

                graphicsContext2D.drawImage(goalImage, (startCol+goalPositionColumn) * cellWidth, (startRow+goalPositionRow) * cellHeight, cellWidth, cellHeight);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void drawSolutionGeneric(Image image) {
        double canvasHeight = getHeight();
        double canvasWidth = getWidth();
        double maxSize = Math.max(colMazeSize, rowMazeSize);
        double cellHeight = canvasHeight / maxSize;
        double cellWidth = canvasWidth / maxSize;
        double startRow = (canvasHeight / 2-(cellHeight * rowMazeSize / 2)) / cellHeight;
        double startCol = (canvasWidth / 2-(cellWidth * colMazeSize / 2)) / cellWidth;
        GraphicsContext graphicsContext2D = getGraphicsContext2D();
        int solLength = 0;
        if (mazeSolutionArr != null) {
            solLength = mazeSolutionArr.length-1;
            if (hint) {
                if (solLength != 1 && (int) Math.sqrt(solLength) == 1)
                    solLength = 2;
                else
                    solLength = (int) Math.sqrt(solLength);
            }
        }
        for(int i = 1; mazeSolutionArr != null && i < solLength; i++) {
            if (!(secondCharacter.getCharacterRow() == mazeSolutionArr[i][0] && secondCharacter.getCharacterCol() == mazeSolutionArr[i][1]))
                graphicsContext2D.drawImage(image, (startCol+mazeSolutionArr[i][1]) * cellWidth, (startRow+mazeSolutionArr[i][0]) * cellHeight, cellWidth, cellHeight);
        }
    }

    public void redrawSolution() {
        try {
            drawSolutionGeneric(solutionImage);
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    public void redrawCancelSolution() {
        try {
            drawSolutionGeneric(backGroundImage);
            mazeSolutionArr = null;
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void setMazeSolutionArr(int[][] mazeSolutionArr) {
        this.mazeSolutionArr = mazeSolutionArr;
    }

    public void setHint(boolean hint) {
        this.hint = hint;
    }

}
