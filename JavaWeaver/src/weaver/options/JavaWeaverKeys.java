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

import java.util.Arrays;

import javax.swing.JFileChooser;

import org.lara.interpreter.joptions.config.interpreter.LaraIKeyFactory;
import org.lara.interpreter.joptions.keys.FileList;
import org.suikasoft.jOptions.Datakey.DataKey;
import org.suikasoft.jOptions.Datakey.KeyFactory;

import spoon.OutputType;

public interface JavaWeaverKeys {

    static DataKey<Boolean> CLEAR_OUTPUT_FOLDER = KeyFactory.bool("clear_output").setLabel("Clear the output folder");
    static DataKey<Boolean> FORMAT = KeyFactory.bool("format").setLabel("Format Code and organize imports");
    static DataKey<Boolean> SHOW_LOG_INFO = KeyFactory.bool("show info").setLabel("Show Log Information");
    static DataKey<Boolean> NO_CLASSPATH = KeyFactory.bool("noclasspath").setLabel("Compile with incomplete classpath");
    static DataKey<Boolean> REPORT = KeyFactory.bool("report kadabra").setLabel("Report Metrics");
    static DataKey<Boolean> FULLY_QUALIFIED_NAMES = KeyFactory.bool("fully_qualified_names")
            .setLabel("Fully Qualified Names");

    static DataKey<FileList> INCLUDE_DIRS = LaraIKeyFactory
            .fileList("java includes dir", JFileChooser.FILES_AND_DIRECTORIES, Arrays.asList("jar"))
            .setLabel("Add Classpaths");
    static DataKey<OutputType> OUTPUT_TYPE = KeyFactory.enumeration("outputType", OutputType.class)
            .setDefault(() -> OutputType.COMPILATION_UNITS)
            .setLabel("Output Type");

}
