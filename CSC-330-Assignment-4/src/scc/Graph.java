
package scc;
import java.util.*; 
import java.lang.*;

/**
 * Coding Assignment 4, CSC 330, Spring 2019
 * (Note there is nothing in this file for you to change).
 * @author Nathaniel Kell
 */
public class Graph {     
    
    /* Graph representation as an adjacency list */
    private final HashMap<Vertex, HashSet<Vertex>> adjList; 
    /* Size of the graph */
    private final int size; 
    
    /**
     * Construct an empty graph with n vertices. 
     * @param n : number of vertices in the graph.
     */
    public Graph(int n){
        adjList = new HashMap(); 
        
        for(int i = 0; i < n; i++){
            Vertex cur = new Vertex(i); 
            adjList.put(cur, new HashSet());
        }     
        size = n; 
    }
    
    /**
     * Construct a graph with the same set of vertices as original.
     * @param original : graph that will share its vertex set with this. 
     */
    public Graph(Graph original){
        adjList = new HashMap(); 
        
        Iterator<Vertex> originalVertIt = original.getVertices();
        
        while(originalVertIt.hasNext()){
            Vertex v = originalVertIt.next();
            adjList.put(v, new HashSet());
        }    
        size = original.getSize(); 
    }
    
    /**
     * Return an iterator that loops of the set of vertices 
     * @return : iterator for the vertex set.
     */
    public Iterator<Vertex> getVertices(){
        return adjList.keySet().iterator(); 
    }
    
    /**
     * Return an iterator that loops over the adjacency list of vertex v
     * @param v : vertex whose adjacency list we'll return an iterator for.
     * @return : an iterator that loops over the adjacency list of vertex v
     */
    public Iterator<Vertex> getAdjacentVertices(Vertex v){
        return adjList.get(v).iterator(); 
    }
        
    /** 
     * Add an edge between vertex v and u.
     * @param v : start point of the directed edge.
     * @param u : end point of the directed edge.
     */
    public void addAdjacency(Vertex v, Vertex u){
        adjList.get(v).add(u);
    }
    
    /**
     * Un-mark all vertices in the graph (should be called before 
     * beginning a new DFS).
     */
    public void unmarkAll(){
        Iterator<Vertex> vIterator = this.getVertices();
        while(vIterator.hasNext()){
            Vertex v = vIterator.next();
            v.unmark(); 
        }
    }           
    
    /**
     * Return a new graph with an identical vertex set, 
     * but with the edges of this reversed.
     * @return : a graph with the reversed edge set. 
     */
    public Graph makeReverseGraph(){
        Graph revGraph = new Graph(this); 
        for(Vertex v : adjList.keySet()){
            Iterator<Vertex> curAdj = getAdjacentVertices(v);     
            while(curAdj.hasNext()){
                Vertex u = curAdj.next();
                revGraph.addAdjacency(u, v);
            }
        }       
        return revGraph; 
    } 
    
    /**
     * Return the number of vertices in the graph.
     * @return : number of vertices.
     */
    public int getSize(){
        return size;
    } 
    
    /**
     * String containing a canonical representation of the adjacency list, 
     * i.e., vertices appear in sorted order based on label, and adjacent 
     * vertices in each list are listed in sorted order. 
     * @return : canonical string representation of the graph. 
     */
    @Override 
    public String toString(){
        String ret = "";
        Vertex[] vertexArray = (adjList.keySet().toArray(new Vertex[size]));
        Arrays.sort(vertexArray);
        
        for(int i = 0; i < vertexArray.length; i++){
            Vertex v = vertexArray[i];
            String cur = v.toString() + ": "; 
            Vertex[] adjVertices = adjList.get(v).toArray(new Vertex[adjList.get(v).size()]); 
            Arrays.sort(adjVertices);
            
            for(int j = 0; j < adjVertices.length; j++){
                Vertex u = adjVertices[j];
                cur += u.toString() + " "; 
            }
            cur += "\n";
            ret += cur; 
        }       
        return ret; 
    }
}
