import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Huffman {
    public static int maxDepth(HuffmanNode root)
    {
        if (root == null)
            return 0;
        else
        {
            /* compute the depth of each subtree */
            int lDepth = maxDepth(root.left);
            int rDepth = maxDepth(root.right);

            /* return larger one */
            if (lDepth > rDepth) {
                return (lDepth + 1);
            }
            else {
                return (rDepth + 1);
            }
        }
    }

    public static int minDepth(HuffmanNode root)
    {
        // Corner case. Should never be hit unless the code is
        // called on root = NULL
        if (root == null)
            return 0;

        // Base case : Leaf Node. This accounts for height = 1.
        if (root.left == null && root.right == null)
            return 1;

        // If left subtree is NULL, recur for right subtree
        if (root.left == null)
            return minDepth(root.right) + 1;

        // If right subtree is NULL, recur for left subtree
        if (root.right == null)
            return minDepth(root.left) + 1;

        return Math.min(minDepth(root.left),
                minDepth(root.right)) + 1;
    }

    public static void main(String [] args)
        throws IOException {

        File file = new File("data/huffman.txt");
        BufferedReader input = new BufferedReader(new FileReader(file));

        String line = input.readLine();
        String [] str = line.split(" ");
        int v = Integer.parseInt(str[0]);

        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>(v, new HuffComparator());

        while((line = input.readLine())!= null){
            str = line.split(" ");
            HuffmanNode hf = new HuffmanNode();
            hf.weight = Integer.parseInt(str[0]);
            hf.left = null;
            hf.right = null;
            pq.add(hf);
        }

        HuffmanNode root = null;

        while (pq.size() > 1){
            HuffmanNode x = pq.peek();
            pq.poll();

            HuffmanNode y = pq.peek();
            pq.poll();

            HuffmanNode xy = new HuffmanNode();
            xy.weight = x.weight+y.weight;

            xy.left = x;
            xy.right = y;
            root = xy;
            pq.add(root);
        }


        System.out.println(maxDepth(root)-1+ " ");
        System.out.println(minDepth(root)-1+ " ");

    }
}

class HuffmanNode {
    int weight;

    HuffmanNode left;
    HuffmanNode right;
}

class HuffComparator implements Comparator<HuffmanNode> {
    public int compare(HuffmanNode x, HuffmanNode y){
        return x.weight - y.weight;
    }
}
