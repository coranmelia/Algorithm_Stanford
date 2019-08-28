import java.io.File;
import java.util.Random;
import java.util.Scanner;

public class QuickSort {

    public void random(int a[], int low, int high) {
        Random rand = new Random();
        int pivot = rand.nextInt(high - low + 1) + low;

        int tmp1 = a[pivot];
        a[pivot] = a[high];
        a[high] = tmp1;
    }

    public int partition(int a[], int low, int high) {

        // pivot is chosen at random

        int pivot = a[high];

        int i = (low - 1); // index of smaller element

        for (int j = low; j < high; j++) {
            // If current element is smaller than or
            // equal to pivot
            if (a[j] <= pivot) {
                i++;

                // swap arr[i] and arr[j]
                int temp = a[i];
                a[i] = a[j];
                a[j] = temp;
            }
        }

        // swap arr[i+1] and arr[high] (or pivot)
        int temp = a[i + 1];
        a[i + 1] = a[high];
        a[high] = temp;

        return i + 1;
    }

    /* The main function that implements QuickSort()
     arr[] --> Array to be sorted,
     low --> Starting index,
     high --> Ending index */
    public void sort(int arr[], int low, int high)
    {
        if (low < high)
        {
            /* pi is partitioning index, arr[pi] is
            now at right place */
            int pi = partition(arr, low, high);

            // Recursively sort elements before
            // partition and after partition
            sort(arr, low, pi-1);
//            System.out.println("before");
//            printArray(arr);

            sort(arr, pi+1, high);
//            System.out.println("after");
//            printArray(arr);
        }
    }

    /* A utility function to print array of size n */
    public static void printArray(int arr[])
    {
        int n = arr.length;
        for (int i = 0; i < n; ++i)
            System.out.print(arr[i]+" ");
        System.out.println();
    }

    // Driver code
    public static void main(String[] args)
            throws Exception {

        String fileName = args[0];
        Scanner s = new Scanner(new File(fileName));

        int[] a = new int [10000]; // length exceed range of int
        int n = a.length;

//        int a[] = {10, 7, 8, 9, 1, 5};
//        int n = a.length;
//
        for (int i = 0; i < n; i++)
            a[i] = s.nextInt();

        QuickSort ob = new QuickSort();
        ob.sort(a, 0, n-1);

        System.out.println("sorted array");
        printArray(a);
    }
}
