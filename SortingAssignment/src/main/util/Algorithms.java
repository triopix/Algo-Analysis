package main.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public final class Algorithms {

    public static final long LIST_SIZE = 75000; // size of the list
    public static final long MIN_RAND = 10; // min bound
    public static final long MAX_RAND = 1000; // max bound

    public static final double REPEAT = 5; // trials
    private static final double CONVERSION_FACTOR = 1e6;

    // cannot be instantiated - just use methods/variables

    // time lists - 8
    public static ArrayList<Double> beforeSortLinExistTimes = new ArrayList<>();
    public static ArrayList<Double> beforeSortLinNonExistTimes = new ArrayList<>();
    public static ArrayList<Double> insertionSortTimes = new ArrayList<>();
    public static ArrayList<Double> defaultSortTimes = new ArrayList<>();
    public static ArrayList<Double> afterSortLinExistTimes = new ArrayList<>();
    public static ArrayList<Double> afterSortLinNonExistTimes = new ArrayList<>();
    public static ArrayList<Double> afterSortBinExistTimes = new ArrayList<>();
    public static ArrayList<Double> afterSortBinNonExistTimes = new ArrayList<>();
    private Algorithms() {}

    // insertion sort
    public static ArrayList<SinglePoint> genList(long size) {
        ArrayList<SinglePoint> list = new ArrayList<>();
        for(long i = 0; i < size; i++) {
            list.add(new SinglePoint(getRandomNumber(MIN_RAND, MAX_RAND)));
        }
        return list;
    }

    public static ArrayList<SinglePoint> insertionSort(ArrayList<SinglePoint> list) {
        for(long i = 1; i < list.size(); i++) {
            long j = i;
            // in the while condition - we already compare the x values
            while((j > 0) && (list.get((int) (j - 1)).getX() > list.get((int) (j)).getX())) {
                // took a while to troubleshoot this!
                Collections.swap(list, (int) j-1, (int) j); // just swap the entire statements instead of the values
                j--;
            }
        }
        return list;
    }

    public static ArrayList<SinglePoint> defaultSort(ArrayList<SinglePoint> list) {
        // implementation is an interesting issue
        /*
        * https://stackoverflow.com/questions/15154158/why-collections-sort-uses-merge-sort-instead-of-quicksort
        *
        * There are variations to the fastest sort algo, including Collections.sort()
        * Arrays.sort() now uses timsort which is in python sorted() and sort() hybrid of insertion and merge sort
        * Collections.sort() uses merge sort which still caries worst case of o(nlogn)
        * */

        // using Collections.sort() will prove to be no problem for our cases
        Collections.sort(list);
        return list;
    }

    // Returns index, -1 if not returned
    public static int linearSearch(ArrayList<SinglePoint> list, long number) {
        for(SinglePoint d : list) {
            if(d.getX() == number) {
                return list.indexOf(d);
            }
        }
        return -1;
    }

    // Binary search for sorted lists only
    public static int binarySearch(ArrayList<SinglePoint> sortedList, long number) {
        int left = 0, right = sortedList.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            // Check if x is present at mid
            if (sortedList.get(mid).getX() == number)
                return mid;
            // If x greater, ignore left half
            if (sortedList.get(mid).getX() < number) {
                left = mid + 1;
            } else {
                /*If x is smaller, ignore right half */
                right = mid - 1;
            }
        }
        // If reached here, then element not present
        return -1;
    }

    // helper method to generate a random number using min and max range
    public static long getRandomNumber(long min, long max) {
        return ThreadLocalRandom.current().nextLong(min, max + 1);
    }

    // printing the time based on conversion factor - *note time's entered are nanoseconds
    // could consider making another method, which would allow to convert from nano to any time specified - different orders of magnitude

    public static double nanoToMillis(double start, double end) {
        return (end - start) / CONVERSION_FACTOR;
    }

}
