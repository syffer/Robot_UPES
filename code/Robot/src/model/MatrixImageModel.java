package model;

public abstract class MatrixImageModel extends ImageModel {

	protected int[][] matrix;
	
	protected MatrixImageModel(int width, int heigth) {
		super(width, heigth);
		this.matrix = new int[this.width][this.height];
	}
	
	@Override
	public int get(int i, int j) {
		return this.matrix[i][j];
	}
}
