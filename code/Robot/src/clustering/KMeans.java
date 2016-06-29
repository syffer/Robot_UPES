package clustering;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import transform.Utils;

public class KMeans {
	
	public KMeans() {
		
	}
	
	
	public List<Cluster> clustering(boolean centerReduce, Cluster data, int nbClasses) throws NumberOfVariablesException, KMeansException {
		if(nbClasses > data.getNbIndividuals()) throw new KMeansException();
		
		if(centerReduce) data.centerAndReduce();
		
		int[] classes = new int[data.getNbIndividuals()];
		List<Cluster> clusters = this.initializeClasses(data, nbClasses, classes);
		List<Individual> gravityCenters = this.getGravityCenters(clusters);
				
		//double inertieInter = 0.0;
		//double inertieInterPrec = 0.0;
		
		boolean change;		
		
		do {
			
			change = false;
			
			for(int i = 0; i < data.getNbIndividuals(); i++) {
				Individual individual = data.getIndividual(i);
				
				int numeroClasse = this.getClosestClass(individual, gravityCenters);
				
				Cluster oldCluster = clusters.get(classes[i]);
				Cluster newCluster = clusters.get(numeroClasse); 
				
				change = change || (numeroClasse != classes[i]);
				
				classes[i] = numeroClasse;
				oldCluster.remove(individual);
				newCluster.add(individual);
				
				gravityCenters.set(numeroClasse, newCluster.getGravityCenter().clone());
				
				//System.out.println(gravityCenters);
				
				//this.updateGravityCenters(classes[i], gravityCenters);______________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________
			}
			
			//inertieInterPrec = inertieInter;
			//inertieInter = this.calculateInertirInter(data.getGravityCenter(), gravityCenters);
			
			
		} while (change); 	// 1e-6
		
		
		return clusters;
	}



	private List<Cluster> initializeClasses(Cluster data, int nbClasses, int[] classes) throws NumberOfVariablesException {
		int nbVariables = data.getNbVariables();
		
		// choose random individuals via their indexes
		Set<Integer> randomIndexs = new HashSet<Integer>(nbClasses);
		while(randomIndexs.size() < nbClasses) {
			int index = Utils.random(0, data.getNbIndividuals() - 1);
			randomIndexs.add(index);
		}
		
		// initialize clusters
		List<Cluster> clusters = new ArrayList<Cluster>(nbClasses);
		int numeroClasse = 0;
		for(int index : randomIndexs) {
			Individual individual = data.getIndividual(index);
			
			Cluster cluster = new Cluster(nbVariables);
			cluster.add(individual);
			
			clusters.add(cluster);
			
			classes[index] = numeroClasse;
			numeroClasse++;
		}
		
		return clusters;
	}
	
	public List<Individual> getGravityCenters(List<Cluster> clusters) {
		List<Individual> gravityCenters = new ArrayList<Individual>(clusters.size());
		
		for(Cluster cluster : clusters) {
			Individual gravityCenter = cluster.getGravityCenter();
			Individual clone = gravityCenter.clone();
			gravityCenters.add(clone);
		}
		
		return gravityCenters;
	}

		
	
	private int getClosestClass(Individual individual, List<Individual> gravityCenters) throws NumberOfVariablesException {
		
		int closestClass = 0;
		double distMin = Individual.distanceEuclidienne(individual, gravityCenters.get(0));
		
		for(int i = 1; i < gravityCenters.size(); i++) {
			Individual gravityCenter = gravityCenters.get(i);
			double dist = Individual.distanceEuclidienne(individual, gravityCenter);
			
			if(dist < distMin) {
				closestClass = i;
				distMin = dist;
			}
		}
		
		return closestClass;
	}
	
	/*
	private double calculateInertirInter(Individual gravityCenterData, List<Individual> gravityCenters) throws NumberOfVariablesException {
		
		double sum = 0.0;
		
		for(Individual gravityCenterCluster : gravityCenters) {
			sum += Individual.distanceEuclidienne(gravityCenterCluster, gravityCenterData);
		}
		
		return sum / gravityCenters.size();
	}
	*/
	
	public static class test {
		public static void main(String[] args) {
			
			Cluster cluster = new Cluster(1);
			
			try {
				
				for(int i = 0; i < 1; i++) cluster.add(new Individual(3.0));
				for(int i = 0; i < 1; i++) cluster.add(new Individual(6.0));
				for(int i = 0; i < 1; i++) cluster.add(new Individual(9.0));
				for(int i = 0; i < 1; i++) cluster.add(new Individual(12.0));
				
				KMeans kmean = new KMeans();
				
				List<Cluster> clusters = kmean.clustering(false, cluster, 3);
				
				for(Cluster c : clusters) {
					System.out.println(c);
				}
				
			
			} catch (NumberOfVariablesException e) {
				e.printStackTrace();
			} catch (KMeansException ke) {
				ke.printStackTrace();
			}
			
		}
	}
}
