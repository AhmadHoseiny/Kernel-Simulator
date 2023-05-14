package Process;

public class Process {
    static int id = 1;
    private int processID;
    private int arrivalTime;
    private String programFileName;
    private int blockInMemory; //either 0 or 1  [0 --> P_a, 1 --> P_b]

    public Process(int arrivalTime, String programFileName){
        this.processID = id++;
        this.arrivalTime = arrivalTime;
        this.programFileName = programFileName;
        this.blockInMemory = -1;
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

    public int getBlockInMemory() {
        return blockInMemory;
    }

    public void setBlockInMemory(int blockInMemory) {
        this.blockInMemory = blockInMemory;
    }
}
