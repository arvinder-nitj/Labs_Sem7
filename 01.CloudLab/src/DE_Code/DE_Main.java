package DE_Code;

import utility.Constants;
import utility.FileArtifactsPair;
import utility.ReadFile;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.CloudletSchedulerSpaceShared;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.UtilizationModelFull;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.VmAllocationPolicySimple;
import org.cloudbus.cloudsim.VmSchedulerTimeShared;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;
import org.json.JSONException;

public class DE_Main {
	
	/** The Cloudlet list. */
	public static List<Cloudlet> CloudletList;

	/** The vmlist. */
	public static List<Vm> vmlist;

	public static List<Vm> createVM(int userId, int vms) {

		//Creates a container to store VMs. This list is passed to the broker later
		LinkedList<Vm> list = new LinkedList<Vm>();

		//VM Parameters
		long size = 10000; //image size (MB)
		int ram = 512; //vm memory (MB)
		int mips = 500;
		long bw = 1000;
		int pesNumber = 1; //number of cpus
		String vmm = "Xen"; //VMM name

		//create VMs
		Vm[] vm = new Vm[vms];
		for(int i=0;i<vms;i++){
			
			double upperLimit = 1000;
	 	    int mips_i= (int) (mips +(Math.random()/1000*(upperLimit - mips)));
	 	    vm[i] = new Vm(i, userId, mips_i, pesNumber, ram, bw, size, vmm, new CloudletSchedulerSpaceShared());
			
			//vm[i] = new Vm(i, userId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerSpaceShared());
			//for creating a VM with a space shared scheduling policy for Cloudlets:
			//vm[i] = Vm(i, userId, mips, pesNumber, ram, bw, size, priority, vmm, new CloudletSchedulerSpaceShared());

			list.add(vm[i]);
		}

		return list;
	}


	public static List<Cloudlet> createCloudlet(int userId, int Cloudlets) throws IOException, JSONException{
		// Creates a container to store Cloudlets
		LinkedList<Cloudlet> list = new LinkedList<Cloudlet>();
		
		
		if(Constants.UseFile) {
			ArrayList<FileArtifactsPair> Lengths =  ReadFile.getCloudletJobs(Constants.FilePath);
			//cloudlet parameters
			//long length = 1000;
			long fileSize = 300;
			//long outputSize = 300;
			int pesNumber = 1;
			UtilizationModel utilizationModel = new UtilizationModelFull();

			Cloudlet[] cloudlet = new Cloudlet[Lengths.size()];

			for(int i=0;i<cloudlet.length;i++) {
			//	Random r=new Random();
//				long curfileSize = fileSize + ((long)Math.random()%753);
				long curfileSize = fileSize;
				cloudlet[i] = new Cloudlet(i, Lengths.get(i).getLen(), pesNumber, curfileSize, Lengths.get(i).getOS(), utilizationModel, utilizationModel, utilizationModel);
				// setting the owner of these Cloudlets
				cloudlet[i].setUserId(userId);
				list.add(cloudlet[i]);
			}
			
		}else {
			// Cloudlet parameters
			long length = 1000;
			long fileSize = 300;
			long outputSize = 300;
			int pesNumber = 1;
			UtilizationModel utilizationModel = new UtilizationModelFull();

			Cloudlet[] Cloudlet = new Cloudlet[Cloudlets];
			
			
			for(int i=0;i<Cloudlets;i++){
				Random random = new Random();
				Cloudlet[i] = new Cloudlet(i, length + random.nextInt(2000), pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
				// Setting the owner of these Cloudlets
				Cloudlet[i].setUserId(userId);
				list.add(Cloudlet[i]);
			}
		}

		

		return list;
	}


	////////////////////////// STATIC METHODS ///////////////////////

	/**
	 * Creates main() to run this example
	 */
	public static void main(String[] args) {
		Log.printLine("Starting Main Program...");

		try {
			// First step: Initialize the CloudSim package. It should be called
			// before creating any entities.
			int num_user = 1;   // number of grid users
			Calendar calendar = Calendar.getInstance();
			boolean trace_flag = false;  // mean trace events

			// Initialize the CloudSim library
			CloudSim.init(num_user, calendar, trace_flag);

			// Second step: Create Datacenters
			//Datacenters are the resource providers in CloudSim. We need at list one of them to run a CloudSim simulation
			@SuppressWarnings("unused")
			Datacenter datacenter0 = createDatacenter("Datacenter_0");
			@SuppressWarnings("unused")
			Datacenter datacenter1 = createDatacenter("Datacenter_1");
			@SuppressWarnings("unused")
			Datacenter datacenter2 = createDatacenter("Datacenter_2");
			@SuppressWarnings("unused")
			Datacenter datacenter3 = createDatacenter("Datacenter_3");
			@SuppressWarnings("unused")
			Datacenter datacenter4 = createDatacenter("Datacenter_4");

			//Third step: Create Broker
			DatacenterBroker broker = createBroker();
			int brokerId = broker.getId();

			//Fourth step: Create VMs and Cloudlets and send them to broker
			vmlist = createVM(brokerId,Constants.NO_OF_VMS); //creating 3 vms
			CloudletList = createCloudlet(brokerId,Constants.NO_OF_TASKS); // creating 5 Cloudlets
			

			broker.submitVmList(vmlist);
			broker.submitCloudletList(CloudletList);
			//Sixth step GeneticAlgorithm
			
			// -----------------------------------------logic starts here---------------------------------------------
			DEAlgorithm de = new DEAlgorithm(Constants.POPULATION_SIZE,
													   Constants.mutationRate , 
													   Constants.crossoverRate, 
													   CloudletList, 
													   vmlist);
			// Initialize population
			System.out.println("Population Initialization");
			int chromosomeLength = Constants.NO_OF_TASKS;
			Population population = de.initPopulation(chromosomeLength);

			// Evaluate population
			de.evalPopulation(population);
			
			int iteration = 1;
			while (iteration <= Constants.NO_OF_ITERATIONS) 
			{	
				// get fittest individual from population in every iteration
				Individual fit = population.getFittest(0);
				
				System.out.print("Fittest: ");
				for(int j=0;j<fit.chromosome.length;j++) {
					System.out.print(fit.chromosome[j] + " ");
				}
				System.out.println("\n  fitness => " + fit.getFitness());
				
				for(int j=0;j<fit.chromosome.length;j++)
				{
					broker.bindCloudletToVm(j, fit.chromosome[j]);
				}
				//List<Cloudlet> newList = broker.getCloudletReceivedList();

				// Apply mutation
				Population tempP = de.getMutatedVectors(population);
				// Apply crossover
				tempP = de.crossoverPopulation(population, tempP);
				//selection and get next generation
				population = de.Selection(population, tempP);

				// Evaluate population
				de.evalPopulation(population);

				// Increment the current generation
				iteration++;
				
			}
			System.out.println("Best solution of DE: " + population.getFittest(0).toString());
			

			// -----------------------------------------logic ends here---------------------------------------------
			

			// Seventh step: Starts the simulation
			CloudSim.startSimulation();

			// Final step: Print results when simulation is over
			List<Cloudlet> newList = broker.getCloudletReceivedList();

			CloudSim.stopSimulation();

			printCloudletList(newList);

			Log.printLine("DE finished!");
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Log.printLine("The simulation has been terminated due to an unexpected error");
		}
	}

	private static Datacenter createDatacenter(String name){

		// Here are the steps needed to create a PowerDatacenter:
		// 1. We need to create a list to store one or more
		//    Machines
		List<Host> hostList = new ArrayList<Host>();

		// 2. A Machine contains one or more PEs or CPUs/Cores. Therefore, should
		//    create a list to store these PEs before creating
		//    a Machine.
		List<Pe> peList1 = new ArrayList<Pe>();

		int mips = 1000;

		// 3. Create PEs and add these into the list.
		//for a quad-core machine, a list of 4 PEs is required:
		peList1.add(new Pe(0, new PeProvisionerSimple(mips))); // need to store Pe id and MIPS Rating
		peList1.add(new Pe(1, new PeProvisionerSimple(mips)));
		peList1.add(new Pe(2, new PeProvisionerSimple(mips)));
		peList1.add(new Pe(3, new PeProvisionerSimple(mips)));

		//Another list, for a dual-core machine
		List<Pe> peList2 = new ArrayList<Pe>();

		peList2.add(new Pe(0, new PeProvisionerSimple(mips)));
		peList2.add(new Pe(1, new PeProvisionerSimple(mips)));

		//4. Create Hosts with its id and list of PEs and add them to the list of machines
		int hostId=0;
		int ram = 2048; //host memory (MB)
		long storage = 1000000; //host storage
		int bw = 10000;

		hostList.add(
    			new Host(
    				hostId,
    				new RamProvisionerSimple(ram),
    				new BwProvisionerSimple(bw),
    				storage,
    				peList1,
    				new VmSchedulerTimeShared(peList1)
    			)
    		); // This is our first machine

		hostId++;

		hostList.add(
    			new Host(
    				hostId,
    				new RamProvisionerSimple(ram),
    				new BwProvisionerSimple(bw),
    				storage,
    				peList2,
    				new VmSchedulerTimeShared(peList2)
    			)
    		); // Second machine


		//To create a host with a space-shared allocation policy for PEs to VMs:
		//hostList.add(
    	//		new Host(
    	//			hostId,
    	//			new CpuProvisionerSimple(peList1),
    	//			new RamProvisionerSimple(ram),
    	//			new BwProvisionerSimple(bw),
    	//			storage,
    	//			new VmSchedulerSpaceShared(peList1)
    	//		)
    	//	);

		//To create a host with a oportunistic space-shared allocation policy for PEs to VMs:
		//hostList.add(
    	//		new Host(
    	//			hostId,
    	//			new CpuProvisionerSimple(peList1),
    	//			new RamProvisionerSimple(ram),
    	//			new BwProvisionerSimple(bw),
    	//			storage,
    	//			new VmSchedulerOportunisticSpaceShared(peList1)
    	//		)
    	//	);


		// 5. Create a DatacenterCharacteristics object that stores the
		//    properties of a data center: architecture, OS, list of
		//    Machines, allocation policy: time- or space-shared, time zone
		//    and its price (G$/Pe time unit).
		String arch = "x86";      // system architecture
		String os = "Linux";          // operating system
		String vmm = "Xen";
		double time_zone = 10.0;         // time zone this resource located
		double cost = 3.0;              // the cost of using processing in this resource
		double costPerMem = 0.05;		// the cost of using memory in this resource
		double costPerStorage = 0.1;	// the cost of using storage in this resource
		double costPerBw = 0.1;			// the cost of using bw in this resource
		LinkedList<Storage> storageList = new LinkedList<Storage>();	//we are not adding SAN devices by now

		DatacenterCharacteristics characteristics = new DatacenterCharacteristics(
                arch, os, vmm, hostList, time_zone, cost, costPerMem, costPerStorage, costPerBw);


		// 6. Finally, we need to create a PowerDatacenter object.
		Datacenter datacenter = null;
		try {
			datacenter = new Datacenter(name, characteristics, new VmAllocationPolicySimple(hostList), storageList, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return datacenter;
	}

	//We strongly encourage users to develop their own broker policies, to submit vms and Cloudlets according
	//to the specific rules of the simulated scenario
	private static DatacenterBroker createBroker(){

		DatacenterBroker broker = null;
		try {
			broker = new DatacenterBroker("Broker");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return broker;
	}

	/**
	 * Prints the Cloudlet objects
	 * @param list  list of Cloudlets
	 */
	private static void printCloudletList(List<Cloudlet> list) {
		
		HashSet<Integer> vmCnt       = new HashSet<Integer>();
		HashSet<Integer> cloudletCnt = new HashSet<Integer>();
		double []vmTimeUtilized      = new double[Constants.NO_OF_VMS];
		double makeSpanTime          = 0;
		
		int size = list.size();
		Cloudlet Cloudlet;

		String indent = "    ";
		Log.printLine();
		Log.printLine("========== OUTPUT ==========");
		Log.printLine("Cloudlet ID" + indent + "STATUS" + indent +
				"Data center ID" + indent + "VM ID" + indent + indent + "Time" + indent + "Start Time" + indent + "Finish Time");

		DecimalFormat dft = new DecimalFormat("###.##");
		for (int i = 0; i < size; i++) {
			Cloudlet = list.get(i);
			Log.print(indent + Cloudlet.getCloudletId() + indent + indent);

			if (Cloudlet.getCloudletStatus() == Cloudlet.SUCCESS){
				Log.print("SUCCESS");

				Log.printLine( indent + indent + Cloudlet.getResourceId() + indent + indent + indent + Cloudlet.getVmId() +
						indent + indent + indent + dft.format(Cloudlet.getActualCPUTime()) +
						indent + indent + dft.format(Cloudlet.getExecStartTime())+ indent + indent + indent + dft.format(Cloudlet.getFinishTime()));
				
				cloudletCnt.add(Cloudlet.getCloudletId());
				vmCnt.add(Cloudlet.getVmId());
				vmTimeUtilized[Cloudlet.getVmId()]+= Cloudlet.getActualCPUTime();				
				makeSpanTime = Math.max(makeSpanTime, Cloudlet.getFinishTime());
				
				//double v = vmlist.get(Cloudlet.getVmId()).getTotalUtilizationOfCpuMips(makeSpanTime);
			}
		}
		
		Log.printLine("-----------------------------------------------------------------");
		Log.printLine("Succesfully executed cloudlets = "+ cloudletCnt.size());
		Log.printLine("-----------------------------------------------------------------");
		Log.printLine("Vms used                       = "+ vmCnt.size());
		Log.printLine("-----------------------------------------------------------------");
		for(int i:vmCnt) {
			Log.printLine("Vm no: "+i+"   used for  ->   " + vmTimeUtilized[i]);
		}
		Log.printLine("-----------------------------------------------------------------");
		Log.printLine("MakeSpan Time                  = "+ makeSpanTime);

	}
}
