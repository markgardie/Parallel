package com.company.ProducerConsumer;

public class Consumer implements Runnable {
    Queue queue;
    public Consumer(Queue queue)
    {
        this.queue = queue;
        new Thread(this, "Consumer").start();
    }

    public void run()
    {
        for (int i = 0; i < 5; i++)
            // consumer get items
            queue.get();
    }
}
