package com.company;

public class Consumer implements Runnable {
    Queue queue;
    Consumer(Queue queue)
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
