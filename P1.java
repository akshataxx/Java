/* Akshata Dhuraji, c3309266
 Operating System Assignment2 - Problem 1 : Sharing the Bridge  
 
 Class Name: P1 
 Description: P1 class reads the data from input file, creates bridge and farmer instances based on the number defined in the file and bridge 
 and bridge instances. Main class starts the farmer thread
 Precondition: Input file with number of farmers from each direction is provided at the run time
 Postcondition: Calls instances of bridge class and farmer class, runs the farmer thread as specified in the assignment Q1
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class P1 {

    public static void main(String[] args) throws FileNotFoundException {
        int N=0,S=0;
        String[] input;
        System.out.print('\u000C');     //clears screen
        if (args.length < 1) {          //checks if the file name is provided, if not provided program will exit
            System.out.println("No file was given as an argument..!");
            System.exit(1);
        }
       
        File inputFile = new File(args[0]);
        try {
            Scanner file = new Scanner(inputFile);          //Throws file not found exception
             while (file.hasNextLine()) {                   //reads the file line by line
                 input = file.nextLine().split("\\s+");     //transfers the data from the file into string array
                 N = Integer.parseInt(input[0].replaceAll("[^0-9]+","")); //transfers data from string array to variable N   
                 S = Integer.parseInt(input[1].replaceAll("[^0-9]+","")); //transfers data from string array to variable S
                }
            } catch (FileNotFoundException e) {
       System.out.println("File not found! Please enter a valid file.");}
      
       Bridge bridge = new Bridge();   //creates the bridge 
       Farmer[] f = new Farmer[N+S];   //creates an array of Farmers trying to cross the bridge
        //create North farmers
       for (int i=0; i<N; i++) {
            f[i] = new Farmer("N_Farmer"+(i+1),"North",bridge);
       }
        //create South farmers
       for (int i=N; i<S+N; i++) {
            f[i]= new Farmer("S_Farmer"+(i-N+1),"South",bridge);
       }
       
        //start all farmers
      
         for (int i=0;i<S+N;i++) {
            f[i].start();   //start Farmer Threads. Farmers can run start, as Farmer extends threa
         }
       
    }

}