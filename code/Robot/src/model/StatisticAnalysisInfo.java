package model;

import model.image.Image;

public class StatisticAnalysisInfo extends InternalModel {

	//private Image image;
	//private Image original;
	
	private double mse;
	private double psnr;
	private double mean;
	private double variance;
	private double standardDeviation;
	
	public StatisticAnalysisInfo(Image image, Image original) {
		//this.image = image;
		//this.original = original;
		
		// first loop : mse, psnr, mean 
		this.mse = 0;
		this.mean = 0;
		
		for(int i = 0; i < image.getWidth(); i++) {
			for(int j = 0; j < image.getHeight(); j++) {
				
				int difference = original.get(i, j) - image.get(i, j);
				this.mse += difference * difference;
				
				this.mean += image.get(i, j);
			}
		}
		
		this.mean /= image.getNbPixels();
		this.mse /= image.getNbPixels();
		
		int b = 255; // 2^8 -1, pixel sur 8 bit
		
		if(this.mse == 0) this.psnr = Double.POSITIVE_INFINITY;
		else this.psnr = 10 * Math.log((b*b) / mse); 
		
		
		// second loop : variance, standard deviation 
		this.variance = 0;
		
		for(int i = 0; i < image.getWidth(); i++) {
			for(int j = 0; j < image.getHeight(); j++) {
				double difference = image.get(i, j) - this.mean;
				this.variance += difference * difference;
			}
		}
		
		this.variance /= image.getNbPixels();
		
		this.standardDeviation = Math.sqrt(this.variance);
	}
	
	public StatisticAnalysisInfo(Image image) {
		
		// first loop : mse, psnr, mean 
		this.mse = 0;
		this.psnr = Double.POSITIVE_INFINITY; 
		
		this.mean = 0;
		
		for(int i = 0; i < image.getWidth(); i++) {
			for(int j = 0; j < image.getHeight(); j++) {
				this.mean += image.get(i, j);
			}
		}
		
		this.mean /= image.getNbPixels();
		
		
		// second loop : variance, standard deviation 
		this.variance = 0;
		
		for(int i = 0; i < image.getWidth(); i++) {
			for(int j = 0; j < image.getHeight(); j++) {
				double difference = image.get(i, j) - this.mean;
				this.variance += difference * difference;
			}
		}
		
		this.variance /= image.getNbPixels();
		
		this.standardDeviation = Math.sqrt(this.variance);
	}

	public double getMSE() {
		return mse;
	}

	public double getPSNR() {
		return psnr;
	}

	public double getMean() {
		return mean;
	}

	public double getVariance() {
		return variance;
	}

	public double getStandardDeviation() {
		return standardDeviation;
	}
	
}
