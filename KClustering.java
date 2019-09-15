import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* *************************************************************************************
 * KCluster Algorithm
 *
 * Algorithm that groups vertices into k clusters and computes the max-spacing distance
 * in the graph
 *
 * @return maxDistance
 *
 * Time Complexity:
 *
 * @author Cora Wang
 *
 * **************************************************************************************/


public class KClustering {
    private int numV; // number of vertices
    private static int maxDistance; // the final maximum distance

    private List<Edge> EdgeList; // a list of edges

    // initialize Clustering
    public KClustering(int numV){
        this.numV = numV;
        EdgeList = new ArrayList<>();
    }

    /* generate the maximum spacing
     * @param k the desired number of clusters
     */
    public void getMaxSpacing(int k){
        // sort the list of edge in increasing weight/cost
        // plug in numV number of vertices into quick union
        // count = |V|
        Collections.sort(EdgeList);
        WeightedUF uf = new WeightedUF(numV);

        // go through the list of edge and union all pairs of vertices stored
        // keep union until count goes down to k
         for(Edge edge: EdgeList){
             int u = edge.src;
             int v = edge.dest;
             uf.union(u,v);

             if(uf.count() == k){
                 break;
             }
         }

         maxDistance = Integer.MAX_VALUE;

        // now we go through the list of edges again
        // if two edges are not in the same component, record their distance
        for(Edge edge: EdgeList){
            if(uf.find(edge.src) != uf.find(edge.dest))
                maxDistance = Math.min(maxDistance, edge.weight);
        }
        System.out.println("Max-Spacing K-Clustering => " + maxDistance);
    }

    public static void main(String[] args)
        throws IOException {

        File file = new File("data/clustering1.txt");
        BufferedReader input = new BufferedReader(new FileReader(file));
        String line = input.readLine();
        String [] str = line.split(" ");
        int numV = Integer.parseInt(str[0]);
        KClustering graph = new KClustering(numV);

        while((line = input.readLine())!= null){
            str = line.trim().split(" ");
            int u = Integer.parseInt(str[0])-1;
            int v = Integer.parseInt(str[1])-1;
            int weight = Integer.parseInt(str[2]);
            graph.EdgeList.add(new Edge(u ,v ,weight));
        }

        int k = 4;
        graph.getMaxSpacing(k);
        }
}

class Edge implements Comparable<Edge>{
    int src;
    int dest;
    int weight;

    public Edge(int src, int dest, int weight){
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }
    public int compareTo(Edge w){
        return this.weight - w.weight;
    }
}
