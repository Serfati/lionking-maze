package View;
import javafx.scene.image.ImageView;
import javafx.beans.InvalidationListener;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import ViewModel.MyViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

public class MyViewController implements IView, Observer {

    public javafx.scene.control.Label curr_row;
    public javafx.scene.control.Label curr_col;
    public javafx.scene.control.Button newMaze;
    public MazeDisplayer mazeDisplayer;
    public javafx.scene.control.Slider sound_control;

    private MyViewModel viewModel;
    private NewView newGame;
    private PropertiesView propertiesMaze;
    private Scene mainScene;
    private Stage mainStage;
    private Media media;
    private MediaPlayer mediaPlayer ;



    private StringProperty characterPositionRow = new SimpleStringProperty();
    private StringProperty characterPositionColumn = new SimpleStringProperty();



    @Override
    public void displayMaze(int[][] maze) {
        mazeDisplayer.setMaze(maze);
        int characterPositionRow = viewModel.getMainCharacterPositionRow();
        int characterPositionColumn = viewModel.getMainCharacterPositionColumn();
        mazeDisplayer.setCharacterPosition(characterPositionRow, characterPositionColumn);
        this.characterPositionRow.set(characterPositionRow+"");
        this.characterPositionColumn.set(characterPositionColumn+"");

    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == viewModel) {
            displayMaze(viewModel.getMaze());
            newMaze.setDisable(false);
        }
    }


    private void bindProperties() {
        curr_row.textProperty().bind(this.characterPositionRow);
        curr_col.textProperty().bind(this.characterPositionColumn);
    }


    public void genertNewGame(){
        newGame.WindowNewMaze();
    }

    public void SaveMyGame(){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("*.Maze");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(null);
        viewModel.saveCurrentMaze(file);
    }

    public void soundMouseClicked(){

    }



    public void solveMaze(){

    }


    public void LodeGame(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Lode Maze:");
        File file = fileChooser.showOpenDialog(null);
        viewModel.loadFile(file);
    }

    public void MazeProperties(){
        propertiesMaze.propertiesWindow();
    }

    public void help(){

    }

    public void About(){
        try {
            Stage stage = new Stage();
            stage.setTitle("About:");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("View/About.fxml").openStream());
            Scene scene = new Scene(root, 400, 350);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        } catch(Exception e) {

        }
    }

    public void Exit(){

    }

   public void KeyPressed(KeyEvent keyEvent){
       viewModel.moveCharacter(keyEvent.getCode());
       keyEvent.consume();
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


    public void setViewModel(MyViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void initialize(MyViewModel viewModel, Stage mainStage, Scene mainScene) {

        this.viewModel = viewModel;
        this.mainScene = mainScene;
        this.mainStage = mainStage;
        String path = new File("resources/Music/backgroundmusic.mp3").getAbsolutePath();
        media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        sound_control.setValue(mediaPlayer.getVolume()*100);
        sound_control.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(javafx.beans.Observable observable) {
                mediaPlayer.setVolume(sound_control.getValue()/100);
            }
        });

        mainStage.setMinWidth(1000);
        mainStage.setMinHeight(700);
        mainStage.setFullScreen(true);
        bindProperties();
        setResizeEvent(mainScene);

    }
}
