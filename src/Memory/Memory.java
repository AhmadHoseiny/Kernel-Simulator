package Memory;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

import File_Readers.ProgramFileReader;
import Process.Process;
import Process.ProcessState;
public class Memory {
    private MemoryWord[] memory;
    private static final int memoryCapacity = 40;
    private boolean hasSpace[]; //true = available, false = not_available
    private Process toBeSwappedOut; //the first one in




    private static final Memory memoryInstance = new Memory();
    private Memory(){
        this.hasSpace = new boolean[2];
        Arrays.fill(this.hasSpace, true);
        this.memory = new MemoryWord[memoryCapacity];
        this.toBeSwappedOut = null;
    }
    public static Memory getMemoryInstance() {
        return memoryInstance;
    }






    public int hasSpace(){
        if(hasSpace[0])
            return 0;
        if(hasSpace[1])
            return 1;
        return -1; //no empty space (swapping is needed)
    }

   public void addProcess(Process p) throws FileNotFoundException {
        int blockIndex = hasSpace();
        if(blockIndex == -1){ //we need to swap

        }
        hasSpace[blockIndex] = false;
        int start = (blockIndex==0)? 0:20;
        p.setStartIndexInMemory(start);
        memory[start++] = new MemoryWord("Boundaries", start +" to " + (start+19));
        memory[start++] = new MemoryWord("Process ID", p.getProcessID());
        memory[start++] = new MemoryWord("Process State", ProcessState.NEW);
        memory[start++] = new MemoryWord("Program Counter", 0);
        memory[start++] = null; //var 1
        memory[start++] = null; //var 2
        memory[start++] = null; //var 3
        ArrayList<String> instructions = ProgramFileReader.readProgramFile(p.getProgramFileName());
        for(int i = 0; i < instructions.size(); i++){
            memory[start++] = new MemoryWord("Instruction", instructions.get(i));
        }
    }
//    public void removeProcess(int index){
//        hasSpace[index] = true;
//        memory[index] = null;
//    }
}
