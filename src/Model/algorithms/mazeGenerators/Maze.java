package Model.algorithms.mazeGenerators;

import java.io.Serializable;
import java.util.ArrayList;

import static java.util.Arrays.stream;
import static java.util.stream.IntStream.range;

/**
 *    The maze is represented as a 2D char array.
 *     Zero ('0') - Pass
 *     One ('1') - Wall
 *     Start ('S') - The starting position
 *     End ('E') - The "Goal" position
 *
 * @author Serfati
 * @version 1.0
 * @since 20-03-2019
 */
public class Maze implements Serializable {
    public int[][] mMaze;
    private Position mStart;
    private Position mGoal;
    private int indexByteArray = 0; //A global index for moving

    /**
     * CTOR
     */
    public Maze(int [][] pMaze, Position pStart, Position pGoal ) {
        this.mMaze = pMaze;
        this.mStart = pStart;
        this.mGoal = pGoal;
    }

    /**
     * CTOR
     * function that decodes the data from the Byte array and returns a maze by an int array.
     *
     * @param byteMaze-
     */
    public Maze(byte[] byteMaze) {
        int row = byteArrayToInt(byteMaze);
        int col = byteArrayToInt(byteMaze);
        Position start = new Position(byteArrayToInt(byteMaze), byteArrayToInt(byteMaze));
        Position end = new Position(byteArrayToInt(byteMaze), byteArrayToInt(byteMaze));
        this.mStart = start;
        this.mGoal = end;

        int[][] mazeArray = new int[row][col];
        for(int i = 0; i < row; i++)
            for(int j = 0; j < col; j++)
                mazeArray[i][j] = byteMaze[indexByteArray++]+(Byte.MAX_VALUE+1);
        this.mMaze = mazeArray;
    }

    /**
     * This method print the maze where every passage is
     * a empty space and every wall is a "1".
     * the start mark with S
     * the goal mark with E
     */
    public void print() {
        for(int i = 0; i < mMaze.length; i++) {
            for(int j = 0; j < mMaze[i].length; j++) {
                if (i == mStart.getRowIndex() && j == mStart.getColumnIndex())
                    System.out.print("S");

                else if (i == mGoal.getRowIndex() && j == mGoal.getColumnIndex())
                    System.out.print("E");

                else if (mMaze[i][j] == 0)
                    System.out.print("0");
                else if (mMaze[i][j] == 1)
                    System.out.print("1");
            }
            System.out.println();
        }
    }

    /******************************************************************************
     ~-=-=-~-~=~-=-=-~=~-=-=-~= IO stream section  ~-==-=-~=~-=-=~-=-=-~=-~=
     ******************************************************************************/

    /**
     * Returns the value after decoding from indexByteArray to END_OF_TYPE
     * read section by section
     *
     * @param byteArray -
     * @return - int from byteArray
     */
    private int byteArrayToInt(byte[] byteArray) {
        int sum = 0;
        sum += (byteArray[indexByteArray])+(Byte.MAX_VALUE+1);
        indexByteArray++;
        while(byteArray[indexByteArray] != (byte) 127) {
            sum += (byteArray[indexByteArray])+(Byte.MAX_VALUE+1);
            indexByteArray++;
        }
        indexByteArray++;
        return sum;
    }

    /**
     * Add Pattern:
     * Row,Column,startRow,startCol,endRow,endCol,MazeData
     *
     * @return - byte array with all critical values of the maze.
     */
    public byte[] toByteArray() {
        ArrayList<Byte> dynamicBytes = new ArrayList<>();
        insertToByteArray(dynamicBytes, mMaze.length);
        insertToByteArray(dynamicBytes, mMaze[0].length);
        insertToByteArray(dynamicBytes, mStart.getRowIndex());
        insertToByteArray(dynamicBytes, mStart.getColumnIndex());
        insertToByteArray(dynamicBytes, mGoal.getRowIndex());
        insertToByteArray(dynamicBytes, mGoal.getColumnIndex());

        stream(mMaze).forEach(ints -> range(0, mMaze.length).mapToObj(j -> toUnsignedByte(ints[j])).forEachOrdered(dynamicBytes::add));
        byte[] byteArray = new byte[dynamicBytes.size()];
        range(0, byteArray.length).forEach(i -> byteArray[i] = dynamicBytes.get(i));

        return byteArray;
    }

    /**
     * @param dynamicByte - our array list of bytes
     * @param insert - int to be inserted
     */
    private void insertToByteArray(ArrayList<Byte> dynamicByte, int insert) {
        if (insert / 254 != 0) {
            int i = 0;
            while(i < insert / 254) {
                dynamicByte.add(toUnsignedByte(254));
                i++;
            }
        } else
            dynamicByte.add(toUnsignedByte(insert % 254)); // remainder
        dynamicByte.add((byte) 127); //delimiter
    }

    /**
     * @param from int to be convert
     * @return - byte
     */
    private byte toUnsignedByte(int from) {
        return (byte) (from-(Byte.MAX_VALUE+1));
    }

    /**
     * This method return the start of the maze
     *
     * @return Position
     */
    public Position getStartPosition() {
        return mStart;
    }

    /**
     * This method return the goal of the maze
     *
     * @return Position
     */
    public Position getGoalPosition() {
        return mGoal;
    }
}