package View;

import ViewModel.MyViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Observable;
import java.util.Observer;

@SuppressWarnings("ALL")
public class MyViewController implements Observer, IView {

    public MazeDisplayer mazeDisplayer;
    public javafx.scene.control.TextField txtfld_rowsNum;
    public javafx.scene.control.TextField txtfld_columnsNum;
    public javafx.scene.control.Label lbl_rowsNum;
    public javafx.scene.control.Label lbl_columnsNum;
    public javafx.scene.control.Button btn_generateMaze;
    @FXML
    private MyViewModel viewModel;
    private StringProperty characterPositionRow = new SimpleStringProperty();
    private StringProperty characterPositionColumn = new SimpleStringProperty();

    public void setViewModel(MyViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == viewModel) {
            displayMaze(viewModel.getMaze());
            btn_generateMaze.setDisable(false);
        }
    }

    @Override
    public void displayMaze(int[][] maze) {
        mazeDisplayer.setMaze(maze);
        int characterPositionRow = viewModel.getMainCharacterPositionRow();
        int characterPositionColumn = viewModel.getMainCharacterPositionColumn();
        mazeDisplayer.setCharacterPosition(characterPositionRow, characterPositionColumn);
        this.characterPositionRow.set(characterPositionRow+"");
        this.characterPositionColumn.set(characterPositionColumn+"");
    }

    public void generateMaze() {
        int heigth = Integer.valueOf(txtfld_rowsNum.getText());
        int width = Integer.valueOf(txtfld_columnsNum.getText());
        btn_generateMaze.setDisable(true);
        viewModel.generateMaze(width, heigth);
    }

    public void solveMaze(ActionEvent actionEvent) {
        showAlert();
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Solving maze..");
        alert.show();
    }


    //region String Property for Binding

    public void KeyPressed(KeyEvent keyEvent) {
        viewModel.moveCharacter(keyEvent.getCode());
        keyEvent.consume();
    }

    public void mouseDragged(MouseEvent mouseEvent) {

        if (mazeDisplayer != null) {
            int maxSize = Math.max(viewModel.getMaze()[0].length, viewModel.getMaze().length);
            double cellHeight = mazeDisplayer.getHeight() / maxSize;
            double cellWidth = mazeDisplayer.getWidth() / maxSize;
            double canvasHeight = mazeDisplayer.getHeight();
            double canvasWidth = mazeDisplayer.getWidth();
            int rowMazeSize = viewModel.getMaze().length;
            int colMazeSize = viewModel.getMaze()[0].length;
            double startRow = (canvasHeight / 2-(cellHeight * rowMazeSize / 2)) / cellHeight;
            double startCol = (canvasWidth / 2-(cellWidth * colMazeSize / 2)) / cellWidth;
            double mouseX = (int) ((mouseEvent.getX()) / (mazeDisplayer.getWidth() / maxSize)-startCol);
            double mouseY = (int) ((mouseEvent.getY()) / (mazeDisplayer.getHeight() / maxSize)-startRow);
            if (!viewModel.isAtTheEnd()) {
                if (mouseY < viewModel.getMainCharacterPositionRow() && mouseX == viewModel.getMainCharacterPositionColumn())
                    viewModel.moveCharacter(KeyCode.UP);
                if (mouseY > viewModel.getMainCharacterPositionRow() && mouseX == viewModel.getMainCharacterPositionColumn())
                    viewModel.moveCharacter(KeyCode.DOWN);
                if (mouseX < viewModel.getMainCharacterPositionColumn() && mouseY == viewModel.getMainCharacterPositionRow())
                    viewModel.moveCharacter(KeyCode.LEFT);
                if (mouseX > viewModel.getMainCharacterPositionColumn() && mouseY == viewModel.getMainCharacterPositionRow())
                    viewModel.moveCharacter(KeyCode.RIGHT);
            }
        }
    }

    public String getCharacterPositionRow() {
        return characterPositionRow.get();
    }

    public StringProperty characterPositionRowProperty() {
        return characterPositionRow;
    }

    public String getCharacterPositionColumn() {
        return characterPositionColumn.get();
    }

    public StringProperty characterPositionColumnProperty() {
        return characterPositionColumn;
    }

    public void setResizeEvent(Scene scene) {
        long width = 0;
        long height = 0;
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                System.out.println("Width: "+newSceneWidth);
            }
        });
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                System.out.println("Height: "+newSceneHeight);
            }
        });
    }

    public void About(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            stage.setTitle("AboutController");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("About.fxml").openStream());
            Scene scene = new Scene(root, 400, 350);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        } catch(Exception e) {

        }
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        this.mazeDisplayer.requestFocus();
    }

}
