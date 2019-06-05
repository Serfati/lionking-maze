package Model.JUnit;

import Model.algorithms.mazeGenerators.Maze;
import Model.algorithms.mazeGenerators.Position;
import Model.algorithms.search.AState;
import Model.algorithms.search.ISearchable;
import Model.algorithms.search.MazeState;
import Model.algorithms.search.SearchableMaze;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JUnitSearchableMazeTest {

    @Test
    void getAllPossibleStates() {
        int[][] map = {{0, 1, 0, 1, 1},
                {0, 0, 1, 0, 1},
                {0, 1, 1, 0, 1},
                {0, 0, 0, 0, 1},
                {0, 1, 0, 0, 1},
                {0, 0, 0, 1, 1}};
        String[] check = {"{3,0}", "{3,1}", "{3,2}", "{4,0}", "{4,2}", "{5,0}", "{5,1}", "{5,2}"};
        Maze maze = null;
        try {
            maze = new Maze(map, new Position(0, 0), new Position(4, 3));
        } catch(Exception ignored) {

        }
        int i = 0;
        ISearchable searchableMaze = new SearchableMaze(maze);
        ArrayList<AState> list = searchableMaze.getAllPossibleStates(new MazeState(0, null, new Position(4, 1)));
        for(AState aState : list) {
            String s = ((MazeState) aState).getPosition().toString();
            System.out.println(s);
            assertEquals(check[i], s);
            i++;
        }
        System.out.println("getAllPossibleStates test Passed!");
    }
}
