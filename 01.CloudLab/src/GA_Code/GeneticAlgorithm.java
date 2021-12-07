package GA_Code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.UtilizationModelFull;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.lists.VmList;

import utility.Constants;


public class GeneticAlgorithm {
	private int populationSize;
	public static List<Cloudlet> cloudletList;
	public static List<Vm> vmList;

	private double mutationRate;
	private double crossoverRate;
	private int elitismCount;

	public GeneticAlgorithm(int populationSize, double mutationRate, double crossoverRate, int elitismCount, List<Cloudlet> cloudletList, List<Vm> vmList) {
		this.populationSize = populationSize;
		this.mutationRate = mutationRate;
		this.crossoverRate = crossoverRate;
		this.elitismCount = elitismCount;
		GeneticAlgorithm.cloudletList = cloudletList;
		GeneticAlgorithm.vmList = vmList;
	}

	public Population initPopulation(int chromosomeLength) {
		// Initialize population
		Population population = new Population(this.populationSize, chromosomeLength);
		return population;
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

	
	public Population crossoverPopulation(Population population) {
		// Create new population
		Population newPopulation = new Population(population.size());

		// Loop over current population by fitness
		for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
			Individual parent1 = population.getFittest(populationIndex);

			// Apply crossover to this individual?
			if (this.crossoverRate > Math.random()&& populationIndex > this.elitismCount ) {
				// Initialize offspring
				Individual offspring = new Individual(parent1.getChromosomeLength());
				
				// Find second parent
				Individual parent2 = selectParent(population);

				// Loop over genome
				for (int geneIndex = 0; geneIndex < parent1.getChromosomeLength(); geneIndex++) {
					// Use half of parent1's genes and half of parent2's genes
					if (0.5 > Math.random()) {
						offspring.setGene(geneIndex, parent1.getGene(geneIndex));
					} else {
						offspring.setGene(geneIndex, parent2.getGene(geneIndex));
					}
				}

				// Add offspring to new population
				newPopulation.setIndividual(populationIndex, offspring);
			} else {
				// Add individual to new population without applying crossover
				newPopulation.setIndividual(populationIndex, parent1);
			}
		}

		return newPopulation;
	}

	public Population mutatePopulation(Population population) {
		// Initialize new population
		Population newPopulation = new Population(this.populationSize);
		Random r = new Random();

		// Loop over current population by fitness
		for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
			Individual individual = population.getFittest(populationIndex);

			// Loop over individual's genes
			for (int geneIndex = 0; geneIndex < individual.getChromosomeLength(); geneIndex++) {
				// Skip mutation if this is an elite individual
				if (populationIndex > this.elitismCount) {
					// Does this gene need mutation?
					if (this.mutationRate > Math.random()) {
						// Get new gene
						
						/*
						int newGene=0;
						if (individual.getGene(geneIndex) == 0) {
							double r=Math.random();
							if(r>0.5)
							{
								newGene=1;
							}
							else
								newGene=2;
						}else if (individual.getGene(geneIndex) == 1) {
							double r=Math.random();
							if(r>0.5)
							{
								newGene=2;
							}
							else
								newGene=0;
						}
						else if (individual.getGene(geneIndex) == 2) {
							double r=Math.random();
							if(r>0.5)
							{
								newGene=0;
							}
							else
								newGene=1;
						}
						*/
						
						int newGene = r.nextInt(Constants.NO_OF_VMS) % Constants.NO_OF_VMS;
						// Mutate gene
						individual.setGene(geneIndex, newGene);
					}
				}
			}

			// Add individual to population
			newPopulation.setIndividual(populationIndex, individual);
		}

		// Return mutated population
		return newPopulation;
	}

}
