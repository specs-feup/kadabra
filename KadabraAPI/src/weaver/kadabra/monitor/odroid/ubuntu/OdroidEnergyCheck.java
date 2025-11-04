/**
 * Copyright 2018 SPeCS.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package weaver.kadabra.monitor.odroid.ubuntu;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import pt.up.fe.specs.util.SpecsIo;
import weaver.kadabra.monitor.RunTaskTimer;
import weaver.kadabra.monitor.odroid.SensorsConstants;
import weaver.kadabra.util.KadabraLog;

public class OdroidEnergyCheck {
    private static final int ENABLE_WAIT = 250;
    private static final File ENABLE_A7 = new File(SensorsConstants.A7_ENABLE);
    private static final File ENABLE_A15 = new File(SensorsConstants.A15_ENABLE);
    private static final File ENABLE_MEMORY = new File(SensorsConstants.MEMORY_ENABLE);
    private static final File ENABLE_GPU = new File(SensorsConstants.GPU_ENABLE);
    private static final File A7 = new File(SensorsConstants.getA7W());
    private static final File A15 = new File(SensorsConstants.getA15W());
    private static final File MEMORY = new File(SensorsConstants.getMemoryW());
    private static final File GPU = new File(SensorsConstants.getGpuW());
    static {
        SpecsIo.write(ENABLE_A7, "1");
        SpecsIo.write(ENABLE_A15, "1");
        SpecsIo.write(ENABLE_MEMORY, "1");
        SpecsIo.write(ENABLE_GPU, "1");
        try {
            KadabraLog.debug("Enabling Sensors...");
            Thread.sleep(ENABLE_WAIT);
            KadabraLog.debug("Ready!");
        } catch (InterruptedException e) {
            KadabraLog.warning("Problem while enabling Odroid sensors: " + e.getMessage());
        }
    }

    private int totalWaitNs;
    private int waitMs;
    private int waitNs;

    private double accA7;
    private double accA15;
    private double accMem;
    private double accGPU;
    private List<Double> a7W;
    private List<Double> a15W;
    private List<Double> memW;
    private List<Double> gpuW;

    private int samples;
    private AtomicBoolean interrupted;
    private AtomicBoolean idle;

    public OdroidEnergyCheck() {
        this(SensorsConstants.defaultSensorDelayNs());
    }

    public OdroidEnergyCheck(int sensorWaitNs) {
        this.totalWaitNs = sensorWaitNs;
        this.waitMs = sensorWaitNs / 1_000_000;
        this.waitNs = sensorWaitNs - (waitMs * 1_000_000);
        interrupted = new AtomicBoolean(false);
        idle = new AtomicBoolean(true);
    }

    public void reset() {
        accA7 = 0;
        accA15 = 0;
        accMem = 0;
        accGPU = 0;
        samples = 0;
        a7W = new ArrayList<>();
        a15W = new ArrayList<>();
        memW = new ArrayList<>();
        gpuW = new ArrayList<>();
        this.interrupted.set(false);
        this.idle.set(true);
    }

    public void start() {
        this.reset();
        Thread thread = new Thread(this::measure);
        thread.start();
    }

    /**
     * 
     * @param ms
     *            period to sample
     */
    public void start(int ms) {
        this.start();
        RunTaskTimer task = new RunTaskTimer(this::stop, ms);
        task.execute();
    }

    public void interrupt() {
        this.interrupted.set(true);
    }

    public void measure() {
        this.idle.set(false);

        while (!interrupted.get()) {
            try {
                double w = getW(A7);
                a7W.add(w);
                accA7 += w;
                w = getW(A15);
                a15W.add(w);
                accA15 += w;
                w = getW(MEMORY);
                memW.add(w);
                accMem += w;
                w = getW(GPU);
                gpuW.add(w);
                accGPU += w;
                samples++;
                Thread.sleep(waitMs, waitNs);
            } catch (InterruptedException e) {
                KadabraLog.warning("Problems while measuring energy consumption in Odroid: " + e.getMessage());
            }
        }
        this.idle.set(true);
    }

    public void naive_report() {

        System.out.println("ACC");
        System.out.println("\tA7:" + getA7Energy());
        System.out.println("\tA15:" + getA15Energy());
        System.out.println("\tMem:" + getMemEnergy());
        System.out.println("\tGPU:" + getGPUEnergy());
        System.out.println("\tSSAMPLES:" + getSamples());
    }

    public int getSamples() {
        return samples;
    }

    public double getA7Raw() {
        return accA7;
    }

    public double getA15Raw() {
        return accA15;
    }

    public double getMemRaw() {
        return accMem;
    }

    public double getGPURaw() {
        return accGPU;
    }

    public double getAccA15() {
        return accA15;
    }

    public double getAccGPU() {
        return accGPU;
    }

    public double getA7Energy() {
        return getEnergy(accA7);
    }

    public double getA15Energy() {
        return getEnergy(accA15);
    }

    public double getCPUEnergy() {
        return getEnergy(accA7 + accA15);
    }

    public double getCPUEnergyRaw() {
        return accA7 + accA15;
    }

    public double getMemEnergy() {
        return getEnergy(accMem);
    }

    public double getGPUEnergy() {
        return getEnergy(accGPU);
    }

    private double getEnergy(double raw) {
        double sum = raw * getSamplingNs();
        return sum;
    }

    public static double getW(File sensor) {
        String trim = SpecsIo.read(sensor).trim();
        return Double.parseDouble(trim);
    }

    public boolean isIdle() {
        return idle.get();
    }

    public static void main(String[] args) {
        OdroidEnergyCheck energy = new OdroidEnergyCheck();
        energy.start(1000);
        double sum = 0;
        for (int i = 0; i < 100000000; i++) {
            sum += i * 10 / 2.4 + (i ^ 2) / 14;
        }
        energy.stop(SensorsConstants.defaultSensorDelayUs() / 1000);
        System.out.println("\nBatman! " + sum);
        energy.naive_report();
    }

    public void stop() {
        stop(this.waitMs, this.waitNs);
    }

    /**
     * 
     * @param waitMs
     *            milliseconds interval to wait for the checker to stop
     */
    public void stop(int waitMs) {
        this.interrupt();
        while (!this.isIdle()) {
            try {
                Thread.sleep(waitMs);
            } catch (InterruptedException e) {
                KadabraLog.warning(
                        "Problems when interrupting energy consumption measurements in Odroid: " + e.getMessage());
            }
        }
    }

    public void stop(int waitMs, int waitNs) {
        this.interrupt();
        while (!this.isIdle()) {
            try {
                Thread.sleep(waitMs, waitNs);
            } catch (InterruptedException e) {
                KadabraLog.warning(
                        "Problems when interrupting energy consumption measurements in Odroid: " + e.getMessage());

            }
        }
    }

    public int getSamplingNs() {
        return totalWaitNs;
    }

}
