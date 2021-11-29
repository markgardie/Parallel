package com.company;

public class CPU implements Runnable{

    private static final int MIN_DURATION = 50;         // time duration of process service or CPU speed
    private static final int MAX_DURATION = 250;
    private static final int TIME_SLOT = 1000;           // simulates operation system minimum  time slot

    private static int counter = 0; //additional var for setting id`s


    private final int id = counter++; //cpu unique id
    private final int time;                             // duration of process service
    private boolean busy; //is cpu busy
    private Process process; //process that cpu is processing
    private Process lost; // lost process

    /**
     * Constructor of CPU
     * creates time duration of any process service
     * creates stack for interrupted processes
     */
    public CPU() {
        this.time = (int) (Math.random() * (MAX_DURATION - MIN_DURATION) + MIN_DURATION);
        busy = false;
    }

    /**
     * Sets Process to perform processing or exit without ones
     */
    public synchronized void setTask(Process p) {
        setProcess(p);
        setBusy(true);
    }

    public synchronized void setProcess(Process process) {
        this.process = process;
    }

    public synchronized void setBusy(boolean busy) {
        this.busy = busy;
    }

    public synchronized boolean isBusy() {
        return busy;
    }


    /**
     * Copy process to lost for restoring in queue
     */
    public synchronized void setLost(Process p) {
        lost = p;
    }

    /**
     * Returns lost process or null
     *
     * @return process or null is stack is empty
     */
    public synchronized Process getLost() {
        return lost;
    }

    /**
     * Returns current process
     *
     * @return
     */
    public synchronized Process getProcess() {
        if (busy && process != null) {
            return process;
        }
        return null;
    }

    /**
     * Main loop of CPU
     * Runs till process queue is not empty
     */
    @Override
    public void run() {
        System.out.println(this + " started");
        while (!Thread.interrupted()) {
            try {
                if (busy) {                             // request for processing
                    if (process == null) {
                        throw new IllegalArgumentException("something wrong with CPU:" + this);
                    }
                    System.out.println(this + " started processing of:" + process);

                    Thread.sleep(time);                   // simulates processing
                    System.out.println(this + " finished processing of:" + process);
                    setProcess(null);  // remove process
                    busy = false;
                }
                tick();
                System.out.println(this + " tick");
            } catch (InterruptedException e) {
                if(process == null) {
                    System.out.println(this+" request to exit received");
                    break;
                }
                setLost(process);                      // store for queue
            }
        }

        System.out.println(this + " finished");
    }

    private void tick() throws InterruptedException {
        Thread.sleep(TIME_SLOT);
    }


    @Override
    public String toString() {
        return "CPU:" + id +
                " time:" + time;
    }
}
