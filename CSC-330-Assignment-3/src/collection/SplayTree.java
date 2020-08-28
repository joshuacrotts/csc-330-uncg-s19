package collection;

import static collection.HeightBalancedTree.DELETED;
import java.util.ArrayList;

/**
 * SplayTree height balancing tree class. 
 * Coding Assignment 3, CSC 330, Spring 2019
 * @author *YOUR NAME HERE*
 * @param <T> type of objects in the tree. 
 */
public class SplayTree<T extends Comparable<T>> extends HeightBalancedTree<T>{
    
    /* Construct an empty splay tree*/ 
    public SplayTree(){
        super();
    }

    @Override 
    public void insert(T element){
        /**
         * 
         * 
         *** WRITE THIS METHOD ***
         * 
         *         
         **/
    }
    
    @Override 
    public boolean delete(T element){
        /**
         * 
         * 
         *** WRITE THIS METHOD ***
         * 
         *         
         **/
    }
    
    @Override
    protected void insertionFix(Node<T> n){
        /**
         * 
         * 
         *** WRITE THIS METHOD ***
         * 
         *         
         **/ 
    }
    
    @Override
    protected void deletionFix(Node<T> del, Node<T> replace, Node<T> parent){
        /**
         * 
         * 
         *** WRITE THIS METHOD ***
         * 
         *         
         **/
    }
    
    /**
     * Splay a node to the root of the tree. 
     * @param n : node to be splayed to the top of the tree
     */
    private void splay(Node<T> n){
        /**
         * 
         * 
         *** WRITE THIS METHOD ***
         * 
         *         
         **/
    }

}