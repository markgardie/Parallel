package com.company;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
public class Main {
    public static void main(String[] args) {
        int[] array = {4, 10, 1, 5, 11, 6, 8, 3, 5, 5, 6};
        System.out.println(findMin(array).get());
        System.out.println(findMax(array).get());
        System.out.println(findElem(array, 6).get());
    }

    public static AtomicInteger findMin(int[] array) {
        AtomicInteger atomicMin = new AtomicInteger();
        atomicMin.set(array[0]);
        IntStream.of(array).parallel().forEach( x -> {
            int oldValue;
            int newValue;
            do {
                oldValue = atomicMin.get();
                if (x < oldValue) {
                    newValue = x;
                }
                else {
                    newValue = oldValue;
                }
            }while (!atomicMin.compareAndSet(oldValue, newValue));
        });

        return atomicMin;
    }

    public static AtomicInteger findMax(int[] array) {
        AtomicInteger atomicMax = new AtomicInteger();
        atomicMax.set(array[0]);
        IntStream.of(array).parallel().forEach(x -> {
            int oldValue;
            int newValue;
            do {
                oldValue = atomicMax.get();
                if (x > oldValue){
                    newValue = x;
                }
                else {
                    newValue = oldValue;
                }

            }while (!atomicMax.compareAndSet(oldValue, newValue));
        });

        return atomicMax;
    }

    public static AtomicInteger findElem(int[] array, int condition) {
        AtomicInteger atomicElemCount = new AtomicInteger();
        atomicElemCount.set(0);
        IntStream.of(array).parallel().forEach(x -> {
            int oldValue;
            int newValue;
            do {
                oldValue = atomicElemCount.get();
                if (x == condition) {
                    newValue = oldValue + 1;
                }
                else {
                    newValue = oldValue;
                }

            }while (!atomicElemCount.compareAndSet(oldValue, newValue));

        });
        return atomicElemCount;
    }
}
