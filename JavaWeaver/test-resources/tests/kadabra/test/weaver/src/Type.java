public class Type {
	
	private String b;
	private Type d; // If this is an array, it will fail not detect on Windows but it will detect on Linux
	private Type c; 
	
	//Constructor
	public Type(String b) {
		this.b = b;
		this.d = null;
		this.c = null;
	}
	
	//Global variable declaration
	public int test1() {
		int a = c.test2();
		a += a;
		return 0;
	}

	public int test2() {
		b += b;
		return 0;
	}	
	
}