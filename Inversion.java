import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;


public class Inversion {

    public static long mergeSort(int a[], int sz) {
        int tmp[] = new int[sz];
        return divideSort(a, tmp, 0, sz - 1);
    }

    public static long divideSort(int a[], int tmp[], int left, int right) {
        int mid;
        long count = 0;
        if (right > left) {
            mid = (right + left) / 2;
            count = divideSort(a, tmp, left, mid);
            count += divideSort(a, tmp, mid + 1, right);

            count += merge(a, tmp, left, mid + 1, right);
        }
        return count;
    }

    public static long merge(int a[], int tmp[], int left, int mid, int right) {
        int i, j, k; // indexes of the three arrays
        long count = 0;

        i = left; // index of left array
        j = mid; // index of right array
        k = left; // index of resultant merged array

        while ((i <= mid - 1) && (j <= right)) {
            if (a[i] <= a[j]) {
                tmp[k++] = a[i++];
            } else {
                tmp[k++] = a[j++];
                /* if a[j] in right array is smaller than a[i] in the left array
                 * then all the rest of elements in the left array are larger than a[j],
                 * so all elements between i+1 and mid inverses with a[j] */
                count = count + (mid - i);
            }
        }
        while (i <= mid - 1) { // if any element left in the left array
            tmp[k++] = a[i++];
        }
        while (j <= right) { // if any element left in the right array
            tmp[k++] = a[j++];
        }
        for (i = left; i <= right; i++) {
            a[i] = tmp[i];
        }
        return count;
    }

    public static void main(String[] args)
            throws Exception {
        // We need to provide file path as the parameter:
        // double backquote is to avoid compiler interpret words
        // like \test as \t (ie. as a escape sequence)

        String fileName = args[0];
        Scanner s = new Scanner(new File(fileName));

        int[] a = new int [100000]; // length exceed range of int

        for (int i = 0; i < a.length; i++)
            a[i] = s.nextInt();

        System.out.println(mergeSort(a, a.length));

    }
}
