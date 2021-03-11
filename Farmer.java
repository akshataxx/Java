/* Akshata Dhuraji, c3309266
 Operating System Assignment2 - Problem 1 : Sharing the Bridge 
 
 Class Name: Farmer 
 Description: Creates the farmer class with details of farmer trying to cross the bridge. 
 Precondition: main class is defined where instance of farmer class is created.
 Postcondition: allows farmer to cross the bridge as specified in the assignment Q1 via regulating the semaphore
 */
public class Farmer extends Thread{
    private String location;    //current location
    private String destination; //Opposite location, destination, set in the constructor
    private String id;          //name      
    private Bridge bridge;      //bridge being used
    
    //constructor
    public Farmer(String id, String location, Bridge bridge) {
        this.id=id;
        this.location=location;
        if (location=="North") destination="South"; // Rule for North or South bound farmers as defined in assignment Q1
        else destination="North";
        this.bridge = bridge;
        System.out.println(id+": Waiting for bridge. Going towards "+destination);  //print initial waiting for bridge
    }

    //getters
    public String getLocation() {
        return location;
    }
    public String getID() {
        return id;
    }

    //Overrides the Thread toString() method. Called with Thread.getCurrent().toString()
    @Override   
    public String toString() {
        return id;
    }
    @Override   //initiatied when the Farmer Thread .start() method is called
    public void run() {
        //if ready to cross
        while(true){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {}
            
            if (bridge.getNorth() != 1 && bridge.getSouth() != 1) { //if neither equal 1 yet and we havent added this to the list
                bridge.upThis(this);    //increments the appropriate north/south counter in a thread safe method, also marks this thread as counted=true
            }
            if ( bridge.getNorth()==1 && id.startsWith("N")) {    //if this  is a northern farmer and there is 1 northern farmer ready
                bridge.cross(this);
                bridge.exit();
                if (bridge.getExited()==1) {    //if both successfully crossed reset counts
                    bridge.resetExited();
                    bridge.resetNorth();
                    //System.out.println("Reset exited and North"); //Debug Code
                    //System.out.println("Exit: "+bridge.getExited()+", North: "+bridge.getNorth()+", South: "+bridge.getSouth()); //Debug Code
                }
            }
            else if ( bridge.getSouth()==1 && id.startsWith("S")) { //else if this is a southern farmer and there is 1 southern farmer ready
                bridge.cross(this);
                bridge.exit();
                if (bridge.getExited()==1) {    //if both successfully crossed reset counts
                    bridge.resetExited();
                    bridge.resetSouth();
                    //System.out.println("Reset exited and South"); //Debug Code
                    //System.out.println("Exit: "+bridge.getExited()+", North: "+bridge.getNorth()+", South: "+bridge.getSouth()); //Debug Code
                }
            }
        }
    }//end run  

}//end class