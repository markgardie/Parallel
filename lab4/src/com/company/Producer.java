package com.company;

public class Producer implements Runnable {
    Queue queue;

    Producer(Queue queue) {
        this.queue = queue;
        new Thread(this, "Producer").start();
    }

    public void run()
    {
        for (int i = 0; i < 5; i++)
            // producer put items
            queue.put(i);
    }
}
