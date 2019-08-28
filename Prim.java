import java.io.*;
import java.util.*;

public class Prim {
    /* Prim's Algorithm implemented using heap with dijkstra's shortest-path */

    public static void main(String[] args) throws IOException {
        Graph graph = new Graph();
        System.out.println(graph.computeMST());
    }
}

/* Graph is initialized by an arrayList of vertices with each vertex
 * maintaining an ArrayList of other vertices that connects with it.
 */

class Graph {
    ArrayList<ArrayList<int[]>> vertices;
    boolean[] explored;
    Heap unexplored; // all unprocessed vertices are maintained in the heap
    int numV; // number of vertices
    int numE; // number of edges

    public Graph() throws IOException {
        File file = new File("data/edges.txt");
        BufferedReader input = new BufferedReader(new FileReader(file));

        String line = input.readLine();
        String [] str = line.split(" ");
        numV = Integer.parseInt(str[0]);
        numE = Integer.parseInt(str[1]);

        vertices = new ArrayList<>();
        for (int i = 0; i < numV; i++) {
            vertices.add(new ArrayList<>());
        }
        for (int i = 0; i < numE; i++) {
            line = input.readLine();
            str = line.trim().split(" ");
            int u = Integer.parseInt(str[0]);
            int w = Integer.parseInt(str[1]);
            int len = Integer.parseInt(str[2]);
            vertices.get(u - 1).add(new int[]{w, len});
            vertices.get(w - 1).add(new int[]{u, len});
        }

        input.close();
    }

    /*
     * Computes the minimum spanning tree using Prim's algorithm.
     * @return an integer indicating the overall cost of a minimum spanning tree
     */
    public int computeMST() {
        explored = new boolean[numV];
        explored[0] = true; // first vertex now is processed
        unexplored = new Heap();
        int sumCost = 0;
        int minCost = Integer.MAX_VALUE; // if not connected, cost = + infinity

        /* for every vertex other than v1, retrieve it from Graph
         * if it is connected to v1, and the cost ei is not +infinity, set minCost to ei
         * add vi to the heap of unexplored vertices
         */
        for (int i = 1; i < numV; i++) {
            for (int[] edge : vertices.get(i)) {
                if (edge[0] == 1 && edge[1] < minCost) {
                    minCost = edge[1];
                }
            }
            unexplored.add(new int[]{i + 1, minCost});
        }
        /* retrieve the next vertex with the cheapest edge */
        for (int i = 1; i < numV; i++) {
            int[] u = unexplored.poll();
            for (int[] edge : vertices.get(u[0] - 1)) {
                int v = edge[0];
                // if vertex has not been explored,
                // compare its cost with the min in unexplored heap to see if need to update cost
                if (!explored[v - 1]) {
                    int cost = edge[1];
                    if (cost < unexplored.extract_min(v)) {
                        unexplored.update(new int[]{v, cost});
                    }
                }
            }
            explored[u[0] - 1] = true;
            sumCost = sumCost + u[1];
        }
        return sumCost;
    }
}

/**
 * Self implemented heap structure with stores vertices and its current min cost as an array
 */
class Heap {
    private ArrayList<int[]> heap;
    HashMap<Integer, Integer> idx;

    public Heap() {
        heap = new ArrayList<>();
        idx = new HashMap<>();
    }

    /**
     * Adds a vertex (ID, cost) to heap
     */
    public void add(int[] a) {
        int n = heap.size();
        heap.add(a);
        while (n > 0 &&
                heap.get((n-1)/2)[1] > heap.get(n)[1]) {
            Collections.swap(heap, (n-1)/2, n);
            int[] b = heap.get(n);
            idx.remove(b[0]);
            idx.put(b[0], n);
            n = (n-1)/2;
        }
        idx.put(a[0], n);
    }

    /**
     * Removes and returns the vertex with first/smallest cost from heap.
     * @return the vertex with first/smallest cost in heap.
     */
    public int[] poll() {
        if (heap.size() == 0) {
            return (int[]) null;
        }
        int[] smallest = heap.get(0);
        int n = heap.size() - 1;
        int i = 0;
        Collections.swap(heap, 0, n);
        heap.remove(n);
        idx.remove(smallest[0]);
        if (heap.size() > 0) {
            idx.remove(heap.get(0)[0]);
            idx.put(heap.get(0)[0], 0);
        }
        n--;
        boolean balanced = false;
        while (!balanced) {
            if (i * 2 + 1 > n) {
                balanced = true;
            } else if (i * 2 + 1 == n) {
                if (heap.get(i)[1] > heap.get(i * 2 + 1)[1]) {
                    Collections.swap(heap, i, 2 * i + 1);
                    idx.remove(heap.get(i)[0]);
                    idx.remove(heap.get(i * 2 + 1)[0]);
                    idx.put(heap.get(i)[0], i);
                    idx.put(heap.get(i * 2 + 1)[0], i * 2 + 1);
                }
                balanced = true;
            } else {
                if (heap.get(i)[1] <= heap.get(i * 2 + 1)[1] && heap.get(i)[1] <= heap.get(i * 2 + 2)[1]) {
                    balanced = true;
                } else {
                    if (heap.get(i * 2 + 1)[1] < heap.get(i * 2 + 2)[1]) {
                        Collections.swap(heap, i, i * 2 + 1);
                        idx.remove(heap.get(i)[0]);
                        idx.remove(heap.get(i * 2 + 1)[0]);
                        idx.put(heap.get(i)[0], i);
                        idx.put(heap.get(i * 2 + 1)[0], i * 2 + 1);
                        i = i * 2 + 1;
                    } else {
                        Collections.swap(heap, i, i * 2 + 2);
                        idx.remove(heap.get(i)[0]);
                        idx.remove(heap.get(i * 2 + 2)[0]);
                        idx.put(heap.get(i)[0], i);
                        idx.put(heap.get(i * 2 + 2)[0], i * 2 + 2);
                        i = i * 2 + 2;
                    }
                }
            }
        }
        return smallest;
    }

    /**
     * Replace the cost of a certain vertex with a smaller one.
     * @param v array of length two. 0 is the vertex id, 1 is the updated cost.
     * @return true if vertex has been updated, false if there is no such vertex in heap
     */
    public boolean update(int[] v) {
        boolean updated = false;
        if (idx.containsKey(v[0])) {
            int index = idx.get(v[0]);
            heap.set(index, v);
            while (index > 0 && heap.get((index-1)/2)[1] > heap.get(index)[1]) {
                Collections.swap(heap, (index-1)/2, index);
                int[] b = heap.get(index);
                idx.remove(b[0]);
                idx.put(b[0], index);
                index = (index-1)/2;
            }
            idx.remove(v[0]);
            idx.put(v[0], index);
            updated = true;
        }
        return updated;
    }

    /**
     * Returns the current min cost of a certain vertex
     * @param vertex id of the vertex
     * @return current min cost
     */
    public int extract_min(int vertex) {
        int index = idx.get(vertex);
        return heap.get(index)[1];
    }

    /**
     * Returns size of the heap.
     * @return size of the heap
     */
    public int size() {
        return heap.size();
    }
}
