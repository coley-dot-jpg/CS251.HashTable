public class sortTest {

    public static int[] arr = {5, 10, 7, 2, 0, 9 , 2, 14, 3, 1, 29, 20, 10};
    public static String[] arr2 = {"5", "10", "7", "2", "0", "9" , "2.1", "14", "3", "1", "29", "20", "10.1"};
    public static int inversionCount = 0;

    // Merge two sorted subarrays arr[low .. mid] and arr[mid + 1 .. high]
    /*public static void merge(int[] left, int[] right, int low, int mid, int high) {
        int k = low, i = low, j = mid + 1;
        int[] sorted = new int[];
        // While there are elements in the left and right runs
        while (i <= mid && j <= high)
        {
            if (arr[i] <= arr[j]) {
                aux[k++] = arr[i++];
            }
            else {
                aux[k++] = arr[j++];
                inversionCount += (mid - i + 1);	// NOTE
            }
        }

        // Copy remaining elements
        while (i <= mid)
            aux[k++] = arr[i++];

        // Don't need to copy second half

        // copy back to the original array to reflect sorted order
        for (i = low; i <= high; i++) {
            arr[i] = aux[i];
        }

        return ;
    }

    // Sort array arr [low..high] using auxiliary array aux
    public static int[] mergeSort(int[] arr, int start, int end) {
        // Base case
        if (end == start) {	// if run size == 1
            return arr;
        }

        // find mid point
        int pivot = (start + ((end - start) >> 1));

        // recursively split runs into two halves until run size == 1,
        // then merge them and return back up the call chain

        // split / merge left  half
        int[] left = mergeSort(arr, start, pivot);

        // split / merge right half
        int[] right = mergeSort(arr, pivot + 1, end);

        // merge the two half runs
        return merge(left, right, start, pivot, end);
    }*/


    private static void quickSort(int start, int end){
        if (start < end) {
            int pivot = partition(start, end);
            quickSort(start, pivot - 1);
            quickSort(pivot + 1, end);
        }
    }
    private static int partition(int start, int end) {
        int pivot = arr[end];
        int i = start - 1;
        int j = start;
        while(j < end) {
            if (compare(arr[j], pivot)) {
                i++;
                swap(arr, i, j);
            }
            j++;
        }
        swap(arr, i + 1, end);
        return i+1;
    }
    private static boolean compare(int a ,int b){
        return a <= b;

    }
    /***************
     * This functions is for the quick sort.
     * By using this function, swap the similarity and the products at the same time.
     *****************/
    private static void swap(int[] arr, int index1, int index2){
        int temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
        String temp2 = arr2[index1];
        arr2[index1] = arr2[index2];
        arr2[index2] = temp2;
    }

    public static void main(String[] args) {
        quickSort(0, arr.length - 1);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
            //System.out.print(arr2[i] + " ");
        }
        int pivot = arr.length / 2;
        int[] left = new int[pivot];
        int[] right = new int[arr.length - pivot];
        for (int i = 0; i < arr.length; i++) {
            if (i < pivot) {
                left[i] = arr[i];
            } else {
                System.out.print("right");
                right[i - pivot] = arr[i];
            }
            System.out.print(arr[i] + " ");
        }

    }
}
