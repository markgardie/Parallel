package com.company;

import com.company.DiningPhilosopher.ChopStick;
import com.company.DiningPhilosopher.Philosopher;
import com.company.ProducerConsumer.Consumer;
import com.company.ProducerConsumer.Producer;
import com.company.ProducerConsumer.Queue;
import com.company.SleepingBarber.Barber;
import com.company.WriterReader.ReadWriteLock;
import com.company.WriterReader.Reader;
import com.company.WriterReader.Writer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    private static final int NUM_PHILOSOPHERS = 5;
    private static final int SIMULATION_MILLIS = 1000 * 10;
    private static final int NUM_CUSTOMERS = 5;
    private static final int NUM_ITERATION = 8;
    private static final int NUM_READERS = 5;
    private static final int NUM_WRITERS = 5;

    public static void main(String[] args) throws InterruptedException {
	    //producerConsumer();
	    //diningPhilosophers();
	    //sleepingBarber();
        //writerReader();
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

            philosophers = new Philosopher[NUM_PHILOSOPHERS];

            //As many forks as Philosophers
            ChopStick[] chopSticks = new ChopStick[NUM_PHILOSOPHERS];
            // Cannot do this as it will fill the whole array with the SAME chopstick.
            //Arrays.fill(chopSticks, new ReentrantLock());
            for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
                chopSticks[i] = new ChopStick(i);
            }

            executorService = Executors.newFixedThreadPool(NUM_PHILOSOPHERS);

            for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
                philosophers[i] = new Philosopher(i, chopSticks[i], chopSticks[(i + 1) % NUM_PHILOSOPHERS]);
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

    public static void sleepingBarber() throws InterruptedException {
        ExecutorService executor = Executors.newWorkStealingPool();
        Barber barber = new Barber(NUM_CUSTOMERS);


        Callable<Void> barberTask = barber::startService;
        Callable<Void> receiveCustomerTask = barber::receiveNewCustomer;

        List<Future<Void>> barberFutures = new ArrayList<>();
        List <Future<Void>> customerFutures = new ArrayList<>();


        for (int i=0; i<NUM_ITERATION; i++) {
            Future <Void> barberFuture = executor.submit(barberTask);
            barberFutures.add(barberFuture);

            Future <Void> customerFuture = executor.submit(receiveCustomerTask);
            customerFutures.add(customerFuture);
        }

        barberFutures.forEach(future -> {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        customerFutures.forEach(future -> {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

    }

    public static void writerReader() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ReadWriteLock RW = new ReadWriteLock();

        for (int i = 0; i < NUM_WRITERS; i++) {
            executorService.execute(new Writer(RW));
        }

        for (int i = 0; i < NUM_READERS; i++) {
            executorService.execute(new Reader(RW));
        }

        try {
            executorService.awaitTermination(5, TimeUnit.SECONDS);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            RW.stopThreads();
            executorService.shutdown();
        }

    }

}
