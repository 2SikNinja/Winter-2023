import java.util.*;


/**
 * A class that implements various algorithms to find the kth smallest element in a given array
 */
public class project2 
{
    /**
     * main method that runs an infinite loop, incrementing k by 1 each iteration and utilizes the three algorithms, Algorithm 1, Algorithm 2, and Algorithm 3
     * creates an array of size 2^k and fills it with random integers between 0 and 1000
     * sets the kth element to be the middle element of the array
     * prints the original array, and the result of using each of the three algorithms
     * starts a timer for each algorithm, calculates the duration and prints it
     * adds a line of separators between each iteration and a message indicating which iteration it is
     * to find the kth smallest element in the given array.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        int k = 1;
        while(true) {
            System.out.println("Iteration: " + k);
            int size = (int) Math.pow(2, k);
            int[] myArray = new int[size];
            Random rand = new Random();
            for (int i = 0; i < size; i++) {
                myArray[i] = rand.nextInt(1001);
            }
            int kth = size/2;
            System.out.println("Size: " + size);
            System.out.println("Original Array: " + Arrays.toString(myArray));
    
            // start timer for algorithm 1
            long startTime1 = System.nanoTime();
            System.out.println("Using Algorithm 1: " + Algorithm1(myArray, size, kth));
            long endTime1 = System.nanoTime();
            long duration1 = (endTime1 - startTime1);
            System.out.println("Algorithm 1 Time: " + duration1 + "ns");
    
            // start timer for algorithm 2
            int[] myArray3 = Arrays.copyOf(myArray, size);
            long startTime2 = System.nanoTime();
            System.out.println("Using Algorithm 2: " + Algorithm2(myArray3, 0, size-1, kth));
            long endTime2 = System.nanoTime();
            long duration2 = (endTime2 - startTime2);
            System.out.println("Algorithm 2 Time: " + duration2 + "ns");
    
            // start timer for algorithm 3
            int[] myArray4 = Arrays.copyOf(myArray, size);
            long startTime3 = System.nanoTime();
            System.out.println("Using Algorithm 3: " + Algorithm3(myArray4, 0, size-1, kth));
            long endTime3 = System.nanoTime();
            long duration3 = (endTime3 - startTime3);
            System.out.println("Algorithm 3 Time: " + duration3 + "ns");
            System.out.println("####################################");
            k++;
        }
    }


    
    

    /**
     * Algorithm 1: find the kth smallest element in the list using the O(n log n) Mergesort sorting method.
     *
     * @param myArray the given array
     * @param size the size of the array
     * @param kth the kth smallest element to find
     * @return the kth smallest element
     */
    public static int Algorithm1(int[] myArray, int size, int kth) {
        Arrays.sort(myArray);
        return myArray[kth-1];
    }


    
    public static int partition(int[] myArray, int l, int r) {
        int x = myArray[r], i = l;
        for (int j = l; j <= r - 1; j++) {
            if (myArray[j] <= x) {
                int temp = myArray[i];
                myArray[i] = myArray[j];
                myArray[j] = temp;
                i++;
            }
        }
        int temp = myArray[i];
        myArray[i] = myArray[r];
        myArray[r] = temp;
        return i;
    }


    /**
     * Algorithm 2: find the kth smallest element in the list using Quicksort algorithm recursively.
     *
     * @param myArray the given array
     * @param l the left boundary of the array
     * @param r the right boundary of the array
     * @param kth the kth smallest element to find
     * @return the kth smallest element
     */
    public static int Algorithm2(int[] myArray, int l, int r, int kth) {
        if (kth > 0 && kth <= r - l + 1) {
            int position = partition(myArray, l, r);
            if (position-l == kth-1) {
                return myArray[position];
            }
            if (position-l > kth-1) {
                return Algorithm2(myArray, l, position-1, kth);
            }
            return Algorithm2(myArray, position+1, r, kth-position+l-1);
        }
        return Integer.MAX_VALUE;
    }


    /**
     * Algorithm 3: find the kth smallest element in the list using partition procedure of Quicksort
     * recursively via Medians of Medians (mm)
     *
     * @param myArray the given array
     * @param low the left boundary of the array
     * @param high the right boundary of the array
     * @param kth the kth smallest element to find
     * @return the kth smallest element
     */
    public static int Algorithm3(int[] myArray, int low, int high, int kth) {
        if (kth > 0 && kth <= high - low + 1) {
            int pivot = medianOfMedians(myArray, low, high);
            int position = partitionWithPivot(myArray, low, high, pivot);

            if (position-low == kth-1) {
                return myArray[position];
            } else if (position-low > kth-1) {
                return Algorithm3(myArray, low, position-1, kth);
            } else {
                return Algorithm3(myArray, position + 1, high, kth-position+low-1);               
            }
        }
        return Integer.MAX_VALUE;
    }

    /**
     * helper function to find the median of medians as pivot
     *
     * @param myArray the given array
     * @param low the left boundary of the array
     * @param high the right boundary of the array
     * @return the median of medians
     */
    public static int medianOfMedians(int[] myArray, int low, int high) {
        if (high <= low) {
            return myArray[low];
        }
        int numOfMedians = (high-low+1)/5;
        if ((high-low+1) % 5 != 0) {
            numOfMedians++;
        }
        int[] medians = new int[numOfMedians];
        int medianIndex = 0;
        for (int i = low; i <= high; i += 5) {
            int subHigh = i+4;
            if (subHigh > high) {
                subHigh = high;
            }
            Arrays.sort(myArray, i, subHigh+1);
            int median = myArray[i+(subHigh-i)/2];
            medians[medianIndex++] = median;
        }
        return medianOfMedians(medians, 0, medians.length-1);
    }

    /**
     * helper function to partition the array with given pivot
     *
     * @param myArray the given array
     * @param low the left boundary of the array
     * @param high the right boundary of the array
     * @param pivot the pivot element
     * @return the partition index
     */
    public static int partitionWithPivot(int[] myArray, int low, int high, int pivot) {
        int i = low;
        for (int j = low; j <= high; j++) {
            if (myArray[j] == pivot) {
                int temp = myArray[high];
                myArray[high] = myArray[j];
                myArray[j] = temp;
                break;
            }
        }
        for (int j = low; j < high; j++) {
            if (myArray[j] <= pivot) {
                int temp = myArray[i];
                myArray[i] = myArray[j];
                myArray[j] = temp;
                i++;
            }
        }
        int temp = myArray[i];
        myArray[i] = myArray[high];
        myArray[high] = temp;
        return i;
    }

}

