package com.company;

public class CPUProcess implements Runnable {

    /** Process fields  **/
    private static int counter = 0; //additional var for setting id`s
    private int id; //process unique id
    private final int time;   // time to next process
    private final int flow; //flow id that generated this process

    /** Process Constructor **/
    public CPUProcess(int time, int flow) {
        this.time = time;
        this.flow = flow;
        setId();
    }

    /** Set unique id to any process **/
    private synchronized void setId() {
        id = counter++;
    }

    /** Method run for creating Thread **/
    @Override
    public void run() {

    }

    /** Class fields getters **/
    public int getId() {
        return id;
    }

    public int getTime() {
        return time;
    }

    public int getFlow() {
        return flow;
    }

    /** Formatting method **/
    @Override
    public String toString() {
        return String.format("Process:%2d flow:%2d time:%4d",id,flow,time);

    }

}
