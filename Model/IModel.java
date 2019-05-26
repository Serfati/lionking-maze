
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public interface IModel {

    void generateBoard(int row, int col);

    int[][] getBoard();

    Position playerPosition();

    Position goalPosition();

    void changePositionBy(int rowChange, int columnChange);

    void solveBoard();

    boolean isPlayerWinTheGame();

    Solution getSolution();

    void exit();

    boolean saveBoard(String fileName);

    boolean loadBoard(String fileName);
}
