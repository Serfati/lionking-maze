package View;

import Model.MazeCharacter;
import ViewModel.MyViewModel;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.ResourceBundle;

public class MyViewController implements IView, Observer, Initializable {

    public Label lbl_characterRow;
    public MazeDisplayer mazeDisplayer;
    private NewGameController newGameController;
    public Label lbl_characterColumn;
    public Label lbl_statusBar;
    public Label label_mainCharacterRow;
    public Label label_mainCharacterCol;
    public MenuItem save_MenuItem;
    public MenuItem solve_MenuItem;

    @FXML
    private MyViewModel myViewModel;
    public javafx.scene.image.ImageView icon_sound;
    public javafx.scene.image.ImageView icon_partSolution;
    public javafx.scene.image.ImageView icon_fullSolution;
    public javafx.scene.image.ImageView icon_makeNewMaze;
    public javafx.scene.image.ImageView icon_zoomImageView;
    private String soundOnOff = "On";
    public ScrollPane mazeScrollPane;
    private Stage stageNewGameController;

    private StringProperty CharacterRow = new SimpleStringProperty();
    private StringProperty CharacterColumn = new SimpleStringProperty();

    public void setViewModel(MyViewModel myViewModel) {
        this.myViewModel = myViewModel;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o != myViewModel) {
            return;
        }
        if (arg != null) {
            String argument = (String) arg;
            switch(argument) {
                case "Maze":
                    mazeScrollPane.setVisible(true);
                    mazeDisplayer.setMaze(myViewModel.getMaze());
                    mazeDisplayer.setMainCharacterPosition(myViewModel.getMainCharacterPositionRow(), myViewModel.getMainCharacterPositionColumn());
                    mazeDisplayer.setMainCharacterDirection(myViewModel.getMainCharacterDirection());
                    mazeDisplayer.setMainCharacterName(myViewModel.getMainCharacterName());
                    Platform.runLater(() -> {
                        CharacterColumn.set(myViewModel.getMainCharacterPositionColumn()+"");
                        CharacterRow.set(myViewModel.getMainCharacterPositionRow()+"");
                        label_mainCharacterRow.setText(myViewModel.getMainCharacterName()+"Row");
                        label_mainCharacterCol.setText(myViewModel.getMainCharacterName()+"Col");
                        lbl_statusBar.setText("Lets see you solve this!");
                        save_MenuItem.setDisable(false);
                        solve_MenuItem.setDisable(false);
                        icon_fullSolution.setVisible(true);
                        icon_partSolution.setVisible(true);
                        if (stageNewGameController != null)
                            stageNewGameController.close();
                        resetZoom();
                    });
                    mazeDisplayer.redrawMaze();
                    mazeDisplayer.redrawCharacter();
                    break;
                case "Maze Load":
                    MazeCharacter mazeCharacter = myViewModel.getLoadedCharacter();
                    mazeDisplayer.setMaze(myViewModel.getMaze());
                    //MainCharacter
                    mazeDisplayer.setMainCharacterPosition(mazeCharacter.getCharacterRow(), mazeCharacter.getCharacterCol());
                    mazeDisplayer.setMainCharacterDirection("front");
                    mazeDisplayer.setMainCharacterName(mazeCharacter.getCharacterName());

                    Platform.runLater(() -> {
                        CharacterColumn.set(myViewModel.getMainCharacterPositionColumn()+"");
                        CharacterRow.set(myViewModel.getMainCharacterPositionRow()+"");
                        label_mainCharacterRow.setText(myViewModel.getMainCharacterName()+"Row");
                        label_mainCharacterCol.setText(myViewModel.getMainCharacterName()+"Col");
                        lbl_statusBar.setText("Lets see you solve this!");
                        solve_MenuItem.setDisable(false);
                        save_MenuItem.setDisable(false);
                        icon_fullSolution.setVisible(true);
                        icon_partSolution.setVisible(true);
                        resetZoom();
                    });
                    mazeDisplayer.redrawMaze();
                    mazeDisplayer.redrawCharacter();
                    break;
                case "Character":
                    mazeDisplayer.setMainCharacterPosition(myViewModel.getMainCharacterPositionRow(), myViewModel.getMainCharacterPositionColumn());

                    mazeDisplayer.setMainCharacterDirection(myViewModel.getMainCharacterDirection());

                    Platform.runLater(() -> {
                        CharacterColumn.set(myViewModel.getMainCharacterPositionColumn()+"");
                        CharacterRow.set(myViewModel.getMainCharacterPositionRow()+"");
                        lbl_statusBar.setText("");
                        icon_fullSolution.setVisible(true);
                        icon_partSolution.setVisible(true);
                    });
                    mazeDisplayer.redraw();
                    mazeDisplayer.redrawCharacter();
                    break;
                case "Solution":
                    Platform.runLater(() -> {
                        lbl_statusBar.setText("Here's the solution");
                        solve_MenuItem.setDisable(false);
                        icon_fullSolution.setVisible(false);
                        icon_partSolution.setVisible(false);

                    });
                    mazeDisplayer.drawSolution(myViewModel.getSou());
                    break;
            }
        }
        if (myViewModel.isAtTheEnd()) {
            Stage winningStage = new Stage();
            VBox root = new VBox();
            root.setAlignment(Pos.CENTER);

            MediaPlayer player = new MediaPlayer(new Media(new File("Resources/Music/"+myViewModel.getMainCharacterName()+"winVideo.mp4").toURI().toString()));
            MediaView mediaView = new MediaView(player);
            player.setOnEndOfMedia(winningStage::close);
            Button skipBtn = new Button();
            skipBtn.setText("Click to skip...");

            skipBtn.setOnMouseClicked(event -> {
                player.stop();
                winningStage.close();
            });
            root.getChildren().add(mediaView);
            root.getChildren().add(skipBtn);
            Scene scene = new Scene(root, 1000, 800);
            winningStage.setScene(scene);
            Platform.runLater(() -> {
                save_MenuItem.setDisable(true);
                solve_MenuItem.setDisable(true);
                icon_fullSolution.setVisible(false);
                icon_partSolution.setVisible(false);
                lbl_statusBar.setText("Good Job! Try a different maze");
                myViewModel.setSound();
                player.play();
                player.setMute(false);
                winningStage.showAndWait();
                newMaze();
            });
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lbl_characterRow.textProperty().bind(CharacterRow);
        lbl_characterColumn.textProperty().bind(CharacterColumn);
        mazeScrollPane.setVisible(false);
        initImages();
    }

    private void initImages() {
        // Set soundBtn
        soundOnOff = "On";
        File file = new File("Resources/Icons/icon_sound"+soundOnOff+".png");
        Image image = new Image(file.toURI().toString());
        icon_sound.setImage(image);
        setSound();

        //Set Solution Buttons
        file = new File("Resources/Icons/icon_partSolution.png");
        image = new Image(file.toURI().toString());
        icon_partSolution.setImage(image);
        setHint();

        file = new File("Resources/Icons/icon_fullSolution.png");
        image = new Image(file.toURI().toString());
        icon_fullSolution.setImage(image);
        setFullSolution();

        file = new File("Resources/Icons/icon_makeNewMaze.png");
        image = new Image(file.toURI().toString());
        icon_makeNewMaze.setImage(image);
        makeNewMaze();

        //zoom button
        file = new File("Resources/Icons/icon_resetZoom.png");
        image = new Image(file.toURI().toString());
        icon_zoomImageView.setImage(image);
        icon_zoomImageView.setVisible(false);
        setZoom();
    }

    private void setZoom() {
        icon_zoomImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            resetZoom();
            event.consume();
        });
    }

    public void KeyPressed(KeyEvent keyEvent) {
        if (!myViewModel.isAtTheEnd())
            myViewModel.moveCharacter(keyEvent.getCode());
        else
            lbl_statusBar.setText("If you want to play again just generate a new maze!");
        keyEvent.consume();
    }

    public void solveMaze() {
        Platform.runLater(() -> {
            lbl_statusBar.setText("Computing solution, please wait.");
            solve_MenuItem.setDisable(true);
            icon_fullSolution.setVisible(false);
            icon_partSolution.setVisible(false);
        });
        myViewModel.generateSolution();
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setGraphic(null);
        alert.setTitle("Error Alert");
        alert.setContentText("Exception!");
        alert.show();
    }

    void setResizeEvent(Scene scene) {
        scene.widthProperty().addListener((observable, oldValue, newValue) -> mazeDisplayer.redraw());
        scene.heightProperty().addListener((observable, oldValue, newValue) -> mazeDisplayer.redraw());
    }

    public void exitButton() {
        exitCorrectly();
    }

    void exitCorrectly() {
        Alert alert = new Alert(Alert.AlertType.NONE);
        ButtonType leaveButton = new ButtonType("Leave", ButtonBar.ButtonData.YES);
        ButtonType stayButton = new ButtonType("Stay", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(stayButton, leaveButton);
        alert.setContentText("Are you sure you want to exit??");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == leaveButton) {
            // ... user chose Leave
            // Close program
            myViewModel.closeModel();
            Platform.exit();
        } else
            // ... user chose CANCEL or closed the dialog
            alert.close();
    }

    public void saveFile(ActionEvent event) {
        int[] choose = {0};
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Save Maze");
        alert.setContentText("What maze do you want to save?");
        ButtonType okButton = new ButtonType("Current", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("Original", ButtonBar.ButtonData.NO);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(okButton, noButton, cancelButton);
        alert.showAndWait().ifPresent(type -> {
            if (type == okButton) //Current
                choose[0] = 1;
             else if (type == noButton)  //Original
                choose[0] = 2;
        });
        if (choose[0] == 0) {
            lbl_statusBar.setText("Save was canceled");
            return;
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a directory to save the maze in");
        File filePath = new File("./Mazes/");
        if (!filePath.exists())
            filePath.mkdir();
        fileChooser.setInitialDirectory(filePath);
        fileChooser.setInitialFileName("myMaze_"+myViewModel.getMainCharacterName());
        File file = fileChooser.showSaveDialog(new PopupWindow() {
        });

        if (file != null) {
            if (choose[0] == 1) {
                myViewModel.saveCurrentMaze(file);
                lbl_statusBar.setText("Saved current maze");
            } else {
                myViewModel.saveOriginalMaze(file);
                lbl_statusBar.setText("Saved original maze");
            }
        }
        event.consume();
    }

    public void loadFile(ActionEvent event) {
        System.out.println("loadFile");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a maze to load");
        File filePath = new File("./Mazes/");
        if (!filePath.exists())
            filePath.mkdir();
        fileChooser.setInitialDirectory(filePath);

        File file = fileChooser.showOpenDialog(new PopupWindow() {
        });
        if (file != null && file.exists() && !file.isDirectory()) {
            myViewModel.loadFile(file);
            lbl_statusBar.setText("Loaded "+file.getName());
        }
        event.consume();
    }

    public void newMaze() {
        try {
            if (stageNewGameController == null) {
                stageNewGameController = new Stage();
                stageNewGameController.setTitle("New Maze Window");
                FXMLLoader fxmlLoader = new FXMLLoader();
                Parent root = fxmlLoader.load(getClass().getResource("NewGame.fxml").openStream());
                Scene scene = new Scene(root, 650, 500);
                scene.getStylesheets().add(getClass().getResource("ViewStyle.css").toExternalForm());
                stageNewGameController.setScene(scene);
                stageNewGameController.setResizable(false);
                newGameController = fxmlLoader.getController();
                newGameController.setViewModel(myViewModel);
                stageNewGameController.initModality(Modality.APPLICATION_MODAL);
            }
            newGameController.enableButtons();
            stageNewGameController.show();
        } catch(Exception ignored) {
        }
    }

    public void onAction_Property() {
        try {
            Stage stage = new Stage();
            stage.setTitle("Properties");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("MyPropertiesView.fxml").openStream());
            Scene scene = new Scene(root, 400, 370);
            scene.getStylesheets().add(getClass().getResource("ViewStyle.css").toExternalForm());
            stage.setScene(scene);
            PropertiesViewController propertiesViewController = fxmlLoader.getController();
            propertiesViewController.setStage(stage);
            if (solve_MenuItem.isDisable())
                solve_MenuItem.setDisable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch(Exception ignored) {
        }
    }

    public void help() {
        Stage helpStage = new Stage();
        helpStage.setAlwaysOnTop(true);
        helpStage.setResizable(false);
        helpStage.setTitle("Help Window");

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("Help.fxml"));
        } catch(IOException e) {
            e.printStackTrace();
            showAlert();
        }
        helpStage.setTitle("Help");
        assert root != null;
        Scene scene = new Scene(root, 520, 495);
        scene.getStylesheets().add(getClass().getResource("ViewStyle.css").toExternalForm());
        helpStage.setScene(scene);
        helpStage.initModality(Modality.WINDOW_MODAL);
        helpStage.show();
    }

    public void About() {
        Stage aboutStage = new Stage();
        aboutStage.setAlwaysOnTop(true);
        aboutStage.setResizable(false);
        aboutStage.setTitle("About Window");

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("About.fxml"));
        } catch(IOException e) {
            showAlert();
        }
        aboutStage.setTitle("About");
        assert root != null;
        Scene scene = new Scene(root, 600, 400);
        scene.getStylesheets().add(getClass().getResource("ViewStyle.css").toExternalForm());
        aboutStage.setScene(scene);
        aboutStage.initModality(Modality.APPLICATION_MODAL);
        aboutStage.show();
    }

    private void setSound() {
        icon_sound.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (myViewModel.setSound())
                soundOnOff = "On";
            else
                soundOnOff = "Off";
            File file = new File("Resources/Icons/icon_sound"+soundOnOff+".png");
            Image image = new Image(file.toURI().toString());
            icon_sound.setImage(image);
            event.consume();
        });
    }

    private void setHint() {
        icon_partSolution.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            mazeDisplayer.setHint();
            solveMaze();
            event.consume();
        });
    }

    private void setFullSolution() {
        icon_fullSolution.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            solveMaze();
            event.consume();
        });
    }

    private void makeNewMaze() {
        icon_makeNewMaze.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            newMaze();
            event.consume();
        });
    }

    public void mouseDragged(MouseEvent mouseEvent) {
        if (mazeDisplayer != null) {
            lbl_statusBar.setText("");
            int maxSize = Math.max(myViewModel.getMaze()[0].length, myViewModel.getMaze().length);
            double cellHeight = mazeDisplayer.getHeight() / maxSize;
            double cellWidth = mazeDisplayer.getWidth() / maxSize;
            double canvasHeight = mazeDisplayer.getHeight();
            double canvasWidth = mazeDisplayer.getWidth();
            int rowMazeSize = myViewModel.getMaze().length;
            int colMazeSize = myViewModel.getMaze()[0].length;
            double startRow = (canvasHeight / 2-(cellHeight * rowMazeSize / 2)) / cellHeight;
            double startCol = (canvasWidth / 2-(cellWidth * colMazeSize / 2)) / cellWidth;
            double mouseX = (int) ((mouseEvent.getX()) / (mazeDisplayer.getWidth() / maxSize)-startCol);
            double mouseY = (int) ((mouseEvent.getY()) / (mazeDisplayer.getHeight() / maxSize)-startRow);
            if (!myViewModel.isAtTheEnd()) {
                if (mouseY < myViewModel.getMainCharacterPositionRow() && mouseX == myViewModel.getMainCharacterPositionColumn())
                    myViewModel.moveCharacter(KeyCode.UP);
                if (mouseY > myViewModel.getMainCharacterPositionRow() && mouseX == myViewModel.getMainCharacterPositionColumn())
                    myViewModel.moveCharacter(KeyCode.DOWN);
                if (mouseX < myViewModel.getMainCharacterPositionColumn() && mouseY == myViewModel.getMainCharacterPositionRow())
                    myViewModel.moveCharacter(KeyCode.LEFT);
                if (mouseX > myViewModel.getMainCharacterPositionColumn() && mouseY == myViewModel.getMainCharacterPositionRow())
                    myViewModel.moveCharacter(KeyCode.RIGHT);
            }
        }
    }

    public void scrollInOut(ScrollEvent scrollEvent) {
        try {
            myViewModel.getMaze();
            AnimatedZoomOperator zoomOperator = new AnimatedZoomOperator();
            double zoomFactor;
            if (scrollEvent.isControlDown()) {
                icon_zoomImageView.setVisible(true);
                zoomFactor = 1.5;
                double deltaY = scrollEvent.getDeltaY();
                if (deltaY < 0)
                    zoomFactor = 1 / zoomFactor;
                zoomOperator.zoom(mazeDisplayer, zoomFactor, scrollEvent.getSceneX(), scrollEvent.getSceneY());
                scrollEvent.consume();
            }
        } catch(NullPointerException e) {
            scrollEvent.consume();
        }
    }

    private void resetZoom() {
        icon_zoomImageView.setVisible(false);
        Timeline timeLine = new Timeline(60);
        timeLine.getKeyFrames().clear();
        timeLine.getKeyFrames().addAll(new KeyFrame(Duration.millis(100), new KeyValue(mazeDisplayer.translateXProperty(), 0)),
                new KeyFrame(Duration.millis(100), new KeyValue(mazeDisplayer.translateYProperty(), 0)),
                new KeyFrame(Duration.millis(100), new KeyValue(mazeDisplayer.scaleXProperty(), 1)),
                new KeyFrame(Duration.millis(100), new KeyValue(mazeDisplayer.scaleYProperty(), 1)));
        timeLine.play();
    }
}