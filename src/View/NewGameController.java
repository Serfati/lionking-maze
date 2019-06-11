package View;

import ViewModel.MyViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class NewGameController implements IView, Initializable {

    ArrayList<String> mainCharacterList = new ArrayList(Arrays.asList("Simba_", "Pumba_"));
    String mainCharacter = "Simba_";
    String secondCharacter = "Simba_Second_";
    @FXML
    public javafx.scene.image.ImageView newGame_mainCharacter_imageView;
    public TextField newGame_rowsInput;
    public TextField newGame_colsInput;
    public Button newGame_mainCharacter_prevBtn;
    public Button newGame_mainCharacter_nextBtn;
    public Button newGame_Button;
    public CheckBox newGame_multiPlayer_checkBox;

    public Label pleaseWait_lbl;
    UnaryOperator<TextFormatter.Change> integerFilter = change -> {

        String newText = change.getControlNewText();
        if (newText.matches("([1-9][0-9]*)?")) {
            return change;
        }
        return null;
    };
    private Stage stage;
    private MyViewModel myViewModel;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setViewModel(MyViewModel myViewModel) {
        this.myViewModel = myViewModel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File file = new File("Resources/Characters/Simba_character.png");
        Image image = new Image(file.toURI().toString());
        newGame_mainCharacter_imageView.setImage(image);
        newGame_rowsInput.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), Integer.valueOf(newGame_rowsInput.getText()), integerFilter));
        newGame_colsInput.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), Integer.valueOf(newGame_colsInput.getText()), integerFilter));
    }

    public void newGameKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER)
            startGame();
        keyEvent.consume();
    }

    public void enableButtons() {
        newGame_mainCharacter_nextBtn.setDisable(false);
        newGame_mainCharacter_prevBtn.setDisable(false);
        newGame_Button.setDisable(false);
        newGame_colsInput.setDisable(false);
        newGame_rowsInput.setDisable(false);
        newGame_multiPlayer_checkBox.setDisable(false);
        pleaseWait_lbl.setVisible(false);
    }

    private void disableButtons() {
        newGame_mainCharacter_nextBtn.setDisable(true);
        newGame_mainCharacter_prevBtn.setDisable(true);
        newGame_Button.setDisable(true);
        newGame_colsInput.setDisable(true);
        newGame_rowsInput.setDisable(true);
        newGame_multiPlayer_checkBox.setDisable(true);
        pleaseWait_lbl.setVisible(true);
    }

    public void startGame() {
        int rows = 0;
        int cols = 0;

        try {
            rows = Integer.valueOf(newGame_rowsInput.getText());
            cols = Integer.valueOf(newGame_colsInput.getText());
            if (rows < 5 || cols < 5)
                throw new Exception();
            myViewModel.setMainCharacterName(mainCharacter);
            disableButtons();
            myViewModel.generateMaze(rows, cols);
            myViewModel.startSoundTrack(mainCharacter);


        } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setGraphic(null);
            alert.setTitle("Error Alert");
            alert.setHeaderText("ERROR!");
            alert.setContentText("Please enter a number above 5.");
            alert.showAndWait();
            e.printStackTrace();

        }
    }

    public void getNextMainCharacter() {
        int curIndex = mainCharacterList.indexOf(mainCharacter);
        String nextCharacter = mainCharacterList.get((curIndex+1) % mainCharacterList.size());
        File file = new File("Resources/Characters/"+nextCharacter+"character.png");
        Image image = new Image(file.toURI().toString());
        newGame_mainCharacter_imageView.setImage(image);
        mainCharacter = nextCharacter;
        secondCharacter = nextCharacter+"Second_";
    }

    public void getPrevMainCharacter() {
        int curIndex = mainCharacterList.indexOf(mainCharacter);
        String prevCharacter = "";
        if (curIndex == 0) {
            curIndex = mainCharacterList.size()-1;
            prevCharacter = mainCharacterList.get((curIndex) % mainCharacterList.size());
            //secondCharacter = secondCharacterList[(curIndex) % mainCharacterList.size()];
        } else {
            prevCharacter = mainCharacterList.get((curIndex-1) % mainCharacterList.size());
            //secondCharacter = secondCharacterList[(curIndex - 1) % mainCharacterList.size()];
        }
        secondCharacter = prevCharacter+"Second_";


        File file = new File("Resources/Characters/"+prevCharacter+"character.png");
        Image image = new Image(file.toURI().toString());
        newGame_mainCharacter_imageView.setImage(image);
        mainCharacter = prevCharacter;
    }


}
