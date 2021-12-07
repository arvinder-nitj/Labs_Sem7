package schedulingPSO;
// the code is for 2-dimensional space problem
// but you can easily modify it to solve higher dimensional space problem

import java.util.Random; 
import java.util.Vector;

//import BasicPSO.ProblemSet; 

public class PSOProcess implements PSOConstants {
	 double[] W=new double[MAX_ITERATION];
	 double[]AbsVelocity=new double[ MAX_ITERATION];
	 double[]VelIdeal=new double[ MAX_ITERATION];
	 static int counter =0;
	  
	private Vector<Particle> swarm = new Vector<Particle>();
	private double[] pBest = new double[SWARM_SIZE];
	  int n=2000; int m=1000;

	private Vector<Location> pBestLocation = new Vector<Location>();
	private double gBest;
	private Location gBestLocation;
	private double[] fitnessValueList = new double[SWARM_SIZE];

	private double[] loc=new double [n];
	double[] vel = new double [n];
	Random generator = new Random();
	
	 public void execute() {
		initializeSwarm();
		updateFitnessList();
		
		    for(int i=0; i<SWARM_SIZE; i++) {
			pBest[i] = fitnessValueList[i];
		//    System.out.println("fitness value is :"+pBest[i]);
			pBestLocation.add(swarm.get(i).getLocation());
		        }
			
		int t = 0;
		double w;
		double err = 9999;
		
		while(t < MAX_ITERATION && err > ProblemSet.ERR_TOLERANCE) {
			// System.out.println("error tolerance..."+ProblemSet.ERR_TOLERANCE);
			// step 1 - update pBest
	//	  System.out.println("iteration is........ :"+t);
			for(int i=0; i<SWARM_SIZE; i++) {
				if(fitnessValueList[i] < pBest[i]) {
					pBest[i] = fitnessValueList[i];
					// System.out.println("pBest value is :"+pBest[i]);
					}
				else
				{
					//System.out.println("pBest value is :"+pBest[i]);
				}  	pBestLocation.set(i, swarm.get(i).getLocation());
			}
			
							// step 2 - update gBest
			
			int bestParticleIndex = PSOUtility.getMinPos(fitnessValueList);
			
			if(t == 0 || fitnessValueList[bestParticleIndex] < gBest) 
			{
				gBest = fitnessValueList[bestParticleIndex];
				System.out.println("gBest particle position is "+bestParticleIndex);
				System.out.println("Particle gbest is :"+gBest);
				gBestLocation = swarm.get(bestParticleIndex).getLocation();
			}
			else
			{  
				System.out.println("gbest is :"+gBest);
				gBestLocation = swarm.get(bestParticleIndex).getLocation();
				
			}
			
		//	int[] bestParticleIndex = PSOUtility.getMinPos(fitnessValueList);
		//  define the value of w for better exploration and exploitation 	
			double AvgVel =0; double AbsVel=0;
			for(int i=0; i<SWARM_SIZE; i++) 
			{
				Particle p = swarm.get(i);
			for(int j=0; i<n; i++)
			{
				AvgVel= AvgVel+ p.getVelocity().getPos()[j];
				
			}
			}		
			/*    double Wmax=.9; double Wmin=.3; 
			    
				//System.out.println("average velocity is :"+AvgVel);
				AbsVel=AvgVel/(n*SWARM_SIZE);
				AbsVelocity[t]=AbsVel;
				System.out.println("absolute average velocity is :"+AbsVelocity[t]);
				
				double Xmax=5; double Xmin=-5;   
				double Vstart=(Xmax-Xmin)/2;
				double Tend=.95*MAX_ITERATION;
			    double value=(t*3.14)/Tend;
			    double Videal= Vstart* (1+Math.cos(value))/2;    
			    VelIdeal[t]=Videal;  
			    System.out.println("ideal velocity is :"+VelIdeal[t]);	
			  
				 W[counter]=.90; 
			    
			    if(t>0)
			    {
//			    	System.out.println("Before Condition");
//			    	System.out.println("Absoulute : "+AbsVelocity[t-1]);
//		    		System.out.println("Ideal : "+VelIdeal[t-1]);
			    	
			    	if(AbsVelocity[t-1] >= VelIdeal[t])
			    	{
			    		System.out.println("Absoulute : "+AbsVelocity[t-1]);
			    		System.out.println("Ideal : "+VelIdeal[t]);
			    		counter++;
			    //		System.out.println("W counter : "+W[counter-1]);
			    //		System.out.println("WMin : "+Wmin);
			    		W[counter]=Math.max(W[counter-1]-.1, Wmin);
			//    		System.out.println("value of W is :..."+W[counter-1]);
			    	
			    	}
			    	else if(AbsVelocity[t-1]< VelIdeal[t])
			
			    	{
//			    		System.out.println("Absoulute : "+AbsVelocity[t-1]);
//			    		System.out.println("Absoulute : "+VelIdeal[t]);
			    		counter++;
//			    		System.out.println("W counter : "+W[counter-1]);
//			    		System.out.println("WMin : "+Wmin);
			    		W[counter]=Math.min(W[counter-1]+.1, Wmax);
			    //		System.out.println("value of W is :..."+W[counter-1]);
			    	}
			     
			    }   */
		//	    System.out.println("Counter : "+counter);
		//	    System.out.println("Value of W : "+W[counter]);
			    
//			    System.out.println("value of interia weight is :"+W[t]);
		
			w = W_UPPERBOUND - (((double) t) / MAX_ITERATION) * (W_UPPERBOUND - W_LOWERBOUND);
			// System.out.println("Value of W is : "+w);
			    
			
		//	double C1=2.5-(((double) t) / MAX_ITERATION) * 2;
		//	double C2=.5+(((double) t) / MAX_ITERATION) * 2;
		  
			for(int k=0; k<SWARM_SIZE; k++) {
				double r1 = generator.nextDouble();
				double r2 = generator.nextDouble();
				
				Particle p = swarm.get(k);
				
				// step 3 - update velocity
				 
				double[] newVel = new double [n];
				
					for( int j=0; j<n; j++)
					 {
						newVel [j] = ((w * p.getVelocity().getPos()[j]) + 
						(r1 * C1)*(pBestLocation.get(k).getLoc()[j] - p.getLocation().getLoc()[j]) +
						(r2 * C2) * (gBestLocation.getLoc()[j] - p.getLocation().getLoc()[j])); 
						if(newVel[j]<0 || newVel[j]>4 )
						{
							
							newVel[j]=ProblemSet.VEL_LOW + generator.nextDouble() * (ProblemSet.VEL_HIGH - ProblemSet.VEL_LOW);;
						}
					//	System.out.print("Velocity of particle is:"+newVel [j]);
					//	System.out.println();
						   //     newVel[j]=	Abs_Vel(newVel[j]);	
						       
					  }
				//	System.out.print("New Velocity_Array of particle is:");
					 for(int j=0; j<n; j++)
					 {
						// System.out.print(newVel [j]+" ");
					 }
					Velocity vel = new Velocity(newVel);
					p.setVelocity(vel);	
					
					// step 4 - update Position of particle..
					
			//	System.out.println();
				double[] newLoc = new double [n];	
				for(int j=0; j<n; j++)
				{
					 
					newLoc[j]=p.getLocation().getLoc()[j]+newVel [j];
				//	System.out.print("Location of particle is:"+newLoc[j]);
				//	System.out.println();
				   if(newLoc[j]>(m-1))
				   {		   
						newLoc[j]=newLoc[j]%m;
					}
					
				}  
			/*	System.out.print("New Position_Array of particle is:");
				 for(int j=0; j<n; j++)
				 {
					 System.out.print((int) newLoc[j]+"   ");
				 }
				 System.out.println();  */
				Location loc = new Location(newLoc); 
				p.setLocation(loc);					
			}
		//	err = ProblemSet.evaluate(gBestLocation) - 0; // minimizing the functions means it's getting closer to 0
			
				t++;
			
			updateFitnessList();
			}
		
	 }

	public void initializeSwarm() {
		Particle p;
		for(int i=0; i<SWARM_SIZE; i++) {
			p = new Particle();
			loc=new Solution().execute1();
			
			Location location = new Location(loc);
			
			// randomize velocity in the range defined in Problem Set
			
			vel=new Solution().execute2();
			Velocity velocity = new Velocity(vel);
			
			p.setLocation(location);
			p.setVelocity(velocity);
			swarm.add(p);
		}
	}
	
	public void updateFitnessList() {
		for(int i=0; i<SWARM_SIZE; i++) {
			fitnessValueList[i] = swarm.get(i).getFitnessValue();
		}
	}
	
	
	
}
