package com.company;

public class CPU extends Thread {
    private static int counter = 0;
    private final int id = counter++;
    private final int time;
    private boolean busy;
    private Process process;

    public CPU(int max_duration, int min_duration){
        this.time = (int) (Math.random() * ((max_duration - min_duration) + min_duration));
        this.busy = false;
    }

    public synchronized void setBusy(boolean busy) {
        this.busy = busy;
    }

    public synchronized boolean isBusy() {
        return busy;
    }

    public synchronized void setTask(Process p) {
        setProcess(p);
        setBusy(true);
    }

    public synchronized void setProcess(Process process) {
        this.process = process;
    }

    public synchronized Process getProcess() {
        if (busy && process != null) {
            return process;
        }
        return null;
    }

    public void run() {
        System.out.println(this + " started");
        while (!Thread.interrupted()) {
            try {
                if (busy) {
                    if (process == null) {
                        throw new IllegalArgumentException("something wrong with CPU:" + this);
                    }
                    System.out.println(this + " started processing of:" + process);

                    Thread.sleep(time);
                    System.out.println(this + " finished processing of:" + process);
                    busy = false;
                    setProcess(null);

                }
            } catch (InterruptedException e) {
                if(process == null) {
                    System.out.println(this+" request to exit received");
                    break;
                }
            }
        }

        System.out.println(this + " finished");
    }

    @Override
    public String toString() {
        return "CPU:" + id +
                " time:" + time;
    }
}
