package scheduler;

import kernel_controller.KernelController;
import memory.Memory;
import mutex.Mutex;
import Process.Process;

import java.io.IOException;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Scheduler {

    private static final Scheduler schedulerInstance = new Scheduler();
    private Scheduler() {
        init();
    }
    private void init(){
        this.userInputInstance = new Mutex();
        this.userOutputInstance = new Mutex();
        this.fileInstance = new Mutex();
        this.generalBlockedQueue = new LinkedList<>();
        this.readyQueue = new LinkedList<>();
    }

    public static Scheduler getSchedulerInstance() {
        return schedulerInstance;
    }

    private Mutex userInputInstance;
    private Mutex userOutputInstance;

    private Mutex fileInstance;

    private Queue<Process> generalBlockedQueue;

    private Queue<Process> readyQueue;








    public void schedule(int timeSlice, PriorityQueue<Process> toBeCreatedPQ) throws IOException {
        init();
        int clk=0;
        Process runningProcess = null;
        int runningTimeSoFar = 0;
        KernelController kc = KernelController.getKernelControllerInstance();
        Memory mem = Memory.getMemoryInstance();

        while(!toBeCreatedPQ.isEmpty() || !readyQueue.isEmpty() || runningProcess != null){

            System.out.println("clk: " + clk);

//            System.out.println("toBeCreatedPQ: "+toBeCreatedPQ.toString());
//            System.out.println("Ready Queue: "+readyQueue.toString());
//            System.out.println("General Blocked Queue: "+generalBlockedQueue.toString());
//            System.out.println(mem.toString());

            //long-term scheduler
            if(!toBeCreatedPQ.isEmpty()){
                Process p = toBeCreatedPQ.peek();
                if(p.getArrivalTime()==clk){
                    toBeCreatedPQ.poll();
                    kc.createProcess(p);
                    kc.addToReadyQueue(p);
                }
            }

            //short-term scheduler
            if(runningProcess == null){
                if(!readyQueue.isEmpty()){
                    Process p = readyQueue.poll();
                    if(p.getBlockInMemory()==-1){ //was swapped out
                        kc.swapIn(p);
                    }
                    mem.setProcessState(p.getBlockInMemory(),"RUNNING");
                    runningProcess = p;
                    System.out.println("Running Process: "+runningProcess +"   " +
                            mem.getProcessPC(runningProcess.getBlockInMemory()));
                    kc.executeProcess(runningProcess);
                    runningTimeSoFar++;
                }
            }
            else{
                System.out.println("Running Process: "+runningProcess+"   " +
                        mem.getProcessPC(runningProcess.getBlockInMemory()));
                if(runningProcess.getBlockInMemory()==-1){ //was swapped out
                    kc.swapIn(runningProcess);
                }
                kc.executeProcess(runningProcess);
                runningTimeSoFar++;
            }


            //to check if the runningProcess is finished (and terminate it if so)
            if(runningProcess != null && kc.checkIfProcessFinishedAndTerminateIfSo(runningProcess)){
                runningProcess = null;
                runningTimeSoFar = 0;
                this.readyQueue.remove(runningProcess);
            }

            //Remove from CPU on blocking or timeout
            if(runningProcess != null){
                if(mem.getProcessState(runningProcess.getBlockInMemory()).equals("BLOCKED")){
                    runningProcess = null;
                    runningTimeSoFar = 0;
                }
                else {
                    if(runningTimeSoFar == timeSlice) {
                        kc.addToReadyQueue(runningProcess);
                        runningProcess = null;
                        runningTimeSoFar = 0;
                    }
                }
            }

            clk++;
        }


    }







    public Queue<Process> getReadyQueue() {
        return readyQueue;
    }

    public Queue<Process> getGeneralBlockedQueue() {
        return generalBlockedQueue;
    }

    public Mutex getUserInputInstance() {
        return userInputInstance;
    }

    public Mutex getUserOutputInstance() {
        return userOutputInstance;
    }

    public Mutex getFileInstance() {
        return fileInstance;
    }
}
