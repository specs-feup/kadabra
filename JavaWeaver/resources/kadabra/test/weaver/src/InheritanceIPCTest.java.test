public class InheritanceIPCTest {
	
	public class MotherClass {

		protected int K = 0;
		
		public MotherClass() {
			K = 3;
			int c = ~K;
			int d = K;
			int e = ~5;
			K += 1;
			K = ~K;
			K = K | 4;		
		}
		
		public MotherClass(String s) {
			K = s.length();
			int c = ~K;
			int d = K;
			int e = ~5;
			K += 1;
			K = ~K;
			K = K | 4;		
		}
	}
	
	public class DerivedClass1 extends MotherClass{
		public DerivedClass1() {			
			super();
			K = 333;
		}
	}
	
	public class DerivedClass2 extends MotherClass{
		public DerivedClass2() {
			super();
			K = 666;
		}
	}
	
	public class DerivedClass3 extends MotherClass{
		public DerivedClass3() {
			super("dc3");
			K = 999;
		}
	}
	
	public class DerivedClass4 extends MotherClass{
		public DerivedClass4() {
			super("dc4");
			K = 121212;
		}
	}
}
