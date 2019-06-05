package Model;

import java.io.Serializable;

public class MazeCharacter implements Serializable {


    private String characterName;
    private int characterRow;
    private int characterCol;
    private String characterDirection;

    public MazeCharacter(String name, int row, int col) {
        characterName = name;
        characterRow = row;
        characterCol = col;
        characterDirection = "front";
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    int getCharacterRow() {
        return characterRow;
    }

    void setCharacterRow(int characterRow) {
        this.characterRow = characterRow;
    }

    int getCharacterCol() {
        return characterCol;
    }

    void setCharacterCol(int characterCol) {
        this.characterCol = characterCol;
    }

    String getCharacterDirection() {
        return characterDirection;
    }

    void setCharacterDirection(String characterDirection) {
        this.characterDirection = characterDirection;
    }
}
