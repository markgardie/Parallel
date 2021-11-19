package com.company;

public class ServiceSystem {
    private static final int NUMBER_OF_CPU = 6;
    private static final int NUMBER_OF_FLOWS = 1;
    private static final int NUMBER_OF_PROCESS = 15;

    private final CPU[] cpus;
    private final CPUProcessFlow[] flows;
    private final Thread[] flowThreads;
    private final Thread[] cpuThreads;
    private int processLost;
    private int processInterrupted;
    private int processes;

    public ServiceSystem(int nCPU, int nFlow, int nProcesses) {
        if (nCPU <= 0 || nFlow <= 0 || nProcesses <= 0) {
            throw new IllegalArgumentException();
        }

        cpus = new CPU[nCPU];
        cpuThreads = new Thread[nCPU];
        for (int i = 0; i < nCPU; i++) {
            cpus[i] = new CPU();                    // runnable object
            cpuThreads[i] = new Thread(cpus[i]);    // flow for this object
        }

        flows = new CPUProcessFlow[nFlow];
        flowThreads = new Thread[nFlow];
        for (int i = 0; i < nFlow; i++) {
            flows[i] = new CPUProcessFlow(nProcesses);  // runnable object
            flowThreads[i] = new Thread(flows[i]);   // thread for this object
        }


        processes = nProcesses; // all processes
        processInterrupted = 0;
        processLost = 0;

    }

    private void runThreads() {
        for (int i = 0; i < cpuThreads.length; i++) {
            cpuThreads[i].start();
        }

        for (int i = 0; i < flowThreads.length; i++) {
            flowThreads[i].start();
        }

    }

    private boolean isAlive() {
        for (int i = 0; i < flowThreads.length; i++) {
            if (flowThreads[i].isAlive()) {
                return true;
            }
        }
        return false;
    }

    public ServiceSystem(int nProcesses) {
        this(NUMBER_OF_CPU, NUMBER_OF_FLOWS, nProcesses);
    }

    private static boolean isAnyFlowAilive(ServiceSystem ss) {
        for (int i = 0; i < ss.flowThreads.length; i++) {
            if (ss.flowThreads[i].isAlive()) {
                return true;
            }
        }
        return false;
    }

    private static boolean isAnyCPUAilive(ServiceSystem ss) {
        for (int i = 0; i < ss.cpuThreads.length; i++) {
            if (ss.cpuThreads[i].isAlive()) {
                return true;
            }
        }
        return false;
    }

    


}
