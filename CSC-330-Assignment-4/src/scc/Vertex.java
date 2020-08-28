package scc;

/**
 * Coding Assignment 4, CSC 330, Spring 2019
 * (Note there is nothing in this file for you to change).
 * @author Nathaniel Kell
 */
public class Vertex implements Comparable<Vertex>{
    
    private final static int DEFAULT = -1;  // dafault pre and post value
    
    private final int label; // vertex label
    
    /* Meta-data for DFS */
    private int pre; 
    private int post; 
    private boolean marked;
    
    public Vertex(int lab){
        pre = DEFAULT; 
        post = DEFAULT; 
        marked = false; 
        label = lab; 
    }
    
    public int getLabel(){
        return label;
    }
    
    public void setPre(int p){
        pre = p;
    }
    
    public void setPost(int p){
        post = p; 
    }
    
    public void mark(){
        marked = true; 
    }
    
    public void unmark(){
        marked = false;
    }
    
    public boolean isMarked(){
        return marked; 
    }  
    
    @Override
    public String toString(){ 
        return ((Integer)label).toString();
    }
    
    @Override
    public int compareTo(Vertex other){
        return label - other.getLabel();       
    }
}
