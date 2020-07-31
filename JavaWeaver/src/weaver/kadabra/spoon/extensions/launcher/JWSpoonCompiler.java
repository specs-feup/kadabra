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

package weaver.kadabra.spoon.extensions.launcher;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import spoon.Launcher;
import spoon.SpoonException;
import spoon.compiler.Environment;
import spoon.reflect.cu.CompilationUnit;
import spoon.reflect.declaration.CtPackage;
import spoon.reflect.declaration.CtType;
import spoon.reflect.factory.Factory;
import spoon.reflect.visitor.DefaultJavaPrettyPrinter;
import spoon.reflect.visitor.PrettyPrinter;
import spoon.support.compiler.jdt.JDTBasedSpoonCompiler;
import weaver.kadabra.spoon.extensions.printer.KadabraPrettyPrinter;
import weaver.utils.SpoonUtils;

/**
 * Extending {@link JDTBasedSpoonCompiler} so the compilation unit printing comprises the use of
 * {@link KadabraPrettyPrinter} instead of {@link DefaultJavaPrettyPrinter}
 * 
 * @author Tiago
 *
 */
public class JWSpoonCompiler extends JDTBasedSpoonCompiler {

    public JWSpoonCompiler(Factory factory) {
        super(factory);
    }

    @Override
    // protected InputStream getCompilationUnitInputStream(String path) {
    protected InputStream getCompilationUnitInputStream(CompilationUnit cu) {
        Environment env = factory.getEnvironment();
        // spoon.reflect.cu.CompilationUnit cu = factory.CompilationUnit().getMap().get(path);
        List<CtType<?>> toBePrinted = cu.getDeclaredTypes();

        PrettyPrinter printer = SpoonUtils.createPrettyPrinter(env);// new JWJavaPrettyPrinter(env);
        printer.calculate(cu, toBePrinted);

        return new ByteArrayInputStream(printer.getResult().toString().getBytes());
    }

    private File getOutputDirectory() {
        // return outputDirectory;
        return getSourceOutputDirectory();
    }

    private void setOutputFolder(File outputDirectory) {
        // setSourceOutputDirectory(outputDirectory); // Spoon 6
        // this.outputDirectory = outputDirectory;
    }

    @Override
    protected void generateProcessedSourceFilesUsingCUs() {
        // System.out.println("OUTPUT DIRECTORY: " + outputDirectory);

        // System.out.println("BINARY OUTPUT DIRECTORY: " + getBinaryOutputDirectory());
        // System.out.println("SOURCE OUTPUT DIRECTORY: " + getSourceOutputDirectory());

        factory.getEnvironment().debugMessage("Generating source using compilation units...");
        // Check output directory
        if (getOutputDirectory() == null) {
            throw new RuntimeException("You should set output directory before generating source files");
        }
        // Create spooned directory
        if (getOutputDirectory().isFile()) {
            throw new RuntimeException("Output must be a directory");
        }
        if (!getOutputDirectory().exists()) {
            if (!getOutputDirectory().mkdirs()) {
                throw new RuntimeException("Error creating output directory");
            }
        }

        try {
            setOutputFolder(getOutputDirectory().getCanonicalFile());
        } catch (IOException e1) {
            throw new SpoonException(e1);
        }

        factory.getEnvironment().debugMessage("Generating source files to: " + getOutputDirectory());

        List<File> printedFiles = new ArrayList<>();
        for (spoon.reflect.cu.CompilationUnit cu : factory.CompilationUnit().getMap().values()) {

            if (cu.getDeclaredTypes().size() == 0) { // case of package-info
                continue;
            }

            CtType<?> element = cu.getMainType();

            CtPackage pack = element.getPackage();

            // create package directory
            File packageDir;
            if (pack.isUnnamedPackage()) {
                packageDir = new File(getOutputDirectory().getAbsolutePath());
            } else {
                // Create current package directory
                packageDir = new File(getOutputDirectory().getAbsolutePath() + File.separatorChar
                        + pack.getQualifiedName().replace('.', File.separatorChar));
            }
            if (!packageDir.exists()) {
                if (!packageDir.mkdirs()) {
                    throw new RuntimeException("Error creating output directory");
                }
            }

            // print type
            try {
                File file = new File(packageDir.getAbsolutePath() + File.separatorChar + element.getSimpleName()
                        + DefaultJavaPrettyPrinter.JAVA_FILE_EXTENSION);
                file.createNewFile();

                // the path must be given relatively to to the working directory
                // try (InputStream is = getCompilationUnitInputStream(cu.getFile().getPath());
                try (InputStream is = getCompilationUnitInputStream(cu);
                        FileOutputStream outFile = new FileOutputStream(file);) {

                    IOUtils.copy(is, outFile);
                }
                if (!printedFiles.contains(file)) {
                    printedFiles.add(file);
                }

            } catch (Exception e) {
                Launcher.LOGGER.error(e.getMessage(), e);
            }
        }
    }
}
