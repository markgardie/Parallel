package com.company;

import com.company.DiningPhilosopher.ChopStick;
import com.company.DiningPhilosopher.Philosopher;
import com.company.ProducerConsumer.Consumer;
import com.company.ProducerConsumer.Producer;
import com.company.ProducerConsumer.Queue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final int NUMBER_OF_PHILOSOPHER = 5;
    private static final int SIMULATION_MILLIS = 1000 * 10;

    public static void main(String[] args) throws InterruptedException {
	    //producerConsumer();
	    diningPhilosophers();
    }

    public static void producerConsumer() {
        // creating buffer queue
        Queue queue = new Queue();

        // starting consumer thread
        new Consumer(queue);

        // starting producer thread
        new Producer(queue);
    }

    public static void diningPhilosophers() throws InterruptedException {
        ExecutorService executorService = null;

        Philosopher[] philosophers = null;
        try {

            philosophers = new Philosopher[NUMBER_OF_PHILOSOPHER];

            //As many forks as Philosophers
            ChopStick[] chopSticks = new ChopStick[NUMBER_OF_PHILOSOPHER];
            // Cannot do this as it will fill the whole array with the SAME chopstick.
            //Arrays.fill(chopSticks, new ReentrantLock());
            for (int i = 0; i < NUMBER_OF_PHILOSOPHER; i++) {
                chopSticks[i] = new ChopStick(i);
            }

            executorService = Executors.newFixedThreadPool(NUMBER_OF_PHILOSOPHER);

            for (int i = 0; i < NUMBER_OF_PHILOSOPHER; i++) {
                philosophers[i] = new Philosopher(i, chopSticks[i], chopSticks[(i + 1) % NUMBER_OF_PHILOSOPHER]);
                executorService.execute(philosophers[i]);
            }
            // Main thread sleeps till time of simulation
            Thread.sleep(SIMULATION_MILLIS);
            // Stop all philosophers.
            for (Philosopher philosopher : philosophers) {
                philosopher.setTummyState(true);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // Close everything down.
            executorService.shutdown();

            // Wait for all thread to finish
            while (!executorService.isTerminated()) {
                Thread.sleep(1000);
            }

            // Time for check
            for (Philosopher philosopher : philosophers) {
                System.out.println(philosopher + " => Number of Turns to Eat ="
                        + philosopher.getNumberOfTurnsToEat());
            }
        }
    }
}
