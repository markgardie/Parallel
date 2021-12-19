import java.util.Random;

public class Main {
    public static int SIZE = 1000;
    public static int NUMBER_JOBS = 10;
    public static int RANDOM_KOEF = 100;
    public static void main(String [] args ) throws InterruptedException{


        double vect[] = new double [SIZE];

        for(int i =0; i<SIZE; i++){
            vect[i]= Math.random() * RANDOM_KOEF;
        }

        double maxSerial =vect[0];
        double minSerial = vect[0];


        for( int i=0; i< SIZE; i++){
            if(vect[i] > maxSerial) {
                maxSerial = vect[i];
            }

            if(vect[i] < minSerial) {
                minSerial = vect[i];
            }
        }

        System.out.println("Max:" + maxSerial);
        System.out.println("Min:" + minSerial);

        MyThread TreadArrray[] = new MyThread[NUMBER_JOBS];

        for(int i = 0; i < NUMBER_JOBS; i++){

            TreadArrray[i] = new MyThread(vect,
                    SIZE/ NUMBER_JOBS * i,
                    i== NUMBER_JOBS -1 ?SIZE:SIZE/ NUMBER_JOBS * (i + 1) );
            TreadArrray[i].start();
        }
        for(int i = 0; i < NUMBER_JOBS; i++){
            TreadArrray[i].join();
        }

        double maxParallel =vect[0];
        double minParallel = vect[0];

        for(int i = 0; i < NUMBER_JOBS; i++){
            maxParallel = TreadArrray[i].getMax();
            minParallel = TreadArrray[i].getMin();
        }

        System.out.println("Max:" + maxParallel);
        System.out.println("Min:" + minParallel);

    }
}