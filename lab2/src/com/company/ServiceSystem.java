package com.company;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class ServiceSystem {
    private static final int NUMBER_OF_PROCESS_THREADS = 1;
    private static final int NUMBER_OF_PROCESS = 10;
    private static final int MIN_DURATION = 50;
    private static final int MAX_DURATION = 250;
    private static final int MIN_TIME_TO_NEXT = 10;
    private static final int MAX_TIME_TO_NEXT = 500;

    private final ArrayList<CPU> cpus;
    private final ProcessThread[] process_threads;

    public ServiceSystem(){

        cpus = new ArrayList<CPU>();
        cpus.add(new CPU(MAX_DURATION, MIN_DURATION));

        process_threads = new ProcessThread[NUMBER_OF_PROCESS_THREADS];
        for (int i = 0; i < NUMBER_OF_PROCESS_THREADS; i++) {
            process_threads[i] = new ProcessThread(NUMBER_OF_PROCESS, MAX_TIME_TO_NEXT, MIN_TIME_TO_NEXT);
        }
    }

    public void runThreads() {
        cpus.get(0).start();

        for (int i = 0; i < NUMBER_OF_PROCESS_THREADS; i++) {
            process_threads[i].start();
        }
    }

    public boolean isAlive() {
        for (int i = 0; i < NUMBER_OF_PROCESS_THREADS; i++) {
            if (process_threads[i].isAlive()) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        ServiceSystem ss = new ServiceSystem();
        ss.runThreads();

        ProcessThread processThread = ss.process_threads[0];
        ArrayList<Process> processes = processThread.getAllProcesses();
        ArrayList<CPU> cpus = ss.cpus;
        Process p;

        while (processThread.isAlive()) {
            if(!processes.isEmpty()) {
                p = processes.remove(processes.size() - 1);
                for (int i = 0; i < cpus.size(); i++) {
                    if (!cpus.get(i).isBusy()) {
                        cpus.get(i).setTask(p);
                    }
                    else {
                        if (i+2 > cpus.size()) {
                            cpus.add(new CPU(MAX_DURATION, MIN_DURATION));
                            cpus.get(cpus.size() - 1).start();
                        }
                    }
                }
            }

        }

        System.out.println("To process " + NUMBER_OF_PROCESS + " processes, program needed " + cpus.size() + " cpus");

        for (int i = 0; i < cpus.size(); i++) {
            System.out.println(cpus.get(i) + " processed " + cpus.get(i).getProcessed().size() + " processes");
        }
    }

}
