package View;

import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class HelpController implements Initializable {
    public javafx.scene.control.Label newMazeWindowLabel;
    public javafx.scene.control.Label MenuBar;
    public javafx.scene.control.Button OK;
    public javafx.scene.image.ImageView soundImage;
    public javafx.scene.image.ImageView newMazeImage;
    public javafx.scene.image.ImageView getHintImage;
    public javafx.scene.image.ImageView solveImage;
    public javafx.scene.image.ImageView resetZoomImage;
    public javafx.scene.control.Label soundLabel;
    public javafx.scene.control.Label newMazeLabel;
    public javafx.scene.control.Label getHintLabel;
    public javafx.scene.control.Label solveLabel;
    public javafx.scene.control.Label extraLabel;
    public javafx.scene.control.Label resetZoomLabel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        newMazeWindowLabel.setWrapText(true);// lets the label text to break row when it is in need.
        MenuBar.setWrapText(true);
        soundLabel.setWrapText(true);
        newMazeLabel.setWrapText(true);
        getHintLabel.setWrapText(true);
        solveLabel.setWrapText(true);
        extraLabel.setWrapText(true);
        resetZoomLabel.setWrapText(true);

        extraLabel.setText("\nHere are all the extras we added to the game:\n\n"+
                "    - Background music & On/Off button.\n\n"+
                "    - Drag the character using the mouse.\n\n"+
                "    - Use Zoom In/Out by clicking on CTRL & SCROLL with your mouse.\n\n"+
                "    - When saving a maze you can choose between saving the current maze.\n"+
                "      or the original maze.\n\n"+
                "    - Multiple character options that change the entire game.\n\n"+
                "    - Getting a hint instead of the full solution.");

        newMazeWindowLabel.setText("\nTo create a new game you can click on \n"+
                "'NewGame', 'New' in the 'File' in the menu or using 'Load'.\n"+
                "By clicking on 'New' or 'NewGame' a new window will pop up:\n"+
                "   In this window you create a new maze.\n\n"+
                "   You can choose a character using the arrow button.\n\n"+
                "   The character you can choose from are:\n"+
                "   Choosing a character will change the entire game looks and sound.\n\n"+
                "   You can decide the maze size by changing the 'Rows' and 'Columns'.\n\n"+
                "   Click on 'Start Game' to start playing.\n\n"+
                "Game Controls:\n"+
                "   You can move around the maze as long as you don't go into a wall\n"+
                "   or try to exit the maze perimeters.\n\n"+
                "   To move regular moves you can use:\n"+
                "       Up: UP arrow, W or 8 in NUMPAD\n"+
                "       Down: DOWN arrow, X or 2 in NUMPAD\n"+
                "       Right: RIGHT arrow, D or 6 in NUMPAD\n"+
                "       Left: LEFT arrow, A or 4 in NUMPAD\n\n"+
                "   You can also move in cross if there is a regular move to that location\n"+
                "       Up & Right: E or 9 in NUMPAD\n"+
                "       Up & Left: Q or 7 in NUMPAD\n"+
                "       Down & Left: Z or 1 in NUMPAD\n"+
                "       Down & Right: C or 3 in NUMPAD\n\n"+
                "   It doesn't ends there, you can also move your character by clicking on the\n"+
                "   character with your mouse, keep clicking and drag your character around\n"+
                "   the maze.\n\n");


        MenuBar.setText("\n"+
                "File - \n"+
                "   New: Opens up another window where you can specify your maze setting.\n"+
                "   Solve: Shows this maze solution on the board (until character moves).\n"+
                "   Save: You can either save this maze with the original or the current position.\n"+
                "   Load: Choose a maze from the previous saved mazes.\n"+
                "\nOptions -\n"+
                "   Lets you set properties regarding to the game such as: \n"+
                "   Maze generator algorithm \n"+
                "   Maze search algorithm - where you can choose between:\n"+
                "       BestFirstSearch\n"+
                "       DepthFirstSearch\n"+
                "       BreadthFirstSearch\n"+
                "   How many threads used in the game.\n"+
                "\nExit -\n"+
                "   Closes the application by clicking the *Leave* button\n"+
                "\nAbout -\n    Read about this Maze game and it's creators.\n\n"+
                "Beneath the maze you can see the current location on your character.\n"+
                "The Status Bar will help you understand what's going on in the game.");


        soundLabel.setText("Sound On/Off:\n"+
                "   App provides a suitable background song according\n to the chosen characters.");

        newMazeLabel.setText("New Maze:\n"+
                "   Opens up another window where you can:\n"+
                "       * Choose your character.\n"+
                "       * Set the maze's size.");

        getHintLabel.setText("Get Hint:\n"+
                "   Allows you to see part of the solution without seeing it all.");

        solveLabel.setText("Get Solution:\n"+
                "   Shows this maze solution on the board (until character moves).");

        resetZoomLabel.setText("Reset Zoom:\n"+
                "   Allows you to reset the zoom.");
        initImages();
    }


    private void initImages() {
        // Set soundBtn

        File file = new File("Resources/Icons/icon_sound"+"On"+".png");
        Image image = new Image(file.toURI().toString());
        soundImage.setImage(image);


        //Set Solution Buttons
        file = new File("Resources/Icons/icon_partSolution.png");
        image = new Image(file.toURI().toString());
        getHintImage.setImage(image);
        //setHint();

        file = new File("Resources/Icons/icon_fullSolution.png");
        image = new Image(file.toURI().toString());
        solveImage.setImage(image);


        file = new File("Resources/Icons/icon_makeNewMaze.png");
        image = new Image(file.toURI().toString());
        newMazeImage.setImage(image);

        file = new File("Resources/Icons/icon_resetZoom.png");
        image = new Image(file.toURI().toString());
        resetZoomImage.setImage(image);


    }

    public void close() {
        Stage s = (Stage) OK.getScene().getWindow();
        s.close();
    }
}