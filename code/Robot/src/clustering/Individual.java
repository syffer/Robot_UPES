package clustering;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an individual, a subject, an observation, or a line in a dataset.
 * An individual can have any number of variables. 
 * The variables are of type doubles (as we have  to apply mathematical operations on them). 
 * 
 * @see clustering.Cluster
 * @author Maxime PINEAU
 */
public class Individual implements Cloneable {
	
	private List<Double> variables;
	
	/**
	 * Creates a new individual with "nbVariables" variables.
	 * All the variables will be initialized to 0.
	 * @param nbVariables the number of variables the new individual have
	 */
	public Individual(int nbVariables) {
		this(new double[nbVariables]);
	}
	
	/**
	 * Creates a new individual which has all the variables given in parameters 
	 * @param variables the variables that the new individual has
	 */
	public Individual(double ... variables) {
		this.variables = new ArrayList<Double>(variables.length);
		
		for(double variable : variables) {
			this.variables.add(variable);
		}	
	}
	
	/**
	 * Creates a new individual from a list containing all the variables of the individual 
	 * @param variables tha variables that the new individual has 
	 */
	public Individual(List<Double> variables) {
		this.variables = new ArrayList<Double>(variables);
	}
	
	/**
	 * Returns the value of the variable at the given index
 	 * @param index the index of the variable we want to get 
	 * @return the value of the variable at the given index 
	 * @throws IndividualException if the index if out of range 
	 */
	public double getVariable(int index) {
		if(!this.isInBound(index)) throw new IndividualException(index);
		return this.variables.get(index);
	}
	
	/**
	 * Sets the variable at the given index with the given value 
	 * @param index the index of the variable we want to change 
	 * @param variable the new value of the variable at the given index 
	 * @throws IndividualException if the index if out of range 
	 */
	public void setVariable(int index, double variable) {
		if(!this.isInBound(index)) throw new IndividualException(index);
		this.variables.set(index, variable);
	}
	
	/**
	 * Returns the number of variables the individual has
	 * @return the number of variables the individual has 
	 */
	public int getNbVariables() {
		return this.variables.size();
	}
	
	/**
	 * Return true if the given index is in bound, false otherwise. 
	 * i.e. if the individual has a variable at the given index  
	 * @param index the given index we want to check 
	 * @return true there is a variable at the given index, false otherwise 
	 */
	public boolean isInBound(int index) {
		return index >= 0 && index < this.variables.size();
	}
	
	
	/**
	 * Returns the Euclidean distance between two individual by comparing there variables.
	 * The two individuals must have the same number of variables. 
	 * @param individual1 the first individual 
	 * @param individual2 the second individual 
	 * @return the Euclidean distance between the two individuals 
	 * @throws NumberOfVariablesException if the individuals don't have the same number of variables 
	 */
	public static double distanceEuclidienne(Individual individual1, Individual individual2) throws NumberOfVariablesException {
		if(individual1.getNbVariables() != individual2.getNbVariables()) throw new NumberOfVariablesException();
		
		double sum = 0.0;
		
		for(int v = 0; v < individual1.getNbVariables(); v++) {
			double variable_1 = individual1.getVariable(v);
			double variable_2 = individual2.getVariable(v);
			
			sum += (variable_1 - variable_2) * (variable_1 - variable_2);
		}
		
		return Math.sqrt(sum);
	}

	
	@Override
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer();
		
		stringBuffer.append("(");
		for(double variable : this.variables) {
			stringBuffer.append(variable + ", ");
		}
		stringBuffer.append(")");
		
		return stringBuffer.toString();
	}
	
	@Override
	public Individual clone() {
		
		try {
			
			Individual clone = (Individual) super.clone();
			
			clone.variables = new ArrayList<Double>();
			for(double variable : this.variables) {
				clone.variables.add(variable);
			}
			
	        return clone;
	        
		} catch( CloneNotSupportedException e ) {
			throw new InternalError("clonage impossible");
		}
		
	}
}
