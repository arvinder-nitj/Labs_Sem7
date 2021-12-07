package utility;

public class Constants {
	//public static final int NO_OF_DATA_CENTERS = 5; // number of Datacenters;
	
	public static final boolean UseFile = false;
	public static final String FilePath = "C:\\Users\\arvindersingh\\Desktop\\GA\\CloudLab\\config\\dax\\Montage_100.xml";
	
	public static final int NO_OF_VMS = 25; // number of VM;
    public static final int NO_OF_TASKS = 100; // number of Cloudlets;
    public static final int POPULATION_SIZE = 15; // Number of Particles.
    public static final int NO_OF_ITERATIONS = 15;
    
    //for GA
    public static final double mutationRate = 0.30;
    public static final double crossoverRate = 0.95;
    public static final int elitismCount =2;
    
    
    
    //for DE
    public static final double MUTATION_FACTOR = 1;
    public static final double CROSSOVER_RATE = 0.8;
}
