import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class QuickSortMedian {

    public static int count = 0;

    /* This function takes last element as pivot,
       places the pivot element at its correct
       position in sorted array, and places all
       smaller (smaller than pivot) to left of
       pivot and all greater elements to right
       of pivot */

    int partitionMedian(int a[], int low, int high)
    {
        int median;
        // median element be pivot

        if((high-low) == 1){ // base case
            median = low;
        }
        else if((high-low)%2 == 1) { //odd
            median = (high - low) / 2 + low;
        }
        else{
            median = (high - low + 1) / 2 + low;
        }

//        System.out.println("left: " + a[low] + " right: " + a[high] + " middle: " + a[median]);
//        for(int k = low; k < high; k++)
//            System.out.print(a[k] + " ");
//        System.out.println(" ");
        int pivot;

        if((a[low] < a[median] && a[median] < a[high]) ||  (a[high] <  a[median] && a[median] < a[low])) { // if a[low] < a[high] < a[median]
                pivot = a[median];
                a[median] = a[low];
                a[low] = pivot;
        }
        else if ((a[median] < a[low] && a[low] < a[high]) || (a[high] < a[low] && a[low] < a[median])){
            pivot = a[low];
        }
        else{
            pivot = a[high];
            a[high] = a[low];
            a[low] = pivot;
        }

        int i = (low+1);

        for (int j = low+1; j<high+1; j++){
            if (a[j] < pivot){
                // swap arr[i] and arr[j]
                int tmp = a[i];
                a[i] = a[j];
                a[j] = tmp;

                i++;
            }
        }

        // swap arr[i-1] and arr[low] (or pivot)
        int tmp = a[low];
        a[low] = a[i-1];
        a[i-1] = tmp;

//        System.out.println("Array is: ");
//        printArray(a);

        return i-1;
    }


    /* The main function that implements QuickSort()
      arr[] --> Array to be sorted,
      low  --> Starting index,
      high  --> Ending index */
    void sort(int arr[], int low, int high)
    {
        if (low < high)
        {
            /* pi is partitioning index, arr[pi] is
              now at right place */
            int pi = partitionMedian(arr, low, high);
            count += (high-low);

            // Recursively sort elements before
            // partition and after partition
            sort(arr, low, pi-1);
            sort(arr, pi+1, high);
        }
    }

    /* A utility function to print array of size n */
    static void printArray(int arr[])
    {
        int n = arr.length;
        for (int i=0; i<n; ++i)
            System.out.print(arr[i]+" ");
        System.out.println();
    }

    // Driver program
    public static void main(String args[])
            throws FileNotFoundException {

        String fileName = args[0];
        Scanner s = new Scanner(new File(fileName));

        int[] a = new int [10000];

        int n = a.length;

        for (int i = 0; i < n; i++)
            a[i] = s.nextInt();

        QuickSortMedian ob = new QuickSortMedian();
        ob.sort(a, 0, n-1);

        System.out.println("count: " + count);

//        System.out.println("sorted array");
//        printArray(a);
    }
}
