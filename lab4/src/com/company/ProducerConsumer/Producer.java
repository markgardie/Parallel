package com.company.ProducerConsumer;

import java.util.Random;

public class Producer implements Runnable {
    private Queue queue;
    private Random randomGenerator = new Random();

    public Producer(Queue queue) {
        this.queue = queue;
        new Thread(this, "Producer").start();
    }

    public void run()
    {
        for (int i = 0; i < randomGenerator.nextInt(100); i++)
            // producer put items
            queue.put(i);
    }
}
