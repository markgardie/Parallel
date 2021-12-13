package com.company.ProducerConsumer;

import java.util.Random;

public class Consumer implements Runnable {
    private Queue queue;
    private Random randomGenerator = new Random();

    public Consumer(Queue queue)
    {
        this.queue = queue;
        new Thread(this, "Consumer").start();
    }

    public void run()
    {
        for (int i = 0; i < randomGenerator.nextInt(100); i++)
            // consumer get items
            queue.get();
    }
}
