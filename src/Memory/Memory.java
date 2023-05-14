package Memory;

import File_Readers.ProgramFileReader;
import Kernel_Controller.KernelController;
import Process.Process;
import Process.ProcessState;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class Memory {
    private static final int memoryCapacity = 40;
    private static final Memory memoryInstance = new Memory();
    private MemoryWord[] memory;
    private boolean hasSpace[]; //true = available, false = not_available

    private Memory() {
        this.hasSpace = new boolean[2];
        Arrays.fill(this.hasSpace, true);
        this.memory = new MemoryWord[memoryCapacity];
    }

    public static Memory getMemoryInstance() {
        return memoryInstance;
    }


    public int hasSpace() {
        if (hasSpace[0])
            return 0;
        if (hasSpace[1])
            return 1;
        return -1; //no empty space (swapping is needed)
    }

    public void addProcess(Process p) throws FileNotFoundException {
        int blockIndex = hasSpace();
        if (blockIndex == -1) {
            this.swapOut(p);
            return;

        }
        hasSpace[blockIndex] = false;
        p.setBlockInMemory(blockIndex);
        int startPCB = (blockIndex == 0) ? 0 : 5;
        int startUserProcess = (blockIndex == 0) ? 10 : 25;
        p.getPcb().setPcbStartBoundary(startPCB);
        p.getPcb().setUserProcessStartBoundary(startUserProcess);
        memory[startPCB] = new MemoryWord("PCB Boundaries", startPCB + " to " + (startPCB + 4));
        startPCB++;
        memory[startPCB++] = new MemoryWord("User Process Boundaries",
                startUserProcess + " to " + (startUserProcess + 14));
        memory[startPCB++] = new MemoryWord("Process ID", p.getProcessID());
        memory[startPCB++] = new MemoryWord("Process State", ProcessState.NEW);
        memory[startPCB++] = new MemoryWord("Program Counter", 0);

        memory[startUserProcess++] = new MemoryWord(); //var 1
        memory[startUserProcess++] = new MemoryWord(); //var 2
        memory[startUserProcess++] = new MemoryWord(); //var 3
        ArrayList<String> instructions = ProgramFileReader.readProgramFile(p.getProgramFileName());
        for (int i = 0; i < instructions.size(); i++) {
            memory[startUserProcess++] = new MemoryWord("Instruction", instructions.get(i));
        }


    }

///TODO

//propagate variables to hard disk for updates

    public void swapOut(Process processToBeSwappedIn) throws FileNotFoundException {
        Process processToBeSwappedOut = new Process();
        int indexSwappedOut = -1;
        // get process info of the one to be swapped out
        if (memory[3].getKey().equals("Process State") && memory[3].getVal() != ProcessState.RUNNING) {
            indexSwappedOut = 0;
            processToBeSwappedOut = this.getFromPoolOfProcesses((int) memory[2].getVal());

        } else if (memory[8].getKey().equals("Process State") && memory[8].getVal() != ProcessState.RUNNING) {
            indexSwappedOut = 1;
            processToBeSwappedOut = this.getFromPoolOfProcesses((int) memory[7].getVal());
        }
        processToBeSwappedOut.setBlockInMemory(-1);
        processToBeSwappedOut.getPcb().setPcbStartBoundary(-1);
        processToBeSwappedOut.getPcb().setUserProcessStartBoundary(-1);
        processToBeSwappedOut.getPcb().setProgramCounter((indexSwappedOut == 0) ? (int) memory[4].getVal() : (int) memory[9].getVal());
        int startVariableindex = -1;

        switch (indexSwappedOut) {
            case 0:
                startVariableindex = 10;
                break;
            case 1:
                startVariableindex = 25;
                break;
        }
        for (int i = 0; i < 3; i++) {
            processToBeSwappedOut.getVariables()[i].setKey(memory[startVariableindex + i].getKey());
            processToBeSwappedOut.getVariables()[i].setVal(memory[startVariableindex + i].getVal());

        }
        cleanPcbPartInMemory((indexSwappedOut == 0) ? 0 : 5);
        cleanUserPartInMemory(startVariableindex);
        hasSpace[indexSwappedOut] = true;
        this.addProcess(processToBeSwappedIn);


        // making block memoy of swapped out to be -1
        // updating its pcb
        // letting has space to be true
        // emptying memory


    }

    public MemoryWord getMemoryWord(int index) {
        return memory[index];

    }

    public Process getFromPoolOfProcesses(int processId) {
        for (int i = 0; i < KernelController.getKernelControllerInstance().getPoolOfProcesses().size(); i++) {
            if (KernelController.getKernelControllerInstance().getPoolOfProcesses().get(i).getProcessID() == processId) {
                return KernelController.getKernelControllerInstance().getPoolOfProcesses().get(i);
            }

        }
        return null; // should never be the case though
    }


    public void cleanPcbPartInMemory(int start) {
        for (int i = 0; i < 5; i++) {
            memory[start + i] = new MemoryWord();

        }
    }


    public void cleanUserPartInMemory(int start) {
        for (int i = 0; i < 15; i++) {
            memory[start + i] = new MemoryWord();

        }
    }

}



