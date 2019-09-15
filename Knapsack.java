import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


/* *************************************************************************************
 * Knapsack algorithm
 * Computes the maximum sum of value given a capacity of total weight
 *
 * Time Complexity: O(wn)
 *
 * @author Cora Wang
 *
 * **************************************************************************************/

public class Knapsack {

     // 2D array to store optimal solution to-date
    private static int [][] arr;

    public static int getWeight (int n, int w, KNode [] knap){
        arr = new int[n][w];
        /* base case
           if total weight = 0, the total value to-date must be 0
           fill the first row of arr with 0's */
        for(int i = 0; i < w; i++) {
            arr[0][i] = 0;
        }

        /* define each sub-problem
           each sub-problem involves two cases
           case 1: S is optimal with the first i-1 elements, i is not in the set: Smax = v(i-1), j
           case 2: S is optimal with the first i-1 elements, plus i: Smax = v(i-1)+vi, j-wi (leftover residual)
         */
        for(int i = 1; i < n; i++){
            for(int j = 0; j < w; j++) {
                if(j > knap[i].weight)
                    arr[i][j] = Math.max((arr[i-1][j]),(arr[i-1][j-knap[i].weight]+knap[i].value));
                else
                    arr[i][j] =arr[i-1][j];
            }
        }

        return arr[n-1][w-1];
    }

    public static void main (String [] args)
        throws IOException{

        File file = new File("data/knapsack1.txt");
        BufferedReader input = new BufferedReader(new FileReader(file));
        String line = input.readLine();
        String [] str = line.split(" ");
        int w = Integer.parseInt(str[0]); // knapsack size/ weight capacity
        int n = Integer.parseInt(str[1]); // number of items

        KNode [] knap = new KNode [n];

        int i = 0;
        while((line = input.readLine())!= null){
            str = line.split(" ");
            int vi = Integer.parseInt(str[0]);
            int wi = Integer.parseInt(str[1]);

            KNode x = new KNode();
            x.value = vi;
            x.weight = wi;

            knap[i] = x;
            i++;
        }

        int result = getWeight(n, w, knap);

        System.out.println(result);
    }

}

class KNode{
    int value;
    int weight;
}
