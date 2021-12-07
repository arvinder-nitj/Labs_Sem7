package DE_Code;

import java.util.Random;

import utility.Constants;


public class Individual {
	public int[] chromosome;
	private double fitness = -1;

	public Individual(int[] chromosome) {
		this.chromosome = chromosome;
	}
	
	public Individual(Individual individual) {
		int chromosomeLength = individual.getChromosomeLength();
		this.chromosome = new int[chromosomeLength];
		for (int gene = 0; gene < chromosomeLength; gene++) {
			int vmId = individual.getGene(gene);
			this.setGene(gene, vmId);
		}
	}


	public Individual(int chromosomeLength) {

		this.chromosome = new int[chromosomeLength];
		Random r = new Random();
		for (int gene = 0; gene < chromosomeLength; gene++) {
			//double r = Math.random();
			/*
			 *  // for 3 vms
			if(r < 0.3333) this.setGene(gene, 0);
			else if(r < 0.66) this.setGene(gene, 1);
			else this.setGene(gene, 2);
			*/
			
			int vmId = r.nextInt(Constants.NO_OF_VMS) % Constants.NO_OF_VMS;
			this.setGene(gene, vmId);
		}

	}


	public int[] getChromosome() {
		return this.chromosome;
	}

	public int getChromosomeLength() {
		return this.chromosome.length;
	}

	public void setGene(int offset, int gene) {
		this.chromosome[offset] = gene;
	}

	public int getGene(int offset) {
		return this.chromosome[offset];
	}

	
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public double getFitness() {
		return this.fitness;
	}
	
	
	
	public String toString() {
		String output = "";
		for (int gene = 0; gene < this.chromosome.length; gene++) {
			output += this.chromosome[gene];
		}
		return output;
	}
}
