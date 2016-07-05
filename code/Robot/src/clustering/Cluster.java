package clustering;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a cluster (or a cloud). 
 * The cluster is composed by a list of individuals, and a gravity center (which is also an individual). 
 * 
 * In order to speed up some process (e.g. the gravity center calculation after adding an individual), 
 * other lists are used to memorize the sum, the mean and the variance of each variables in the cluster.  
 * 
 * @see clustering.Individual 
 * @author Maxime PINEAU
 */
public class Cluster implements Iterable<Individual> {

	private int nbVariables;
	private List<Individual> individuals;
	private Individual gravityCenter;
	
	private List<Double> sums;
	private List<Double> squareSum;
	private List<Double> means;
	private List<Double> variances;
	
	/**
	 * Creates a new cluster in which the individuals must have "nbVariables" variables.
	 * @param nbVariables the number of variables the individuals in the cluster must have 
	 */
	public Cluster(int nbVariables) {
		this.nbVariables = nbVariables;
		this.individuals = new ArrayList<Individual>();
		this.gravityCenter = new Individual(nbVariables); 
		
		
		this.sums = new ArrayList<Double>(this.nbVariables);
		this.squareSum = new ArrayList<Double>(this.nbVariables);
		this.means = new ArrayList<Double>(this.nbVariables);
		this.variances = new ArrayList<Double>(this.nbVariables);
		
		for(int v = 0; v < this.nbVariables; v++) {
			this.sums.add(0.0);
			this.squareSum.add(0.0);
			this.means.add(0.0);
			this.variances.add(0.0);
		}
		
	}
	
	/**
	 * Returns the individual at the given index
	 * @param index the index at which we want to get the individual 
	 * @return the individual at the given index 
	 */
	public Individual getIndividual(int index) {
		return this.individuals.get(index);
	}

	/**
	 * Returns the number of individuals in the cluster 
	 * @return the number of individuals in the cluster 
	 */
	public int getNbIndividuals() {
		return this.individuals.size();
	}
	
	/**
	 * Returns the number of variables the individuals in the cluster must have 
	 * @return the number of variables that the individuals must have 
	 */
	public int getNbVariables() {
		return this.nbVariables;
	}
	
	/**
	 * Returns the gravity center of the cluster, i.e. an individual in which each of his variables 
	 * correspond to the mean of the variable (the same index) of all the individuals in the cluster 
	 * @return the gravity center of the cluster 
	 */
	public Individual getGravityCenter() {
		return this.gravityCenter;
	}
	
	
	/**
	 * Adds a new individual to the cluster. 
	 * Changes the gravity center. 
	 * @param individual the added individual 
	 * @throws NumberOfVariablesException if the new individual don't have the same required number of variables 
	 */
	public void add(Individual individual) throws NumberOfVariablesException { 
		if(individual.getNbVariables() != this.nbVariables) throw new NumberOfVariablesException();
		
		this.individuals.add(individual);
		
		// https://fr.wikipedia.org/wiki/Variance_(statistiques_et_probabilit%C3%A9s) 
		for(int v = 0; v < this.nbVariables; v++) {
			double variable = individual.getVariable(v);
			
			this.sums.set(v,  this.sums.get(v) + variable);
			this.squareSum.set(v, this.squareSum.get(v) + (variable * variable));
			
			this.means.set(v, this.sums.get(v) / this.getNbIndividuals());
			this.variances.set(v,  (this.squareSum.get(v) / this.getNbIndividuals()) - this.means.get(v)*this.means.get(v)); 
			
			this.gravityCenter.setVariable(v, this.means.get(v));
		}
	}
	
	/**
	 * Removes the individual at the given index. 
	 * Changes the gravity center. 
	 * @param index the given index at which we want to remove the individual from the cluster 
	 * @return true if the individual has been removed, false otherwise 
	 */
	public boolean remove(int index) {
		Individual individual = this.individuals.get(index);
		return this.remove(individual);
	}
	
	/**
	 * Removes the individual from the cluster. 
	 * Changes the gravity center. 
	 * @param individual the individual to remove 
	 * @return true if the individual has been removed, false otherwise 
	 */
	public boolean remove(Individual individual) {
		boolean succes = this.individuals.remove(individual);
		
		if(!succes) return false;
		
		for(int v = 0; v < this.nbVariables; v++) {
			double variable = individual.getVariable(v);
			
			this.sums.set(v,  this.sums.get(v) - variable);
			this.squareSum.set(v, this.squareSum.get(v) - (variable * variable));
			
			this.means.set(v, this.sums.get(v) / this.getNbIndividuals());
			this.variances.set(v,  (this.squareSum.get(v) / this.getNbIndividuals()) - this.means.get(v)*this.means.get(v)); 
			// https://fr.wikipedia.org/wiki/Variance_(statistiques_et_probabilit%C3%A9s) 
			
			this.gravityCenter.setVariable(v, this.means.get(v));
		}		
		
		return true;
	}
	
	

	
	/**
	 * Center and reduce the variables of the individuals.
	 * After this operation, all the variables will be on the same scale. 
	 * 
	 * Changes the gravity center. 
	 * Changes the variable values of all the individuals. 
	 */
	public void centerAndReduce() {

		double biais = Math.sqrt(this.getNbIndividuals() / (this.getNbIndividuals() - 1));
		
		for(int v = 0; v < this.nbVariables; v++) {

			double mean = this.means.get(v);
			double variance = this.variances.get(v);
			double standardDeviation = Math.sqrt(variance);
			
			for(Individual individual : this.individuals) {
				double variable = individual.getVariable(v);
				variable = biais * (variable - mean) / standardDeviation;
				
				if(Double.isNaN(variable)) variable = 0.0;
				
				individual.setVariable(v, variable);
			}
			
		}
		
		this.gravityCenter = new Individual(this.means);
	}

	
	@Override
	public Iterator<Individual> iterator() {
		return this.individuals.iterator();
	}

	@Override
	public String toString() {
		return this.individuals.toString();
	}
	
	
	
}
