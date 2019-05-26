import algorithms.search.Solution;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer {

    private IModel IModel;
    public IntegerProperty currentRow, currentColumn;
    public StringProperty statusUpdate;

    public MyViewModel(IModel iModel) {
        this.IModel = iModel;
        currentColumn = new SimpleIntegerProperty();
        currentRow = new SimpleIntegerProperty();
        statusUpdate = new SimpleStringProperty();
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
