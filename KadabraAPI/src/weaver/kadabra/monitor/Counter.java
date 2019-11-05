package weaver.kadabra.monitor;

public class Counter {
    private int counter;

    public int getValue() {
	return counter;
    }

    public void increment() {
	counter++;
    }

    public void reset() {
	counter = 0;
    }
}
