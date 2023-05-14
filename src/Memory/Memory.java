package Memory;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

import File_Readers.ProgramFileReader;
import Process.Process;
import Process.ProcessState;
public class Memory {
    private static final int memoryCapacity = 40;
    private MemoryWord[] memory;
    private boolean hasSpace[]; //true = available, false = not_available




    private static final Memory memoryInstance = new Memory();
    private Memory(){
        this.hasSpace = new boolean[2];
        Arrays.fill(this.hasSpace, true);
        this.memory = new MemoryWord[memoryCapacity];
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
        p.setBlockInMemory(blockIndex);
        int startPCB = (blockIndex==0)? 0:5;
        int startUserProcess = (blockIndex==0)? 10:25;

        memory[startPCB] = new MemoryWord("PCB Boundaries", startPCB +" to " + (startPCB+4));
        startPCB++;
        memory[startPCB++] = new MemoryWord("User Process Boundaries",
                startUserProcess +" to " + (startUserProcess+14));
        memory[startPCB++] = new MemoryWord("Process ID", p.getProcessID());
        memory[startPCB++] = new MemoryWord("Process State", ProcessState.NEW);
        memory[startPCB++] = new MemoryWord("Program Counter", 0);

        memory[startUserProcess++] = new MemoryWord(); //var 1
        memory[startUserProcess++] = null; //var 2
        memory[startUserProcess++] = null; //var 3
        ArrayList<String> instructions = ProgramFileReader.readProgramFile(p.getProgramFileName());
        for(int i = 0; i < instructions.size(); i++){
            memory[startUserProcess++] = new MemoryWord("Instruction", instructions.get(i));
        }
   }
    public MemoryWord getMemoryWord(int index){
        return memory[index];
    }
}
