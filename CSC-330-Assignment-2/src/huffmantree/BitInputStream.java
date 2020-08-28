/**
 * Code directly sourced from the Mark Allen Weiss book.
 * 
 * Reads in a sequence of bits from a file for Huffman compression.
 */

package huffmantree;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class BitInputStream {
    
    private InputStream is;
    private int buffer;
    private int bufferPos;
    
    private static final int BITS_PER_BYTE = 8;
    
    public BitInputStream(String file){
        try{
            this.is = new FileInputStream(file);
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
        
        this.bufferPos = BITS_PER_BYTE;
    }
    
    public int readBit() throws IOException{
        if(this.bufferPos == BITS_PER_BYTE){
            this.buffer = this.is.read();
            if(this.buffer == -1)
                return -1;
            this.bufferPos = 0;
        }
        return getBit(this.buffer, this.bufferPos++);
    }
    
    public void close() throws IOException{
        this.is.close();
    }
    
    private static int getBit(int pack, int pos){
        return (pack & (1 << pos) ) != 0 ? 1 : 0;
    }

}
