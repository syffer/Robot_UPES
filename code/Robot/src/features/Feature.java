package features;

import image.Position;

public class Feature {
	
	// https://books.google.co.in/books?id=M_Lr8NTfAHcC&pg=PA226&lpg=PA226&dq=curvature+with+chain+code&source=bl&ots=hNHF5hTbuM&sig=S4scMfCxCVUUHFms09hFjCMt7RU&hl=fr&sa=X&redir_esc=y#v=onepage&q=curvature%20with%20chain%20code&f=false 
	// https://www.uio.no/studier/emner/matnat/ifi/INF4300/h15/undervisningsmateriale/inf4300-2015-f06-description.pdf (page 9) 
	// https://books.google.co.in/books?id=_SPyCAAAQBAJ&pg=PA507&lpg=PA507&dq=area+chain+code&source=bl&ots=N26V9BAEdW&sig=p3kAcg1ag0vbPX7v4IPl-_fz6Rc&hl=fr&sa=X&redir_esc=y#v=onepage&q=area%20chain%20code&f=false 
	
	private Position origin; // upper left pixel 
	private ChainCode chainCode;
	private ChainCode differentialChainCode;
	
	private double area; // number of pixels in the region
	private double perimeter;
	
	private double compactness;
	private double circularity;
	private int curvature; // represent the successive changes in direction 
	private double bendingEnergy;
	
	private int width;
	private int height;
	private double ratioWidthHeight;
	private double depth;
	
	
	public Feature(Position origin, ChainCode chainCode) {
		this.origin = origin;
		this.chainCode = chainCode;
		this.differentialChainCode = chainCode.getDifferenceCodeChain();
		
		this.perimeter = 0;
		this.area = 0;
		this.curvature = 0;
		this.bendingEnergy = 0;
		
		//int previousY = ChainCodeExtractor.verticalMove[chainCode.get(chainCode.size() - 1)]; 
		int b = 1;
		
		//for(int code : chainCode) {
		for(int i = 0; i < chainCode.size(); i++) {
			int code = chainCode.get(i);
			
			// perimeter 
			if(code == 0 || code == 2 || code == 4 || code == 6) this.perimeter += 1;  // 
			else this.perimeter += Math.sqrt(2); 	// diagonal move 
			
			// area
			/*
			int cix = ChainCodeExtractor.horizontalMove[code];
			int ciy = ChainCodeExtractor.verticalMove[code];
			this.area += cix * (previousY + (ciy / 2)); 
			System.out.println("cix " + cix + ", ciy " + ciy + ", prevY " + previousY + ", result " + cix * (previousY + (ciy / 2)) + " area " + this.area);
			previousY = ciy; 
			*/
			if(code == 0) {
				this.area += b;
			} else if(code == 1) {
				this.area += b;
				b++;
			} else if(code == 2) {
				b++;
			} else if(code == 3) {
				this.area -= b;
				b++;
			} else if(code == 4) {
				this.area -= b;
			} else if(code == 5) {
				this.area -= b;
				b--;
			} else if(code == 6) {
				b--;
			} else {
				this.area += b;
				b--;
			}
			
			// height + width , always start from the upper left pixel 
			if(code == 5 || code == 6 || code == 7) this.height++; 
			if(code == 0 || code == 1 || code == 7) this.width++;
						
			// curvature + bending energy
			int diffCode = this.differentialChainCode.get(i);
			this.curvature += diffCode;
			this.bendingEnergy += diffCode * diffCode;
			
		}
		
		this.perimeter = this.perimeter * (1 + Math.sqrt(2)) * (Math.PI / 8); 	// https://www.uio.no/studier/emner/matnat/ifi/INF4300/h15/undervisningsmateriale/inf4300-2015-f06-description.pdf (page 10, 11) 
		//this.area += this.perimeter; 	// to include the boundary as the area as well 
		this.compactness = (this.area > 0) ? this.perimeter * this.perimeter / this.area : 0; 
		this.circularity = 4 * Math.PI * this.area / (this.perimeter * this.perimeter);
		this.ratioWidthHeight = (double)this.width / (double)this.height;
		this.bendingEnergy /= this.differentialChainCode.size() - 1; 
	}


	public Position getPosition() {
		return origin;
	}


	public ChainCode getChainCode() {
		return chainCode;
	}


	public ChainCode getDifferentialChainCode() {
		return differentialChainCode;
	}


	public double getArea() {
		return area;
	}


	public double getPerimeter() {
		return perimeter;
	}


	public double getCompactness() {
		return compactness;
	}


	public double getCircularity() {
		return circularity;
	}


	public int getCurvature() {
		return curvature;
	}


	public double getBendingEnergy() {
		return bendingEnergy;
	}


	public int getWidth() {
		return width;
	}


	public int getHeight() {
		return height;
	}


	public double getRatioWidthHeight() {
		return ratioWidthHeight;
	}


	public double getDepth() {
		return depth;
	}


	@Override
	public String toString() {
		return "Feature [ " 
				+ "\n\t position=" + origin  
				+ "\n\t chainCode=" + chainCode
				+ "\n\t differentialChainCode=" + differentialChainCode
				+ "\n\t area=" + area + ", perimeter=" + perimeter
				+ "\n\t compactness=" + compactness + ", circularity=" + circularity 
				+ "\n\t curvature=" + curvature + ", bendingenergy=" + bendingEnergy 
				+ "\n\t width=" + width + ", height=" + height
				+ "\n\t ratioWidthHeight=" + ratioWidthHeight + ", depth=" + depth
				+ "]";
	}
		
}
