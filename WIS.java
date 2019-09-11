import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

public class WIS {

    private long[] a;


    public Set<Integer> findWIS(long[] w){

        if ((w == null) || (w.length == 0)) {
            return new HashSet<>();
        }

        if (w.length == 1) {
            Set<Integer> m = new HashSet<>();
            m.add(1);
            return m;
        }

        a = new long[w.length];
        a[0] = w[0];
        a[1] = Math.max(w[0], w[1]);
        for (int k = 2; k < w.length; ++k) {
            a[k] = Math.max(a[k - 1], a[k - 2] + w[k]);
        }

        // reconstruct
        //        Hashtable<Integer, Integer> dict = new Hashtable<Integer, Integer>();
        Set<Integer> dict = new HashSet<>();

        int i = a.length-1;
        while (i >= 2) {
            if (a[i - 1] >= (a[i - 2] + w[i])) {
                i--;
            } else {
                dict.add(i);
                i = i - 2;
            }
        }
        if(i == 1){
            if(w[0] > w[1]) {
                dict.add(0);
            }
            else {
                dict.add(1);
            }
        }else if(i == 0){
            dict.add(0);
        }

        return dict;
    }

    public static void main(String[] args)
            throws IOException {
        File file = new File("data/mwis.txt");
        BufferedReader input = new BufferedReader(new FileReader(file));

        String line = input.readLine();
        String[] str = line.split(" ");
        int v = Integer.parseInt(str[0]);
        long w[] = new long[v + 1];
        int i = 0;
        while ((line = input.readLine()) != null) {
            str = line.split(" ");
            w[i] = Integer.parseInt(str[0]);
            i++;
        }


//        int arr3[] = new int[]{1, 2, 3, 4, 17, 117, 517, 997};
        int arr3[] = new int[]{0, 1, 2, 3, 16, 116, 516, 996};

        WIS m = new WIS();
        Set<Integer> x = m.findWIS(w);

        System.out.println(x);

        for(int q : arr3) {
            if (x.remove(q))
                System.out.print(1);
            else
                System.out.print(0);
        }
    }
}
