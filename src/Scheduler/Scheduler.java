package Scheduler;

import Mutex.Mutex;
import Process.Process;

import java.util.LinkedList;
import java.util.Queue;

public class Scheduler {

    private static final Scheduler schedulerInstance = new Scheduler();
    private static Mutex userInputInstance = new Mutex();
    private static Mutex userOutputInstance = new Mutex();
    private static Mutex fileInstance = new Mutex();

    private static Queue<Process> generalBlockedQueue = new LinkedList<>();
    private Queue<Process> readyQueue = new LinkedList<>();

    private Scheduler() {
    }

    public static Scheduler getSchedulerInstance() {
        return schedulerInstance;
    }

    public static Mutex getUserInputInstance() {
        return userInputInstance;
    }

    public static Mutex getUserOutputInstance() {
        return userOutputInstance;
    }

    public static Mutex getFileInstance() {
        return fileInstance;
    }

    public void schedule() {
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
