/**
 * Min heap/priority queue data structure .
 *
 * @author Joshua Crotts, COMPSCI 330, Spring 2019.
 */
package huffmantree;

import java.util.*;

public class MinHeap<T extends Comparable<T>> {

    private ArrayList<T> theHeap;
    private int size;

    private final static int root = 1;

    /* Note that positions are one more than the index 
    where an element is stored (e.g., the root has position 1,
    but is stored at index 0).
     */
    /**
     * Constructs an empty heap.
     */
    public MinHeap() {
        theHeap = new ArrayList<T>();
        size = 0;
    }

    /**
     * Insert an element into the heap.
     *
     * @param element element to be inserted in the heap.
     */
    public void insert(T element) {
        this.theHeap.add(element);
        this.size++;

        int root = this.size; //Position of the newly-inserted node.
        int parentPos = this.getParent(root); // Parent of newly-inserted node

        //We have more than one element in the heap, we can "heapify" it
        if (size > 1) {
            while (true) {

                T child = this.theHeap.get(root - 1); //Grabs the child 
                T parent = this.theHeap.get(parentPos - 1); //Grabs its parent

                //If the child is greater than its parent, no swaps are needed
                if (child.compareTo(parent) >= 0) {
                    break;
                } else {

                    this.swap(root, parentPos);
                    root = parentPos; //Sets the root to be where the parent was
                    parentPos = this.getParent(root); //Grabs the parent of the new root

                    //If we are at the top of the heap, we can stop
                    //(this implies the newly inserted node is the smallest
                    // i.e. new minimum)
                    if (parentPos == 0) {
                        break;
                    }

                }
            }
        }
    }

    /**
     * Remove and return the minimum element from the heap.
     *
     * @return the minimum element in the heap.
     */
    public T extractMin() {
        T min = null;

        if (this.theHeap.get(0) != null) {
            min = this.theHeap.get(0);
        }

        //Removes the newest node to add to the top of the heap
        T newestNode = this.theHeap.remove(this.size - 1);
        this.size--;

        //If we just removed the last element in the heap, just
        //return it
        if (this.size == 0) {
            return newestNode;
        }

        //We set the most recently-added node to the top.
        this.theHeap.set(0, newestNode);

        //Decrement the size to account for the removed minimum
        int root = 1; //Beginning position
        int leftPos = -1; //Left child position of parent
        int rightPos = -1;//Right child position of parent

        //Loop to heapify the heap
        while (true) {
            T parent = this.theHeap.get(root - 1); //Parent
            T left = null; //Left child
            T right = null; //Right child

            leftPos = this.getLeft(root) - 1;
            rightPos = this.getRight(root) - 1;

            //If the left position is valid, we grab the child
            if (leftPos < size && this.theHeap.get(leftPos) != null) {
                left = this.theHeap.get(leftPos);
            }

            //If the right position is valid, we grab the child
            if (rightPos < size && this.theHeap.get(rightPos) != null) {
                right = this.theHeap.get(rightPos);
            }

            //If there are two children, we can compare against the two of them.
            if (left != null && right != null) {

                //If the parent is currently the new minimum, we break out
                if (parent.compareTo(left) <= 0 && parent.compareTo(right) <= 0) {
                    return min;
                }
            }

            //If the left child is null, we only need to check the right
            if (left == null && right != null) {
                if (parent.compareTo(right) <= 0) {
                    return min;
                }

            }

            //If the right child is null, we only need to check the left
            if (right == null && left != null) {
                if (parent.compareTo(left) <= 0) {
                    return min;
                }
            }

            //There's no children to swap with so we break.
            if (left == null && right == null) {
                return min;
            }

            //At this point, the parent is greater than its children and needs to be swapped.
            int minChild = -1;

            //If there is no left child, we grab the right
            if (left == null) {
                minChild = rightPos;
            } else if (right == null) {
                minChild = leftPos;
            } else {
                //If both children exist we grab the smaller of the two
                minChild = left.compareTo(right) <= 0 ? (leftPos) : (rightPos);
            }

            //Swaps the parent with the smaller of its children
            this.swap(root, minChild + 1);

            //Reassigns the root to be the position where the swapped child was 
            //for further comparison
            root = minChild + 1;
        }
    }

    /**
     * @return the array representation of the heap.
     */
    public String toString() {
        return theHeap.toString();
    }

    /**
     * @return return the minimum value in the heap.
     */
    public T getMin() {
        return theHeap.get(root - 1);
    }

    /**
     * @return the number of elements in the heap.
     */
    public int getSize() {
        return size;
    }

    /**
     * The remaining private methods should be useful in writing insert() and
     * extractMin() *
     */
    /* Return the position of pos's parent */
    private int getParent(int pos) {
        return pos / 2;
    }

    /* Return the position of pos's left child 
     * 
     */
    private int getLeft(int pos) {
        return 2 * pos;
    }

    /* Return the position of pos's right child */
    private int getRight(int position) {
        return 2 * position + 1;
    }

    /* Swap the elements at positions pos1 and pos2 */
    private void swap(int pos1, int pos2) {
        T temp = theHeap.get(pos1 - 1);
        theHeap.set(pos1 - 1, theHeap.get(pos2 - 1));
        theHeap.set(pos2 - 1, temp);
    }
}
