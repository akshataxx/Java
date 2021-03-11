/* Akshata Dhuraji, c3309266
 Operating System Assignment2 - Problem 3 : Covid-safe restaurant using monitor
 
 Class Name: Serve 
 Description: Serve class creats serve thread and passes it to restaurent class
 Precondition: Main class and restaurent classes are defined
 Postcondition: Serve thread holding serving data is created 
 */
public class Serve extends Thread {
    Restaurent restaurent;

    Serve(Restaurent restaurent) {
        this.restaurent = restaurent;
    }
    //called from P3 class
    @Override
    public void run() {
        try {
            restaurent.work();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.run();
    }
}