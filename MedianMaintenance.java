import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;

public class MedianMaintenance {
    private PriorityQueue<Integer> maxHeap;
    private PriorityQueue<Integer> minHeap;
    private int size;

    public MedianMaintenance(){
        minHeap = new PriorityQueue<>(5000); // find max
        maxHeap = new PriorityQueue<>(5000, Collections.reverseOrder()); // find min
        size = 0;
    }

    public void add(int n){
        if (size == 0){
            maxHeap.add(n);
//            System.out.println("first element added to maxHeap: " + n);
        }
        else if(size % 2 == 0){
            if (n > minHeap.peek()){
//                System.out.println("element " + n + " is LARGER than the largest element in minHeap: " + minHeap.peek());
                maxHeap.add(minHeap.poll());
                minHeap.add(n);
//                System.out.println("\t"+n+" is added to minHeap, largest element in minHeap is added to maxHeap" );
            } else {
                maxHeap.add(n);
//                System.out.println("element " + n + " is LESS than the largest element in minHeap: " + minHeap.peek());
//                System.out.println("\t"+n+" is added to maxHeap" + " and maxHeap peek is: "+maxHeap.peek() );
            }

        } else { // size is odd
            if (n < maxHeap.peek()){
                minHeap.add(maxHeap.poll());
                maxHeap.add(n);
            } else {
                minHeap.add(n);
            }
        }
        size++;
    }
    public int median(){
        return maxHeap.peek();
    }

    public static void main(String [] args)
            throws IOException {
        int sum = 0;
        MedianMaintenance m = new MedianMaintenance();
        Scanner in = new Scanner(new File("data/Median2.txt"));
        while (in.hasNextInt()){
            int a = in.nextInt();
            m.add(a);
            sum = sum + m.median();
            System.out.println(a + "    " + m.median());
        }
        System.out.println(sum % 10000);
    }
}
