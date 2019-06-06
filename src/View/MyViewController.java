package View;

import ViewModel.MyViewModel;

import java.util.Observable;
import java.util.Observer;

public class MyViewController implements IView, Observer {

    private MyViewModel viewModel;
    private NewView newMaze;
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

    }


    public void LodeGame(){

    }

    public void MazeProperties(){
        propertiesMaze.propertiesWindow();
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
