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

package weaver.kadabra.monitor.odroid;

public class SensorsConstants {
    public static final String DRIVER_LOC = "/sys/bus/i2c/drivers/INA231/4-004";
    public static final String GPU_LOC = "4";
    public static final String MEMORY_LOC = "1";
    public static final String A15_LOC = "0";
    public static final String A7_LOC = "5";
    public static final String SENSOR_V = "/sensor_V";
    public static final String SENSOR_A = "/sensor_A";
    public static final String SENSOR_W = "/sensor_W";

    private static final String GPU_W = DRIVER_LOC + GPU_LOC + "/sensor_W";
    private static final String MEMORY_W = DRIVER_LOC + MEMORY_LOC + "/sensor_W";
    private static final String A15_W = DRIVER_LOC + A15_LOC + "/sensor_W";
    private static final String A7_W = DRIVER_LOC + A7_LOC + "/sensor_W";

    public static final String GPU_ENABLE = DRIVER_LOC + GPU_LOC + "/enable";
    public static final String MEMORY_ENABLE = DRIVER_LOC + MEMORY_LOC + "/enable";
    public static final String A15_ENABLE = DRIVER_LOC + A15_LOC + "/enable";
    public static final String A7_ENABLE = DRIVER_LOC + A7_LOC + "/enable";
    private static final int SENSOR_POLL_DELAY_US_DEFAULT = 263808;

    public static String getGpuW() {
        return GPU_W;
    }

    public static String getMemoryW() {
        return MEMORY_W;
    }

    public static String getA15W() {
        return A15_W;
    }

    public static String getA7W() {
        return A7_W;
    }

    public static int defaultSensorDelayUs() {
        return SENSOR_POLL_DELAY_US_DEFAULT;
    }

    public static int defaultSensorDelayNs() {
        return SENSOR_POLL_DELAY_US_DEFAULT * 1000;
    }

    // public static final String CPU0_SCALING_FREQ = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq";
    // public static final String CPU1_SCALING_FREQ = "/sys/devices/system/cpu/cpu1/cpufreq/scaling_cur_freq";
    // public static final String CPU2_SCALING_FREQ = "/sys/devices/system/cpu/cpu2/cpufreq/scaling_cur_freq";
    // public static final String CPU3_SCALING_FREQ = "/sys/devices/system/cpu/cpu3/cpufreq/scaling_cur_freq";

}
