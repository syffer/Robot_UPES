package image;

public class Matrix {
	
	private int[][] data;
	private int width;
	private int height;
	
	private Matrix(int width, int height, int[][] data) {
		this.data = data;
		this.width = width;
		this.height = height;
	}
	
	public Matrix(int width, int height) {
		this(width, height, new int[width][height]);
	}
	
	public Matrix(int data[][]) {
		this(data.length, data[0].length, data);
	}

	
	public int get(int i, int j) {
		return this.data[i][j];
	}
	
	public void set(int i, int j, int value) {
		this.data[i][j] = value;
	}
	
	public boolean isInBound(int i, int j) {
		return i >=0 && i < this.width && j >= 0 && j < this.height;
	}
	
	public int[][] getData() {
		return data;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	
	
}
