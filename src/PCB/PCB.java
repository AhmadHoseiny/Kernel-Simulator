package PCB;
import Process.ProcessState;
public class PCB {

    private int processId;
    private ProcessState processState;
    private int programCounter;

    private int pcbStartBoundary;

    private int userProcessStartBoundary;
    public int getProcessId() {
        return processId;
    }

    public ProcessState getProcessState() {
        return processState;
    }

    public void setProcessState(ProcessState processState) {
        this.processState = processState;
    }

    public int getProgramCounter() {
        return programCounter;
    }

    public void setProgramCounter(int programCounter) {
        this.programCounter = programCounter;
    }

    public int getPcbStartBoundary() {
        return pcbStartBoundary;
    }

    public void setPcbStartBoundary(int pcbStartBoundary) {
        this.pcbStartBoundary = pcbStartBoundary;
    }

    public int getUserProcessStartBoundary() {
        return userProcessStartBoundary;
    }

    public void setUserProcessStartBoundary(int userProcessStartBoundary) {
        this.userProcessStartBoundary = userProcessStartBoundary;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
    }



    public PCB( int processId,ProcessState processState, int programCounter, int pcbStartBoundary,  int userProcessStartBoundary)
    {
        this.processId= processId;
        this.processState= processState;
        this.programCounter=programCounter;
        this.pcbStartBoundary=pcbStartBoundary;
        this.userProcessStartBoundary=userProcessStartBoundary;
    }




}
