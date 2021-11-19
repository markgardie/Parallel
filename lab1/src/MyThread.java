class MyThread extends Thread {

    double vect[];
    int startIndex;
    int endIndex;
    double max;
    double min;

    public MyThread(double[] vect, int startIndex, int endIndex) {
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
    public void run() {
        for (int i = startIndex; i < endIndex; i++) {
            if (vect[i] > max) {
                max = vect[i];
            }

            if (vect[i] < min) {
                min = vect[i];
            }
        }
    }

}
