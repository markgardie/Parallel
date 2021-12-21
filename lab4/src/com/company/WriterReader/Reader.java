package com.company.WriterReader;

public class Reader implements Runnable {
    private ReadWriteLock RW_lock;


    public Reader(ReadWriteLock rw) {
        RW_lock = rw;
    }
    public void run() {
        while (RW_lock.keepWorking()){
            try {
                RW_lock.readLock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
