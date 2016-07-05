package clustering;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import transform.Utils;

/**
 * Applies the KMeans algorithm on a cluster. 
 * The KMeans algorithm allows to partition "n" individuals into "k" clusters
 * into which each individuals belong to the cluster with the nearest mean / gravity center.
 * 
 * The number of resulting clusters must be given by the user. 
 * As the starting gravity centers of each new clusters are generated randomly, it is possible that 
 * two execution of KMeans on the same data produces different results. 
 * 
 * An array called "classes" is used to keep a track of the class affectation of each individuals. 
 * In this array, classes will be represented by a number (0 to nbClasses - 1). To recover the class of 
 * the fist individual, we can do : this.classes[1]. 
 * 
 * @see clustering.Individual
 * @see clustering.Cluster
 * @author Maxime PINEAU
 */
public class KMeans {
	
	private int[] classes;
	
	public KMeans() {
		
	}
	
	/**
	 * Applies the KMeans algorithm on the data. 
	 * The cluster will be centered and reduced by default. 
	 * @param data the cluster that contains all the individuals we want to partition 
	 * @param nbClasses the number of resulting classes / clusters
	 * @return a list of "nbClasses" clusters, each one containing nearest individuals 
	 * @throws NumberOfVariablesException if one individual doesn't have the required number of variables 
	 * @throws KMeansException if the number of classes is <= 0 or is > to the number of individuals in the data cluster 
	 */
	public List<Cluster> clustering(Cluster data, int nbClasses) throws NumberOfVariablesException, KMeansException {
		return this.clustering(data, nbClasses, true);
	}
	
	/**
	 * Applies the KMeans algorithm on the data. 
	 * @param data the cluster that contains all the individuals we want to partition 
	 * @param nbClasses the number of resulting classes / clusters
	 * @param shouldCenterReduce whether the data must be centered and reduced or not 
	 * @return a list of "nbClasses" clusters, each one containing nearest individuals 
	 * @throws NumberOfVariablesException if one individual doesn't have the required number of variables 
	 * @throws KMeansException if the number of classes is <= 0 or is > to the number of individuals in the data cluster 
	 */
	public List<Cluster> clustering(Cluster data, int nbClasses, boolean shouldCenterReduce) throws NumberOfVariablesException, KMeansException {
		if(nbClasses > data.getNbIndividuals()) throw new KMeansException();
		if(nbClasses <= 0) throw new KMeansException();
		
		if(shouldCenterReduce) data.centerAndReduce();
		
		this.classes = new int[data.getNbIndividuals()];
		
		List<Cluster> clusters = this.initializeClasses(data, nbClasses);
		List<Individual> gravityCenters = this.getGravityCenters(clusters);
				
		//double inertieInter = 0.0;
		//double inertieInterPrec = 0.0;
		
		boolean change;		
		
		do {
			
			change = false;
			
			for(int i = 0; i < data.getNbIndividuals(); i++) {
				Individual individual = data.getIndividual(i);
				
				int numeroClasse = this.getClosestClass(individual, gravityCenters);
				
				Cluster oldCluster = clusters.get(this.classes[i]);
				Cluster newCluster = clusters.get(numeroClasse); 
				
				change = change || (numeroClasse != this.classes[i]);
				
				this.classes[i] = numeroClasse;
				oldCluster.remove(individual);
				newCluster.add(individual);
				
				gravityCenters.set(numeroClasse, newCluster.getGravityCenter().clone());
				
			}
			
			//inertieInterPrec = inertieInter;
			//inertieInter = this.calculateInertirInter(data.getGravityCenter(), gravityCenters);
			
			
		} while (change); 
		
		return clusters;
	}
	
	
	/**
	 * Initialize the clusters by adding a random gravity center.
	 * The initial gravity center are generated by picking individuals from the original data.
	 * Also, update the array "classes", that keep a track of which classes belong each individuals
	 * 
	 * @param data the individuals we want to split / partition.
	 * @param nbClasses the number of classes / clusters 
	 * @return a list containing the initial clusters, each containing one individual (which represent the gravity center)
	 * @throws NumberOfVariablesException if one individual doesn't have the required number of variables 
	 */
	private List<Cluster> initializeClasses(Cluster data, int nbClasses) throws NumberOfVariablesException {
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
			
			// 
			this.classes[index] = numeroClasse;
			numeroClasse++;
		}
		
		return clusters;
	}
	
	/**
	 * Returns a copy of each gravity centers of the given clusters 
	 * @param clusters the clusters from which we want a copy of their gravity centers 
	 * @return a list of gravity centers 
	 */
	private List<Individual> getGravityCenters(List<Cluster> clusters) {
		List<Individual> gravityCenters = new ArrayList<Individual>(clusters.size());
		
		for(Cluster cluster : clusters) {
			Individual gravityCenter = cluster.getGravityCenter();
			Individual clone = gravityCenter.clone();
			gravityCenters.add(clone);
		}
		
		return gravityCenters;
	}

		
	
	/** 
	 * Returns the nearest class number of the given individual by comparing 
	 * the Euclidean distance between the given individual and the gravity centers of each classes / clusters 
	 * @param individual the individual from which we want to get the nearest class 
	 * @param gravityCenters the gravity centers of the classes 
	 * @return the class number of the nearest class of the individual 
	 * @throws NumberOfVariablesException if one individual doesn't have the required number of variables 
	 */
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
	
	
	/**
	 * Returns the array that allow to know the class of each individuals.
	 * Each individual has an entry in the index, the value given by the array 
	 * correspond to the class number of the individual.
	 * @return the array 
	 */
	public int[] getClasses() {
		return this.classes;
	}
	
	
	/**
	 * Test the KMeans algorithm. 
	 * @author Maxime PINEAU
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
				
				List<Cluster> clusters = kmean.clustering(cluster, 3);
				
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
