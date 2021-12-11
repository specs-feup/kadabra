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

package pt.up.fe.specs.kadabra.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.up.fe.specs.kadabra.ast.App;
import pt.up.fe.specs.util.SpecsIo;
import pt.up.fe.specs.util.SpecsLogs;
import pt.up.fe.specs.util.SpecsStrings;
import pt.up.fe.specs.util.SpecsSystem;
import pt.up.fe.specs.util.providers.ResourceProvider;
import pt.up.fe.specs.util.providers.Resources;

public abstract class AKadabraParserTester {

    private static final boolean CLEAN_CLANG_FILES = true;
    private static final String OUTPUT_FOLDERNAME = "temp-clang-ast";

    private final Collection<ResourceProvider> resources;
    private List<String> compilerOptions;

    private boolean showAst = false;
    private boolean showCode = false;
    private boolean onePass = false;

    public <T extends Enum<T> & ResourceProvider> AKadabraParserTester(Class<T> resource) {
        this(resource, Collections.emptyList());
    }

    public <T extends Enum<T> & ResourceProvider> AKadabraParserTester(Class<T> resources,
            List<String> compilerOptions) {
        this(Arrays.asList(resources.getEnumConstants()), compilerOptions);
    }

    public AKadabraParserTester(String base, String file) {
        this(base, Arrays.asList(file));
    }

    public AKadabraParserTester(String base, List<String> files) {
        this(new Resources(base, files), Collections.emptyList());
    }

    public AKadabraParserTester(String base, String file, List<String> compilerOptions) {
        this(base, Arrays.asList(file), compilerOptions);
    }

    public AKadabraParserTester(String base, List<String> files, List<String> compilerOptions) {
        this(new Resources(base, files), compilerOptions);
    }

    private AKadabraParserTester(Resources resources, List<String> compilerOptions) {
        this(resources.getResources(), compilerOptions);
    }

    public AKadabraParserTester(Collection<ResourceProvider> resources, List<String> compilerOptions) {
        this.resources = resources;
        this.compilerOptions = new ArrayList<>(compilerOptions);
    }

    public AKadabraParserTester showAst() {
        showAst = true;
        return this;
    }

    public AKadabraParserTester showCode() {
        showCode = true;
        return this;
    }

    public AKadabraParserTester onePass() {
        onePass = true;
        return this;
    }

    /*
    public AClangAstTester keepFiles() {
        this.keepFiles = true;
        return this;
    }
    */

    public AKadabraParserTester addFlags(String... flags) {
        return addFlags(Arrays.asList(flags));
    }

    public AKadabraParserTester addFlags(List<String> flags) {
        compilerOptions.addAll(flags);
        return this;
    }

    public void test() {
        try {
            setUp();
            testProper();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Before
    public void setUp() throws Exception {
        SpecsSystem.programStandardInit();

        // Copy resources under test
        File outputFolder = SpecsIo.mkdir(AKadabraParserTester.OUTPUT_FOLDERNAME);

        resources.forEach(resource -> resource.write(outputFolder, true, AKadabraParserTester::resourceNameMapper));
    }

    private static String resourceNameMapper(String resourceName) {
        if (resourceName.endsWith(".test")) {
            return resourceName.substring(0, resourceName.length() - ".test".length());
        }

        return resourceName;
    }

    // @After
    public static void clear() throws Exception {

        // Delete ClangAst files
        if (CLEAN_CLANG_FILES) {

            // Delete resources under test
            File outputFolder = SpecsIo.mkdir(AKadabraParserTester.OUTPUT_FOLDERNAME);
            SpecsIo.deleteFolderContents(outputFolder);
            outputFolder.delete();

            // ClangAstParser.getTempFiles().stream()
            // .forEach(filename -> new File(filename).delete());
        }

    }

    @Test
    public void testProper() {
        // Parse files

        KadabraParser codeParser = KadabraParser.newInstance()
                .set(KadabraParser.SHOW_AST, showAst)
                .set(KadabraParser.SHOW_CODE, showCode);

        File workFolder = new File(AKadabraParserTester.OUTPUT_FOLDERNAME);

        App ast = codeParser.parse(Arrays.asList(workFolder));
        // App ast = codeParser.parse(Arrays.asList(workFolder), compilerOptions);

        ast.write(SpecsIo.mkdir(AKadabraParserTester.OUTPUT_FOLDERNAME + "/outputFirst"));
        if (onePass) {
            return;
        }

        KadabraParser testCodeParser = KadabraParser.newInstance();

        // Parse output again, check if files are the same

        File firstOutputFolder = new File(AKadabraParserTester.OUTPUT_FOLDERNAME + "/outputFirst");

        App testClavaAst = testCodeParser.parse(Arrays.asList(firstOutputFolder));
        // App testClavaAst = testCodeParser.parse(Arrays.asList(firstOutputFolder), compilerOptions);
        // App testClavaAst = testCodeParser.parseParallel(Arrays.asList(firstOutputFolder), compilerOptions);
        testClavaAst.write(SpecsIo.mkdir(AKadabraParserTester.OUTPUT_FOLDERNAME + "/outputSecond"));

        // Test if files from first and second are the same

        Map<String, File> outputFiles1 = SpecsIo
                .getFiles(new File(AKadabraParserTester.OUTPUT_FOLDERNAME + "/outputFirst"))
                .stream()
                .collect(Collectors.toMap(file -> file.getName(), file -> file));

        Map<String, File> outputFiles2 = SpecsIo
                .getFiles(new File(AKadabraParserTester.OUTPUT_FOLDERNAME + "/outputSecond"))
                .stream()
                .collect(Collectors.toMap(file -> file.getName(), file -> file));

        for (String name : outputFiles1.keySet()) {
            // Get corresponding file in output 2
            File outputFile2 = outputFiles2.get(name);

            Assert.assertNotNull("Could not find second version of file '" + name + "'", outputFile2);

        }

        // Compare with .txt, if available
        for (ResourceProvider resource : resources) {

            // Get .txt resource
            String txtResource = resource.getResource() + ".txt";

            if (!SpecsIo.hasResource(txtResource)) {
                SpecsLogs.msgInfo("ClangTest: no .txt check file for resource '" + resource.getResource() + "'");
                System.out.println("Contents of output:\n" + SpecsIo.read(outputFiles2.get(resource.getFilename())));
                continue;
            }

            String txtContents = SpecsStrings.normalizeFileContents(SpecsIo.getResource(txtResource), true);
            File generatedFile = outputFiles2.get(resource.getFilename());
            String generatedFileContents = SpecsStrings.normalizeFileContents(SpecsIo.read(generatedFile), true);

            Assert.assertEquals(txtContents, generatedFileContents);
        }
    }

}
