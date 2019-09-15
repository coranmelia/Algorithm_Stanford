import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/* *************************************************************************************
 * Space-efficient Knapsack algorithm
 * Computes the maximum sum of value given a capacity of total weight while maintaining
 * space efficiency
 *
 * Time Complexity: O(wn)
 *
 * @author Cora Wang
 *
 * **************************************************************************************/


public class AdvancedKnapsack {

    // 2D array to store optimal solution to-date
    private static int [][] arr;

    public static int getWeight (int n, int w, KNode [] knap){
        // only two rows are maintained
        arr = new int[2][w];

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
        for(int i = 1; i < n; i++){ //number of nodes: row index
            for(int j = 0; j < knap[i].weight; j++) { //weight index
                // There's no need to loop for the cases x <= w, copy over from previous row
                arr[1][j] = arr[0][j];
            }

            for(int j = knap[i].weight; j < w; j++){
                    arr[1][j] = Math.max((arr[0][j]),(arr[0][j-knap[i].weight]+knap[i].value));
            }

            // copy over data in row 2 to row 1
            for(int j = 0; j < w; j++){
                arr[0][j] = arr[1][j];
            }
        }

        return arr[1][w-1];
    }

    public static void main (String [] args)
            throws IOException {

        File file = new File("data/knapsack_big.txt");
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
