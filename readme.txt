Akshata
Sharing the Bridge  
 
 Class Name: P1
 Description: P1 class reads the data from input file, creates bridge and farmer instances based on the number defined in the file and bridge instances. Main class starts the farmer thread
 Precondition: Input file with number of farmers from each direction is provided at the run time
 Postcondition: Calls instances of bridge class and farmer class, runs the farmer thread as specified in the assignment Q1

 Class Name: Farmer 
 Description: Creates the farmer class with details of farmer trying to cross the bridge. 
 Precondition: main class is defined where instance of farmer class is created.
 Postcondition: allows farmer to cross the bridge as specified in the assignment Q1 via regulating the semaphore
 
 Class Name: Bridge 
 Description: Creates the bridge as Semaphore, which famers are trying to cross. 
 Precondition: main class is defined where instance of farmer class is created.
 Postcondition: Calls instances of bridge class and farmer class, runs the farmer thread as specified in the assignment Q1
