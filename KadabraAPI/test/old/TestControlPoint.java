package old;
import tdrc.tuple.Tuple;
import tdrc.tuple.TupleList;
import tdrc.utils.ListUtils;
import weaver.kadabra.concurrent.KadabraChannel;
import weaver.kadabra.concurrent.KadabraThread;
import weaver.kadabra.concurrent.Product;
import weaver.kadabra.control.ControlPoint;
import weaver.kadabra.control.ControlPoint2D;
import weaver.kadabra.control.ControlPointFactory;
import weaver.kadabra.control.utils.ProviderUtils;
import weaver.kadabra.control.utils.VersionProvider;
import weaver.kadabra.monitor.CodeTimer;

/**
 * Copyright 2017 SPeCS.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License. under the License.
 */

public class TestControlPoint {

    private static int K = 16;
    private static int J = 16;

    private static int execs = 60;

    public static void main(String[] args) throws InterruptedException {
        // System.out.println("RANGE");
        // testRange(60);
        // System.out.println("LIST");
        // testList(60);
        // System.out.println("2D");
        // test2D(60);
        // System.out.println("NTuples");
        // nTuples(100);
        System.out.println("TuplesMap");
        testTupleMap(40);
    }

    public static void testTupleMap(int execs) throws InterruptedException {
        Integer[] xs = { 20, 25, 18, 10 };
        Integer[] ys = { 10, 18, 10, 12 };

        // // List<O[]> asList = Arrays.asList(xs, ys, zs);
        // TupleList<Object> pairs = ListUtils.createTuples(xs, ys);
        // VersionProvider<Tuple<Object>> tupleProvider = ProviderUtils.fromList(pairs);
        final ControlPoint<Tuple<Object>> cp1 = ControlPointFactory.createTuples("K", xs, ys);
        cp1.onUpdate(points -> {

            K = (int) points.get(0);
            J = (int) points.get(1);
        }).setNumTests(1).startTest();

        CodeTimer timer = CodeTimer.MillisTimer();
        while (execs-- > 0) {

            cp1.update();
            timer.start();
            System.out.println(K + "->" + J);
            Thread.sleep((K + J) * 10);
            timer.stop();
            cp1.measurement(timer.getTime());
        }
        System.out.println(cp1.getMeasurementsAcc());
        System.out.println(cp1.getMeasurementsAvg());
    }

    public static void testRange(int execs) throws InterruptedException {
        ControlPoint<Integer> cp = ControlPointFactory.rangedTest("K", 16, 2, (report) -> report.getVersion() >> 1)
                .setNumTests(2).setNumWarmup(1)
                .onUpdate(v -> K = v)
                .startTest();
        CodeTimer timer = CodeTimer.MillisTimer();
        while (execs-- > 0) {
            cp.update();
            timer.start();
            doStuff(K, J);
            timer.stop();
            cp.measurement(timer.getTime());
        }
    }

    private static void doStuff(int k2, int j2) throws InterruptedException {
        System.out.println(k2 + "," + j2);
        Thread.sleep(k2 + j2);
    }

    static final KadabraChannel<Integer, Long> channel = KadabraChannel.newInstance(20);
    static final KadabraThread thread = KadabraThread.newInstance(TestControlPoint::thread);
    static final ControlPoint<Integer> cp = ControlPointFactory.newInstance("K", 1, 2);

    public static void testList(int execs) throws InterruptedException {
        cp.onUpdate(v -> K = v)
                .startTest(new Integer[] { 20, 25, 18, 10, 12 });
        CodeTimer timer = CodeTimer.MillisTimer();
        thread.start();
        while (execs-- > 0) {
            cp.update();
            timer.start();
            doStuff(K, J);
            timer.stop();
            channel.offer(K, timer.getTime());
        }
        thread.terminate();
    }

    private static void thread() {

        while (thread.isRunning() && !cp.hasFinished()) {
            Product<Integer, Long> take = channel.take();
            cp.measurement(take.getKey(), take.getValue());
        }
        System.out.println("thread has stopped");
    }

    public static void test2D(int execs) throws InterruptedException {
        final ControlPoint2D<Integer, Integer> cp1 = ControlPoint2D.newInstance("K", 1, 2);
        cp1.setVersionConsumer((t, v) -> {
            K = t;
            J = v;
        }).startTest(new Integer[] { 20, 25, 18, 10, 12 }, new Integer[] { 20, 25, 18, 10, 12 }, true);
        CodeTimer timer = CodeTimer.MillisTimer();
        while (execs-- > 0) {

            cp1.update();
            timer.start();
            doStuff(K, 30 - J);
            timer.stop();
            cp1.measurement(timer.getTime());

        }
    }

    public static void nTuples(int execs) throws InterruptedException {

        Integer[] xs = { 20, 25, 18, 10 };
        Float[] ys = { 10f, 18f, 10f, 12f };
        Double[] zs = { 10.0, 20.0, 40.0, 50.0 };

        // List<O[]> asList = Arrays.asList(xs, ys, zs);
        TupleList<Object> triple = ListUtils.createTuples(xs, ys, zs);
        VersionProvider<Tuple<Object>> tupleProvider = ProviderUtils.fromList(triple);

        final ControlPoint<Tuple<Object>> cp1 = ControlPointFactory.newInstance("K");
        cp1.onUpdate(points -> {

            K = (int) points.get(0);
            J = (int) ((float) points.get(1) - (double) points.get(2));
        }).startTest(tupleProvider);

        CodeTimer timer = CodeTimer.MillisTimer();
        while (execs-- > 0) {

            cp1.update();
            timer.start();
            System.out.println(K + "->" + J);
            // doStuff(K, 30 - J);
            timer.stop();
            cp1.measurement(timer.getTime());

        }
    }
}
