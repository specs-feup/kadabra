/**
 * Copyright 2016 SPeCS.
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

package weaver.options;

import org.lara.interpreter.weaver.options.OptionArguments;
import org.lara.interpreter.weaver.options.WeaverOption;
import org.lara.interpreter.weaver.options.WeaverOptionBuilder;
import org.suikasoft.jOptions.Datakey.DataKey;

public enum JavaWeaverOption {

    Includes("I", OptionArguments.ONE_ARG, "path", "Include folders in classpath", JavaWeaverKeys.INCLUDE_DIRS),
    Xignore("X", "Compile with incomplete classpath", JavaWeaverKeys.NO_CLASSPATH),
    Format("F", "Format Code and organize imports", JavaWeaverKeys.FORMAT),
    Clear("C", "Clear output folder", JavaWeaverKeys.CLEAR_OUTPUT_FOLDER),
    Log("L", "Show logging information", JavaWeaverKeys.SHOW_LOG_INFO),
    Report("R", "Report Strategy Metrics", JavaWeaverKeys.REPORT),
    OutputType("T", "Type of output", JavaWeaverKeys.OUTPUT_TYPE),;

    private WeaverOption opt;

    private JavaWeaverOption(String shortOpt, String description, DataKey<?> dataKey) {
        this(shortOpt, OptionArguments.NO_ARGS, "arg", description, dataKey);
    }

    private JavaWeaverOption(String shortOpt, OptionArguments args, String argName, String description,
            DataKey<?> dataKey) {
        opt = WeaverOptionBuilder.build(shortOpt, name(), args, argName, description, dataKey);
    }

    public WeaverOption getOption() {
        return opt;
    }
}
