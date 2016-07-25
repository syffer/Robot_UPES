package features;

import java.awt.Color;

public enum Tag {
	OTHER(Color.BLUE),
	ROCK(Color.YELLOW), 
	TREE(Color.GREEN), 
	PERSON(Color.PINK), 
	WALL(Color.WHITE), 
	CAR(Color.RED);
	
	private Color color;
	
	private Tag(Color color) {
		this.color = color;
	}
	
	public static Tag getTag(String label) {
		try {
			return Tag.valueOf(label.toUpperCase());
		} catch(IllegalArgumentException ile) {
			return Tag.OTHER;
		}
	}
	
	public Color getColor() {
		return this.color;
	}
	
	
	public static class Test {
		public static void main(String[] args) {
			Tag t = Tag.OTHER;
			
			t = Tag.getTag("car");
			System.out.println(t);
		}
	}
	
}
