package Scheduler;

import java.util.Queue;

public class Scheduler {
    private static final Scheduler schedulerInstance = new Scheduler();
    private Scheduler(){}
    public static Scheduler getSchedulerInstance() {
        return schedulerInstance;
    }

    private Queue<Process> readyQueue;
    private Queue<Process> generalBlockedQueue;

    public void schedule(){
        //TODO
        /*
            1- get the process with the highest priority from the ready queue
            2- call the kernel controller to execute it
         */
    }

    public Queue<Process> getReadyQueue() {
        return readyQueue;
    }

    public Queue<Process> getGeneralBlockedQueue() {
        return generalBlockedQueue;
    }
}
