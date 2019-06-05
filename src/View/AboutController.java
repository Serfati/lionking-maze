package View;

import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AboutController implements Initializable {
    public javafx.scene.control.Button close;
    public javafx.scene.control.Label Itext;

    public void close() {
        Stage s = (Stage) close.getScene().getWindow();
        s.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Itext.setWrapText(true);
        Itext.setText("Creators Avihai Serfati and Moran Chery");
    }
}