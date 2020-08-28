package trietest;

/**
 * Coding Assignment 1, CSC 330, Spring 2019 University of North Carolina at
 * Greensboro
 */
import java.io.*;

public class TrieTest {

    private static final String testFile1 = "alice.txt";
    private static final String frankenstein = "frankenstein.txt";
    private static final String huckfinn = "huckfinn.txt";
    private static final String pandp = "prideandprejudice.txt";
    private static final String big = "big.txt";
    private static final String dracula = "dracula.txt";

    public static void main(String[] args) throws Exception {
        
        /*   
        Trie frankTrie = makeTrie(frankenstein);
        System.out.println(frankTrie.wordFrequency("frightened"));
        System.out.println(frankTrie.wordFrequency("in"));
        System.out.println(frankTrie.kthWord(2001)); */
    	/*
    	Trie hfinn = makeTrie(huckfinn);
    	System.out.println(hfinn.wordFrequency("huckleberry"));
    	System.out.println(hfinn.wordFrequency("finn"));
    	System.out.println(hfinn.wordFrequency("jim"));
    	System.out.println(hfinn.kthWord(7867));
        */
        
        Trie aliceTrie = makeTrie(testFile1);
        System.out.println(aliceTrie.wordFrequency("a"));        // 685
        System.out.println(aliceTrie.wordFrequency("nonsense")); // 7
        System.out.println(aliceTrie.wordFrequency("alice"));    // 385
        System.out.println(aliceTrie.wordFrequency("queen"));    // 68
        System.out.println(aliceTrie.wordFrequency("fred"));     // 0
        System.out.println(aliceTrie.kthWord(1));       // a
        System.out.println(aliceTrie.kthWord(10));      // accepted
        System.out.println(aliceTrie.kthWord(500));     // confirmation
        System.out.println(aliceTrie.kthWord(2000));    // perform
        System.out.println(aliceTrie.kthWord(10000));   // null
        
     /*   
        Trie bigTrie = makeTrie(big);
        
        System.out.println(bigTrie.wordFrequency("a"));
        System.out.println(bigTrie.kthWord(9501));
    
        Trie draculaTrie = makeTrie(dracula);
        System.out.println(draculaTrie.wordFrequency("dracula"));
        System.out.println(draculaTrie.kthWord(500));
        */
    }

    /**
     * Creates a trie constructed from the textfile fileName.
     *
     * @param fileName - file to be processed.
     * @return A trie representing fileName.
     */
    public static Trie makeTrie(String fileName) throws Exception {
        File f = new File(fileName);
        BufferedReader br = new BufferedReader(new FileReader(f));
        BufferedWriter bw = new BufferedWriter(new FileWriter("alice.out"));

        Trie retTrie = new Trie();
        String curLine;
        int count = 1;
        while ((curLine = br.readLine()) != null) {
            String[] words = curLine.split("\\s+");
            for (String word : words) {
                //System.out.println(word);
                word = cleanWord(word);
                retTrie.addWord(word);
                if(!word.equals(""))
                	bw.write(word+"\n");
            }
            
        }
        
        bw.close();
        return retTrie;
    }

    /**
     * Strips word of all non a-through-z characters.
     *
     * @param word - string to be cleaned.
     * @return word with only character a through z.
     */
    public static String cleanWord(String word) {
        String ret = "";
        for (int i = 0; i < word.length(); i++) {
            char cur = word.charAt(i);
            if ((cur >= 'A' && cur <= 'Z')) {
                cur -= 'A' - 'a';
                ret += cur;
            } else if ((cur >= 'a' && cur <= 'z')) {
                ret += cur;
            }
        }
        return ret;
    }
}
