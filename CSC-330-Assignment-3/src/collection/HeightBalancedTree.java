package collection;

import java.util.*;

/**
 * Abstract class for height balancing trees to extend from. Coding Assignment
 * 3, CSC 330, Spring 2019
 *
 * @author *YOUR NAME HERE
 *
 * @param <T> type of objects in the tree.
 */
public abstract class HeightBalancedTree<T extends Comparable<T>> implements Collection<T> {

    Node root;
    int size;
    Node nullNode; // node to serve as null pointer 

    /* Indices for the nodes returned by deleteNode() */
    protected final static int DELETED = 0;
    protected final static int REPLACED = 1;
    protected final static int PARENT = 2;

    /* Node class used for storing the structure of the tree */
    protected class Node<T> {

        protected Node left, right, parent;
        protected T data;

        protected Node(Node l, Node r, Node p, T d) {
            left = l;
            right = r;
            parent = p;
            data = d;
        }
    }

    /* Construct an emepty height balancing tree */
    public HeightBalancedTree() {
        size = 0;
        nullNode = null;
        root = nullNode;
    }

    /**
     * Balances the tree after an insertion (starting at node n).
     *
     * @param n : node where balancing procedure begins
     */
    protected abstract void insertionFix(Node<T> n);

    /**
     * Balances the tree after a deletion.
     *
     * @param del : node that was just deleted from the tree.
     * @param replace: node that took the place del in the tree.
     * @param parent: parent of del.
     */
    protected abstract void deletionFix(Node<T> del, Node<T> replace, Node<T> parent);

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean contains(T element) {
        return findNode(element) != nullNode;
    }

    /**
     * Insert a new node into the tree and return the newly constructed node.
     *
     * @param element : element to be inserted into the tree.
     * @return : newly inserted node.
     */
    protected Node<T> insertNode(T element) {
        Node<T> current = root;
        Node<T> par = nullNode;
        boolean left = false; // whether we take left or right links

        /* Find the correct location to insert */
        while (current != nullNode) {
            par = current;
            if (current.data.compareTo(element) >= 0) {
                current = current.left;
                left = true;
            } else {
                current = current.right;
                left = false;
            }
        }

        Node<T> newNode = new Node(nullNode, nullNode, par, element);

        /* Check if new node is the root */
        if (par == nullNode) {
            root = newNode;
        } /* Set up parent links */ else if (left == true) {
            par.left = newNode;
        } else {
            par.right = newNode;
        }

        return newNode;
    }

    /**
     * Deletes a node from the tree with data equal to element.
     *
     * @param element : element to be deleted from the tree.
     * @return an array list that contains the node deleted, the node that
     * replaced it, and the parent of the deleted node.
     */
    public ArrayList<Node<T>> deleteNode(T element) {
        Node<T> del = findNode(element);
        Node<T> replace = nullNode;
        Node<T> parent = nullNode;
        ArrayList<Node<T>> ret = new ArrayList();

        ret.add(del);
        ret.add(replace);
        ret.add(parent);

        /* The node does not exist in the tree */
        if (del != nullNode) {
            /* Case 1: left is null */
            if (del.left == nullNode) {
                replace = del.right;
                setParentLink(del.parent, del, del.right);
            } /* Case 2: right is null */ else if (del.right == nullNode) {
                replace = del.left;
                setParentLink(del.parent, del, del.left);
            } /* Case 4: two children */ else {
                Node<T> successor = findMinNode(del.right);
                del.data = successor.data;
                setParentLink(successor.parent, successor, successor.right);

                /* In this case, we've deleteed successor, and its right 
                child has taken its place */
                del = successor;
                replace = del.right;
            }

            ret.set(DELETED, del);
            ret.set(REPLACED, replace);
            ret.set(PARENT, del.parent);
        }
        return ret;
    }

    /**
     * Searches for and returns a node with data equal to element.
     *
     * @param element: method finds a node with data equal to element.
     * @return node that has data equal to element.
     */
    protected Node<T> findNode(T element) {
        Node<T> current = root;
        while (current != nullNode) {
            if (current.data.equals(element)) {
                return current;
            } else if (current.data.compareTo(element) >= 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return nullNode;
    }

    /**
     * Perform a left rotation on the subtree rooted at rotRoot.
     *
     * @param rotRoot: root of the subtree to be rotated.
     */
    protected void leftRotate(Node rotRoot) {
        if (rotRoot.right == nullNode) {
            return;
        } else {
            //If there is a subtree in the new root (rotRoot's right's left child),
            //we need to make it rotRoot's right child after the rotation.
            Node tempTree = (rotRoot.right.left != nullNode) ? rotRoot.right.left : nullNode;
            Node newRoot = rotRoot.right;
            //If rotRoot is the root, we only need to update
            //the root's pointers.
            if (rotRoot == root) {
                root = newRoot;
                //Do the rotation
                root.left = rotRoot;
                rotRoot.parent = root;
            } else {
                //Otherwise, we need to set the parent links for
                //the new root, the rotRoot and the temp tree.
                this.setParentLink(rotRoot.parent, rotRoot, newRoot);
                this.setParentLink(newRoot, tempTree, rotRoot);
                
                //Actually perform the rotation
                newRoot.left = rotRoot;
            }
            rotRoot.right = tempTree;
            tempTree.parent = rotRoot;
        }
    }

    /**
     * Perform a right rotation on the subtree rooted at rotRoot.
     *
     * @param rotRoot: root of the subtree to be rotated.
     */
    protected void rightRotate(Node rotRoot) {
        if (rotRoot.left == nullNode) {
            return;
        } else {
            Node tempTree = (rotRoot.left.right != nullNode) ? rotRoot.left.right : nullNode;
            Node newRoot = rotRoot.left;

            if (rotRoot == root) {
                root = newRoot;
                root.right = rotRoot;
                rotRoot.parent = root;
            } else {
                this.setParentLink(rotRoot.parent, rotRoot, newRoot);
                this.setParentLink(newRoot, tempTree, rotRoot);
                newRoot.right = rotRoot;
            }
            rotRoot.left = tempTree;
            tempTree.parent = rotRoot;
        }
    }

    /**
     * Find the minimum node the subtree rooted at node n.
     *
     * @param n : starting node of the search
     * @return node with minimum value in the subtree rooted at n.
     */
    protected Node<T> findMinNode(Node<T> n) {
        Node<T> cur = n;
        while (cur.left != nullNode) {
            cur = cur.left;
        }
        return cur;
    }

    /**
     * Replaces parent's child pointer to child with newChild.
     *
     * @param par : node to replace child link.
     * @param child : original child of parent.
     * @param newChild : new child of parent.
     */
    protected void setParentLink(Node<T> par, Node<T> child, Node<T> newChild) {
        if (newChild != nullNode) {
            newChild.parent = par;
        }
        if (par != nullNode) {
            if (child == par.left) {
                par.left = newChild;
            } else {
                par.right = newChild;
            }
        } else {
            root = newChild;
        }
    }

    /**
     * Return the pre-order representation of the tree
     *
     * @return string containing the pre-order sequence of the tree.
     */
    @Override
    public String toString() {
        ArrayList<String> stringList = new ArrayList();
        preOrder(root, stringList);

        String treeString = "";
        for (int i = 0; i < stringList.size() - 1; i++) {
            treeString += stringList.get(i) + ", ";
        }
        treeString += stringList.get(stringList.size() - 1);

        return treeString;
    }

    /**
     * Return the in-order representation of the tree
     *
     * @return string containing the in-order sequence of the tree.
     */
    public String inOrderString() {
        ArrayList<String> stringList = new ArrayList();
        inOrder(root, stringList);

        String treeString = "";
        for (int i = 0; i < stringList.size() - 1; i++) {
            treeString += stringList.get(i) + ", ";
        }
        treeString += stringList.get(stringList.size() - 1);

        return treeString;
    }

    /**
     * Recursively perform an in-order traversal or the tree.
     *
     * @param cur : current node of the traversal.
     * @param stringList : string that will contain the sequence at the end of
     * search.
     */
    protected void preOrder(Node<T> cur, ArrayList<String> stringList) {
        if (cur != nullNode) {
            stringList.add(cur.data.toString());
            preOrder(cur.left, stringList);
            preOrder(cur.right, stringList);
        }
    }

    /**
     * Recursively perform an in-order traversal or the tree.
     *
     * @param cur : current node of the traversal.
     * @param stringList : string that will contain the sequence at the end of
     * search.
     */
    protected void inOrder(Node<T> cur, ArrayList<String> stringList) {
        if (cur != nullNode) {
            inOrder(cur.left, stringList);
            stringList.add(cur.data.toString());
            inOrder(cur.right, stringList);
        }
    }

}
