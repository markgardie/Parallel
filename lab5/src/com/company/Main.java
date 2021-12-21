package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class Main {
    private static final int NUM_ELEM = 5;
    private static int average = 0;


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<Integer> first = new ArrayList<>();
        List<Integer> second = new ArrayList<>();
        List<Integer> third = new ArrayList<>();
        List<Integer> finalList = new ArrayList<>();
        Random r = new Random();


        for (int i = 0; i < NUM_ELEM; i++) {
            first.add(r.nextInt(100));
            second.add(r.nextInt(100));
            third.add(r.nextInt(100));
        }

        for (int i = 0; i < NUM_ELEM; i++) {
            average += third.get(i);
        }

        average = average / NUM_ELEM;

        System.out.println("First List:");
        for (int i = 0; i < NUM_ELEM; i++) {

            System.out.print(first.get(i) + " ");
        }

        System.out.println();
        System.out.println("Second List:");
        for (int i = 0; i < NUM_ELEM; i++) {

            System.out.print(second.get(i) + " ");
        }

        System.out.println();
        System.out.println("Third List:");
        for (int i = 0; i < NUM_ELEM; i++) {

            System.out.print(third.get(i) + " ");

        }
        System.out.println();

        CompletableFuture<List<Integer>> mapFirst = CompletableFuture
                        .supplyAsync( () -> first.stream()
                                .map(x -> x * 3))
                        .thenApplyAsync( s-> s.sorted().collect(Collectors.toList()) );

        CompletableFuture<List<Integer>> mapSecond = CompletableFuture
                .supplyAsync( () -> second.stream()
                        .filter(x -> x % 2  == 0))
                .thenApplyAsync( s-> s.sorted().collect(Collectors.toList()) );

        CompletableFuture<List<Integer>> mapThird = CompletableFuture
                .supplyAsync( () -> third.stream()
                        .filter(x -> ((Math.random()*1.2) + 0.8) * average == x))
                .thenApplyAsync( s-> s.sorted().collect(Collectors.toList()) );

        CompletableFuture<List<Integer>> filterFinal = CompletableFuture
                .supplyAsync(() -> {
                    finalList.addAll(mapFirst.join());
                    finalList.addAll(mapSecond.join());
                    finalList.addAll(mapSecond.join());
                    return finalList;
                })
                .thenApplyAsync(s -> s.stream().sorted().collect(Collectors.toList()));

        System.out.println("Final List:");
        System.out.println(filterFinal.get());

    }
}
