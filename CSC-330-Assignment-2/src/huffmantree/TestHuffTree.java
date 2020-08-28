/**
 * Class for testing MinHeap and Huffman Tree methods.
 * Also contains the buildHuffmanTree() method (to be written by you),
 * which should construct a Huffman tree for the test file using the
 * greedy algorithm described in the assignment write-up.
 *
 * @author Joshua Crotts, COMPSCI 330, Spring 2019.
 */
package huffmantree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public class TestHuffTree {

    private final static String testFile = "alice.txt";

    /* Strings used to test the correctness of your min heap implementation. */
    private static final String[] heapTestStrings
            = {
                "[1, 10, 20]",
                "[2, 5, 10, 20]",
                "[5, 6, 7, 100, 20, 10]",
                "[10, 100, 20]",
                "[3, 4, 20, 9, 15, 33, 42, 100, 10, 77, 99]",
                "[3, 5, 6, 9, 84, 19, 17, 22, 10]"
            };

    /* Strings used to test the correctness of your Huffman 
    tree implementation. */
    private static final String[] huffTestStrings
            = {
                "The Cat only grinned when it saw Alice.",
                "Well, I’d hardly finished the first verse,’ said the Hatter.",
                "Off with their heads!",
                "The lazy dog jumped over the quick brown fox.",
                "COMPSCI 330 is my favorite class.",
                "We are the best!",
                "We are truly the best. Like, you guys, it is not even funny as to how "
                + "absolutely outstanding and perfectly great we are, and for all of the hard work you have invested"
                + " into this company.",
                "Do not fret! For we are... yknow, kind of shy!"
            };

    /* Encoded versions of the strings of huffTestStrings. */
    private static final String[] huffEncodedStrings
            = {
                "100010010010000111110001110001101011111011101011001110010011110101111"
                + "0010011010101010001010011110101000100000101111001110111111101101"
                + "10101010111110001011001100111100000001000110",
                "01001100100010011100111001011111101001011010101010011100100110110011010"
                + "0100111001001111000010011010100111101100100001010011110110010000111100001"
                + "0011110011101110111110100100000110011101100010010111010101111101101100011"
                + "1010011110110010000111100011101011010111011000110011000110",
                "010010111100001100001111101010001110110010111101100100000011110011110"
                + "0100000110101001101101001010",
                "1000100100100001111001101101000111110110010011110100011110101111111"
                + "0100111001000100000110101100010100111011101001000001100111110110010000"
                + "11110001000000100000111100001000101111110100011001011110101001011111"
                + "00001011111000100001000110",
                "1100011100010010111110100111111000100011000100011100011100110100101111"
                + "10001110111101100011101111011000111011001110011110111111000001001"
                + "00111100001011001001000111110010011101100011111000010011011011011110"
                + "111000110",
                "01001100100011101101100100011110110010000111110100000011011101101001010",
                "01001100100011101101100100011110111100101000100111001001111011001000011111010000001101110111000110111100011111100111000101000100101111100100011101000111101011010001001001101110010111100111011111001111011111010101111011111000010010000001011111000010100001010101100100111011011011111101101111110010011110101011101101101000110110111100110100010110001001110010011101110100010111101110110110010110100001101011010111110110010110100111110101100011001100001000110000101110011100100111101011110010000110101111110101000011101101100100010010111101100101101001111000010111110011110110100111001111101111000011111011001000011100100110110011010011110101001111100110001011111001000111010001110010011001001000001110011010101001000001101110110001010011100110101101101111111011001000111101111111000001111000001101011011001011001001000110",
                "010011000011111101010111101111110000111001000101101001010111010011011101111100111110101000011101101100100010001101000110100011011110010010001010101011110101010010111110001010011010110100111011110000111111011001010010001001010"
            };

    /**
     * Constructs a Huffman tree from characters frequencies given in charFreq.
     *
     * @param charFreq HashMap that maps characters to their frequencies (in the
     * test file).
     * @return a Huffman tree constructed from the greedy Huffman algorithm (see
     * write-up).
     */
    public static HuffmanTree buildHuffmanTree(HashMap<Character, Integer> charFreq) {

        MinHeap<HuffmanTree> huffTree = new MinHeap<>();

        //Builds the heap with characters and corresponding frequencies
        //using a lambda operator and for-each loop
        charFreq.entrySet().forEach((pair) -> {
            huffTree.insert(new HuffmanTree(pair.getKey(), pair.getValue()));
        });

        //While the heap still has elements, pool the two minimum nodes from the 
        //heap, combine the two, and add the sum back to the heap.
        while (huffTree.getSize() > 1) {
            HuffmanTree min = huffTree.extractMin();
            HuffmanTree nextMin = huffTree.extractMin();
            HuffmanTree newRoot = new HuffmanTree(min, nextMin);

            huffTree.insert(newRoot);
        }

        return huffTree.extractMin();
    }

    /**
     * Tests the insert() and extractMin() methods for MinHeap.
     */
    public static int testMinHeap() {
        int[] totalCorrect = {0};
        int testNumber = 1;
        MinHeap<Integer> myHeap = new MinHeap();

        /* First insert 10, 1 and, 20 
           Current heap: [1, 10, 20] */
        myHeap.insert(10);
        myHeap.insert(1);
        myHeap.insert(20);
        printHeapTestMessage(myHeap, testNumber, totalCorrect);
        testNumber++;

        /* Extract min, and then insert 5 and 2 
         Current heap: [2, 5, 10, 20] */
        myHeap.extractMin();
        myHeap.insert(5);
        myHeap.insert(2);
        printHeapTestMessage(myHeap, testNumber, totalCorrect);
        testNumber++;

        /* Insert 3, 7, 100, and 6; then extract min twice 
           Current heap: [5, 6, 7, 100, 20, 10]*/
        myHeap.insert(3);
        myHeap.insert(7);
        myHeap.insert(100);
        myHeap.insert(6);
        myHeap.extractMin();
        myHeap.extractMin();
        printHeapTestMessage(myHeap, testNumber, totalCorrect);
        testNumber++;

        /* Extract min three more times 
           Current heap: [10, 100, 20] */
        myHeap.extractMin();
        myHeap.extractMin();
        myHeap.extractMin();
        printHeapTestMessage(myHeap, testNumber, totalCorrect);
        testNumber++;

        /* Insert 3, 15, 33, 42, 9, 4, 0, 99, and 77; finally exract min
           Current heap: [3, 4, 20, 9, 15, 33, 42, 100, 10, 77, 99] */
        myHeap.insert(3);
        myHeap.insert(15);
        myHeap.insert(33);
        myHeap.insert(42);
        myHeap.insert(9);
        myHeap.insert(4);
        myHeap.insert(0);
        myHeap.insert(99);
        myHeap.insert(77);
        myHeap.extractMin();
        printHeapTestMessage(myHeap, testNumber, totalCorrect);
        testNumber++;

        MinHeap<Integer> myHeap2 = new MinHeap<>();
        myHeap2.insert(5);
        myHeap2.insert(3);
        myHeap2.insert(17);
        myHeap2.insert(10);
        myHeap2.insert(84);
        myHeap2.insert(19);
        myHeap2.insert(6);
        myHeap2.insert(22);
        myHeap2.insert(9);
        printHeapTestMessage(myHeap2, testNumber, totalCorrect);
        testNumber++;

        return totalCorrect[0];

    }

    /**
     * Tests the insert() and extractMin() methods for MinHeap.
     * 
     * @return total number of problems correct
     * @throws Exception
     */
    public static int testHuffTree() throws Exception {
        HashMap<Character, Integer> charFreq = makeFreqTable(testFile);
        HuffmanTree hTree = buildHuffmanTree(charFreq);
        int totalCorrect = 0;
        int testNumber = 1;

        for (int i = 0; i < huffEncodedStrings.length; i++) {
            String s = huffEncodedStrings[i];
            String targetString = huffTestStrings[i];
            String decoded = hTree.decode(s);

            System.out.printf("Huffman Tree Test Number: %d\n", testNumber);
            System.out.printf("Target string: %s\nDecoded string: %s\n", targetString, decoded);
            if (decoded.equals(huffTestStrings[i])) {
                System.out.println("*** CORRECT ***\n");
                totalCorrect++;
            } else {
                System.out.println("*** INCORRECT ***\n");
            }
            testNumber++;
        }

        return totalCorrect;
    }

    public static HuffmanTree encodeFile(String file) throws Exception {
        HashMap<Character, Integer> mappedChars = makeFreqTable(file);
        HuffmanTree hTree = buildHuffmanTree(mappedChars);

        //Hashmap with bit-strings corresponding to characters from file
        HashMap<Character, String> map = hTree.encode();

        BufferedReader input = new BufferedReader(new FileReader(file));
        BitOutputStream output = new BitOutputStream((file.substring(0, file.length() - 4)) + "-encoded.txt");

        char c = 0;
        while ((c = (char) input.read()) != (char) -1) {
            String x = map.get(c);

            if (x == null) {
                continue;
            }

            for (int i = 0; i < x.length(); i++) {
                output.writeBit(x.charAt(i) - '0');
            }
        }

        input.close();
        output.close();

        return hTree;
    }

    public static void decodeFile(String file, HuffmanTree bitMapping) throws Exception {
        BitInputStream input = new BitInputStream(file);
        String newFileName = file.contains("encoded.txt")
                ? (file.substring(0, file.length() - 12)) + "-decoded.txt" : file;

        FileWriter output = new FileWriter(newFileName);

        //Bit sequence
        StringBuilder s = new StringBuilder();

        int c = 0;
        while ((c = input.readBit()) != -1) {
            s.append(c);
        }

        output.write(bitMapping.decode(s.toString()));
        output.close();
    }

    /* Print test results for testing MinHeap. */
    public static void printHeapTestMessage(MinHeap<Integer> myHeap,
            int tNum, int[] totalCorrect) {
        System.out.printf("MinHeap Test Number: %d\n", tNum);
        System.out.printf("Target heap: %s\nConstructed string: %s\n",
                heapTestStrings[tNum - 1], myHeap.toString());
        if (heapTestStrings[tNum - 1].equals(myHeap.toString())) {
            System.out.println("*** CORRECT ***\n");
            totalCorrect[0]++;
        } else {
            System.out.println("*** INCORRECT ***\n");
        }
    }

    /* Returns a frequency table for the characters in fileName. */
    public static HashMap<Character, Integer> makeFreqTable(String fileName) throws Exception {
        File f = new File(fileName);
        BufferedReader br = new BufferedReader(new FileReader(f));

        HashMap<Character, Integer> freq = new HashMap();

        String curLine;
        while ((curLine = br.readLine()) != null) {
            for (int i = 0; i < curLine.length(); i++) {
                char curChar = curLine.charAt(i);
                if (freq.get(curChar) == null) {
                    freq.put(curChar, 1);
                } else {
                    freq.put(curChar, freq.get(curChar) + 1);
                }
            }
        }
        return freq;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("\n**** MIN HEAP TESTS *****");
        int totalHeapCorrect = testMinHeap();
        System.out.printf("Total MinHeap Tests Correct: %d\n", totalHeapCorrect);

        /* Huffman tree tests.*/
        System.out.println("\n-\n\n**** HUFFMAN TREE TESTS *****");
        int totalHuffmanCorrect = testHuffTree();
        System.out.printf("Total Huffman Tree Tests Correct: %d\n", totalHuffmanCorrect);

        /* Total correct.*/
        System.out.printf("\nTotal Overall Correct: %d\n", totalHeapCorrect + totalHuffmanCorrect);

        HuffmanTree t = encodeFile("test/frankenstein.txt");
        decodeFile("test/frankenstein-encoded.txt", t);

        t = encodeFile("test/test.txt");
        decodeFile("test/test-encoded.txt", t);
    }
}
