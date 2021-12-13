package com.company.ProducerConsumer;

public class Producer implements Runnable {
    private Queue queue;
    private static final int RANDOM = (int) (Math.random()*(100 - 1) + 1);

    public Producer(Queue queue) {
        this.queue = queue;
        new Thread(this, "Producer").start();
    }

    public void run()
    {
        for (int i = 0; i < RANDOM; i++)
            // producer put items
            queue.put(i);
    }
}
