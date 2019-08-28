import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SCC {
    private int v; // vertices
    private LinkedList<Integer> adj[]; // adjacent list
    private static ArrayList<Integer> arr = new ArrayList<Integer>();
    private static int depth;

    SCC(int v) {
        this.v = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; i++) {
            adj[i] = new LinkedList<>();
        }
    }

    // add directed edge from u to v
    void addEdge(int u, int v) {
        adj[u].add(v);
    }

    void DFSloop(int v, boolean visited[]) {
        visited[v] = true; // assume v is visited
//        System.out.print(v + " "); // forget printing out every component it takes forever
        int n;

        depth++;

        Iterator<Integer> k = adj[v].iterator();  // go through from the first to the last
        while (k.hasNext()) {
            n = k.next();
            if (!visited[n]){
                DFSloop(n, visited);
            }
        }
    }

    SCC reverse() {
        SCC G = new SCC(v);
        for (int i = 0; i < v; i++) {
            Iterator<Integer> k = adj[i].listIterator(); // stack take off from the last element to the first
            while (k.hasNext())
                G.adj[k.next()].add(i); // if k has next element, add k.next() to the new SCC graph
        }
        return G;
    }

    void fillOrder(int v, boolean visited[], Stack stack) {

        visited[v] = true; // Mark the current node as visited

        // Recur for all the vertices adjacent to this vertex
        Iterator<Integer> i = adj[v].iterator();
        while (i.hasNext()) {
            int n = i.next();
            if (!visited[n])
                fillOrder(n, visited, stack);
        }
        stack.push(v); // All vertices reachable from v are processed, push v to Stack
    }

    /* finds and prints all strongly connected components */
    void processSCCs() {
        Stack stack = new Stack();

        // Mark all the vertices as not visited (For first DFS)
        boolean visited[] = new boolean[v];
        for (int i = 0; i < v; i++)
            visited[i] = false;

        // Fill vertices in stack according to their finishing times
        for (int i = 0; i < v; i++)
            if (visited[i] == false)
                fillOrder(i, visited, stack);

        // Create a reversed graph
        SCC Grev = reverse();

        // Mark all the vertices as not visited (For second DFS)
        for (int i = 0; i < v; i++)
            visited[i] = false;

        // Now process all vertices in order defined by Stack
        while (stack.empty() == false) {
            // Pop a vertex from stack
            int k = (int) stack.pop();

            // Print Strongly connected component of the popped vertex
            if (visited[k] == false) {
                depth = 0;
                Grev.DFSloop(k, visited);
//                System.out.println("depth: " + depth);
                arr.add(depth);
            }
        }
//        System.out.println(arr);
        Collections.sort(arr);
        Collections.reverse(arr);
        for(int i = 0; i < 5; i++)
            System.out.print(arr.get(i) + ",");
        System.out.println();
    }

    public static void main(String args[])
            throws IOException {
        File file = new File("data/SCC.txt");
        BufferedReader input = new BufferedReader(new FileReader(file));

        String line;
        SCC G = new SCC(875714);

        // Read a line of text from the file
        while ((line = input.readLine()) != null) {
            int u = Integer.parseInt(line.split(" ")[0])-1;
            int v = Integer.parseInt(line.split(" ")[1])-1;

            if(v != u) {
                G.addEdge(u, v);
            }
        }

        G.processSCCs();

        input.close();
    }
}
