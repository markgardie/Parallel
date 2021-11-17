package com.company;

public class CPUProcessFlow implements Runnable {

    private static int counter = 0; //additional var for setting id`s
    private final int id = counter++; //process flow unique id
    private static final int MIN_TIME_TO_NEXT = 10;     // time to next process started
    private static final int MAX_TIME_TO_NEXT = 500;
    private final int n; //number of processes to generate
    private boolean finished; //if flow is finished

    /**
     * Constructor
     */
    public CPUProcessFlow(int n) {
        if(n <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;

    }

    /**
     * Creates new Process with time to next and duration parameters
     * Generates random time to start next process
     *
     * @return generated process object
     */
    public CPUProcess genProcess() {
        int timeToNext = (int) (Math.random() * (MAX_TIME_TO_NEXT - MIN_TIME_TO_NEXT) + MIN_TIME_TO_NEXT);
        return new CPUProcess(timeToNext, id);
    }

    /**
     * Generates  n number of processes
     * waits process.getTime() milliseconds after
     * generated process
     */
    @Override
    public void run() {
        for (int i = 0; i < n; i++) {
            CPUProcess p = genProcess();
            System.out.println(String.format("%s  %s time:%4d",p,this, p.getTime()));

            //Thread.sleep(p.getTime());
        }


        System.out.println(this + " finished");
    }

    /**
     * Getter
     */
    public synchronized int getId() {
        return id;
    }

    /**
     * Formatting method
     */
    @Override
    public String toString() {
        return String.format("ProcessFlow:%2d",id);
    }
}
