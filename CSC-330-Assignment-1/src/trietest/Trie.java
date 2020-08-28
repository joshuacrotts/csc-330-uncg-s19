/**
 * I have abided by the UNCG Academic Integrity Agreement.
 * 
 * @author Joshua Crotts
 * @date February 12, 2019
 * 
 * Class for a Trie object, adds words from .txt files, stores frequencies
 * of repeated words, and searches for arbitrary words lexicographically.
 */

package trietest;

/**
 * * Implement this class **
 */
public class Trie {

    private Node root;
    
    /* Holds the number that is being searched for when
     * kthWord(...) is called.
     */
    private int searchWord;
    
    private static final int ALPHABET = 26;

    /**
     * Construct an empty Trie with just a root node.
     */
    public Trie() {
        this.root = new Node();
    }

    /**
     * Adds a word to the trie (or increases its frequency by one if it already
     * exists in the trie).
     *
     * @param word - word to add to the trie.
     */
    public void addWord(String word) {

        //If our word is below length of 1, we don't need to do anything
        if (word.length() < 1) {
            return;
        }
        Node currentNode = this.root;

        //Keeps track of the ASCII index 
        int currentIndex = -1;

        //Current char 
        char currentChar;

        /* System.out.println("WORD: "+word);
         * Loops over the length of the string and adds it to the 
         * trie.
         */
        for (int i = 0; i < word.length(); i++) {

            currentChar = word.charAt(i);
            currentIndex = word.charAt(i) - 'a';

            //If the node is blank, we need to add the char to the trie
            if (currentNode.getLetters()[currentIndex] == null) {

                /* Creates a new Node based off whatever char is being added 
                 * with a frequency of 0.
            	 */
                currentNode.getLetters()[currentIndex] = new Node(currentChar, 0);

                /* If we are just beginning a word, we need to designate it a 
                 * parent.
                 */
                if (i == 0) {
                    currentNode.getLetters()[currentIndex].setParent(true);
                }

            }

            /* If we are about to be at the end of the word, we need to advance
             * the pointer that's traversing the trie.
             */
            if (i + 1 != word.length()) {
                currentNode = currentNode.getLetters()[currentIndex];
            }

        }

        /* Once we have hit the bottom (i.e. finished adding the last char of 
         * the word), we need to increment its frequency by 1 and designate it
         * as a word.
         */
        currentNode.getLetters()[currentIndex].setFrequency(
        		currentNode.getLetters()[currentIndex].getFrequency() + 1);
        
        currentNode.getLetters()[currentIndex].setWord(true);
    }

    /**
     * Returns the number of times a word appears in the Trie.
     *
     * @param word - query word to be searched.
     * @return - number of times word appears in file represented by the trie.
     */
    public int wordFrequency(String word) {
        Node currentNode = this.root;

        int currentIndex;
        char currentChar;

        int count = 0;

        for (int i = 0; i < word.length(); i++) {
            /* Applies a 0-25 normalization offset to the current letter 
             * so we can use its position as an index in the letters array.
             */
            currentIndex = word.charAt(i) - 'a';
            
            currentChar = word.charAt(i);

            if (currentNode.getLetters()[currentIndex] == null) {
                return 0;
            }

            count = currentNode.getLetters()[currentIndex].getFrequency();

            currentNode = currentNode.getLetters()[currentIndex];

        }

        return count;
    }

    /**
     * kthWord(...) calls a helper recursive method to traverse through
     * the trie, returns the kth word in a lexicographically-sorted list
     * of unique words.
     * 
     * @param k - Position of word we're querying
     * @return - Return the kth word in the sorted sequence of unique words that
     * exist in the file/trie, where the first unique word is at position 1.
     * (returns null if k exceeds the number unique words in the file).
     *
     */
    public String kthWord(int k) {
    	//Instance variable that keeps track of what word we're searching for
        this.searchWord = k;
        
        StringBuilder kthWord = kTraverse(this.root, new StringBuilder());
        
        return kthWord != null ? kthWord.toString() : null;
    }
    
    

    /**
     * kTraverse will traverse through the trie and returns the word 
     * as specified by k in kthWord.
     * @param n - Root node 
     * @param word - StringBuilder object that is constructed throughout 
     * 				 recursive calls.
     * @return kth word in trie
     */
    private StringBuilder kTraverse(Node n, StringBuilder word) {

    	/* If our node is a valid word, we need to decrement the 
    	 * counter denoting how many words we need to pass left
    	 * before we return.
    	 */
        if (n.isWord()) {
            if (this.searchWord == 1){
                return word;
            } else {
                this.searchWord--;
            }
        }

        /* Iterates over the nodes and their children
         * as each node contains an array of 26 nodes
         * otherwise signifying the 26 letters of the 
         * alphabet.
         */
        for (int i = 0; i < ALPHABET; i++) {
            //Grabs the current node's child
            Node node = n.getLetters()[i];
            
            /* If we're on a node that doesn't have a letter
             * assigned, we can further check its' children,
             * given they exist. 
             *
             * Else, we continue searching through the current
             * node's children until we find one with a letter
             * assigned, or the end of the alphabet is reached,
             * whichever comes first.
             */
            if (node != null) {

            	/* Add the current char to the StringBuilder
            	 * that's constructing the word we wish to
            	 * return
            	 */
                word.append(node.getCharacter());

                /* Recursively call the method on the node's
                 * children and assign its returned 
                 * StringBuilder to result.
                 */
                StringBuilder result = kTraverse(node, word);

                /* If our returned StringBuffer is not null
                 * then we can simply return it here.
                 */
                if (result != null && result.length() > 0) {

                    return result;

                }
                
                /* By cutting the length off the SB by 1, we allow
                 * for proper printing of the words in the Trie.
                 */
                word.setLength(word.length() - 1);
            }
        }

        return null;
    }
}

class Node {

    private Node[] letters;

    private int frequency;
    private char character;
    private boolean isParent;
    private boolean isWord;
    
    private static final int ALPHABET = 26;

    public Node() {
        this.letters = new Node[ALPHABET];

        this.frequency = 0;
        this.character = '\000';
    }

    public Node(char character, int frequency) {
        this.letters = new Node[ALPHABET];

        //Null termination character
        this.character = character;
        this.frequency = frequency;
    }
    
    public Node[] getLetters() {
    	return this.letters;
    }
    
    public int getFrequency() {
    	return this.frequency;
    }
    
    public char getCharacter() {
    	return this.character;
    }
    
    public boolean isParent() {
    	return this.isParent;
    }
    
    public boolean isWord() {
    	return this.isWord;
    }
    
    public void setFrequency(int f) {
    	this.frequency = f;
    }
    
    public void setCharacter(char c) {
    	this.character = c;
    }
    
    public void setParent(boolean p) {
    	this.isParent = p;
    }
    
    public void setWord(boolean w) {
    	this.isWord = w;
    }
}
