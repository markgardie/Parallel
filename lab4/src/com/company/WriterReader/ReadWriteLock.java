package com.company.WriterReader;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

public class ReadWriteLock {
    static Semaphore readLock = new Semaphore(1);
    static Semaphore writeLock = new Semaphore(1);
    volatile static int readCount = 0;
    private final AtomicBoolean keep_working = new AtomicBoolean(true);

    public void readLock() throws InterruptedException {

        readLock.acquire();
        readCount++;
        if (readCount == 1) {
            writeLock.acquire();
        }
        readLock.release();

        //Reading section
        System.out.println("Thread "+Thread.currentThread().getName() + " is READING");
        Thread.sleep(1500);
        System.out.println("Thread "+Thread.currentThread().getName() + " has FINISHED READING");

        //Releasing section
        readLock.acquire();
        readCount--;
        if(readCount == 0) {
            writeLock.release();
        }
        readLock.release();
    }

    public void writeLock() throws InterruptedException {
        writeLock.acquire();
        System.out.println("Thread "+Thread.currentThread().getName() + " is WRITING");
        Thread.sleep(2500);
        System.out.println("Thread "+Thread.currentThread().getName() + " has finished WRITING");
        writeLock.release();

    }

    public void stopThreads(){
        keep_working.set(false);
    }

    public boolean keepWorking(){
        return keep_working.get();
    }
}
