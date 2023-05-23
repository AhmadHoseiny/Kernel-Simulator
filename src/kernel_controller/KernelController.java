package kernel_controller;

import file_readers.ProgramFileReader;
import interpreter.Interpreter;
import memory.Memory;
import Process.Process;
import memory.MemoryWord;
import scheduler.Scheduler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class KernelController {
    private static final KernelController kernelControllerInstance = new KernelController();
    private KernelController() {
    }
    public static KernelController getKernelControllerInstance() {
        return kernelControllerInstance;
    }



    public void createProcess(Process p) throws IOException {
        Memory mem = Memory.getMemoryInstance();
        int blockIndex = mem.getEmptySpace();
        if (blockIndex == -1) {
            swapOut();
            blockIndex = mem.getEmptySpace();
        }
        mem.getHasSpace()[blockIndex] = false;
        p.setBlockInMemory(blockIndex);
        switch(blockIndex) {
            case 0:
                mem.setProcess_1(p);
                break;
            case 1:
                mem.setProcess_2(p);
                break;
        }

        int startPCB = (blockIndex == 0) ? 0 : 5;
        int startUserProcess = (blockIndex == 0) ? 10 : 25;
        int endUserProcess = (blockIndex == 0) ? 24 : 39;
        mem.getMemory()[startPCB] = new MemoryWord("PCB Boundaries",
                startPCB + " to " + (startPCB + 4));
        startPCB++;
        mem.getMemory()[startPCB++] = new MemoryWord("User Process Boundaries",
                startUserProcess + " to " + (startUserProcess + 14));
        mem.getMemory()[startPCB++] = new MemoryWord("Process ID", p.getProcessID()+"");
        mem.getMemory()[startPCB++] = new MemoryWord("Process State", "NEW");
        mem.getMemory()[startPCB++] = new MemoryWord("Program Counter", 0+"");

        mem.getMemory()[startUserProcess++] = new MemoryWord(); //var 1
        mem.getMemory()[startUserProcess++] = new MemoryWord(); //var 2
        mem.getMemory()[startUserProcess++] = new MemoryWord(); //var 3
        ArrayList<String> instructions = ProgramFileReader.readProgramFile(p.getProgramFileName());
        for (int i = 0; i < instructions.size(); i++) {
            mem.getMemory()[startUserProcess++] = new MemoryWord("Instruction " + i, instructions.get(i));
        }
        while(startUserProcess <= endUserProcess) {
            mem.getMemory()[startUserProcess++] = null;
        }

    }

    public void swapIn(Process p) throws IOException {
        Memory mem = Memory.getMemoryInstance();
        int blockIndex = mem.getEmptySpace();
        if (blockIndex == -1) {
            swapOut();
            blockIndex = mem.getEmptySpace();
        }
        mem.getHasSpace()[blockIndex] = false;
        p.setBlockInMemory(blockIndex);
        switch(blockIndex) {
            case 0:
                mem.setProcess_1(p);
                break;
            case 1:
                mem.setProcess_2(p);
                break;
        }

        int startPCB = (blockIndex == 0) ? 0 : 5;
        int startUserProcess = (blockIndex == 0) ? 10 : 25;
        int endUserProcess = (blockIndex == 0) ? 24 : 39;
        File file = new File("Process_"+p.getProcessID() +"_Disk.txt");
        Scanner sc = new Scanner(file);


        mem.getMemory()[startPCB] = new MemoryWord("PCB Boundaries", sc.nextLine());
        startPCB++;
        mem.getMemory()[startPCB++] = new MemoryWord("User Process Boundaries", sc.nextLine());
        mem.getMemory()[startPCB++] = new MemoryWord("Process ID", sc.nextLine());
        mem.getMemory()[startPCB++] = new MemoryWord("Process State", sc.nextLine());
        mem.getMemory()[startPCB++] = new MemoryWord("Program Counter", sc.nextLine());
        for(int i=0 ; i<3 ; i++){
            String var[] = sc.nextLine().split(",");
            if(var[0].equals("null") && var[1].equals("null"))
                mem.getMemory()[startUserProcess++] = new MemoryWord();
            else
                mem.getMemory()[startUserProcess++] = new MemoryWord(var[0], var[1]); //var i
        }
        int i=0;
        while(sc.hasNextLine()) {
            mem.getMemory()[startUserProcess++] = new MemoryWord("Instruction " + i, sc.nextLine());
            i++;
        }
        while(startUserProcess <= endUserProcess) {
            mem.getMemory()[startUserProcess++] = null;
        }
    }

    public void swapOut() throws IOException {
        Memory mem = Memory.getMemoryInstance();
        int blockToBeSwappedOut;

        // Only one process should be running at a time
        if (!mem.getProcessState(0).equals("RUNNING")) {
            blockToBeSwappedOut = 0;
        } else{
            blockToBeSwappedOut = 1;
        }
        mem.getHasSpace()[blockToBeSwappedOut] = true;
        Process processToBeSwappedOut = null;
        switch (blockToBeSwappedOut) {
            case 0:
                processToBeSwappedOut = mem.getProcess_1();
                mem.setProcess_1(null);
                break;
            case 1:
                processToBeSwappedOut = mem.getProcess_2();
                mem.setProcess_2(null);
                break;
        }
        processToBeSwappedOut.setBlockInMemory(-1);

        int startPCB = (blockToBeSwappedOut == 0) ? 0 : 5;
        int startUserProcess = (blockToBeSwappedOut == 0) ? 10 : 25;

        int processID = mem.getProcessId(blockToBeSwappedOut);
        File file = new File("Process_"+processID +"_Disk.txt");
        FileWriter fw = new FileWriter(file);
        for(int i=0 ; i<5 ; i++){
            fw.write(mem.getMemory()[startPCB+i].getVal()+"\n");
            mem.getMemory()[startPCB+i] = null;
        }
        int i=0;
        while(i<3){ //variables
            fw.write(mem.getMemory()[startUserProcess+i].getKey() + "," +
                    mem.getMemory()[startUserProcess+i].getVal() + "\n");
            mem.getMemory()[startUserProcess+i] = null;
            i++;
        }
        while(true){ //instructions
            if(mem.getMemory()[startUserProcess+i] == null)
                break;
            fw.write(mem.getMemory()[startUserProcess+i].getVal()+"\n");
            mem.getMemory()[startUserProcess+i] = null;
            i++;
        }
        fw.close();
    }




    public void blockProcess(Process p){
        Scheduler sch = Scheduler.getSchedulerInstance();
        sch.getGeneralBlockedQueue().add(p);

        Memory mem = Memory.getMemoryInstance();
        mem.setProcessState(p.getBlockInMemory(), "BLOCKED");
    }

    public void unblockProcess(Process p){
        addToReadyQueue(p); //add to readyQueue and update state

        //remove from generalBlockedQueue
        Scheduler sch = Scheduler.getSchedulerInstance();
        sch.getGeneralBlockedQueue().remove(p);
    }
    public void addToReadyQueue(Process p){ //add to readyQueue and update state
        Scheduler sch = Scheduler.getSchedulerInstance();
        sch.getReadyQueue().add(p);

        Memory mem = Memory.getMemoryInstance();
        mem.setProcessState(p.getBlockInMemory(), "READY");
    }



    public void executeProcess(Process p) throws IOException {
        Memory mem = Memory.getMemoryInstance();

        int pc = mem.getProcessPC(p.getBlockInMemory());
        String instruction = mem.getProcessInstruction(p.getBlockInMemory(), pc);
        Interpreter.interpretAndRoute(p, instruction);
        mem.incrementProcessPC(p.getBlockInMemory());
    }

    //returns true if process is finished
    public boolean checkIfProcessFinishedAndTerminateIfSo(Process p) throws IOException {
        Memory mem = Memory.getMemoryInstance();
        Scheduler sch = Scheduler.getSchedulerInstance();
        int pc = mem.getProcessPC(p.getBlockInMemory());
        int userProcessStart = (p.getBlockInMemory() == 0) ? 10 : 25;
        MemoryWord instruction = mem.getMemory()[userProcessStart+3+pc];
        if (instruction == null) {
            mem.setProcessState(p.getBlockInMemory(), "FINISHED");
            sch.getReadyQueue().remove(p);
            return true;
        }
        return false;
    }
}
