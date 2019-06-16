package View;

import ViewModel.MyViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.converter.IntegerStringConverter;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class NewGameController implements IView, Initializable {
    private ArrayList<String> mainCharacterList = new ArrayList(Arrays.asList(new String[]{"Simba_", "Pumba_", "Genie_"}));
    private String mainCharacter = "Simba_";
    @FXML
    public javafx.scene.image.ImageView newGame_mainCharacter_imageView;
    public TextField newGame_rowsInput;
    public TextField newGame_colsInput;
    public Button newGame_mainCharacter_prevBtn;
    public Button newGame_mainCharacter_nextBtn;
    public Button newGame_Button;
    public Label pleaseWait_lbl;
    private MyViewModel myViewModel;

    private UnaryOperator<TextFormatter.Change> integerFilter = change -> {
        String newText = change.getControlNewText();
        if (newText.matches("([1-9][0-9]*)?")) {
            return change;
        }
        return null;
    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File file = new File("Resources/Characters/Simba_character.png");
        Image image = new Image(file.toURI().toString());
        newGame_mainCharacter_imageView.setImage(image);
        newGame_rowsInput.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), Integer.valueOf(newGame_rowsInput.getText()), integerFilter));
        newGame_colsInput.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), Integer.valueOf(newGame_colsInput.getText()), integerFilter));
    }

    public void newGameKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER)
            startGame();
        keyEvent.consume();
    }

    public void startGame() {

        try {
            int rows = Integer.valueOf(newGame_rowsInput.getText());
            int cols = Integer.valueOf(newGame_colsInput.getText());
            if (cols < 5 || rows != cols)
                throw new Exception();
               int twoDem = Integer.max(rows,cols);
            myViewModel.setMainCharacterName(mainCharacter);
            disableButtons();
            myViewModel.generateMaze(twoDem, twoDem);
            myViewModel.startSoundTrack(mainCharacter);

        } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setGraphic(null);
            alert.setTitle("Error Alert");
            alert.setHeaderText("ERROR!");
            alert.setContentText("Please enter a valid numbers \n * only dimensions above 5 \n * only square dimensions");
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
    }

    public void getPrevMainCharacter() {
        int curIndex = mainCharacterList.indexOf(mainCharacter);
        String prevCharacter;
        if (curIndex == 0) {
            curIndex = mainCharacterList.size()-1;
            prevCharacter = mainCharacterList.get((curIndex) % mainCharacterList.size());
        } else
            prevCharacter = mainCharacterList.get((curIndex-1) % mainCharacterList.size());
        File file = new File("Resources/Characters/"+prevCharacter+"character.png");
        Image image = new Image(file.toURI().toString());
        newGame_mainCharacter_imageView.setImage(image);
        mainCharacter = prevCharacter;
    }

    void enableButtons() {
        newGame_mainCharacter_nextBtn.setDisable(false);
        newGame_mainCharacter_prevBtn.setDisable(false);
        newGame_Button.setDisable(false);
        newGame_colsInput.setDisable(false);
        newGame_rowsInput.setDisable(false);
        pleaseWait_lbl.setVisible(false);
    }

    private void disableButtons() {
        newGame_mainCharacter_nextBtn.setDisable(true);
        newGame_mainCharacter_prevBtn.setDisable(true);
        newGame_Button.setDisable(true);
        newGame_colsInput.setDisable(true);
        newGame_rowsInput.setDisable(true);
        pleaseWait_lbl.setVisible(true);
    }

    public void setViewModel(MyViewModel myViewModel) {
        this.myViewModel = myViewModel;
    }
}
