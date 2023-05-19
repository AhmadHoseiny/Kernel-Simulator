import Process.Process;
import scheduler.Scheduler;

import java.io.IOException;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) throws IOException {

        String process1FileName = "Program_1.txt";
        String process2FileName = "Program_2.txt";
        String process3FileName = "Program_3.txt";

        int process1ArrivalTime = 1;
        int process2ArrivalTime = 7;
        int process3ArrivalTime = 3;

        int timeSlice = 2;

        Process process1 = new Process(process1ArrivalTime, process1FileName);
        Process process2 = new Process(process2ArrivalTime, process2FileName);
        Process process3 = new Process(process3ArrivalTime, process3FileName);

        PriorityQueue<Process> toBeCreatedPQ = new PriorityQueue<>((p1, p2)->
                p1.getArrivalTime()-p2.getArrivalTime());
        toBeCreatedPQ.add(process1);
        toBeCreatedPQ.add(process2);
        toBeCreatedPQ.add(process3);

        Scheduler.getSchedulerInstance().schedule(timeSlice, toBeCreatedPQ);

    }
}