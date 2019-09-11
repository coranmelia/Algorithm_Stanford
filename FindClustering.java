import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class FindClustering {
    private static int numV;
    private static int nBits;
    private static ArrayList<BitSet> list = new ArrayList<>();

    public static void findk(){

        Map<BitSet, Integer> map = new HashMap<>();

        int numNodes = list.size();
        WeightedUF uf = new WeightedUF(numNodes);

        for(int i = 0; i < numNodes; i++){
            BitSet bit = list.get(i);
            if (!map.containsKey(bit)) {
                map.put(bit, i);
            }else{
                uf.union(i, map.get(bit));
            }
        }


        Set<BitSet> keySet = map.keySet();
        Iterator<BitSet> iterator = keySet.iterator();

        while (iterator.hasNext()) {
            BitSet bitSet = (BitSet) iterator.next();
            for (int i = 0; i < nBits; i++) {
                BitSet temp = (BitSet) bitSet.clone();
                temp.flip(i);
                if (map.containsKey(temp)) {
                    uf.union(map.get(bitSet), map.get(temp));
                }
            }
        }

        iterator = keySet.iterator();
        while (iterator.hasNext()) {
            BitSet bitSet = (BitSet) iterator.next();
            for (int i = 0; i < nBits; i++) {
                for (int j = 0; (j < nBits) && (j != i); j++) {
                    BitSet temp = (BitSet) bitSet.clone();
                    temp.flip(i);
                    temp.flip(j);
                    if (map.containsKey(temp)) {
                        uf.union(map.get(bitSet), map.get(temp));
                    }
                }
            }
        }

        int i = 0;

        System.out.println("*** Number of Clusters => " + uf.count());
    }

    // only want to put two points in one cluster iff the distance between them is 1 or 2


    public static void main(String[] args)
            throws IOException {

        File file = new File("data/Clustering_big.txt");
        BufferedReader input = new BufferedReader(new FileReader(file));
        String line = input.readLine();
        String[] str = line.split(" ");
        numV = Integer.parseInt(str[0]);
        nBits = Integer.parseInt(str[1]);

        int j = 0;

        while ((line = input.readLine()) != null) {
            str = line.split(" ");
            BitSet bit = new BitSet(nBits);

            for (int i = 0; i < str.length; i++) {
                if (str[i].compareTo("1") == 0) {
                    bit.set(i);
                }
            }
            list.add(bit);
        }
        input.close();

        findk();
    }
}
