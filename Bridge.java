/* Akshata Dhuraji, c3309266
 Operating System Assignment2 - Problem 1 : Sharing the Bridge 
 
 Class Name: Bridge 
 Description: Creates the bridge as Semaphore, which famers are trying to cross. 
 Precondition: main class is defined where instance of farmer class is created.
 Postcondition: Calls instances of bridge class and farmer class, runs the farmer thread as specified in the assignment Q1
 */
import java.util.concurrent.Semaphore;
public class Bridge {
    private int crossed;                    //Count the number of crossings
    private static Semaphore bridgeSem;     //semaphore to only allow 1 crossing at a time
    private int northWaiting, southWaiting; //counts the number farmers waiting to cross from North and South
    private int exited;

    //Constructor
    public Bridge() {
        crossed=0;
        bridgeSem = new Semaphore(1);   //bridge resource with mutual exclusivity
        northWaiting = southWaiting = 0;
        exited = 0;
        
    }

    //Getters
    public int getCrossed() {
        return crossed;
    }
    //Methods
    public synchronized void upCross() {
        crossed++;
        System.out.println("NEON = "+getCrossed());
    }
   
    public synchronized void upThis(Farmer f) {
        if (f.getID().startsWith("N")) {
            northWaiting++;
            System.out.println(f.getID()+" is waiting for bridge. Going towards South");
        }
        else {
            southWaiting++;
            System.out.println(f.getID()+" is waiting for bridge. Going towards North");
        }
    }

    public synchronized void upExited() {
        exited++;
    }
    public synchronized int getNorth() {
        return northWaiting;
    }
    public synchronized int getSouth() {
        return southWaiting;
    }
    public synchronized int getExited() {
        return exited;
    }
    public synchronized void resetExited() {
        exited=0;
    }
    public synchronized void resetNorth() {
        northWaiting=0;
    }
    public synchronized void resetSouth() {
        southWaiting=0;
    }

    public void cross(Farmer f) { 
        //Semaphore acquire
        try {   
            bridgeSem.acquire();    
            System.out.println(f.getID()+": Crossing bridge Step 5.");
            System.out.println(f.getID()+": Crossing bridge Step 10.");
            System.out.println(f.getID()+": Crossing bridge Step 15.");

            //Sleep for 200 ms ,as specified in the assignment 
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {} //No interrupts implemented, so thread shouldn't be interrupted?

            System.out.println(f.getID()+": Across the Bridge.");
            upCross();  //increment NEON counter, synchronized to avoid print conflicts
            //Sleep for 200 ms as specified in the assignment
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {} //No interrupts implemented, so thread shouldn't be interrupted
        }
        catch (InterruptedException e) {} 
    }

    public void exit() {
        //Semaphore release
        upExited();
        bridgeSem.release();

    }
}