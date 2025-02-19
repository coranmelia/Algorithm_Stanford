import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class QuickSortRight {

    public static int count = 0;
    /* This function takes last element as pivot,
       places the pivot element at its correct
       position in sorted array, and places all
       smaller (smaller than pivot) to left of
       pivot and all greater elements to right
       of pivot */
    int partitionRight(int a[], int low, int high)
    {
        // last element be pivot

        int pivot = a[high];
        a[high] = a[low]; //swap high(pivot) and low
        a[low] = pivot;


        int i = (low+1);

//        System.out.println("pivot is: " + pivot);

        for (int j = low+1; j<high+1; j++){
            if (a[j] < pivot){
                // swap arr[i] and arr[j]
                int tmp = a[i];
                a[i] = a[j];
                a[j] = tmp;

                i++;
                count++;
            }
            else{
                count++;
            }
        }

        // swap arr[i-1] and arr[low] (or pivot)
        int tmp = a[low];
        a[low] = a[i-1];
        a[i-1] = tmp;

//        System.out.print("print array: ");
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
            int pi = partitionRight(arr, low, high);

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

        QuickSortRight ob = new QuickSortRight();
        ob.sort(a, 0, n-1);

        System.out.println("count: " + count);

//        System.out.println("sorted array");
//        printArray(a);
    }
}
