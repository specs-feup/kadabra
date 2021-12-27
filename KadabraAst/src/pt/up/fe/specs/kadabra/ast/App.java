/**
 * Copyright 2020 SPeCS.
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

package pt.up.fe.specs.kadabra.ast;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.suikasoft.jOptions.Interfaces.DataStore;

import pt.up.fe.specs.util.SpecsIo;

/**
 * Represents a Java application.
 *
 * @author JoaoBispo
 *
 */
public class App extends KadabraNode {

    public App(DataStore data, Collection<? extends KadabraNode> children) {
        super(data, children);
    }

    /**
     * Writes the source-code of the current application to the given destination folder.
     * 
     * @param outputFolder
     */
    public List<File> write(File outputFolder) {
        List<File> writtenFiles = new ArrayList<>();

        for (var tUnit : getCompilationUnits()) {
            // TODO: Get package and use as base folder

            var file = new File(outputFolder, tUnit.get(CompilationUnit.NAME) + ".java");
            SpecsIo.write(file, tUnit.getCode());
            writtenFiles.add(file);
        }

        return writtenFiles;

    }

    public List<CompilationUnit> getCompilationUnits() {
        return getChildrenOf(CompilationUnit.class);
    }

    @Override
    public String getCode() {
        StringBuilder code = new StringBuilder();

        for (var tu : getCompilationUnits()) {
            // TODO: Use package name
            code.append("/**** File '" + tu.get(CompilationUnit.NAME) + "' ****/"
                    + ln() + ln());
            code.append(tu.getCode());
            code.append(ln() + "/**** End File ****/" + ln() + ln());
        }

        return code.toString();
    }

}
