import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;

public class MinSum {
    /* scheduling problem with
     * The goal is to compute the minimized weighted sum of completion time
     * implementation using heap should take O(n log n)
     */


    public static void main(String[] args)
        throws IOException{

        File file = new File("data/jobs.txt");
        BufferedReader input = new BufferedReader(new FileReader(file));
        int n = Integer.parseInt(input.readLine().split(" ")[0]);
        Comparator<int[]> comparator = new Jcomparator();
        PriorityQueue<int[]> pq = new PriorityQueue<>(n, comparator);

        String line;
        while ((line = input.readLine()) != null){
            String[] str = line.trim().split(" ");
            int[] job = new int[2];
            job[0] = Integer.parseInt(str[0]);
            job[1] = Integer.parseInt(str[1]);
            pq.add(job);
        }
        input.close();

        long sum = 0;
        int len = 0;

        for (int i = 0; i < n; i++) {
            int[] currentJob = pq.poll();
            len = len + currentJob[1];
            sum = sum + currentJob[0] * len;
        }
        System.out.println(sum);

    }

    static class Jcomparator implements Comparator<int[]>{

        /* compare two job's weighted completion time by (wi-li) - (wj-lj);
         * if i = j, return 0
         * if i > j, return negative
         * if i < j, return positive */

//        @Override
//        public int compare(int[] x, int[] y) {
//            if (x[0] - x[1] == y[0] - y[1])
//                return y[0] - x[0];
//            else
//                return (y[0] - y[1]) - (x[0] - x[1]);
//        }

        /* compare two job's weighted completion time by (wi/li) - (wj/lj);
         * if i = j, return y[0] - x[0] to break tie
         * if i > j, return -1
         * if i < j, return 1
         */

        @Override
        public int compare(int[] x, int[] y) {
            double xRatio = (double) x[0] / x[1];
            double yRatio = (double) y[0] / y[1];
            if (xRatio == yRatio)
                return y[0] - x[0];
            else if(xRatio < yRatio)
                return 1;
            else
                return -1;
        }
    }
}
