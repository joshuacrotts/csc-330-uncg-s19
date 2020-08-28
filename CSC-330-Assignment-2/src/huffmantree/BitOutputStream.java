/**
 * Code directly sourced from the Mark Allen Weiss book.
 * 
 * Outputs a sequence of bits to a file for Huffman compression.
 */

package huffmantree;

import java.io.*;

public class BitOutputStream {

    private int bufferPos;
    private int buffer;
    private OutputStream os;

    private static final int BITS_PER_BYTE = 8;

    public BitOutputStream(String file) {
        this.bufferPos = 0;
        this.buffer = 0;
        
        try {
            this.os = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void writeBit(int val) throws IOException {
        this.buffer = setBit(this.buffer, this.bufferPos++, val);
        if (this.bufferPos == BITS_PER_BYTE) {
            flush();
        }
    }

    public void writeBits(int[] val) throws IOException {
        for (int i = 0; i < val.length; i++) {
            writeBit(val[i]);
        }
    }

    public void flush() throws IOException {
        if (this.bufferPos == 0) {
            return;
        }

        this.os.write(buffer);
        this.bufferPos = 0;
        this.buffer = 0;
    }

    public void close() throws IOException {
        this.flush();
        this.os.close();
    }

    private int setBit(int pack, int pos, int val) {
        if (val == 1) {
            pack |= (val << pos);
        }
        return pack;
    }

}
