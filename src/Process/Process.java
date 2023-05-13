package Process;

public class Process {
    static int id = 1;
    private int processID;
    private int arrivalTime;
    private String programFileName;
    private int startIndexInMemory;

    public Process(int arrivalTime, String programFileName){
        this.processID = id++;
        this.arrivalTime = arrivalTime;
        this.programFileName = programFileName;
        this.startIndexInMemory = -1;
    }
    public int getProcessID() {
        return processID;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public String getProgramFileName() {
        return programFileName;
    }

    public int getStartIndexInMemory() {
        return startIndexInMemory;
    }

    public void setStartIndexInMemory(int startIndexInMemory) {
        this.startIndexInMemory = startIndexInMemory;
    }
}
