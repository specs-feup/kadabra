public class TimerTest {

    public double bar() {
        return 1.0;
    }
    
    public static double bar2() {
        return 1.0;
    }

    public static double bar3() {
        return 1.0;
    }

    public double foo() {
        double a = 0;

        for (int i = 0; i < 5; i++) {
            a += bar();
        }

        return a;
    }

    public static void main(String[] args) {
        new TimerTest().foo();
        
        TimerTest.bar2();
        
        TimerTest.bar3();
    }
}
