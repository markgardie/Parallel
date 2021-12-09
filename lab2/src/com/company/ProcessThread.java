package com.company;

import java.util.ArrayList;

public class ProcessThread extends Thread{
    private static int counter = 0;
    private final int id = counter++;
    private final int processes_amount;
    private boolean finished;
    private final int max_time_to_next;
    private final int min_time_to_next;
    private final ArrayList<Process> processes;


    public ProcessThread(int processes_amount, int max_time_to_next, int min_time_to_next) {
        if(processes_amount <= 0) {
            throw new IllegalArgumentException();
        }
        this.processes_amount = processes_amount;
        this.max_time_to_next = max_time_to_next;
        this.min_time_to_next = min_time_to_next;
        this.processes = new ArrayList<Process>();
    }

    public Process generateProcess(){
        int timeToNext = (int) (Math.random() * (max_time_to_next - min_time_to_next) + min_time_to_next);
        return new Process(timeToNext, id);
    }


    public synchronized ArrayList<Process> getAllProcesses(){
        return processes;
    }

    public synchronized int _getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("ProcessThread:%2d",id);
    }

    @Override
    public void run() {
        System.out.println(this + " started");
        try {

            for (int i = 0; i < processes_amount; i++) {
                Process p = generateProcess();
                processes.add(p);
                System.out.println(String.format("%s  %s time:%4d",p,this, p.getTime()));

                Thread.sleep(p.getTime());
            }

        } catch (InterruptedException e) {
            System.out.println(this + " interrupted");
        }
        System.out.println(this + " finished");

    }

}
