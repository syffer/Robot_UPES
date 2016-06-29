package clustering;

import java.util.ArrayList;
import java.util.List;

public class Individual implements Cloneable {
	
	private List<Double> variables;
	
	public Individual(int nbVariables) {
		this(new double[nbVariables]);
	}
	
	public Individual(double ... variables) {
		this.variables = new ArrayList<Double>(variables.length);
		
		for(Double variable : variables) {
			this.variables.add(variable);
		}	
	}
	
	public double getVariable(int index) {
		if(!this.isInBound(index)) throw new IndividualException(index);
		return this.variables.get(index);
	}
	
	public void setVariable(int index, double variable) {
		if(!this.isInBound(index)) throw new IndividualException(index);
		this.variables.set(index, variable);
	}
	
	public int getNbVariables() {
		return this.variables.size();
	}
	
	public boolean isInBound(int index) {
		return index >= 0 && index < this.variables.size();
	}
	
	
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
