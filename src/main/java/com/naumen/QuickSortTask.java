package com.naumen;

import java.util.*;
import java.util.stream.Collectors;

public class QuickSortTask implements TaskRunnable {
    private static final Scanner scanner = new Scanner(System.in);
    private List<Double> randomValuesList;

    private void quickSort() {
        boolean isSuccess = initializeArray();
        while (!isSuccess) {
            System.out.println("Try again");
            isSuccess = initializeArray();
        }
        System.out.println(getSortResult());
    }

    private void quickSort(ArrayList<Double> list) {
        if (list == null || list.size() <= 1) {
            return;
        }
        quickSort(list, 0, list.size() - 1);
    }

    private void quickSort(List<Double> randomValuesList, int left, int right) {
        if (left < right) {
            int pivotInd = partition(randomValuesList, left, right);
            quickSort(randomValuesList, pivotInd + 1, right);
            quickSort(randomValuesList, left,pivotInd - 1);
        }
    }

    private int partition(List<Double> randomValuesList, int left, int right) {
        var pivot = randomValuesList.get(right);
        var i = left - 1;
        for (int j = left; j < right; j++) {
            if (randomValuesList.get(j) < pivot) {
                i++;
                swap(randomValuesList, i, j);
            }
        }
        swap(randomValuesList, i + 1, right);
        return i + 1;
    }

    private void swap(List<Double> randomValuesList, int i, int j) {
        double temp = randomValuesList.get(i);
        randomValuesList.set(i, randomValuesList.get(j));
        randomValuesList.set(j, temp);
    }

    private boolean initializeArray() {
        System.out.println("[Quicksort] Print number for array size init");
        int n = scanner.nextInt();
        if (checkCorrectInput(n)) {
            if (n == 0) {
                System.out.println("Array is empty");
                return false;
            }
            randomValuesList = loadListWithRandomValues(n);
            return true;
        } else {
            System.out.println("n must be in [ 0 ; inf ) ");
            return false;
        }
    }

    private ArrayList<Double> loadListWithRandomValues(int n) {
        Random random = new Random();
        return random
                .doubles(n, -100, 101)
                .boxed()
                .collect(Collectors
                        .toCollection(ArrayList<Double>::new));
    }

    private boolean checkCorrectInput(int n) {
        return n >= 0;
    }

    private SortResult getSortResult() {
        ArrayList<Double> sorted = new ArrayList<>(randomValuesList);
        quickSort(sorted);
        return new SortResult(randomValuesList.toString(), sorted.toString());
    }

    @Override
    public void run() {
        quickSort();
    }

    private record SortResult(String originalList, String sortedList) {
        @Override
        public String toString() {
            return String.format("SortResult:\noriginalList: %s\nsortedList: %s", originalList, sortedList);
        }
    }
}