import Process.Process;

public class Main {
    public static void main(String[] args) {

        String process1FileName = "Program_1.txt";
        String process2FileName = "Program_2.txt";
        String process3FileName = "Program_3.txt";

        int process1ArrivalTime = 0;
        int process2ArrivalTime = 1;
        int process3ArrivalTime = 4;

        Process process1 = new Process(process1ArrivalTime, process1FileName);
        Process process2 = new Process(process2ArrivalTime, process2FileName);
        Process process3 = new Process(process3ArrivalTime, process3FileName);
    }
}