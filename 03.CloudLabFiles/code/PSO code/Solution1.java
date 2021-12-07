package schedulingPSO;

import java.util.Random;

public class Solution1 
{
	   
	   
	     public static void main(String[] args)
	     {
	    	 int n=10;
	     
	    	 double[] Pos_Array=new double[n];
	    	 int x=0; int y=4;
	     
	    	 for(int j=0; j<n; j++)
	            {
	         	   int number= (int) (Math.random()*(y-x));
	         	   Pos_Array[j]=number;	   
	             }
	            for(int j=0; j<n; j++)
	            {
	         	   System.out.print("Position Array is:" +Pos_Array[j]+" ");
	         	    System.out.println();
	           }
	            
	            Random rn = new Random();
                   for(int j=0; j<n; j++)
                   {
                	   int answer = rn.nextInt(4);
   	                  System.out.println(answer); 
                   }
	               
	            
	            
		
	     }   
}
