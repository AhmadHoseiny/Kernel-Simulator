package interpreter;

import executer.CPUExecuter;
import memory.Memory;
import memory.MemoryWord;
import Process.Process;

import java.io.IOException;
import java.sql.SQLOutput;

public class Interpreter {

    public static void interpretAndRoute(Process p, String instruction) throws IOException {
        String[] instructionParts = instruction.split(" ");
        String instructionType = instructionParts[0];
        String instructionOperand1 = instructionParts[1];
        String instructionOperand2;
        int blockInMemory = p.getBlockInMemory();
        int startUserProcess = (blockInMemory == 0) ? 10 : 25;
        switch (instructionType) {
            case "print":
                String toBePrinted = getVariableValue(p, instructionOperand1);
                CPUExecuter.print(toBePrinted);
                break;
            case "assign":
                String str = instructionParts[2]; //either "input" or "readFile"
                if (str.equals("readFile")) {
                    if(Memory.getMemoryInstance().getProcessTmp(p.getBlockInMemory()).equals("EMPTY"))
                    {
                        String fileName = getVariableValue(p, instructionParts[3]);
                        String fileContent = CPUExecuter.readFile(fileName);
                        Memory.getMemoryInstance().setProcessTmp(p.getBlockInMemory(),fileContent);
                    }
                    else
                    {
                        int variableIndex = getFirstEmptyVariableIndex(p) + startUserProcess;
                        Memory.getMemoryInstance().getMemory()[variableIndex].setKey(instructionOperand1);
                        Memory.getMemoryInstance().getMemory()[variableIndex].setVal(Memory.getMemoryInstance().getProcessTmp(p.getBlockInMemory()));
                        Memory.getMemoryInstance().setProcessTmp(p.getBlockInMemory(), "DONE");
                    }


                } else {
                    //input
                    if(Memory.getMemoryInstance().getProcessTmp(p.getBlockInMemory()).equals("EMPTY"))
                    {
                        String strInp = CPUExecuter.readInput();
                        Memory.getMemoryInstance().setProcessTmp(p.getBlockInMemory(),strInp);



                    }
                    else
                    {
                        int variableIndex = getFirstEmptyVariableIndex(p) + startUserProcess;

                        Memory.getMemoryInstance().getMemory()[variableIndex].setKey(instructionOperand1);
                        Memory.getMemoryInstance().getMemory()[variableIndex].setVal(Memory.getMemoryInstance().getProcessTmp(p.getBlockInMemory()));
                        Memory.getMemoryInstance().setProcessTmp(p.getBlockInMemory(), "DONE");
                    }


                }
                break;
            case "writeFile":
                instructionOperand2 = instructionParts[2];
                String fileName = getVariableValue(p, instructionOperand1);
                String toBeWritten = getVariableValue(p, instructionOperand2);
                CPUExecuter.writeFile(fileName, toBeWritten);
                break;
            case "printFromTo":
                instructionOperand2 = instructionParts[2];
                int start = Integer.parseInt(getVariableValue(p, instructionOperand1));
                int end = Integer.parseInt(getVariableValue(p, instructionOperand2));
                CPUExecuter.printFromTo(start, end);
                break;
            case "semWait":
                CPUExecuter.semWait(p, instructionOperand1);
                break;
            case "semSignal":
                CPUExecuter.semSignal(p, instructionOperand1);
                break;
            default:
                System.out.println("Invalid Instruction");
        }
    }


    public static String getVariableValue(Process p, String variableName) {
        int blockInMemory = p.getBlockInMemory();
        int startUserProcess = (blockInMemory == 0) ? 10 : 25;
        for (int i = 0; i < 3; i++) {
            MemoryWord mw = Memory.getMemoryInstance().getMemory()[startUserProcess+i];
            if (mw.getKey().equals(variableName)) {
                return mw.getVal();
            }
        }
        return null; //supposedly unreachable case
    }

    public static int getFirstEmptyVariableIndex(Process p) {
        int blockInMemory = p.getBlockInMemory();
        int startUserProcess = (blockInMemory == 0) ? 10 : 25;
        for (int i = 0; i < 3; i++) {
            MemoryWord mw = Memory.getMemoryInstance().getMemory()[startUserProcess+i];

            if (mw.getKey()==null && mw.getVal()==null){
                return i;
            }
        }
        return (int)-1e9; //supposedly unreachable case (no space found)
    }
}

