class TreadLab1 extends Thread{

    double vect[];
    int startIndex;
    int endIndex;
    double max;
    double min;

    public TreadLab1(double[] vect, int startIndex, int endIndex) {
        this.vect = vect;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.max = vect[0];
        this.min = vect[0];
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }


    @Override
    public void run(){
        for(int i = startIndex; i<endIndex; i++ ){
            if(vect[i] > max) {
                max = vect[i];
            }

            if(vect[i] < min) {
                min = vect[i];
            }
        }
    }

}

public class Lab1 {
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

        TreadLab1 TreadArrray[] = new TreadLab1[NUMBER_JOBS];

        for(int i = 0; i < NUMBER_JOBS; i++){

            TreadArrray[i] = new TreadLab1(vect,
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