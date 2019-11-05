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

package weaver.kadabra.util;

import pt.up.fe.specs.util.logging.StringLogger;

// public class KadabraLog extends SpecsLogger {
public class KadabraLog {

    // private static final String KADABRA_WEAVER_TAG = buildLoggerName(KadabraLog.class);
    // private static final Lazy<KadabraLog> LOGGER = buildLazy(KadabraLog::new);
    private static final StringLogger LOGGER = new StringLogger(KadabraLog.class.getName());
    private static boolean debug;

    // public static KadabraLog logger() {
    public static StringLogger logger() {
        // return LOGGER.get();
        return LOGGER;
    }

    // private KadabraLog() {
    // super(KADABRA_WEAVER_TAG);
    // }

    public static void info(String message) {
        logger().info(message);
    }

    // public static void warning(Pragma pragma, String message) {
    // warning(message + " (" + pragma.getLocation() + ")");
    // // LoggingUtils.msgInfo("[Warning] " + message + " (" + pragma.getLocation() + ")");
    // }

    public static void warning(String message) {
        logger().info("Warning", message);
        // SpecsLogs.msgInfo("[Warning] " + message);
    }

    public static void debug(String message) {
        if (isDebug()) {
            logger().debug(message);
            // SpecsLogs.msgInfo("[DEBUG] " + message);
        }
    }

    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(boolean debug) {
        KadabraLog.debug = debug;
        // if (debug == true) {
        // KadabraLog.debug("Debug mode is active, to deativate use " + KadabraLog.class.getCanonicalName()
        // + ".seDebug(false)");
        // }
    }

}
