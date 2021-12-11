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

package pt.up.fe.specs.kadabra.parser.spoon;

import java.io.File;
import java.util.List;

import spoon.Launcher;

public class KadabraSpoonLauncher extends Launcher {

    private final List<File> originalSources;

    public KadabraSpoonLauncher(List<File> originalSources) {
        this.originalSources = originalSources;
    }

    public List<File> getOriginalSources() {
        return originalSources;
    }

    // @Override
    // public Factory createFactory() {
    //
    // return new KadabraFactory(new DefaultCoreFactory(), createEnvironment());
    // }

    // @Override
    // public Environment createEnvironment() {
    // return new JWEnvironment();
    // }

    // @Override
    // public SpoonModelBuilder createCompiler(Factory factory) {
    // SpoonModelBuilder comp = new JWSpoonCompiler(factory); // Overriding to comprise the use of JWSpoonCompiler
    // Environment env = getEnvironment();
    // // building
    // // comp.setEncoding(getArguments().getString("encoding"));
    // env.setEncoding(Charset.forName(getArguments().getString("encoding")));
    // // comp.setBuildOnlyOutdatedFiles(jsapActualArgs.getBoolean("buildOnlyOutdatedFiles")); // Spoon 6
    // comp.setBinaryOutputDirectory(jsapActualArgs.getFile("destination"));
    // // comp.setSourceOutputDirectory(jsapActualArgs.getFile("output"));
    // env.setSourceOutputDirectory(jsapActualArgs.getFile("output"));
    // // comp.setEncoding(jsapActualArgs.getString("encoding"));
    // env.setEncoding(Charset.forName(jsapActualArgs.getString("encoding")));
    //
    // // backward compatibility
    // // we don't have to set the source classpath
    // if (jsapActualArgs.contains("source-classpath")) {
    // comp.setSourceClasspath(
    // jsapActualArgs.getString("source-classpath").split(System.getProperty("path.separator")));
    // }
    //
    // env.debugMessage("output: " + comp.getSourceOutputDirectory());
    // env.debugMessage("destination: " + comp.getBinaryOutputDirectory());
    // env.debugMessage("source classpath: " + Arrays.toString(comp.getSourceClasspath()));
    // env.debugMessage("template classpath: " + Arrays.toString(comp.getTemplateClasspath()));
    //
    // if (jsapActualArgs.getBoolean("precompile")) {
    // comp.compile(InputType.FILES);
    // }
    //
    // return comp;
    // }
}
