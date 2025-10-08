package com.naumen;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class MinAbsValueTask implements TaskRunnable {
    private static final Scanner scanner = new Scanner(System.in);
    private int[] randomValuesArray;

    private void getMinAbsValue() {
        var isSuccess = initializeArray();
        while (!isSuccess) {
            System.out.println("Try again");
            isSuccess = initializeArray();
        }
        System.out.println(findMinAbsValueResult());
    }

    private boolean initializeArray() {
        System.out.println("[Minabs] Print number for array size init");
        int n = scanner.nextInt();
        if (checkCorrectInput(n)) {
            if (n == 0) {
                System.out.println("Array is empty");
                return false;
            }
            randomValuesArray = loadArrayWithRandomValues(n);
            return true;
        } else {
            System.out.println("n must be in [ 0 ; inf ) ");
            return false;
        }
    }

    private int[] loadArrayWithRandomValues(int n) {
        Random random = new Random();
        return random.ints(n, -100, 101)
                .toArray();
    }

    private int findMinAbsValue(int[] array) {
        return Arrays.stream(array)
                .reduce((a,b) ->
                        Math.abs(a) <= Math.abs(b) ? a : b)
                .orElseThrow(() ->
                        new IllegalArgumentException("array size must be > 0"));
    }

    private MinAbsValueFinderResult findMinAbsValueResult() {
        return new MinAbsValueFinderResult(findMinAbsValue(randomValuesArray), Arrays.toString(randomValuesArray));
    }

    private boolean checkCorrectInput(int n) {
        return n >= 0;
    }

    private record MinAbsValueFinderResult(int minAbsValue, String randomValuesArrayToString) {
        @Override
        public String toString() {
            return String.format("MinAbsValueFinderResult: \n minAbsValue: %s \n randomValuesArrayToString: %s",
                    minAbsValue, randomValuesArrayToString);
        }
    }

    @Override
    public void run() {
        getMinAbsValue();
    }
}