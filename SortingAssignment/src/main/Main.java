package main;

import main.util.SinglePoint;

import java.util.ArrayList;
import static main.util.Algorithms.*;

public class Main {

    public static void main(String[] args) {


        for(int i = 0; i < REPEAT; i++) {
            doThis(); // should be 5 "time" entries entered for each time list
        }

        printTimeLists();
    }

    public static void doThis() {

        ArrayList<SinglePoint> finishedList = genList(LIST_SIZE);
        ArrayList<SinglePoint> copyOfFinishedList = new ArrayList<>(finishedList); // this one is for the default sort algorithm

        // we put a random (index, value) pair in the list, so that we know linear search for both unsorted and sorted lists can find them
        // also for binary search, we can find this, but we need to insert the element into the list we will sort beforehand
        // we can't just put any value with the index randomly in a SORTED list, because well... its sorted already.

        long randomNumberForSearch = getRandomNumber(MIN_RAND, MAX_RAND); // random number has to be between the ranges
        long randIndexForSearch = getRandomNumber(0, LIST_SIZE); // random index between 0 and the list size

        // LINEAR SEARCH ON AN UNSORTED LIST ("finishedList") - WHERE ELEMENT EXISTS
        finishedList.add((int) randIndexForSearch, new SinglePoint(randomNumberForSearch)); // {SinglePoint -> random x value} at random index
        double linFoundStartTime = System.nanoTime();
        linearSearch(finishedList, randomNumberForSearch); // returns index position of object (if found) - in this case yes
        double linFoundEndTime = System.nanoTime();
        beforeSortLinExistTimes.add(nanoToMillis(linFoundStartTime, linFoundEndTime)); // add the times to the specified time list
//        System.out.println(
//
//                // time took to run linear search on an un-sorted list - where element is found
//                nanoToMillis(linFoundStartTime, linFoundEndTime)
//        );

        // LINEAR SEARCH ON AN UNSORTED LIST ("finishedList") - WHERE ELEMENT DOES NOT EXIST
        double linNotFoundStartTime = System.nanoTime();
        linearSearch(finishedList, -100); // returns -1 because element does not exist
        double linNotFoundEndTime = System.nanoTime();
        beforeSortLinNonExistTimes.add(nanoToMillis(linNotFoundStartTime, linNotFoundEndTime));
//        System.out.println(
//
//                // time took to run linear search on a non-sorted list - where element is NOT found
//                nanoToMillis(linNotFoundStartTime, linNotFoundEndTime)
//        );

        // INSERTION SORT ON "finishedList" - which is unsorted
        double startInsertionSortTime = System.nanoTime();
        insertionSort(finishedList);
        double endInsertionSortTime = System.nanoTime();
        insertionSortTimes.add(nanoToMillis(startInsertionSortTime, endInsertionSortTime));
//        System.out.println(
//
//                // time took to run insertion sort on unsorted list ("finishedList")
//                nanoToMillis(startInsertionSortTime, endInsertionSortTime)
//        );

        // we need to sort our (randIndexForSearch, randValue) pair and add it to copyOfFinishedList, so that the binary search can work
        // this is before sorting
        copyOfFinishedList.add((int) randIndexForSearch, new SinglePoint(randomNumberForSearch));

        // now sorting can continue
        // JAVA DEFAULT SORTING - ON UNSORTED LIST ("copyOfFinishedList")
        double startDefaultSortTime = System.nanoTime();
        defaultSort(copyOfFinishedList); // this list passed here is unchanged and unaffected by insertion sort performed on "finishedList"
        double endDefaultSortTime = System.nanoTime();
        defaultSortTimes.add(nanoToMillis(startDefaultSortTime, endDefaultSortTime));
//        System.out.println(
//
//                // time it took to run java default sorting algorithm on unsorted list ("copyOfFinishedList")
//                nanoToMillis(startDefaultSortTime, endDefaultSortTime)
//        );

        // LINEAR SEARCH ON SORTED LIST "copyOfFinishedList" - Element *EXISTS*
        double linSortExistStart = System.nanoTime();
        linearSearch(copyOfFinishedList, randomNumberForSearch); // linear searching for the randomValue again, now at a different index
        double linSortExistEnd = System.nanoTime();
        afterSortLinExistTimes.add(nanoToMillis(linSortExistStart, linSortExistEnd));
//        System.out.println(
//
//                // time it took to run linear search on the sorted list "copyOfFinishedList" - where the element exists
//                nanoToMillis(linSortExistStart, linSortExistEnd)
//        );


        // LINEAR SEARCH ON SORTED LIST "copyOfFinishedList" - Element *DOES NOT EXIST*
        double linSortNonStart = System.nanoTime();
        linearSearch(copyOfFinishedList, -100);
        double linSortNonEnd = System.nanoTime();
        afterSortLinNonExistTimes.add(nanoToMillis(linSortNonStart, linSortNonEnd));
//        System.out.println(
//
//                // time it took to run linear search on the sorted list "copyOfFinishedList" - where the element DOES NOT exist
//                nanoToMillis(linSortNonStart, linSortNonEnd)
//        );

        // BINARY SEARCH - SORTED LIST "copyOfFinishedList" - ELEMENT EXISTS
        double binSortExistStart = System.nanoTime();
        binarySearch(copyOfFinishedList, randomNumberForSearch); // now that the randValue is sorted, we can easily find it - instead of randomly inserting an element
        double binSortExistEnd = System.nanoTime();
        afterSortBinExistTimes.add(nanoToMillis(binSortExistStart, binSortExistEnd));
//        System.out.println(
//
//                // time it took to run binary search on sorted list "copyOfFinishedList" - where element exists
//                nanoToMillis(binSortExistStart, binSortExistEnd)
//        );

        // BINARY SEARCH - SORTED LIST "copyOfFinishedList" - ELEMENT DOES NOT EXIST
        double binSortNonExistStart = System.nanoTime();
        binarySearch(copyOfFinishedList, -100); // same element - should take less time
        double binSortNonExistEnd = System.nanoTime();
        afterSortBinNonExistTimes.add(nanoToMillis(binSortNonExistStart, binSortNonExistEnd));
//        System.out.println(
//
//                // time it took to run binary search on sorted list "copyOfFinishedList" - where element *DOES NOT* exists
//                nanoToMillis(binSortNonExistStart, binSortNonExistEnd)
//        );
    }

    // for debugging
    public static void printTimeLists() {
        System.out.println("Before sorting linear search exist times: " + beforeSortLinExistTimes);
        System.out.println("Before sorting linear search not exist times: " + beforeSortLinNonExistTimes);
        System.out.println("Insertion sort times: " + insertionSortTimes);
        System.out.println("Default sort times: " + defaultSortTimes);
        System.out.println("After sorting linear search exist times: " + afterSortLinExistTimes);
        System.out.println("After sorting linear search not exist times: " + afterSortLinNonExistTimes);
        System.out.println("After sorting binary search exist times: " + afterSortBinExistTimes);
        System.out.println("After sorting binary search not exist times: " + afterSortBinNonExistTimes);
    }
}