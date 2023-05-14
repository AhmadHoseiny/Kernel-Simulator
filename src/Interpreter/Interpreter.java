package Interpreter;
import Kernel_Controller.KernelController;
import Memory.Memory;
import Memory.MemoryWord;
import Process.Process;
public class Interpreter {

    public static void interpretAndRoute(Process p, String instruction){
        String[] instructionParts = instruction.split(" ");
        String instructionType = instructionParts[0];
        String instructionOperand = instructionParts[1];
        String instructionOperand2;
        int blockInMemory = p.getBlockInMemory();
        int startUserProcess = (blockInMemory==0)? 10:25;
        switch (instructionType){
            case "print":
                String toBePrinted = getVariableValue(p, instructionOperand);
                Executer.print(toBePrinted);
                break;
            case "assign":
                String str = instructionParts[2]; //either "input" or "readFile"
                int variableIndex = getFirstEmptyVariableIndex(p) + startUserProcess;
                Memory.getMemoryInstance().getMemoryWord(variableIndex).setKey(instructionOperand);
                if(str.equals("readFile")){
                    String fileName = instructionParts[3];
                    String fileContent = Executer.readFile(fileName);
                    Memory.getMemoryInstance().getMemoryWord(variableIndex).setVal(fileContent);
                }
                else{ //input
                    String input = Executer.readInput();
                    Memory.getMemoryInstance().getMemoryWord(variableIndex).setVal(str);
                }
                break;
            case "writeFile":
                instructionOperand2= instructionParts[2];
                String toBeWritten = getVariableValue(p, instructionOperand2);
                Executer.writeFile(instructionOperand, toBeWritten);
                break;
            case "printFromTo":
                instructionOperand2 = instructionParts[2];
                int start = Integer.parseInt(getVariableValue(p, instructionOperand));
                int end = Integer.parseInt(getVariableValue(p, instructionOperand2));
                Executer.printFromTo(start, end);
                System.out.println("OUT " + instructionOperand);
                break;
            case "semWait":
                Executer.semWait(p, instructionOperand);
                break;
            case "semSignal":
                Executer.semSignal(p, instructionOperand);
                break;
            default:
                System.out.println("Invalid Instruction");
        }
    }
    public static String getVariableValue(Process p, String variableName){
        int blockInMemory = p.getBlockInMemory();
        int startUserProcess = (blockInMemory==0)? 10:25;
        for(int i=0; i < 3 ; i++){
            MemoryWord mw = Memory.getMemoryInstance().getMemoryWord(i);
            if(mw.getKey().equals(variableName)){
                return (String)mw.getVal();
            }
        }
        return null; //supposedly unreachable case
    }
    public static int getVariableIndex(Process p, String variableName){
        int blockInMemory = p.getBlockInMemory();
        int startUserProcess = (blockInMemory==0)? 10:25;
        for(int i=0; i < 3 ; i++){
            MemoryWord mw = Memory.getMemoryInstance().getMemoryWord(i);
            if(mw.getKey().equals(variableName)){
                return i;
            }
        }
        return -1; //supposedly unreachable case (variable not found)
    }
    public static int getFirstEmptyVariableIndex(Process p){
        int blockInMemory = p.getBlockInMemory();
        int startUserProcess = (blockInMemory==0)? 10:25;
        for(int i=0; i < 3 ; i++){
            MemoryWord mw = Memory.getMemoryInstance().getMemoryWord(i);
            if(mw == null){
                return i;
            }
        }
        return -1; //supposedly unreachable case (no space found)
    }
}
