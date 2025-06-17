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

package pt.up.fe.specs.kadabra.parser.spoon;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import pt.up.fe.specs.kadabra.KadabraNodeFactory;
import pt.up.fe.specs.kadabra.ast.App;
import pt.up.fe.specs.kadabra.ast.CompilationUnit;
import pt.up.fe.specs.kadabra.ast.KadabraContext;
import pt.up.fe.specs.kadabra.ast.KadabraNode;
import pt.up.fe.specs.kadabra.ast.generic.GenericKadabraNode;
import pt.up.fe.specs.kadabra.parser.KadabraParser;
import pt.up.fe.specs.kadabra.parser.KadabraParserConfig;
import pt.up.fe.specs.kadabra.parser.spoon.elementparser.MainParser;
import pt.up.fe.specs.util.SpecsIo;
import pt.up.fe.specs.util.SpecsLogs;
import spoon.Launcher;
import spoon.compiler.Environment;

public class SpoonParser extends KadabraParser {

    private final KadabraParserConfig config;

    public SpoonParser(KadabraParserConfig config) {
        this.config = config;
    }

    @Override
    public App parse(List<File> sources) {

        // TODO: to enable parallel parsing, parse each file separately, with a KadabraContext initialized to a
        // different id (e.g. file1_)

        var spoonLaucher = buildLauncher(sources);
        spoonLaucher.buildModel();
        spoonLaucher.process();

        var compUnits = spoonLaucher.getFactory().CompilationUnit().getMap().values();

        var kadabraContext = new KadabraContext();
        var elementParser = new MainParser(kadabraContext);

        var app = new KadabraNodeFactory(kadabraContext).newNode(App.class);

        var compilationUnits = compUnits.stream()
                .map(element -> (CompilationUnit) elementParser.parse(element))
                .collect(Collectors.toList());
        // for (var compUnit : spoonLaucher.getFactory().CompilationUnit().getMap().values()) {
        // compilationUnits.add(elementParser.parse(compUnit));
        // // for (var compUnit : spoonLaucher.getModel().getElements(node -> node instanceof CtCompilationUnit)) {
        // // System.out.println("PATH: " + compUnit.getFile());
        // }

        app.setChildren(compilationUnits);

        verifyPreviousId(app);

        System.out.println("SOURCES: " + sources);
        System.out.println("COMP UNITS: " + spoonLaucher.getFactory().CompilationUnit().getMap());
        for (var aClass : spoonLaucher.getFactory().Class().getAll()) {
            System.out.println("CLASS: " + aClass.getSimpleName());
        }

        // var app = new App(getDataStore(), compilationUnits);

        System.out.println("TREE:\n" + app);

        return app;
    }

    // private CompilationUnit parse(CtCompilationUnit compUnit) {
    // compUnit.getDirectChildren()
    // return new CompilationUnit(, null)
    // }

    private void verifyPreviousId(App app) {
        // If any of the tree nodes has a previousId and is not a generic node, this could indicate a problem
        var astNodesWithPreviousId = app.getDescendantsAndSelfStream()
                .filter(node -> !(node instanceof GenericKadabraNode) && !node.get(KadabraNode.PREVIOUS_ID).isEmpty())
                .collect(Collectors.toList());

        if (!astNodesWithPreviousId.isEmpty()) {
            // SpecsLogs.info("Found nodes with previous id:");
            // astNodesWithPreviousId.stream().forEach(node -> SpecsLogs.info("PrevId: " + node.toString()));

            SpecsLogs.info("Nodes with previous id: " + astNodesWithPreviousId.stream().count());
        } else {
            SpecsLogs.info("Passed verification 'previous id'");
        }

    }

    private Launcher buildLauncher(List<File> sources) {
        var spoon = new KadabraSpoonLauncher(sources);

        Environment environment = spoon.getFactory().getEnvironment();

        var classPath = config.get(KadabraParserConfig.CLASSPATH);
        if (!classPath.isEmpty()) {

            try {
                // Process classpath
                // If there are JARs present in folders, add them as individual classpaths
                List<File> processedClasspath = new ArrayList<>();
                processedClasspath.addAll(classPath);
                var additionalJars = classPath.stream()
                        .filter(classPathFolder -> classPathFolder.isDirectory())
                        // .flatMap(folder -> SpecsIo.getFiles(folder, "jar").stream())
                        .flatMap(folder -> SpecsIo.getFilesRecursive(folder, "jar").stream())
                        .collect(Collectors.toList());
                SpecsLogs.debug("Adding JARs to classpath: " + additionalJars);
                processedClasspath.addAll(additionalJars);

                List<String> filesStr = processedClasspath.stream().map(SpecsIo::getCanonicalPath)
                        .collect(Collectors.toList());
                String[] classPathArray = filesStr.toArray(new String[0]);
                // KadabraLog.info(filesStr);
                environment.setSourceClasspath(classPathArray);
                // KadabraLog.info(Arrays.toString(environment.getSourceClasspath());
            } catch (Exception e) {
                throw new RuntimeException("setting the classpath", e);
            }
        }
        // environment.disableConsistencyChecks(); // Spoon 8
        // environment.setSelfChecks(false); // Spoon 6
        // environment.setSelfChecks(true);
        // environment.setAutoImports(true);
        environment.setCommentEnabled(true);
        // environment.setGenerateJavadoc(true);

        environment.setNoClasspath(config.get(KadabraParserConfig.NO_CLASSPATH));

        setInputSources(sources, spoon);
        // spoon.addProcessor(new IfProcessor());
        // spoon.addProcessor(new CommentProcessor());

        // Set fully qualified names
        // environment.setAutoImports(true);
        // environment.setAutoImports(!args.get(JavaWeaverKeys.FULLY_QUALIFIED_NAMES));
        // if (args.get(JavaWeaverKeys.FULLY_QUALIFIED_NAMES)) {
        // environment.setAutoImports(false);
        // } else {
        // environment.setAutoImports(true);
        // }

        // environment.setPreserveLineNumbers(false);

        // environment.setCopyResources(args.get(JavaWeaverKeys.COPY_RESOURCES));

        environment.setComplianceLevel(config.get(KadabraParserConfig.JAVA_VERSION));

        return spoon;
    }

    private void setInputSources(List<File> sources, Launcher spoon) {
        for (File source : sources) {
            spoon.addInputResource(SpecsIo.getCanonicalPath(source));
        }
    }

}
