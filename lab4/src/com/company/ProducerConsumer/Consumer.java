package com.company.ProducerConsumer;

public class Consumer implements Runnable {
    private Queue queue;
    private static final int RANDOM = (int) (Math.random()*(100 - 1) + 1);

    public Consumer(Queue queue)
    {
        this.queue = queue;
        new Thread(this, "Consumer").start();
    }

    public void run()
    {
        for (int i = 0; i < RANDOM; i++)
            // consumer get items
            queue.get();
    }
}
