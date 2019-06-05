package ViewModel;

import Model.IModel;
import Model.MazeCharacter;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer {

    private IModel model;
    private MediaPlayer gameSoundTrack;
    private boolean isPlayed = true;
    private MazeCharacter mainCharacter = new MazeCharacter("Simba", 0, 0);


    public MyViewModel(IModel model) {
        this.model = model;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == model) {
            if (arg != null) {
                String argument = (String) arg;
                if (argument.equals("Maze Load")) {
                    startSoundTrack(model.getLoadedCharacter().getCharacterName());
                }
            }
            setChanged();
            notifyObservers(arg);
        }
    }

    public boolean isPlayed() {
        return isPlayed;
    }

    public void moveCharacter(KeyCode movement) {
        model.moveCharacter(movement);
    }

    public void generateMaze(int row, int col) {
        model.generateMaze(row, col);
    }

    public int[][] getMaze() {
        return model.getMaze();
    }

    public int getMainCharacterPositionRow() {
        return model.getMainCharacterPositionRow();
    }

    public int getMainCharacterPositionColumn() {
        return model.getMainCharacterPositionColumn();
    }

    public String getMainCharacterDirection() {
        return model.getMainCharacterDirection();
    }

    public String getMainCharacterName() {
        return mainCharacter.getCharacterName();
    }

    public void setMainCharacterName(String character) {
        mainCharacter.setCharacterName(character);
    }

    public boolean isAtTheEnd() {
        return model.isAtTheEnd();
    }

    public ArrayList getSolution() {
        return model.getSolution();
    }

    public ArrayList getMazeSolutionArr() {
        return model.getMazeSolutionArr();
    }

    public void generateSolution() {
        model.generateSolution();
    }

    public void saveOriginalMaze(File file) {
        model.saveOriginalMaze(file, mainCharacter.getCharacterName());
    }

    public void saveCurrentMaze(File file) {
        model.saveCurrentMaze(file, mainCharacter.getCharacterName());
    }

    public void loadFile(File file) {

        model.loadMaze(file);
    }

    public void closeModel() {
        model.closeModel();
    }

    private void startSoundTrack(String character) {
        if (gameSoundTrack != null)
            gameSoundTrack.stop();
        String musicFile = "resources/Sounds/"+character+"GameSoundTrack.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        gameSoundTrack = new MediaPlayer(sound);
        gameSoundTrack.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                gameSoundTrack.seek(Duration.ZERO);
            }
        });
        if (isPlayed)
            gameSoundTrack.play();

    }

    public boolean setSound() {
        if (gameSoundTrack == null)
            isPlayed = !isPlayed;
        else {
            gameSoundTrack.play();
            if (isPlayed) {
                gameSoundTrack.setMute(true);
                isPlayed = false;
            } else {
                gameSoundTrack.setMute(false);
                isPlayed = true;
            }
        }
        return isPlayed;
    }

    public MazeCharacter getLoadedCharacter() {
        mainCharacter = model.getLoadedCharacter();
        return mainCharacter;
    }

}