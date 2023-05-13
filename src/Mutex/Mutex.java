package Mutex;

import java.util.LinkedList;
import java.util.Queue;
import Process.Process;
public class Mutex {

    private boolean mutex; //true = available, false = not_available
    private Integer ownerID;
    private Queue<Process> blockedQueue;
    public Mutex(){
        this.blockedQueue = new LinkedList<>();
        this.mutex = true;
        this.ownerID = null;
    }
    public void semWait(Process p){
        if(!mutex){
            blockedQueue.add(p);
        }
        else{
            this.ownerID = p.getProcessID();
            mutex = false;
        }
    }
    public Process semSignal(Process p){
        if(Integer.compare(this.ownerID, p.getProcessID())==0){
            if(blockedQueue.isEmpty()){
                this.mutex = true;
                this.ownerID = null;
            }
            else{
                Process nextProcess = blockedQueue.poll();
                this.ownerID = nextProcess.getProcessID();
                this.mutex = false;
                return nextProcess; //the process to be added to ready queue
            }
        }
        return null; //no process to be added back to ready queue
    }

}
