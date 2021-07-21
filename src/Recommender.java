import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;

public class Recommender{

    /********************************
     * Do not change below code
     ********************************/

    int swaps, compares;
    int[] inversionCounts;
    String[] products;

    public Recommender(){
        swaps = 0;
        compares = 0;
    }

    public int getComapares() {
        return compares;
    }

    public int getSwaps() {
        return swaps;
    }
    /**************
     * This function is for the quick sort.
     **************/
    private boolean compare(int a ,int b){
        compares++;
        return a <= b;

    }
    /***************
     * This functions is for the quick sort.
     * By using this function, swap the similarity and the products at the same time.
     *****************/
    private void swap(int[] nums, int index1, int index2){
        swaps++;
        int temp = nums[index1];
        nums[index1] = nums[index2];
        nums[index2] = temp;

        String tempS = products[index1];
        products[index1] = products[index2];
        products[index2] = tempS;

    }

    /********************************
     * Do not change above code
     ********************************/

    /**
     * This function is for the calculate inversion counts of each option's.
     * @param dataset is file name of all data for hash table
     * @param options is the list of product name which we want to getting the inversion counts
     * @return it is integer numsay of each option's inversion counts. The order of return should be matched with options.
     */
    public int[] inversionCounts(String dataset, String[] options) {
        HashTable hash = new HashTable();
        try {
            hash.load(dataset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        inversionCounts = new int[options.length];
        for (int i = 0; i < options.length; i++) {
            HashTable.Pair pair = hash.get(options[i]);
            int[] ratings = pair.value.getDepRating();
            mergeSort(ratings, 0, ratings.length - 1, i);
        }
        return inversionCounts;
    }

    /**
     * Get the sequence of recommendation from the dataset by sorting the inverse count.
     * Compare the similarity of depRating between RecentPurchase's and each option's.
     * Use inverse count to get the similarity of two numsay.
     * */
    public String[] recommend(String dataset, String recentPurchase, String[] options) {
        products = options.clone();
        HashTable hash = new HashTable();
        try {
            hash.load(dataset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // get the recent purchases rating
        inversionCounts = new int[1];
        HashTable.Pair recent = hash.get(recentPurchase);
        int[] recRates = recent.value.getDepRating();
        mergeSort(recRates,0, recRates.length - 1, 0);
        int recInv = inversionCounts[0];
        // get the ratings for the rest of the options
        inversionCounts(dataset, options);
        // replace each value with its similarity comparison
        for (int i = 0; i < options.length; i++) {
            inversionCounts[i] = Math.abs(recInv - inversionCounts[i]);
        }
        // sort the values in the list
        quickSort(0, options.length - 1);
        return products;
    }

    private void mergeSort(int[] nums, int start, int end, int countIndex) {
        if (start < end) {
            int pivot = (start + end) / 2;
            mergeSort(nums, start, pivot, countIndex);
            mergeSort(nums, pivot + 1, end, countIndex);
            merge(nums, start, pivot, end, countIndex);
        }
    }

    private void merge(int[] nums, int start, int pivot, int end, int countIndex) {

        int[] left = Arrays.copyOfRange(nums, start, pivot + 1);
        int[] right = Arrays.copyOfRange(nums, pivot + 1, end + 1);

        // initalize left, right, and nums index counter
        int l = 0;
        int r = 0;
        int i = start;
        while (l < left.length && r < right.length) {
            if (left[l] <= right[r]) {
                nums[i] = left[l];
                l++;
            } else {
                nums[i] = right[r];
                // add the inverse to its slot in the counts array
                inversionCounts[countIndex] += pivot - start - l + 1;
                r++;
            }
            i++;
        }
        // add rest of arrays if any
        while (l < left.length) {
            nums[i++] = left[l++];
        }
        while (r < right.length) {
            nums[i++] = right[r++];
        }
    }

    private void quickSort(int start, int end) {
        if (start < end) {
            int pivot = partition(start, end);
            quickSort(start, pivot - 1);
            quickSort(pivot + 1, end);
        }
    }
    private int partition(int start, int end) {
        //pivot is always last number in array
        int pivot = inversionCounts[end];
        // counters for lowest number and for each loop
        int i = start - 1;
        int j = start;
        while(j < end) {
            if (compare(inversionCounts[j], pivot)) {
                i++;
                swap(inversionCounts, i, j);
            }
            j++;
        }
        // swap pivot to correct spot in array
        swap(inversionCounts, i + 1, end);
        return i+1;
    }
    public static void main(String[] args) throws Exception{
        Recommender rec = new Recommender();
        String[] opt = {"zKKqOe", "yBrsyn", "vUxtff", "qxZfIJ", "Ohfwph", "pvURPN", "iimnTs"};
        String bought = "yjBmYa";
        rec.recommend("custom_test.txt", bought, opt);
        System.out.println("expected: yBrsyn zKKqOe iimnTs vUxtff qxZfIJ pvURPN Ohfwph");
    }
}
