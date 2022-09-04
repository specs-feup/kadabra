public class EapkExcessiveMethodCalls {

	int field1 = 0;
	int field2 = 1;

    public int someAdd(int i) {
		return i + field1;
	}

    public int someMul(int i) {
		return i * field2;
	}

	public void forLoopBad() {
		field1 = 0;
		for (int i = 0; i < 10; i++) {
			int p = someAdd(2);

            field2 += p;

			int p2 = someMul(2);
		}
	}
}
