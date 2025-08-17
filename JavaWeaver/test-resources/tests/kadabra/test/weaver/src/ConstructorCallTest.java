public class ConstructorCallTest{

	public class Class1 {
		public int x = -1;
		
		public Class1() {
			x = 0;
		}
		
	}
	
	public class Class2 {
		public Class1 obj;
		
		public Class2(Class1 obj) {
			this.obj = obj;
		}
	}
	
	public Class1 obj;
	
	public ConstructorCallTest() {
		this.obj = new Class1();
		
		Class2 test = new Class2(new Class1());
	}
}
