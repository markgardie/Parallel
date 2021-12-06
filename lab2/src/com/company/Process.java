package com.company;

public class Process {
    private static int counter = 0;
    private int id;
    private final int time;
    private final int process_thread_id;

    public Process(int time, int process_thread_id) {
        this.time = time;
        this.process_thread_id = process_thread_id;
        setId();
    }

    private synchronized void setId() {
        id = counter++;
    }

    public int getId() {
        return id;
    }

    public int getTime() {
        return time;
    }

    public int getProcessThread() {
        return process_thread_id;
    }

    @Override
    public String toString() {
        return String.format("Process:%2d thread:%2d time:%4d",id,process_thread_id,time);

    }
}
