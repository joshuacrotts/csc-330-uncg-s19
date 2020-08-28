package collection;

import java.util.ArrayList;

/**
 * Implementation of a red-black tree.
 * Coding Assignment 3, CSC 330, Spring 2019
 * (Note there is nothing in this file for you to change)
 * @author Nathaniel Kell
 * @param <T> type of objects in the tree. 
 */
public class RedBlackTree<T extends Comparable<T>> extends HeightBalancedTree<T>{
    /* boolean values corresponding to red and black. */
    private static final boolean RED = true; 
    private static final boolean BLACK = false; 
    
    /* Modified node class that now has a color value */
    protected class RBNode<T> extends Node<T>{
        protected boolean color; 
   
        protected RBNode(Node l, Node r, Node p , T d, boolean c){
            super(l, r, p, d);
            color = c; 
        }
    }
    
    /* Construct an empty red-black tree */
    public RedBlackTree(){
        super();
        nullNode = new RBNode(null, null, null, null, BLACK); 
        nullNode.parent = nullNode;
        nullNode.left = nullNode;
        nullNode.right = nullNode;
        root = nullNode; 
    }
    
    @Override 
    public void insert(T element){
        Node<T> newNode = insertNode(element); 
        RBNode<T> RBnewNode = convertToRBNode(newNode, RED);   
 
        if(((RBNode)RBnewNode.parent).color == RED)
            insertionFix(RBnewNode); 
        
        ((RBNode)root).color = BLACK; 
        size++; 
    }
    
    @Override 
    public boolean delete(T element){        
        ArrayList<Node<T>> delNodes = deleteNode(element);
        Node<T> del = delNodes.get(DELETED);
        Node<T> replace = delNodes.get(REPLACED); 
        Node<T> parent = delNodes.get(PARENT); 
        
        if(del == nullNode)
            return false;
        
        if(((RBNode)del).color == BLACK)
            deletionFix(del, replace, parent);

        size--; 
        return true; 
    }

    @Override 
    protected void insertionFix(Node<T> n){
        RBNode cur = (RBNode)n; 
        while(((RBNode)cur.parent).color == RED){
           RBNode grandparent = (RBNode)cur.parent.parent;

            /* Meta case 1: cur's parent is a left child of its parent */
            if(cur.parent == grandparent.left){
                RBNode aunt = (RBNode)grandparent.right; 
                /* Case 1: cur's aunt is red */ 
                if(aunt.color == RED){
                    
                    ((RBNode)cur.parent).color = BLACK; 
                    aunt.color = BLACK;
                    grandparent.color = RED; 
                    cur = grandparent; 
                }
                else{
                    /* Case 2: aunt is black and cur is a right child */
                    if(cur == cur.parent.right){
                        leftRotate((cur.parent)); 
                        cur = (RBNode)cur.left; 
                    }
                    /* Case 3: aunt is black and cur is a left child */
                    ((RBNode)cur.parent).color = BLACK;
                    grandparent.color = RED; 
                    rightRotate(grandparent); 
                }
            } 
            /* Meta case 2: cur's parent is a right child of its parent
             * (each subcase are symmetric to those of meta-case 1) */
            else{
                
                RBNode aunt = (RBNode)grandparent.left; 
                if(aunt.color == RED){
                    ((RBNode)cur.parent).color = BLACK;
                    aunt.color = BLACK; 
                    grandparent.color = RED; 
                    cur = grandparent; 
                }
                else{
                    if(cur == cur.parent.left){
                        rightRotate((cur.parent)); 
                        cur = (RBNode)cur.right; 
                    }
                    ((RBNode)cur.parent).color = BLACK;
                    grandparent.color = RED; 
                    leftRotate(grandparent); 
                }
            }   
        } // end while 
    }
    
    @Override
    protected void deletionFix(Node<T> del, Node<T> replace, Node<T> parent){
        RBNode<T> cur = (RBNode)replace; 
        while(cur != root && cur.color == BLACK){
            RBNode<T> par = (RBNode)cur.parent;
            if(cur == nullNode)
                par = (RBNode)parent;
            /* Meta-case 1: cure is a left child */
            if(cur == par.left){
                RBNode<T> sibling = (RBNode)par.right; 
                /* Case 1: cur's sibling is red */ 
                if(sibling.color == RED){
                    sibling.color = BLACK; 
                    par.color = RED; 
                    leftRotate(par);
                    sibling = (RBNode)par.right; 
                }
                /* Case 2: cur's sibling is black and sibling's children are both black */
                if(((RBNode)sibling.left).color == BLACK && ((RBNode)sibling.right).color == BLACK){
                    sibling.color = RED; 
                    cur = par;
                }
                /* Case 3: cur's sibling is black and just sibling's right child is black */
                else{
                    if(((RBNode)sibling.right).color == BLACK){
                        ((RBNode)sibling.left).color = BLACK;
                        sibling.color = RED;  
                        rightRotate(sibling); 
                        sibling = (RBNode)par.right; 
                    }
                    /* Case 4: cur's sibling is black and sibling's left child is red */
                    sibling.color = par.color; 
                    par.color = BLACK;
                    ((RBNode)sibling.right).color = BLACK; 
                    leftRotate(par);
                    cur = (RBNode)root;  
                }
            }
            /* Meta-case 2: cur is a right child 
             * (subcases are symmetric to those of meta-case 1) */
            else{
                RBNode<T> sibling = (RBNode)par.left; 
           
                if(sibling.color == RED){
                    sibling.color = BLACK; 
                    par.color = RED; 
                    rightRotate(par);
                    sibling = (RBNode)par.left; 
                }
                if(((RBNode)sibling.left).color == BLACK && ((RBNode)sibling.right).color == BLACK){
                    sibling.color = RED; 
                    cur = par;  
                }
                else{
                    if(((RBNode)sibling.left).color == BLACK){
                        ((RBNode)sibling.right).color = BLACK;
                        sibling.color = RED;  
                        leftRotate(sibling); 
                        sibling = (RBNode)par.left; 
                    }
                    sibling.color = par.color; 
                    par.color = BLACK;
                    ((RBNode)sibling.left).color = BLACK; 
                    rightRotate(par);
                    cur = (RBNode)root;  
                }
            }   
        } // end while 
        cur.color = BLACK;
    }
    
    /**
     * Convert a standard node into a red-black node (where the the RBnode 
     * replaces the standard node in the tree with respect to its links).
     * @param n : standard node to be converted.
     * @param c : color of the red-black node
     * @return converted RBNode.
     */
    private RBNode<T> convertToRBNode(Node<T> n, boolean c){
        RBNode<T> rbN = new RBNode(n.left, n.right, n.parent, n.data, c); 
        if(rbN.parent == nullNode)
            root = rbN; 
        else if(rbN.parent.left == n)
            rbN.parent.left = (Node)rbN;
        else
            rbN.parent.right =(Node)rbN;  
       return rbN;
    }
    
}
