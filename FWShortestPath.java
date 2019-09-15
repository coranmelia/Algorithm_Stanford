import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.PriorityQueue;


/* *************************************************************************************
 * Floyd-Warshall's algorithm
 * Algorithm that computes the all-pair shortest path of a directed graph
 *
 * @return the shortest shortest path in the graph
 *
 * Time Complexity: O(n^3)
 * Space Complexity: O(n^2)
 *
 * @author Cora Wang
 *
 * **************************************************************************************/


public class FWShortestPath {

    public void FW (int arr[][], int n){
        int edge [][][] = new int [n][n][2];

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){

                if(i == j)
                    edge[i][j][0] = 0;
                else
                    edge[i][j][0] = arr[i][j];
            }
        }

        for(int k = 1; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    edge[i][j][1] = Math.min(edge[i][j][0], (edge[i][k][0] + edge[k][j][0]));
                    edge[i][j][0] = edge[i][j][1];
                }
            }
        }


        minSolution(edge, n);
    }

    void minSolution(int edge[][][], int n) {
        for (int i = 0; i < n; i++) {
            if (edge[i][i][1] < 0)
                throw new NumberFormatException("negative cycle");
        }

        int minPath = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(i != j)
                    if(edge[i][j][1] < minPath)
                        minPath = edge[i][j][1];
            }
        }

        System.out.println(minPath);
    }

    public static void main(String [] args)
        throws IOException{


        File file = new File("data/g1.txt");
        BufferedReader input = new BufferedReader(new FileReader(file));
        String line = input.readLine();
        String [] str = line.split(" ");
        int n = Integer.parseInt(str[0]); // # vertices
        int e = Integer.parseInt(str[1]); // # edge

        int graph [][] = new int [n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                graph[i][j] = 99999;
            }
        }

        while((line = input.readLine())!= null){
            str = line.split(" ");
            int src = Integer.parseInt(str[0]);
            int dist = Integer.parseInt(str[1]);
            int len = Integer.parseInt(str[2]);
            graph[src-1][dist-1] = len;
        }

        FWShortestPath fw = new FWShortestPath();
        fw.FW(graph, n);
    }
}
