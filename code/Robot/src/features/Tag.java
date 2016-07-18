package features;

public enum Tag {
	OTHER,
	ROCK, 
	TREE, 
	PERSON, 
	WALL, 
	CAR;
	
	public static Tag getTag(String label) {
		try {
			return Tag.valueOf(label.toUpperCase());
		} catch(IllegalArgumentException ile) {
			return Tag.OTHER;
		}
	}
	
	
	public static class Test {
		public static void main(String[] args) {
			Tag t = Tag.OTHER;
			
			t = Tag.getTag("car");
			System.out.println(t);
		}
	}
	
}
