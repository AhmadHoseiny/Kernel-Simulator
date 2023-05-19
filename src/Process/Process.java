package Process;

import kernel_controller.KernelController;
import memory.Memory;

import java.io.IOException;
import java.util.Objects;

public class Process {
    static int id = 1;

    private int processID;
    private int arrivalTime;

    private int blockInMemory; //either 0 or 1  [0 --> P_a, 1 --> P_b], -1--> on disk not allocated in memory

    private String programFileName;
    public Process() {

    }

    //used for creating the process for the first time
    public Process(int arrivalTime, String programFileName) throws IOException {
        this.processID = id++;
        this.arrivalTime = arrivalTime;
        this.blockInMemory = -1;
        this.programFileName = programFileName;
//        KernelController.getKernelControllerInstance().createProcess(this);
    }

    public int getProcessID() {
        return processID;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }
    public int getBlockInMemory() {
        return blockInMemory;
    }

    public String getProgramFileName() {
        return programFileName;
    }

    public void setBlockInMemory(int blockInMemory) {
        this.blockInMemory = blockInMemory;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Process process = (Process) o;
        return processID == process.processID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(processID);
    }

    public String toString(){
        return "Process_" + getProcessID();
    }
}


