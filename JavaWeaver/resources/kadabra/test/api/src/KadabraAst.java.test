public class KadabraAst {

	private final int a = 0;
	private final int b;
	private int c;
	
	public KadabraAst(int b, int c) {
		this.b = b;
		this.c = c;
	}
	
	public int foo() {
		final int a = 0;
		int b = 1;
		
		return a + b;
	}
	
	public int loops() {
		int a = 0;
		for(int i=0; i<10; i++) {
			for(int j=0; j<10; j++) {
				for(int k=0; k<10; k++) {
					a++;
				}
			}		
		}
		return a;
	}

}
