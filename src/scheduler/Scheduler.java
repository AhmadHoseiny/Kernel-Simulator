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

            if(clk>0)
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

            System.out.println("clk: " + clk);

            System.out.println("toBeCreatedPQ: "+toBeCreatedPQ.toString());
            printQueuesMemory();    //print queues and memory
            for(int i=0 ; i<3 ; i++)
                System.out.println();

            //long-term scheduler
            while(!toBeCreatedPQ.isEmpty() && toBeCreatedPQ.peek().getArrivalTime()==clk){
                                            //create processes that arrived at this clk (if any)
                                            //and it's a loop because there might be more than one process that
                                            //arrived at this clk
                Process p = toBeCreatedPQ.poll();
                kc.createProcess(p);
                kc.addToReadyQueue(p);
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
                    System.out.println("Currently Executing: "+runningProcess+"   PC: " +
                            mem.getProcessPC(runningProcess.getBlockInMemory()));
                    kc.executeProcess(runningProcess);
                    runningTimeSoFar++;
                }
            }
            else{
                if(runningProcess.getBlockInMemory()==-1){ //was swapped out
                    kc.swapIn(runningProcess);
                }
                System.out.println("Currently Executing: "+runningProcess+"   PC: " +
                        mem.getProcessPC(runningProcess.getBlockInMemory()));
                kc.executeProcess(runningProcess);
                runningTimeSoFar++;
            }


            //to check if the runningProcess is finished (and terminate it if so)
            if(runningProcess != null && kc.checkIfProcessFinishedAndTerminateIfSo(runningProcess)){
                runningProcess = null;
                runningTimeSoFar = 0;
            }

            //Remove from CPU on blocking or timeout
            if(runningProcess != null){
                if(mem.getProcessState(runningProcess.getBlockInMemory()).equals("BLOCKED")){
                    runningProcess = null;
                    runningTimeSoFar = 0;
                }
                else {
                    if(runningTimeSoFar == timeSlice) {

                        // I should add the newly arrived here
                        while(!toBeCreatedPQ.isEmpty() && toBeCreatedPQ.peek().getArrivalTime()==clk+1){
                            //create processes that arrived at this clk (if any)
                            //and it's a loop because there might be more than one process that
                            //arrived at this clk
                            Process p = toBeCreatedPQ.poll();
                            kc.createProcess(p);
                            kc.addToReadyQueue(p);
                        }




                        kc.addToReadyQueue(runningProcess);
                        runningProcess = null;
                        runningTimeSoFar = 0;
                    }
                }
            }

            clk++;
        }

        //print final state after execution of all 3 processes
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Final State");
        printQueuesMemory();    //print queues and memory
    }

    public void printQueuesMemory(){
        System.out.println("Ready Queue: "+readyQueue.toString());
        System.out.println("General Blocked Queue: "+generalBlockedQueue.toString());
        System.out.println("File Blocked Queue: "+fileInstance.getBlockedQueue().toString());
        System.out.println("User Input Blocked Queue: "+userInputInstance.getBlockedQueue().toString());
        System.out.println("User Output Blocked Queue: "+userOutputInstance.getBlockedQueue().toString());
        System.out.println("Memory: \n" + Memory.getMemoryInstance().toString());
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
