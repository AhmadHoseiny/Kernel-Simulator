package Interpreter;
import Process.Process;
public class Executer {
    public static void print(String toBePrinted){
        System.out.println(toBePrinted);
    }
    public static void writeFile(String fileName, String toBeWritten){
        //TODO
    }
    public static String readFile(String fileName){

        return "";
    }

    public static String readInput() {

        return "";
    }

    public static void printFromTo(int start, int end){
        for(int i=start; i<=end; i++){
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public static void semWait(Process p, String resourceName){
        //TODO
        /*
            1- get mutex corresponding to resourceName
            2- call its semWait()
            3- add the process to the  generalBlockedQueue if blocked
         */
    }

    public static void semSignal(Process p, String resourceName) {
        //TODO
        /*
            1- get mutex corresponding to resourceName
            2- call its semSignal()
            3- add the process to the ready queue
         */
    }
}
