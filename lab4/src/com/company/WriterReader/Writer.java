package com.company.WriterReader;

public class Writer implements Runnable {
    private ReadWriteLock RW_lock;


    public Writer(ReadWriteLock rw) {
        RW_lock = rw;
    }

    public void run() {
        while (RW_lock.keepWorking()){
            try {
                RW_lock.writeLock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
