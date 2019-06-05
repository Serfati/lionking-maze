package Model.IO;
import java.io.IOException;
import java.io.InputStream;

/**
 * The MyDecompressorInputStream program implements an application that read the
 * maze from file. The reading is performed by the number of letters appearance
 * from file.
 *
 * @author >
 * @version 3.0
 */
public class MyDecompressorInputStream extends InputStream {
    private InputStream in;
    private int delimiter;
    private int repeat;
    private boolean val;

    /**
     * CTOR
     *
     * @param in - InputStream variable
     */
    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
        this.delimiter = 0;
        this.repeat = 0;
        val = true;
    }

    /**
     * My Stream Pattern:
     * <p> Row; 127; Column; 127; startRow; 127; startCol; 127; endRow; 127; endCol; 127; MazeData;
     * {@inheritDoc}
     * @return an int the machine just read.
     * @throws IOException
     */
    @Override
    public int read() throws IOException {
        // maze array data
        if (delimiter >= 6) {
            if (repeat == 0) {
                repeat = in.read();
                val = !val;
            }
            repeat--;
            int returnValue = 0;
            if (val)
                returnValue = 1;
            if (repeat == 0) {
                repeat = in.read();
                val = !val;
            }
            return returnValue;
        }
        //regular data
        else {
            int i = in.read();
            if ((byte) (i-(Byte.MAX_VALUE+1)) == (byte) 127)
                delimiter++;
            return i;
        }
    }
}