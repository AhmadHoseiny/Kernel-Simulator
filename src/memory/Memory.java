package memory;

import file_readers.ProgramFileReader;
import Process.Process;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Memory {
    private static final int memoryCapacity = 40;
    private static final Memory memoryInstance = new Memory();
    private MemoryWord[] memory;
    private boolean hasSpace[]; //true = available, false = not_available

    private Memory() {
        this.hasSpace = new boolean[2];
        Arrays.fill(this.hasSpace, true);
        this.memory = new MemoryWord[memoryCapacity];
        this.Process_1 = new Process();
        this.Process_2 = new Process();
        this.Process_1_tmp="EMPTY";
        this.Process_2_tmp="EMPTY";
    }

    public static Memory getMemoryInstance() {
        return memoryInstance;
    }


    private Process Process_1;
    private Process Process_2;

    private  String Process_1_tmp;

    private String Process_2_tmp;




    public int getEmptySpace() {
        if (hasSpace[0])
            return 0;
        if (hasSpace[1])
            return 1;
        return -1; //no empty space (swapping is needed)
    }




    public int getProcessId(int blockIndex){
        int startPCB = (blockIndex == 0) ? 0 : 5;
        return Integer.parseInt(memory[startPCB+2].getVal());
    }

    public String getProcessState(int blockIndex){
        int startPCB = (blockIndex == 0) ? 0 : 5;
        return memory[startPCB+3].getVal();
    }

    public int getProcessPC(int blockIndex){
        int startPCB = (blockIndex == 0) ? 0 : 5;
        return Integer.parseInt(memory[startPCB+4].getVal());
    }

    public String getProcessInstruction(int blockIndex, int instructionOffset){
        int startUserProcess = (blockIndex == 0) ? 10 : 25;
        return memory[startUserProcess+3+instructionOffset].getVal(); //3 = 3 variables
    }
    public String getProcessTmp(int blockIndex)
    {
        return (blockIndex==0)? this.Process_1_tmp: this.Process_2_tmp;
    }

    public void incrementProcessPC(int blockIndex){
        int startPCB = (blockIndex == 0) ? 0 : 5;
        int pc = Integer.parseInt(memory[startPCB+4].getVal());
        memory[startPCB+4].setVal((pc+1)+"");
    }

    public void setProcessState(int blockIndex, String newState){
        int startPCB = (blockIndex == 0) ? 0 : 5;
        memory[startPCB+3].setVal(newState);
    }
    public void setProcessTmp(int blockIndex, String newTmp)
    {

        if(blockIndex==0)
            this.Process_1_tmp=newTmp;
        else
            this.Process_2_tmp=newTmp;

    }






//    public void clearMemory(int blockIndex){
//        int startPCB = (blockIndex == 0) ? 0 : 5;
//        int startUserProcess = (blockIndex == 0) ? 10 : 25;
//        for (int i = startPCB; i < startPCB+5; i++) {
//            memory[i] = new MemoryWord();
//        }
//        for (int i = startUserProcess; i < startUserProcess+15; i++) {
//            memory[i] = new MemoryWord();
//        }
//        hasSpace[blockIndex] = true;
//    }


    public MemoryWord[] getMemory() {
        return memory;
    }

    public boolean[] getHasSpace() {
        return hasSpace;
    }

    public Process getProcess_1() {
        return Process_1;
    }

    public void setProcess_1(Process process_1) {
        Process_1 = process_1;
    }

    public Process getProcess_2() {
        return Process_2;
    }

    public void setProcess_2(Process process_2) {
        Process_2 = process_2;
    }

    public String toString(){
        String s = "";
        for (int i = 0; i < memory.length; i++) {
            s += "address " +i +" : ";
            if(memory[i]==null)
                s+="null";
            else
                s += memory[i].toString();
            s+="\n";
        }
        return s;
    }
}



