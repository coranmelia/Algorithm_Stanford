import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class DPQ {
    private int dist[];
    private Set<Integer> X;
    private PriorityQueue<Node> heap;
    private int v; // Number of vertices
    List<List<Node>> adj;

    /* declare Algorithm */
    public DPQ(int v) {
        this.v = v; // vertex
        dist = new int[v]; // distance
        X = new HashSet<Integer>(); // set X
        heap = new PriorityQueue<Node>(v, new Node()); // set V-X
    }

    /* Main Loop */
    public void dijkstraLoop(List<List<Node>> adj, int src) {
        this.adj = adj;

        for (int i = 0; i < v; i++)
            dist[i] = Integer.MAX_VALUE; // declare all vertices as having +infinite distance

        // Add source node to heap, dist[src] = 0
        heap.add(new Node(src, 0));
        dist[src] = 0;

        while (X.size() != v) {
            int w = heap.remove().node; // pass the min-distance node from heap to set X

            X.add(w); // adding w whose distance is finalized

            passNode(w); // process the edges
        }
    }

    /* process all neighbors of w */
    private void passNode(int w) {
        int edgeDistance = -1;
        int newDistance = -1;

        for (int i = 0; i < adj.get(w).size(); i++) {
            Node v = adj.get(w).get(i); // neighbor of w

            heap.remove(v);

            // for all neighbors v of w, If v is not processed, compute key[v]
            if (!X.contains(v.node)) {
                edgeDistance = v.key;
                newDistance = dist[w] + edgeDistance;

                if (newDistance < dist[v.node]) {
                    dist[v.node] = newDistance; // If new distance is smaller, change key
                    v.key = newDistance;
                }

                heap.add(v); // Add the current node to heap
            }
        }
    }
    public static void main(String arg[])
            throws IOException {

        int v = 200;
        int src = 0;

        List<List<Node> > adj = new ArrayList<List<Node>>();

        for (int i = 0; i < v; i++) {
            List<Node> item = new ArrayList<Node>();
            adj.add(item);
        }

        File file = new File("data/dijkstra.txt");
        BufferedReader input = new BufferedReader(new FileReader(file));

        String line;
        while((line = input.readLine()) != null){

            String[] str = line.trim().split("\\s+");

            int tail = Integer.parseInt(str[0])-1;

            for (int i = 1; i < str.length; i++) {
                int head = Integer.parseInt(str[i].split(",")[0])-1;
                int dist = Integer.parseInt(str[i].split(",")[1]);
                adj.get(tail).add(new Node(head, dist));
            }
        }

        DPQ d = new DPQ(v);
        d.dijkstraLoop(adj, src);
        for(int i=0; i< 200; i++){
            System.out.println(d.dist[i]);
        }
    }
}

/* define node */
class Node implements Comparator<Node> {
    public int node;
    public int key;

    public Node() {
    }

    public Node(int node, int key) {
        this.node = node;
        this.key = key;
    }

    @Override
    public int compare(Node x, Node y) {
        if (x.key < y.key)
            return -1;
        if (x.key > y.key)
            return 1;
        return 0;
    }
}
