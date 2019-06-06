package View;

import ViewModel.MyViewModel;

import java.util.Observable;
import java.util.Observer;

public class MyViewController implements IView, Observer {

    private MyViewModel viewModel;
    private NewView newMaze;
    private SaveView saveMaze;
    private PropertiesView propertiesMaze;


    @Override
    public void displayMaze(int[][] maze) {

    }

    @Override
    public void update(Observable o, Object arg) {

    }

    public void genertNewGame(){
        newMaze.WindowNewMaze();
    }

    public void SaveMyGame(){
        saveMaze.saveWindow();
    }


    public void LodeGame(){

    }

    public void MazeProperties(){

    }

    public void help(){

    }

    public void About(){

    }

    public void Exit(){

    }

    public void ChangeSound(){

    }


}
