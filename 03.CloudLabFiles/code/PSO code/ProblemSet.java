package schedulingPSO;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.UtilizationModelStochastic;

// you can modify the function depends on your needs
// if your problem space is greater than 2-dimensional space
// you need to introduce a new variable (other than x and y)

public class ProblemSet
{
	static int n=2000; static int m=1000;
	static double[] mat=new double[n];
	public static final double VEL_LOW = 0;
	public static final double VEL_HIGH =10;
	// public static final double LOC_X_LOW =0;
	// public static final double LOC_X_HIGH=5;
	Random r=new Random();
	
//	static double[] VM=new double[]{300,250,200,280,230, 210, 240, 270, 220, 290};
	// static double[][] Cost=new double[m][n];
	private static long[] array1;
	static double[] Task=new double[n];
	static double[] VM=new double[m];
	static double[]RU=new double[m];
	
	 static double[] Makespan=new double[m];
	// static double[] Task =  new double[]  {100000,220000,150000,180000,200000,240000,300000,110000,260000,160000,120000,180000,210000,230000,250000,270000,170000,
		// 130000,190000,290000};	
	static double[] Deadline= new double[n];	
	public static final double ERR_TOLERANCE = 1E-20;
	public static double evaluate(Location location)
    	{
		// Create Number of Tasks............
		
	  for(int j=0; j<n; j++)
	    {
		   double x=200000; double y=500000;
	 	   double number= (int) (x+(Math.random()*(y-x)));
	 	   Task[j]=number;	   
	     }  
			
	  
	  // Create deadline of the task
	  
	  for(int j=0; j<n; j++)
	    {
		   double x=500; double y=3000;
	 	   double number= (int) (x+(Math.random()*(y-x)));
	 	  Deadline[j]=number;	   
	     }  
	  
	//	 Create Virtual machine.....
		   for(int l=0; l<m; l++)
	        {
		     double x=100; double y=1000;
	 	     double number= (int) (x+(Math.random()*(y-x)));
	 	        VM[l]=number;	   
	           }    
			 
		   	   
			// Makespan Time calculation..........
		  /*    double Total_ETime= 0;
	           double ExeTime=0;
	           double z=0;
	 		   int count=0;
	 		   int Task_AcceptRatio=0;
	          Makespan[(int) z]=0;
	         for(int j=0; j<m; j++)
	          {
	        	 Makespan[j]=0;
	          }
	         double MST=0;
	         for(int j=0; j<n; j++)
	         {
	        	 z=location.getLoc()[j];
	        	 ExeTime=(Task[j]/(VM[(int) z]));
	        	 Makespan[(int) z]= ExeTime+Makespan[(int) z];
	        	 if(Deadline[j]>Makespan[(int) z])
	        	 {
	        	    count++;
	        	 }
	         }
	         Task_AcceptRatio=(count*100)/n;
	         
	         double max= Makespan[0];
	      //   System.out.println("ALL Makespan is "+ Makespan[0]);
	         for(int k=1; k<m; k++)
	         {
	        //	 System.out.println("ALL Makespan is "+ Makespan[k]);
	        	 if(max<Makespan[k])
	        	 {
	        		 max=Makespan[k];
	        	 }
	         }     
	//	System.out.println();				
		return max/3600;  
    	}  
    	}	  */ 
     
    	
    	
		   // Task Acceptance or Rejection Ratio
		   
		 /*  double Total_ETime= 0;
           double ExeTime=0;
           double z=0;
 		   double count=0;
 		   double Task_AcceptRatio=0;
          Makespan[(int) z]=0;
         for(int j=0; j<m; j++)
          {
        	 Makespan[j]=0;
          }
         double MST=0;
         for(int j=0; j<n; j++)
         {
        	 z=location.getLoc()[j];
        	 ExeTime=Task[j]/(VM[(int) z]);
        	 Makespan[(int) z]= ExeTime+Makespan[(int) z];
        	 if(Deadline[j]>Makespan[(int) z])
        	 {
        	    count++;
        	 }
         }
         Task_AcceptRatio=(count*100)/n;
         double Task_Rejetcion=100- Task_AcceptRatio;
         System.out.println("Percentage of Task Rejetion Ratio  is "+Task_Rejetcion);
		   return Task_Rejetcion;                
    	}
}   */
		   
		   //Execution time calculation.........
		 /*    double Total_ETime= 0;
             double ExeTime=0;
             double z=0;
	         
		    for(int j=0; j<n; j++)
			     {
				 z=location.getLoc()[j];
				 ExeTime=Task[j]/(VM[(int) z])/60;
				// sum=(int) (sum+mat[j]*GetCost(i,j));
				 Total_ETime=Total_ETime+ ExeTime; 
			 }	 
		     	 return  Total_ETime;  
    	}
}     */  
		   // resource utilization and energy consumption
			 
			 
		 	   double MST=0;
		       double z=0;  double  ExeTime=0;
		       Makespan[(int) z]=0;
		        for(int j=0; j<m; j++)
		         {
		        	 Makespan[j]=0;
		         }
		       
		         for(int j=0; j<n; j++)
		         {
		        	 z=location.getLoc()[j];
		        	 ExeTime=Task[j]/(VM[(int) z]*3600);
		        	 Makespan[(int) z]= ExeTime+Makespan[(int) z];
		         }
		         for(int j=0; j<m; j++)
		         {
		       // 	 System.out.println("makespan time is:"+ Makespan[j]);
		         }
		     double max_Makespan= Makespan[0]; 
		     for(int j=1; j<m; j++)
		     {
		    	if(max_Makespan<Makespan[(int)j]) 
		    	{
		    		max_Makespan=Makespan[(int)j];
		    	}
		     }
			// calculate resource utilization
		     
		     for(int j=0; j<m; j++)
		     {
		    	 RU[j]= (Makespan[(int)j]/max_Makespan);
		    //	 System.out.println("Resource utilization is :"+RU[j]);
		     }
		     double Energy_Consumption1=0;
		     double Energy_Consumption2=0;
		    
		     double Total_EC=0;
		     for(int j=0; j<m; j++)
		     {
		    	if(RU[j]<.3)
		    	{
		    		Energy_Consumption1=Energy_Consumption1+50*max_Makespan;
		    		
		    	}
		    	if(RU[j]>=.3)
		    	{
		    	
		    			Energy_Consumption2=Energy_Consumption2+ 100*RU[j]*max_Makespan;    		
		    		
		          }
		     }	
		     Total_EC=Energy_Consumption1+Energy_Consumption2;
		     return Total_EC;
	}
	        
}          


	// 	   Cost Calculation  .......

	      /*   double Cost_Factor=0;
		     double Cost=0;
		     double Total_Cost=0;
		     double Fitness_value=0;
		     double ExeTime=0;
	         double z=0;
	         double MST=0;
	       
	       Makespan[(int) z]=0;
	        for(int j=0; j<m; j++)
	         {
	        	 Makespan[j]=0;
	         }
	       
	         for(int j=0; j<n; j++)
	         {
	        	 z=location.getLoc()[j];
	        	 ExeTime=Task[j]/(VM[(int) z]*3600);
	        	 Makespan[(int) z]= ExeTime+Makespan[(int) z];
	         }
		   for(int j=0; j<m; j++)
		      {
			   if(VM[j]<=250)
			   {
				   Cost_Factor=0.0084;   
			   }
			   else if (VM[j]>250 && VM[j]<=500)
			   {
				   Cost_Factor=0.0255; 
			   }
			   else if (VM[j]>500 && VM[j]<=750)
			   {
				   Cost_Factor=0.0501; 
			   }
			   
			   else  
			     {Cost_Factor=.102;}
			 //  System.out.println("Cost factor value is "+Cost_Factor);
			   Cost =  Math.ceil(Makespan[j])* Cost_Factor;
			   Total_Cost = Total_Cost + Cost;
		   }
		   System.out.println("Total Cost is : "+Total_Cost);
		//   Fitness_value=Total_Cost * .5+ Total_ETime * .5;
		         return Total_Cost; 	
		//  return  Fitness_value;                 
		          
	}
}     */
	
/*	 public static int GetCost(int x, int y)
	{  
		   int i=x;
		   int j=y;
		Cost[i][j]=Task[j]/VM[i];
		int value=(int) Cost[i][j];
		return value;
	}  	*/
	
