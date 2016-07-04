package clustering;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Cluster implements Iterable<Individual> {

	private int nbVariables;
	private List<Individual> individuals;
	private Individual gravityCenter;
	
	private List<Double> sums;
	private List<Double> squareSum;
	private List<Double> means;
	private List<Double> variances;
	
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
	
	public Individual getIndividual(int index) {
		return this.individuals.get(index);
	}

	public int getNbIndividuals() {
		return this.individuals.size();
	}
	
	public int getNbVariables() {
		return this.nbVariables;
	}
	
	public Individual getGravityCenter() {
		return this.gravityCenter;
	}
	
	
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
	
	public boolean remove(int index) {
		Individual individual = this.individuals.get(index);
		return this.remove(individual);
	}
	
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
