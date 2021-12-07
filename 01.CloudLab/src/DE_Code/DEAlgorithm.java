package DE_Code;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Vm;
import utility.Constants;


public class DEAlgorithm {
	private int populationSize;
	public static List<Cloudlet> cloudletList;
	public static List<Vm> vmList;

	private double MUTATION_FACTOR;
	private double CROSSOVER_RATE;
	
	public DEAlgorithm(int populationSize, double MUTATION_FACTOR, double CROSSOVER_RATE,List<Cloudlet> cloudletList, List<Vm> vmList) {
		this.populationSize = populationSize;
		this.MUTATION_FACTOR = MUTATION_FACTOR;
		this.CROSSOVER_RATE = CROSSOVER_RATE;
		DEAlgorithm.cloudletList = cloudletList;
		DEAlgorithm.vmList = vmList;
	}

	public Population initPopulation(int chromosomeLength) {
		// Initialize population
		Population population = new Population(this.populationSize, chromosomeLength);
		return population;
	}
	
	public Population copyPopulation(Population population) {
		Population copypopulation = new Population(population);
		return copypopulation;
	}

	public double calcFitness(Individual individual) {
		
		double fitness = 0;
		
		for (int geneIndex = 0; geneIndex < individual.getChromosomeLength(); geneIndex++) {
			int vmIndex = individual.getGene(geneIndex);
			//double cost = cloudletList.get(geneIndex).getProcessingCost()/ vmList.get(geneIndex).getMips();
			try {
			double cost = cloudletList.get(geneIndex).getCloudletLength()/ vmList.get(vmIndex).getMips();	
			fitness += cost;
			}catch(Exception e) {
				Log.printLine("Error in GA:"+e.getMessage()+ " +->" + geneIndex);
			}
		}
		return fitness;
	}

	public void evalPopulation(Population population) {
		
		// Loop over population evaluating individuals and suming population fitness
		double populationFitness=0;

		for (Individual individual : population.getIndividuals()) {

			double individualFitness = calcFitness(individual); 
			individual.setFitness(individualFitness);
			populationFitness+=individualFitness;
		
		}
		population.setPopulationFitness(populationFitness);

	}

	public Individual selectParent(Population population) {
		// Get individuals
		Individual individuals[] = population.getIndividuals();

		// Spin roulette wheel
		double populationFitness = population.getPopulationFitness();
		double rouletteWheelPosition = Math.random() * populationFitness;

		// Find parent
		double spinWheel = 0;
		for (Individual individual : individuals) {
			spinWheel += individual.getFitness();
			if (spinWheel >= rouletteWheelPosition) {
				return individual;
			}
		}
		return individuals[population.size() - 1];
	}

	
	public Population crossoverPopulation(Population population, Population mutatedPopulation) {
		// Create new population
		Population newPopulation = new Population(population.size());
		// Loop over current population
		for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
			Individual individual_genx = population.getIndividual(populationIndex);
			Individual mutatedVector   = mutatedPopulation.getIndividual(populationIndex);
			
			
			int clen = Constants.NO_OF_TASKS;
			Individual individual_genxPlus1 = new Individual(clen);
			
			double rangeMin = 0.0f;
		    double rangeMax = 1.0f;
		    Random rn = new Random();
		    
		    for(int geneIndex=0;geneIndex<clen;geneIndex++) {
		    	double crossProb = rangeMin + (rangeMax - rangeMin) * rn.nextDouble();
		    	int geneSelected = 0;
		    	if (this.CROSSOVER_RATE > crossProb) {
		    		geneSelected = mutatedVector.getGene(geneIndex);
		    	}else {
		    		geneSelected = individual_genx.getGene(geneIndex);
		    	}
		  
		    	individual_genxPlus1.setGene(geneIndex, geneSelected);
		    	
		    }
		    
		    newPopulation.setIndividual(populationIndex, individual_genxPlus1);
		}

		return newPopulation;
	}
	
	
	public Population Selection(Population population, Population alteredPopulation) {
		// Create new population
		evalPopulation(population);
		evalPopulation(alteredPopulation);		
		Population newPopulation = new Population(population.size());
		
		for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
			
			Individual orignalIndividual = population.getIndividual(populationIndex);
			Individual alteredIndividual = alteredPopulation.getIndividual(populationIndex);
			if(orignalIndividual.getFitness() >= alteredIndividual.getFitness()) {
				newPopulation.setIndividual(populationIndex, orignalIndividual);				
			}else {
				newPopulation.setIndividual(populationIndex, alteredIndividual);
			}		    
		}

		return newPopulation;
	}
	
	public Population getMutatedVectors(Population population) {
		return mutatePopulation(population);
	}

	public Population mutatePopulation(Population population) {
		// Initialize new population
		Population mutatedPopulation = new Population(this.populationSize);
		Random rn = new Random();

		// Loop over current population by fitness
		for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
			Individual individual = population.getIndividual(populationIndex);
			ArrayList<Integer> randomVectorIndex = new ArrayList<Integer>();			
			
			while(randomVectorIndex.size()<3) {
				int index = rn.nextInt(population.size())%population.size();					
				if(index!= populationIndex && randomVectorIndex.contains(index)==false) {
					randomVectorIndex.add(index);
				}
			}
			
			Individual mutatedvector = new Individual(individual.getChromosomeLength());
			int a = randomVectorIndex.get(0);
			int b = randomVectorIndex.get(1);
			int c = randomVectorIndex.get(2);
			// Loop over individual's genes
			for (int geneIndex = 0; geneIndex < individual.getChromosomeLength(); geneIndex++) {
				int mutatedVm = (int) ((Math.floor(a+MUTATION_FACTOR*(Math.abs(b-c)))) % Constants.NO_OF_VMS);
				mutatedvector.setGene(geneIndex, mutatedVm);
			}

			// Add individual to population
			mutatedPopulation.setIndividual(populationIndex, mutatedvector);
		}
		// Return mutated population
		return mutatedPopulation;
	}

}
