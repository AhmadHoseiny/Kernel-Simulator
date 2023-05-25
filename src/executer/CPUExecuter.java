package executer;

import Process.Process;
import kernel_controller.KernelController;
import scheduler.Scheduler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CPUExecuter {

    public static void print(String toBePrinted) {
        System.out.println(toBePrinted);
    }

    public static void writeFile(String fileName, String toBeWritten) throws IOException {

        File fileToBeWritten = new File(fileName);
        FileWriter writer = new FileWriter(fileToBeWritten);
        writer.write(toBeWritten);
        writer.close();

    }


    //we assume that the file exists and that it has exactly one line
    public static String readFile(String fileName) throws FileNotFoundException {

        Scanner sc = new Scanner(new File(fileName));
        return sc.nextLine();
//        StringBuilder sb = new StringBuilder();
//        while (sc.hasNextLine()) {
//            sb.append(sc.nextLine());
//            sb.append("\n");
//        }
//
//        return sb.toString();
    }

    public static String readInput() {

        System.out.println("Please enter a value: ");
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();

    }

    public static void printFromTo(int start, int end) {
        for (int i = start; i <= end; i++) {
           printSystemCall(i);
        }
        System.out.println();
    }

    public static void semWait(Process p, String resourceName) {
        KernelController kc = KernelController.getKernelControllerInstance();
        Scheduler sch = Scheduler.getSchedulerInstance();
        switch (resourceName) {
            case "userInput" -> {
                if (!sch.getUserInputInstance().semWait(p))
                    kc.blockProcess(p);
            }
            case "userOutput" -> {
                if (!sch.getUserOutputInstance().semWait(p))
                    kc.blockProcess(p);
            }
            case "file" -> {
                if (!sch.getFileInstance().semWait(p))
                    kc.blockProcess(p);
            }
            default -> System.out.println("Wrong Resource Name!!");
        }
    }

    public static void semSignal(Process p, String resourceName) {
        KernelController kc = KernelController.getKernelControllerInstance();
        Scheduler sch = Scheduler.getSchedulerInstance();
        Process newP = null;
        switch (resourceName) {
            case "userInput" -> {
                newP = sch.getUserInputInstance().semSignal(p);
            }
            case "userOutput" -> {
                newP = sch.getUserOutputInstance().semSignal(p);
            }
            case "file" -> {
                newP = sch.getFileInstance().semSignal(p);
            }
            default -> System.out.println("Wrong Resource Name!!");
        }
        if(newP != null)
            kc.unblockProcess(newP);
    }
    public static void printSystemCall(int i)
    {
        System.out.print(i + " ");
    }


}
