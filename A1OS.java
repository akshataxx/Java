// Java Program to illustrate reading File input 
//running different algorithms and returning the values as per assignment specifications
import java.io.*;
import java.util.Scanner;
import java.lang.String; 
public class A1 
{ 
  static int i=0;
  public static void main(String[] args)throws Exception 
  { 
      A1 a1=new A1();
      System.out.print('\u000C');
      //Scanner inScanner = new Scanner(System.in);
     // System.out.print("Enter input file path and name:");
      //String inFile = inScanner.next();
      //System.out.println("You entered: " + inFile);  
      String inFile="C:\\Users\\santo\\Documents\\datafile2.txt";  
      String st;    
      int disp=0;
      int es[]=new int[5]; //reads execution size
      int at[]=new int[5];//reads arrival time
      int p[]= new int[5];//reads process id
      int pr[]=new int[5];//reads priority
 
      try{FileReader file= new FileReader(inFile);
      BufferedReader br = new BufferedReader(file); 
         
      while((st = br.readLine()) != null)
      {
            if(st.contains("DISP"))
                 disp=(Integer.valueOf(st.substring(6)));
    
            if(st.contains("ID"))
                 p[i]=(Integer.valueOf(st.substring(5)));
            
            if(st.contains("Arrive"))
                at[i]=(Integer.valueOf(st.substring(8)));     
 
            if (st.contains("ExecSize"))
               es[i]=(Integer.valueOf(st.substring(10)));
               
            if(st.contains("Priority"))
             {
                  pr[i]=(Integer.valueOf(st.substring(10)));
                  i++;
             }
      }
      a1.checkFCFS(disp,p,at,es,pr,i);
      a1.checkSPN(disp,p,at,es,pr,i);
      a1.checkPP(disp,p,at,es,pr,i);
      a1.checkPRR(disp,p,at,es,pr,i);  
        } catch (IOException e){ 
          System.out.println(" Error opening the file " + inFile);
          System.exit(0);}
   }
    
    public void checkFCFS(int disp,int p[],int at[],int es[],int pr[],int i )
    {
        int st[]=new int[6];//start time
        int ft[]=new int[6];//finish time
        int wt[]=new int[6];//wait time
        int tt[]=new int[6]; //turnaround time
        int totaltt=0, totalwt=0;
        float avgwt=0, avgtt=0;
        System.out.println(" FCFS:" );
        st[0]=0;
        for(int j=0;j<i;j++)
        {
           ft[j]= es[j]+disp+st[j];
           tt[j]=ft[j]-at[j];
           wt[j]=tt[j]-es[j];
           st[j+1]=ft[j];
           System.out.println(" "+"T" + (st[j]+1)+":" + "p"+p[j]+ "(" + pr[j] +")"  );
           totaltt+=tt[j];
           totalwt+=wt[j];
        }
        System.out.println("\n");
        System.out.println(" Process Turnaround Time Waiting Time" );
        for(int j=0;j<i;j++)
             System.out.println(" "+ "p" + p[j]+ "\t\t" + tt[j] + "\t\t" + wt[j]);
       
       avgtt=(float)(totaltt/i);
       avgwt=(float)(totalwt/i);
      // a1.printFCFS(avgtt,avgwt); 
        
    }
    
    public void checkSPN(int disp,int p[],int at[],int es[],int pr[],int n )
    {
        int st=0;//start time
        int ft[]=new int[6];//finish time
        int wt[]=new int[6];//wait time
        int tt[]=new int[6]; //turnaround time
        int f[] = new int[6];// flag to check process is completed or not
        float avgwt=0, avgtt=0;
        System.out.println("\n");
        System.out.println(" SPN:" );
        int tot=0;
        boolean a = true;
        while(true)
        {
            int c=n, min=999;
            if (tot == n) // total no of process = completed process loop will be terminated
                break;
            
            for (int i=0; i<n; i++)
            {
                /*
                * If i'th process arrival time <= system time and its flag=0 and execution size<min 
                * That process will be executed first 
                */ 
                if ((at[i] <= st) && (f[i] == 0) && (es[i]<min))
                {
                   min=es[i];
                   c=i;
                }
            }
            /* If c==n means c value can not updated because no process arrival time< system time so we increase the system time */
                if (c==n) 
                    st++;
                else
                {
                    ft[c]=st+es[c]+disp;
                    st=ft[c];
                    tt[c]=ft[c]-at[c];
                    wt[c]=tt[c]-es[c];
                    f[c]=1;
                    tot++;
                    System.out.println(" " +"T" + (ft[c]-es[c])+":" + "p" + p[c]+ "(" + pr[c] +")"  );  
                }
        }
        System.out.println("\n");
        System.out.println(" Process Turnaround Time Waiting Time" );
        for(int i=0;i<n;i++)
            System.out.println("  " +"p"+ p[i]+ "\t\t" + tt[i] + "\t\t" + wt[i]);
        for(int i=0;i<n;i++)
        {
            avgwt+= wt[i];
            avgtt+= tt[i];
        }
        System.out.println ("\naverage tat is "+ (float)(avgtt/n));
        System.out.println ("average wt is "+ (float)(avgwt/n));
    }
    
    public void checkPP(int disp,int p[],int at[],int es[],int pr[],int n )
    {
        int rem_es[] = new int[n]; //remaining execution size
        int is_completed[] = new int[n]; //completed execution
        int wt[] = new int[n], tat[] = new int[n], st[]=new int[n], ft[]=new int[n]; 
        int total_wt = 0, total_tat = 0; 
        int current_time = disp;
        int completed = 0;
        
        float avg_turnaround_time;
        float avg_waiting_time;
        boolean a = true;
        System.out.println("\n");
        System.out.println(" PP:" );       
        for (int i = 0 ; i < n ; i++) 
            rem_es[i] =  es[i]; 
        
        for(int i = 0 ; i < n ; i++) 
            is_completed[i] =  0; 
        
        current_time = 0; // Current time 
        
        // until all of them are not done.
        while(true) 
        { 
            int idx = -1;
            int mx = 100; 
            if (completed ==n)
                break;
            for (int i = 0 ; i < n; i++) 
            { 
               if(at[i] <= current_time && is_completed[i] == 0)
                {
                    if(pr[i] < mx)
                    {
                        mx = pr[i];
                        idx = i;
                    }
                    if(pr[i] == mx) {
                    if(at[i] < at[idx]) {
                        mx = pr[i];
                        idx = i;
                    }
                }
                }
            }
            
            if(idx != -1) {
                if(rem_es[idx] == es[idx]) {
                    st[idx] = current_time+disp;
                    current_time=st[idx];
                    rem_es[idx]-=1;
                    current_time=current_time+1;
                }
                else{
                    rem_es[idx]-=1;
                    current_time=current_time+1;
                    st[idx]=current_time;
                   // if(rem_es[idx]==0)
                    //      current_time+=disp;
                }
                if (rem_es[idx]==0)
                {
                    ft[idx]=current_time;
                    tat[idx]=ft[idx]-at[idx];
                    wt[idx]=tat[idx]-es[idx];
         
                    total_tat+=tat[idx];
                    total_wt+=wt[idx];
                                        
                    is_completed[idx]=1;
                    completed++;
                }
            }
            else
                current_time++;
            System.out.println(" " +"T" + st[idx] +":" + "p"+p[idx]+ "(" + pr[idx] +")" + rem_es[idx]+  current_time);
        }
        System.out.println("\n");
        System.out.println(" Process Turnaround Time Waiting Time" );
        for (int i=0; i<n; i++) 
        { System.out.println(" " + "p" + p[i] + "\t\t" + tat[i] + "\t\t "+  wt[i]);
        } 
        avg_turnaround_time = (float) total_tat / n;
        avg_waiting_time = (float) total_wt / n;
        System.out.println("Average waiting time = " + 
                          avg_waiting_time); 
        System.out.println("Average turn around time = " + 
                           avg_turnaround_time); 
  }
    
    public void checkPRR(int disp,int p[],int at[],int es[],int pr[],int n )
    {
        int rem_es[] = new int[n]; //remaining execution size
        int quantum[]= new int[n]; 
        int wt[] = new int[n], tat[] = new int[n]; 
        int total_wt = 0, total_tat = 0; 
        
        System.out.println("\n");
        System.out.println(" PRR:" );
        for (int i=0;i<n;i++)//HPC has a quantum of 4 and LPC has a quantum of 2
        {
            if (pr[i] <= 2)
                quantum[i]=4;
            else if(pr[i]>2)
                quantum[i]=2;
        } 
        
        for (int i = 0 ; i < n ; i++) 
            rem_es[i] =  es[i]; 
       
        int t = disp; // Current time 
        int st=disp;
        // Keep traversing processes in round robin manner 
        // until all of them are not done.
        while(true) 
        { 
            boolean done = true; 
           
            // Traverse all processes one by one repeatedly 
            for (int i = 0 ; i < n; i++) 
            { 
                // If remaining execution size of a process is greater than 0 
                // then only need to process further 
                if (rem_es[i] > 0) 
                { 
                    done = false; // There is a pending process 
       
                    if (rem_es[i] > quantum[i]&& at[i] < st)
                     { 
                           st=t;
                           // Decrease the remaining execution_size of current process 
                           // by quantum 
                           rem_es[i] -= quantum[i];
                        
                           // Increase the value of t i.e. shows 
                           // how much time a process has been processed 
                           t = t+disp+quantum[i];
                           System.out.println(" " +"T" + st +":" + "p"+p[i]+ "(" + pr[i] +")" );
                           st=t;
                       } 
       
                    // If execution_size is smaller than or equal to 
                    // quantum. Last cycle for this process
                    else if (rem_es[i] <= quantum[i]&& at[i] < st )
                    { 
                        st=t;
                        //System.out.println(" from ELSE t= " +t+ " remaining es= " + rem_es[i]+" at=" +at[i]);
                        // Increase the value of t i.e. shows 
                        // how much time a process has been processed 
                        t = t + rem_es[i]+disp; 
                        
                        // Waiting time is current time minus time 
                        // used by this process 
                        if(es[i]<=quantum[i])
                            wt[i]=st-at[i];
                        else
                            wt[i]=((t-at[i])-es[i])-disp;
                        
                        // As the process gets fully executed 
                        // make its remaining execution size = 0 
                        rem_es[i] = 0; 
                        System.out.println(" " +"T" + st +":" + "p" + p[i]+ "(" + pr[i] +")");
                    } 
                } 
                
            }
            // If all processes are done 
            if (done == true) 
              break;
         }
         // calculating turnaround time by adding 
        // es[i] + wt[i] 
        for (int i = 0; i < n ; i++) 
            tat[i] = es[i] + wt[i]; 
        System.out.println("\n");
        System.out.println(" Process Turnaround Time Waiting Time" );
        for (int i=0; i<n; i++) 
        { 
            total_wt = total_wt + wt[i]; 
            total_tat = total_tat + tat[i]; 
            System.out.println(" " + "p" + p[i] + "\t\t" + tat[i] + "\t\t "+  wt[i]);
        } 
        System.out.println("Average waiting time = " + 
                          (float)total_wt / (float)n); 
        System.out.println("Average turn around time = " + 
                           (float)total_tat / (float)n); 
    }
  /*  a1.printFCFS(float att, float awt)
    {
        System.out.println(" Summary");
        System.out.println(" Algorithm  Average Turnaround Time  Average Waiting Time" );
        System.out.println(" FCFS" +"\t\t" + att +"\t\t" + awt);
    }*/
}
