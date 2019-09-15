import java.util.*;
import java.lang.*;
import java.io.*;

/* *************************************************************************************
 * Kruskal's algorithm
 * Algorithm that computes a minimum-spanning tree and output the total weight
 *
 * Time Complexity:
 *
 * @author Cora Wang
 *
 * **************************************************************************************/


public class Kruskal{
    // A class to represent a graph edge
    class Edge implements Comparable<Edge> {
        int src, dest;
        long weight;

        // Comparator function used for sorting edges
        // based on their weight
     public int compareTo(Edge compareEdge) {

         if(this.weight == compareEdge.weight)
             return 0;
         else if(this.weight > compareEdge.weight)
             return 1;
         else
             return -1;
        }
    }

    // A class to represent a subset for union-find
    class subset {
        int parent;
        int size;
    }

    int V, E;    // V-> no. of vertices & E->no.of edges
    Edge edge[]; // collection of all edges

    // Creates a graph with V vertices and E edges
    public Kruskal(int v, int e)
    {
        V = v;
        E = e;
        edge = new Edge[E];
        for (int i = 0; i < e; i++)
            edge[i] = new Edge();
    }

    // A utility function to find set of an element i
    // (uses path compression technique)
    int find(subset subsets[], int i)
    {
        // find root and make root as parent of i (path compression)
        while (subsets[i].parent != i)
//            subsets[i].parent = find(subsets, subsets[i].parent);
            i = subsets[i].parent;

        return i;
    }

    // A function that does union of two sets of x and y
    // (uses union by rank)
    void Union(subset subsets[], int x, int y)
    {
        int xroot = find(subsets, x);
        int yroot = find(subsets, y);

        // Attach smaller rank tree under root of high rank tree
        // (Union by Rank)
        if (subsets[xroot].size < subsets[yroot].size) {
            subsets[xroot].parent = yroot;
            subsets[yroot].size += subsets[xroot].size;
        }
        else {
            subsets[yroot].parent = xroot;
            subsets[xroot].size += subsets[yroot].size;
        }
    }

    // The main function to construct MST using Kruskal's algorithm
    void KruskalMST()
    {
        Edge result[] = new Edge[V];  // Tnis will store the resultant MST
        int e = 0;  // An index variable, used for result[]
        int i = 0;  // An index variable, used for sorted edges
        for (i = 0; i < V; ++i)
            result[i] = new Edge();

        // Step 1:  Sort all the edges in non-decreasing order of their
        // weight.  If we are not allowed to change the given graph, we
        // can create a copy of array of edges
        Arrays.sort(edge);

        // Allocate memory for creating V ssubsets
        subset subsets[] = new subset[V];
        for(i = 0; i < V; i++)
            subsets[i]=new subset();

        // Create V subsets with single elements
        for (int v = 0; v < V; ++v)
        {
            subsets[v].parent = v;
            subsets[v].size = 1;
        }

        i = 0;  // Index used to pick next edge

        // Number of edges to be taken is equal to V-1
        while (e < V - 1)
        {
            // Step 2: Pick the smallest edge. And increment
            // the index for next iteration
            Edge next_edge = new Edge();
            next_edge = edge[i++];

            int x = find(subsets, next_edge.src);
            int y = find(subsets, next_edge.dest);

            // If including this edge does't cause cycle,
            // include it in result and increment the index
            // of result for next edge
            if (x != y)
            {
                result[e++] = next_edge;
                Union(subsets, x, y);
            }
            // Else discard the next_edge
        }

        // print the weight

        long weight = 0;

        for (i = 0; i < e; i++)
            weight = weight + result[i].weight;

        System.out.println(weight);
    }


    public static void main (String[] args)
        throws IOException {

        File file = new File("data/edges.txt");
        BufferedReader input = new BufferedReader(new FileReader(file));
        String line = input.readLine();
        String [] str = line.split(" ");
        int V = Integer.parseInt(str[0]);
        int E = Integer.parseInt(str[1]);

        Kruskal kruskal = new Kruskal(V, E);

        for (int i = 0; i < E; i++) {
            line = input.readLine();
            str = line.trim().split(" ");
            int u = Integer.parseInt(str[0]);
            int w = Integer.parseInt(str[1]);
            long len = Integer.parseInt(str[2]);
            kruskal.edge[i].src = u - 1;
            kruskal.edge[i].dest = w - 1;
            kruskal.edge[i].weight = len;
        }
        input.close();

        kruskal.KruskalMST();
    }
}
