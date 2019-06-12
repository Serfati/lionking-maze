package View;
import Model.MazeCharacter;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.io.FileInputStream;

public class MazeDisplayer extends Canvas {
    private MazeCharacter mainCharacter = new MazeCharacter("Simba_", 0, 0);

    private char[][] mazeCharArr;
    private int[][] mazeIntArr;
    private int[][] mazeSolutionArr;
    private int goalPositionRow;
    private int goalPositionColumn;
    private int rowMazeSize;
    private int colMazeSize;
    private boolean hint;
    private int oldMainCharacterRow;
    private int oldMainCharacterCol;
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

    public void setMaze(char[][] maze) {
        mazeCharArr = maze;
        rowMazeSize = maze.length;
        colMazeSize = maze[0].length;
    }

    private void setGoalPosition(int row, int column) {
        goalPositionRow = row;
        goalPositionColumn = column;
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

//    void setSecondCharacterName(String name) {
//        secondCharacter.setCharacterName(name);
//    }

    void redraw() {
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
//
                if (mainCharacter.getCharacterRow() != goalPositionRow || mainCharacter.getCharacterCol() != goalPositionColumn)
                    graphicsContext2D.drawImage(goalImage, (startCol+goalPositionColumn) * cellWidth, (startRow+goalPositionRow) * cellHeight, cellWidth, cellHeight);
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
                        if (mazeIntArr[i][j] == 1) {
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

    void redrawMaze() {
        if (mazeCharArr != null) {
            hint = false;
            mazeSolutionArr = null;
            drawMazeIteration();
        }
    }

    void redrawCharacter() {
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
            graphicsContext2D.drawImage(backGroundImage, (startCol+getMainCharacterColumn()) * cellWidth, (startRow+getMainCharacterRow()) * cellHeight, cellWidth, cellHeight);
            graphicsContext2D.drawImage(mainCharacterImage, (startCol+getMainCharacterColumn()) * cellWidth, (startRow+getMainCharacterRow()) * cellHeight, cellWidth, cellHeight);

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
                if (solLength != 1 && (int) Math.sqrt(solLength) == 1) {
                }
                else
                    solLength = (int) Math.sqrt(solLength);
            }
        }
    }

    void redrawSolution() {
        try {
            drawSolutionGeneric(solutionImage);
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    void redrawCancelSolution() {
        try {
            drawSolutionGeneric(backGroundImage);
            mazeSolutionArr = null;
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    void setMazeSolutionArr(int[][] mazeSolutionArr) {
        this.mazeSolutionArr = mazeSolutionArr;
    }

    void setMazeInt(int[][] mazeInt) {
        this.mazeIntArr = mazeInt;
    }
}
