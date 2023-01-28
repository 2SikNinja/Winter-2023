import java.util.Arrays;

public class projecttwo {

    public static int Algorithm1(int[] myArray, int size, int kth) {
        Arrays.sort(myArray);
        return myArray[kth-1];
    }

    public static int partition2(int[] myArray, int l, int r) {
        int x = myArray[r], i = l;
        for (int j = l; j <= r - 1; j++) {
            if (myArray[j] <= x) {
                int temporary = myArray[i];
                myArray[i] = myArray[j];
                myArray[j] = temporary;
                i++;
            }
        }
        int temporary = myArray[i];
        myArray[i] = myArray[r];
        myArray[r] = temporary;
        return i;
    }

    public static int Algorithm2a(int[] myArray, int low, int high, int kth) {
        if (kth > 0 && kth <= high - low + 1) {
            int position = partition2(myArray, low, high);
            if (position-low == kth-1) {
                return myArray[position];
            } else if (position-low > kth-1) {
                return Algorithm2a(myArray, low, position-1, kth);
            } else {
                return Algorithm2a(myArray, position + 1, high, kth-position+1-1);
            }
        }
        return Integer.MAX_VALUE;
    }

    public static int Algorithm2b(int[] myArray, int l, int r, int kth) {
        if (kth > 0 && kth <= r - l + 1) {
            int position = partition2(myArray, l, r);
            if (position-l == kth-1) {
                return myArray[position];
            }
            if (position-l > kth-1) {
                return Algorithm2b(myArray, l, position-1, kth);
            }
            return Algorithm2b(myArray, position+1, r, kth-position+l-1);
        }
        return Integer.MAX_VALUE;
    }

    public static int findMedian3(int[] myArray, int size) {
        Arrays.sort(myArray);
        return myArray[size/2];
    }

    public static int partition3(int[] myArray, int l, int r, int kth) {
        // todo: implement partition3
        return 0;
    }

    public static int Algorithm3(int[] myArray, int l, int r, int kth) {
        // todo: implement Algorithm3
        return 0;
    }
}
