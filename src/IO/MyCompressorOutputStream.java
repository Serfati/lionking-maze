package Model.IO;

import java.io.IOException;
import java.io.OutputStream;

/**
 * The MyCompressorOutputStream program implements an application that write the
 * maze into a file. The writing is performed by the letters and their counters
 *
 * @author ?
 * @version 3.0
 */
public class MyCompressorOutputStream extends OutputStream {
    public OutputStream out;
    private int lastByte;
    private int counter;
    private int delimiter = 0;

    /**
     * CTOR
     *
     * @param out - OutputStream variable
     */
    public MyCompressorOutputStream(OutputStream out) {
        this.out = out;
        lastByte = 0;
        counter = 0;
    }

    /**
     * My Stream Pattern:
     * <p> Row; 127; Column; 127; startRow; 127; startCol; 127; endRow; 127; endCol; 127; MazeData;
     * {@inheritDoc}
     * @param b- int number smaller that 255
     * @return an int the machine just read.
     * @throws IOException
     */
    public void write(int b) throws IOException {
        // maze array data
        if (delimiter >= 6) {
            if (lastByte == b)
                counter++;
            else {
                int i = (counter / 254);
                while(i >= 0) {
                    out.write(toUnsignedByte(254));
                    i--;
                }
                if ((counter % 254) > 0)
                    out.write(toUnsignedByte(counter % 254));
            }
            out.write(counter);
            lastByte = b;
            counter = 1;
        } else {
            out.write(b);
            if (toUnsignedByte(b) == (byte) 127)
                delimiter++;
        }
    }

    private byte toUnsignedByte(int from) {
        return (byte) (from-(Byte.MAX_VALUE+1));
    }
}