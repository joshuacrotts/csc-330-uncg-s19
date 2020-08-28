package collection;

/**
 * Interface for a collection class. 
 * Coding Assignment 3, CSC 330, Spring 2019
 * (Note there is nothing in this file for you to change).
 * @author Nathaniel Kell
 * @param <T> type of objects in the collection. 
*/
public interface Collection<T>{
    
    /**
     * Insert an element into the collection.
     * @param element: object to be inserted
     */ 
    public void insert(T element);
    
    /**
     * Delete an element from the collection.
     * @param element: object to be deleted
     * @return true if the element was deleted (false otherwise).
     */ 
    public boolean delete(T element);
    
     /**
     * Check if an element exists in the collection.
     * @param element: object to be checked for containment.
     * @return true if the element exists in the collection (false otherwise).
     */ 
    public boolean contains(T element);
    
    /**
     * Returns the number of elements in the collection.
     * @return size of the collection.
     */
    public int getSize(); 
    
}

 