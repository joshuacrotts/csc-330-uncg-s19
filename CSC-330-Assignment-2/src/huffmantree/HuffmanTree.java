/**
 * Huffman Tree data structure used for Huffman compressions.
 *
 * @author Joshua Crotts, COMPSCI 330, Spring 2019.
 */
package huffmantree;

import java.util.*;

public class HuffmanTree implements Comparable<HuffmanTree> {

    private Node root;
    private static final char DEF_CHAR = 0;

    /* Node class used to store the structure of the tree. */
    class Node<T extends Comparable<T>> {

        protected Node left;
        protected Node right;
        protected char myChar;
        protected int frequency;

        protected Node(Node l, Node r, char c, int f) {
            left = l;
            right = r;
            myChar = c;
            frequency = f;
        }
    }

    /**
     * Construct a Huffman tree that contains a singleton node with a character
     * and its frequency.
     *
     * @param c character to be stored at node.
     * @param f frequency of character.
     */
    public HuffmanTree(char c, int f) {
        root = new Node(null, null, c, f);
    }

    /**
     * Construct a Huffman that combines two other Huffman trees (i.e., the
     * parameter trees become the left and right subtree of the new tree's
     * root).
     *
     * @param t1 left subtree of the constructed tree.
     * @param t2 right subtree of the constructed tree.
     */
    public HuffmanTree(HuffmanTree t1, HuffmanTree t2) {
        root = new Node(t1.root, t2.root, DEF_CHAR, t1.root.frequency + t2.root.frequency);
    }

    /* Trees are compared based on the frequency of the root */
    public int compareTo(HuffmanTree other) {
        return root.frequency - other.root.frequency;
    }

    /**
     * Returns the decoded string from the encoded string bitString.
     *
     * @param bitString encoded string of 0s and 1s.
     * @return the string decoded from bitString (derived from the Huffman
     * tree's encoding).
     */
    public String decode(String bitString) {
        StringBuilder decoded = new StringBuilder();
        Node traversal = this.root;

        for (int i = 0; i < bitString.length(); i++) {
            //If the bit is a 0, traverse left. Otherwise,
            //traverse right.
            traversal = bitString.charAt(i) == '0' ? traversal.left : traversal.right;

            //Once we arrive at a leaf, we add the char to 
            //the decoded string builder
            if (isLeaf(traversal)) {
                decoded.append(traversal.myChar);
                traversal = this.root; //Resets the pointer to the top of the tree

            }
        }
        return decoded.toString();
    }

    private static boolean isLeaf(Node n) {
        return n.left == null && n.right == null;
    }

    /**
     * Returns the character encodings derived from the Huffman tree.
     *
     * @return hash map that maps characters to their binary encoding
     * (represented as a string of 0 and 1 characters).
     */
    public HashMap<Character, String> encode() {
        HashMap<Character, String> retMap = new HashMap();
        ArrayList<Character> curCode = new ArrayList();
        encodeHelper(retMap, curCode, root);
        return retMap;
    }

    /* Recursive function that computes all character encodings. */
    private static void encodeHelper(HashMap<Character, String> encodeMap,
            ArrayList<Character> curCode, Node cur) {

        if (isLeaf(cur)) {
            encodeMap.put(cur.myChar, arrayToString(curCode));
        } else {
            curCode.add('0');
            encodeHelper(encodeMap, curCode, cur.left);
            curCode.remove(curCode.size() - 1);
            curCode.add('1');
            encodeHelper(encodeMap, curCode, cur.right);
            curCode.remove(curCode.size() - 1);
        }
    }

    /* Utility method that converts an array to a string that 
     * is the concatenation of all elements in the array (
     * instead of the elements seperated by commas). */
    private static String arrayToString(ArrayList<Character> theArray) {
        String ret = "";
        for (int i = 0; i < theArray.size(); i++) {
            ret += theArray.get(i);
        }
        return ret;
    }

    /**
     * Returns the root of the Huffman tree.
     * @return root
     */
    public Node getRoot() {
        return this.root;
    }

}
