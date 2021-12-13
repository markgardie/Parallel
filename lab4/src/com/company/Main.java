package com.company;

import com.company.ProducerConsumer.Consumer;
import com.company.ProducerConsumer.Producer;
import com.company.ProducerConsumer.Queue;

public class Main {

    public static void main(String[] args) {
	    producerConsumer();
    }

    public static void producerConsumer() {
        // creating buffer queue
        Queue queue = new Queue();

        // starting consumer thread
        new Consumer(queue);

        // starting producer thread
        new Producer(queue);
    }
}
