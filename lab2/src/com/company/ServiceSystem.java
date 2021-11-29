package com.company;

public class ServiceSystem {
    private static final int NUMBER_OF_CPU = 10;
    private static final int NUMBER_OF_PROCESS_THREADS = 1;
    private static final int NUMBER_OF_PROCESS = 15;
    private static final int MIN_DURATION = 50;
    private static final int MAX_DURATION = 250;
    private static final int MIN_TIME_TO_NEXT = 10;
    private static final int MAX_TIME_TO_NEXT = 500;

    private final CPU[] cpus;
    private final ProcessThread[] process_threads;

    public ServiceSystem(){

        cpus = new CPU[NUMBER_OF_CPU];
        for (int i = 0; i < NUMBER_OF_CPU; i++) {
            cpus[i] = new CPU(MAX_DURATION, MIN_DURATION);
        }

        process_threads = new ProcessThread[NUMBER_OF_PROCESS_THREADS];
        for (int i = 0; i < NUMBER_OF_PROCESS_THREADS; i++) {
            process_threads[i] = new ProcessThread(NUMBER_OF_PROCESS, MAX_TIME_TO_NEXT, MIN_TIME_TO_NEXT);
        }
    }

    private void runThreads() {
        for (int i = 0; i < NUMBER_OF_CPU; i++) {
            cpus[i].start();
        }

        for (int i = 0; i < NUMBER_OF_PROCESS_THREADS; i++) {
            process_threads[i].start();
        }
    }


}
