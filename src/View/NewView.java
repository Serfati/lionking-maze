package View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.Observable;
import java.util.Observer;

public class NewView implements Observer {

    public javafx.scene.control.TextField rows;
    public javafx.scene.control.TextField columns;

    private Scene scene;

    public void cancel(){

    }


    public void WindowNewMaze(){
        try {
            Stage stage = new Stage();
            stage.setTitle("New Game:");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("View/MyNewView.fxml").openStream());
            scene = new Scene(root, 400, 350);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        } catch(Exception e) {

        }
    }

    public void WindowGeneratMaze(){

    }


    @Override
    public void update(Observable o, Object arg) {

    }
}
