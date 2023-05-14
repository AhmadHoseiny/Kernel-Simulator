package Kernel_Controller;

import Memory.Memory;
import Process.Process;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class KernelController {
    private static final KernelController kernelControllerInstance = new KernelController();
    private ArrayList<Process> poolOfProcesses;

    private KernelController() {
    }

    public static KernelController getKernelControllerInstance() {
        return kernelControllerInstance;
    }

    public void createProcess(Process p) throws FileNotFoundException {
        Memory mem = Memory.getMemoryInstance();
        mem.addProcess(p);
        poolOfProcesses.add(p);


        //TODO
        /*
           add to the ready queue
        */

    }

    public void executeInstruction() {
        //TODO
        /*
            1- get instrution to be executed
            2- call interpreter to route and execute it
            3- increment PC
        */

    }

    public ArrayList<Process> getPoolOfProcesses() {
        return poolOfProcesses;
    }
}
