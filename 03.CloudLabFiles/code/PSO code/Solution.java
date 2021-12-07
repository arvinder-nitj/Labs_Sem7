package schedulingPSO;

import java.util.ArrayList; 
import java.util.List;
import java.util.Random;

// import BasicPSO.ProblemSet;
public class Solution {     
	       int n=2000;
		double[] Vel_Array=new double[n];
		double[] Pos_Array=new double[n];
		Random generator = new Random();
	     static int i=0;
		 public double[] execute1()
		 {
			// System.out.println(i+"th Position_Array:"); i++;
	          //List<List<Integer>> list = new ArrayList<List<Integer>>();
               int x=0; int y=1000;
               for(int j=0; j<n; j++)
               {
            	   int number= (int) (Math.random()*(y-x));
            	//   int number= (int)(ProblemSet.LOC_X_LOW+ generator.nextDouble() * (ProblemSet.LOC_X_HIGH - ProblemSet.LOC_X_LOW));
            	   Pos_Array[j]=number;	   
                }

        //	   System.out.print("Position Array is:");
               for(int j=0; j<n; j++)
               {
            	 //  System.out.print(Pos_Array[j]+" ");
            	  // System.out.println();
               }   
               System.out.println();
            return   Pos_Array;
	 }
	   
	public double[]execute2()
	{ 
	 //  int x=0; int y=7;
       for(int j=0; j<n; j++)
       {
    	   double number = ProblemSet.VEL_LOW + generator.nextDouble() * (ProblemSet.VEL_HIGH - ProblemSet.VEL_LOW);
    	 //  int number= (int) (x+Math.random()*(y-x));
    	   Vel_Array[j]=number;	   
        }
   //    System.out.print("Velocity Array is:");
       for(int j=0; j<n; j++)
       {
    	  // System.out.print(Vel_Array[j]+" ");
       }
    //   System.out.println();	
       return Vel_Array;	
	}
}



