package main;

import main.util.SinglePoint;

import java.util.ArrayList;
import static main.util.Algorithms.*;

public class Main {

    public static void main(String[] args) {
        
        // repeat for however long defined in Algorithms.java
        for(int i = 0; i < REPEAT; i++) {
            doThis(); // 5 time entries for each list
        }

        printTimeLists();
    }

    public static void doThis() {

        ArrayList<SinglePoint> finishedList = genList(LIST_SIZE);
        ArrayList<SinglePoint> copyOfFinishedList = new ArrayList<>(finishedList); // this one is for the default sort algorithm

        // random (index, value) pair in the list, so that we know linear search for both unsorted and sorted lists can find them
        // For "binary search" - inserting the element beforehand is necessary as sorting will cause problems if entered after

        long randomNumberForSearch = getRandomNumber(MIN_RAND, MAX_RAND); // random number has to be between the ranges
        long randIndexForSearch = getRandomNumber(0, LIST_SIZE); // random index between 0 and the list size

        // LINEAR SEARCH ON AN UNSORTED LIST ("finishedList") - WHERE ELEMENT EXISTS
        finishedList.add((int) randIndexForSearch, new SinglePoint(randomNumberForSearch)); 
        double linFoundStartTime = System.nanoTime();
        linearSearch(finishedList, randomNumberForSearch);
        double linFoundEndTime = System.nanoTime();
        beforeSortLinExistTimes.add(nanoToMillis(linFoundStartTime, linFoundEndTime)); // add the times to the specified time list


        // LINEAR SEARCH ON AN UNSORTED LIST ("finishedList") - WHERE ELEMENT DOES NOT EXIST
        double linNotFoundStartTime = System.nanoTime();
        linearSearch(finishedList, -100); 
        double linNotFoundEndTime = System.nanoTime();
        beforeSortLinNonExistTimes.add(nanoToMillis(linNotFoundStartTime, linNotFoundEndTime));


        // INSERTION SORT ON "finishedList" - which is unsorted
        double startInsertionSortTime = System.nanoTime();
        insertionSort(finishedList);
        double endInsertionSortTime = System.nanoTime();
        insertionSortTimes.add(nanoToMillis(startInsertionSortTime, endInsertionSortTime));


        // inserting the element before sort
        copyOfFinishedList.add((int) randIndexForSearch, new SinglePoint(randomNumberForSearch));

        
        // JAVA DEFAULT SORTING - ON UNSORTED LIST ("copyOfFinishedList")
        double startDefaultSortTime = System.nanoTime();
        defaultSort(copyOfFinishedList); 
        double endDefaultSortTime = System.nanoTime();
        defaultSortTimes.add(nanoToMillis(startDefaultSortTime, endDefaultSortTime));


        // LINEAR SEARCH ON SORTED LIST "copyOfFinishedList" - Element *EXISTS*
        double linSortExistStart = System.nanoTime();
        linearSearch(copyOfFinishedList, randomNumberForSearch); // linear searching for the randomValue again, now at a different index
        double linSortExistEnd = System.nanoTime();
        afterSortLinExistTimes.add(nanoToMillis(linSortExistStart, linSortExistEnd));


        // LINEAR SEARCH ON SORTED LIST "copyOfFinishedList" - Element *DOES NOT EXIST*
        double linSortNonStart = System.nanoTime();
        linearSearch(copyOfFinishedList, -100);
        double linSortNonEnd = System.nanoTime();
        afterSortLinNonExistTimes.add(nanoToMillis(linSortNonStart, linSortNonEnd));


        // BINARY SEARCH - SORTED LIST "copyOfFinishedList" - ELEMENT EXISTS
        double binSortExistStart = System.nanoTime();
        binarySearch(copyOfFinishedList, randomNumberForSearch); // now that the randValue is sorted, we can easily find it - instead of randomly inserting an element
        double binSortExistEnd = System.nanoTime();
        afterSortBinExistTimes.add(nanoToMillis(binSortExistStart, binSortExistEnd));


        // BINARY SEARCH - SORTED LIST "copyOfFinishedList" - ELEMENT DOES NOT EXIST
        double binSortNonExistStart = System.nanoTime();
        binarySearch(copyOfFinishedList, -100); // same element - should take less time
        double binSortNonExistEnd = System.nanoTime();
        afterSortBinNonExistTimes.add(nanoToMillis(binSortNonExistStart, binSortNonExistEnd));

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
