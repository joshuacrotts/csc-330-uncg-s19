package scc;

/**
 *
 * Coding Assignment 4, CSC 330, Spring 2019
 *
 * @author Joshua Crotts
 */
import java.util.*;

public class SCC {

    private final static int CUR_TIME = 0;

    /* Random seed for graph generation. */
    private final static int SEED = 330151337;

    /* Target outputs for tests */
    private final static String[] targetSCCs
            = {"[[0], [1], [2], [3], [4], [5], [6], [7], [8], [9]]",
                "[[0], [1], [2], [3], [4], [5], [6], [7], [8], [9]]",
                "[[0], [1, 3, 4, 6, 7, 9], [2], [5], [8]]",
                "[[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]]",
                "[[0], [1], [2], [3], [4], [5], [6], [7], [8], [9], [10], [11], [12], [13], [14]]",
                "[[0], [1], [2, 4, 5, 6, 10, 13], [3], [7], [8], [9], [11], [12], [14]]",
                "[[0], [1, 2, 4, 6, 9, 11, 12], [3, 5], [7], [8], [10], [13], [14]]",
                "[[0, 1, 2, 3, 4, 5, 6, 8, 9, 11, 12, 13, 14], [7], [10]]",
                "[[0], [1], [2], [3], [4], [5], [6], [7], [8], [9], [10], [11], [12], [13], [14], [15], [16], [17], [18], [19]]",
                "[[0, 6, 10], [1, 2, 5, 8, 9, 12, 13, 14, 15, 16, 18, 19], [3], [4], [7], [11], [17]]",
                "[[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 13, 14, 15, 16, 17, 18], [11], [19]]",
                "[[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19]]",
                "[[0, 2, 6, 7, 8, 11, 14, 15, 18, 24], [1], [3], [4], [5], [9], [10], [12], [13], [16], [17], [19], [20], [21], [22], [23]]",
                "[[0, 1, 2, 3, 4, 5, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 21, 23, 24], [6], [19], [20], [22]]",
                "[[0], [1, 2, 4, 5, 6, 7, 8, 9, 11, 12, 13, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24], [3], [10], [14]]",
                "[[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24]]",
                "[[0], [1], [2], [3], [4], [5], [6], [7, 15, 27], [8], [9], [10], [11], [12], [13], [14], [16], [17], [18], [19], [20], [21], [22], [23], [24], [25], [26], [28], [29]]",
                "[[0, 2, 3, 4, 5, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29], [1], [6]]",
                "[[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29]]",
                "[[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29]]"};

    /**
     *
     * @param G : input graph
     * @return Collection of sets representing the SCCs of the graph.
     */
    public static HashSet<HashSet<Vertex>> findSCC( Graph G ) {

        //HashSet of SCCs
        HashSet<HashSet<Vertex>> stronglyConnectedComponents = new HashSet<>();

        //Graph's vertices
        Iterator<Vertex> v = G.getVertices();

        //Visited vertices for later post-value traversal
        Stack<Vertex> visited = new Stack<>();

        //We perform a DFS on every node in the graph, and
        //using the post-value algorithm, we add the
        //nodes in order of decreasing post-value
        while ( v.hasNext() ) 
        {
            Vertex vertex = v.next();

            if ( !vertex.isMarked() ) 
            {
                DFSUtil( G, vertex, visited, null );
            }
        }

        
        //Returns the reversed graph
        Graph reversedGraph = G.makeReverseGraph();
        
        //We need to unmark every vertex because we're performing
        //a second DFS.
        reversedGraph.unmarkAll();
        
        //While we still have vertices remaining, we perform
        //another DFS on the post-value nodes from the stack
        //on the reversed graph. Any node that is a part of this
        //DFS is a member of the current SCC.
        while ( !visited.isEmpty() ) 
        {
            HashSet<Vertex> scc = new HashSet<>();

            Vertex vx = visited.pop();
            if ( !vx.isMarked() ) 
            {
                DFSUtil( reversedGraph, vx, null, scc );
            }
            
            //Any elements we visited that are now a part of the SCC
            //don't need to have a DFS performed.
            visited.removeAll( scc );
            
            stronglyConnectedComponents.add( scc );

        }

        return stronglyConnectedComponents;
    }

    /* Test the SCC algorithm on a graph with n vertices, with edges that are
       placed with probability p (see makeGraph() for details).*/
    public static void runTest(int n, double p, Random r, int tNum, int[] totalCorrect) {
        Graph G = makeGraph(n, p, r);
        printTestMessage(findSCC(G), n, targetSCCs, tNum, totalCorrect);
    }

    /* Print the result of a given test. */
    public static void printTestMessage(HashSet<HashSet<Vertex>> SCCs, int n,
            String[] SCCStrings, int tNum, int[] totalCorrect) {

        String produced = SCCString(SCCs, n);
        System.out.printf("Test Number: %d\n", tNum);
        System.out.printf("Target SCC: %s\n"
                + "Produced SCCs: %s\n",
                 SCCStrings[tNum - 1], SCCString(SCCs, n));

        if (SCCStrings[tNum - 1].equals(produced)) {
            System.out.println("*** CORRECT ***\n");
            totalCorrect[0]++;
        } else {
            System.out.println("*** INCORRECT ***\n");
        }
    }

    /* Construct a graph with n vertices, where for all pairs of vertices u 
    and v, we make v adjacent to u with probability p. */
    public static Graph makeGraph(int n, double p, Random r) {
        Graph G = new Graph(n);
        Vertex[] sVerts = new Vertex[n];

        Iterator<Vertex> vIterator = G.getVertices();
        int index = 0;
        while (vIterator.hasNext()) {
            sVerts[index] = (vIterator.next());
            index++;
        }

        Arrays.sort(sVerts);

        for (int u = 0; u < n; u++) {
            for (int v = 0; v < n; v++) {
                if (v != u && r.nextFloat() <= p) {
                    G.addAdjacency(sVerts[u], sVerts[v]);
                }
            }
        }
        return G;
    }

    /* Create a cannonical string represenation of a collection of SCCs (vertices 
    are sorted by label within a components, components themselves are sorted 
    based the first vertex in the component)*/
    public static String SCCString(HashSet<HashSet<Vertex>> SCCs, int size) {
        ArrayList<ArrayList<Integer>> sortedSCC = new ArrayList();

        /* Sort each component by vertex label */
        for (HashSet<Vertex> component : SCCs) {
            Vertex[] vertexArray = (component.toArray(new Vertex[component.size()]));
            Arrays.sort(vertexArray);
            ArrayList<Integer> curALComp = new ArrayList();
            for (int i = 0; i < vertexArray.length; i++) {
                curALComp.add(vertexArray[i].getLabel());
            }
            sortedSCC.add(curALComp);
        }
        /* Sort componenets by lowest vertex label in component */
        ArrayList<ArrayList<Integer>> lexoSCC = new ArrayList();
        for (int i = 0; i < size; i++) {
            for (ArrayList<Integer> sortedComp : sortedSCC) {
                if (sortedComp.get(0) == i) {
                    lexoSCC.add(sortedComp);
                }
            }
        }

        return lexoSCC.toString();
    }

    /* Test the SCC algorithm. */
    public static void main(String[] args) {
        int tNum = 1;
        int[] totalCorrect = new int[1];
        totalCorrect[0] = 0;

        Random r = new Random(SEED);

        /* Create graphs that range from sizes 10 through 30, 
        and edge probability that range from 0.05 to 0.2 */
        for (int n = 10; n <= 30; n += 5) {
            for (double p = 0.05; p <= 0.2; p += 0.05) {
                runTest(n, p, r, tNum, totalCorrect);
                tNum++;
            }
        }

        /* Total correct.*/
        System.out.printf("\nTotal Correct: %d\n", totalCorrect[0]);
    }

    /**
     * Performs a DFS on the standard graph G. No need for a separate DFS
     * method [for the reversed graph] as we can combine them into one.
     *
     * @param g - either normal or reversed graph
     * @param vertex
     * @param visited - used to find the appropriate ordering for post-value.
     *                  If null, we are on the reverse-graph traversal.
     * @param scc - used for the reverse-graph traversal, finds the scc's.
     *              If null, we are determining the order of the post-traversal
     */
    private static void DFSUtil( Graph g, Vertex vertex, Stack<Vertex> visited, HashSet<Vertex> scc ) {
        //We need to mark & add the vertices
        vertex.mark();

        Iterator<Vertex> it = g.getAdjacentVertices(vertex);
        
        while (it.hasNext()) 
        {
            Vertex v = it.next();

            if ( !v.isMarked() ) 
            {
                if( visited != null )
                    DFSUtil( g, v, visited , null );
                else if( scc != null )
                    DFSUtil( g, v, null , scc );
            }
        }
        
        //Depending on which graph we're using, we'll either push 
        //nodes to the post-order stack or add the vertices to the current SCC.
        if( visited != null )
        {
            visited.push( vertex );
        }
        else if( scc != null )
        {
            scc.add( vertex );
        }
    }
}
