package Interpreter;

import Process.Process;
import Scheduler.Scheduler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Executer {

    public static void print(String toBePrinted) {
        System.out.println(toBePrinted);
    }

    public static void writeFile(String fileName, String toBeWritten) throws IOException {
        //TODO
        /*
            1- create a file with the name fileName
            2- write toBeWritten to the file
         */

        File fileToBeWritten = new File(fileName);
        FileWriter writer = new FileWriter(fileToBeWritten);
        writer.write(toBeWritten);
        writer.close();

    }

    public static String readFile(String fileName) throws FileNotFoundException {

        Scanner sc = new Scanner(new File(fileName));
        StringBuilder sb = new StringBuilder();
        sb.append("");
        while (sc.hasNextLine()) {
            sb.append(sc.nextLine());
            sb.append("\n");
        }

        return sb.toString();
    }

    public static String readInput() {

        System.out.println("Please enter a value: ");
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();

    }

    public static void printFromTo(int start, int end) {
        for (int i = start; i <= end; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public static void semWait(Process p, String resourceName) {
        //TODO
        /*
            1- get mutex corresponding to resourceName
            2- call its semWait()
            3- add the process to the  generalBlockedQueue if blocked
         */
        switch (resourceName) {
            case "userInput" -> {
                if (!Scheduler.getUserInputInstance().semWait(p))
                    Scheduler.getSchedulerInstance().getGeneralBlockedQueue().add(p);
            }
            case "userOutput" -> {
                if (!Scheduler.getUserOutputInstance().semWait(p))
                    Scheduler.getSchedulerInstance().getGeneralBlockedQueue().add(p);
            }
            case "file" -> {
                if (!Scheduler.getFileInstance().semWait(p))
                    Scheduler.getSchedulerInstance().getGeneralBlockedQueue().add(p);
            }
            default -> System.out.println("Wrong Resource Name!!");
        }
    }

    public static void semSignal(Process p, String resourceName) {
        //TODO
        /*
            1- get mutex corresponding to resourceName
            2- call its semSignal()
            3- add the process to the ready queue
         */
        switch (resourceName) {
            case "userInput" -> {
                if (Scheduler.getUserInputInstance().semSignal(p) != null)
                    Scheduler.getSchedulerInstance().getReadyQueue().add(p);
            }
            case "userOutput" -> {
                if (Scheduler.getUserOutputInstance().semSignal(p) != null)
                    Scheduler.getSchedulerInstance().getReadyQueue().add(p);
            }
            case "file" -> {
                if (Scheduler.getFileInstance().semSignal(p) != null)
                    Scheduler.getSchedulerInstance().getReadyQueue().add(p);
            }
            default -> System.out.println("Wrong Resource Name!!");
        }
    }
}
