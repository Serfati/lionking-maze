package Model.JUnit;

import Model.algorithms.mazeGenerators.IMazeGenerator;
import Model.algorithms.mazeGenerators.Maze;
import Model.algorithms.mazeGenerators.MyMazeGenerator;
import Model.algorithms.mazeGenerators.Position;
import Model.algorithms.search.ASearchingAlgorithm;
import Model.algorithms.search.BestFirstSearch;
import Model.algorithms.search.SearchableMaze;
import Model.algorithms.search.Solution;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JUnitTestingBestFirstSearch extends BestFirstSearch {

    @Test
    void mazeSize() {
        IMazeGenerator ig = new MyMazeGenerator();
        Maze maze = ig.generate(10, 10);
        assertEquals(maze.mMaze.length, 10);
    }

    @Test
    void notNull1() {
        IMazeGenerator ig = new MyMazeGenerator();
        Maze maze = ig.generate(0, 0);
        assertNull(maze);
    }

    @Test
    void notNull2() {
        IMazeGenerator ig = new MyMazeGenerator();
        Maze maze = ig.generate(-1, -1);
        assertNull(maze);
    }

    @Test
    void multiplyMazes() {
        IMazeGenerator ig = new MyMazeGenerator();
        for (int i = 2; i < 10; i++) {
            Maze maze = ig.generate(i, i*2);
            assertNotNull(maze);
        }
    }

    @Test
    void testBfsWithNullMaze() {
        ASearchingAlgorithm bfs = new BestFirstSearch();
        SearchableMaze maze = null;
        Solution sol = bfs.solve(maze);
        assertEquals(sol.getSolutionPath().size(), 0);
    }

    @Test
    void testBfsWithNullStart() {
        ASearchingAlgorithm bfs = new BestFirstSearch();
        SearchableMaze maze = new SearchableMaze(new Maze(new int[10][10], null, new Position(4, 4)));
        Solution sol = bfs.solve(maze);
        assertEquals(sol.getSolutionPath().size(), 0);
    }

    @Test
    void testBfsWithNullGoal() {
        ASearchingAlgorithm bfs = new BestFirstSearch();
        SearchableMaze maze = new SearchableMaze(new Maze(new int[10][10], new Position(4, 4), null));
        Solution sol = bfs.solve(maze);
        assertNull(sol);
    }

    @Test
    void testBfsWithSameStartGoal() {
        ASearchingAlgorithm bfs = new BestFirstSearch();
        SearchableMaze maze = new SearchableMaze(new Maze(new int[10][10], new Position(4, 4), new Position(4, 4)));
        Solution sol = bfs.solve(maze);
        assertEquals(sol.getSolutionPath().size(), 1);
    }


    @Test
    void solve() {
        Maze maze = new Maze(new int[10][10], new Position(5, 5), new Position(4, 4));
        SearchableMaze sMaze = new SearchableMaze(maze);
        BestFirstSearch BFS = new BestFirstSearch();
        Solution solution = BFS.solve(sMaze);
        assertNotNull(solution);
    }

    @Test
    void getName1() {
        BestFirstSearch BFS = new BestFirstSearch();
        assertEquals("Best First Search", BFS.getName());
    }

    @Test
    void PositiveEvaluatedNumber() {
        BestFirstSearch BFS = new BestFirstSearch();
        MyMazeGenerator myMaze = new MyMazeGenerator();
        Maze maze = myMaze.generate(100, 100);
        SearchableMaze searchableMaze = new SearchableMaze(maze);
        BFS.solve(searchableMaze);
        Assertions.assertTrue(BFS.getNumberOfNodesEvaluated() > 0);
    }

    @Test
    void solveInMinute() {
        BestFirstSearch BFS = new BestFirstSearch();
        MyMazeGenerator myMaze = new MyMazeGenerator();
        Maze maze = myMaze.generate(1000, 1000);
        SearchableMaze searchableMaze = new SearchableMaze(maze);
        long before = System.currentTimeMillis();
        BFS.solve(searchableMaze);
        long time = System.currentTimeMillis() - before;
        Assertions.assertTrue(time <= 60000);
    }

    @Test
    void testUnSolvableMaze() {
        int[][] map = {{0, 1, 0, 1, 1},
                {0, 0, 1, 0, 1},
                {0, 1, 1, 0, 1},
                {0, 0, 0, 0, 1},
                {0, 0, 0, 1, 1},
                {0, 0, 0, 1, 1}};
        boolean test;
        Solution solution;
        SearchableMaze sm = null;
        BestFirstSearch bestfs = null;
        try {
            Maze maze = new Maze(map, new Position(0, 0), new Position(4, 4));
            sm = new SearchableMaze(maze);
            bestfs = new BestFirstSearch();
        } catch(Exception ignored) {
        }
        assert bestfs != null;
        solution = bestfs.solve(sm);
        test = solution == null;
        if (test)
            System.out.println("BestFirstSearch unsolvable test PASSED!");
        else
            System.out.println("BestFirstSearch unsolvable test FAILED!");
        assertTrue(test);
    }


}