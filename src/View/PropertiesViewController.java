package View;

import Server.Configurations;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PropertiesViewController extends Dialog implements Initializable {
    public ChoiceBox<String> algorithmChoiceBox;
    public ChoiceBox<String> mazeGeneratorChoiceBox;
    public Spinner<Integer> spinner;
    private SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);
    private Stage stage;

    public void saveChanges() {
        System.out.println("Properties: saveChanges");
        String algorithmString = algorithmChoiceBox.getValue();
        String generatorString = mazeGeneratorChoiceBox.getValue();
        int threadNum = spinner.getValue();
        System.out.println(generatorString);
        System.out.println(algorithmString);
        System.out.println(spinner.getValue());
        Configurations.runConf();
        Configurations.properties.setProperty("SearchingAlgorithm" , algorithmString);
        Configurations.properties.setProperty("MazeGenerator", generatorString);
        Configurations.properties.setProperty("NumberOfThreads",Integer.toString(threadNum));
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        spinner.setValueFactory(valueFactory);
        algorithmChoiceBox.getItems().addAll("BFS", "DepthFirstSearch", "BestFirstSearch");
        String searchValue = Configurations.properties.getProperty("SearchingAlgorithm");
        algorithmChoiceBox.setValue(searchValue);
        mazeGeneratorChoiceBox.getItems().addAll("MyMazeGenerator", "SimpleMazeGenerator");
        String generateValue = Configurations.properties.getProperty("MazeGenerator");
        mazeGeneratorChoiceBox.setValue(generateValue);

    }

    public void closeButton() {
        System.out.println("Properties: closeButton");
        stage.close();
    }

    void setStage(Stage stage) {
        this.stage = stage;
    }
}
