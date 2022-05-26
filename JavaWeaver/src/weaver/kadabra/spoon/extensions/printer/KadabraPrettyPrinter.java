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

package weaver.kadabra.spoon.extensions.printer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import pt.up.fe.specs.util.SpecsLogs;
import spoon.compiler.Environment;
import spoon.processing.Processor;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.code.CtComment;
import spoon.reflect.code.CtFor;
import spoon.reflect.code.CtForEach;
import spoon.reflect.code.CtIf;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtSwitch;
import spoon.reflect.code.CtSynchronized;
import spoon.reflect.code.CtTry;
import spoon.reflect.code.CtWhile;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtConstructor;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtTypeMember;
import spoon.reflect.visitor.DefaultImportComparator;
import spoon.reflect.visitor.DefaultJavaPrettyPrinter;
import spoon.reflect.visitor.DefaultTokenWriter;
import spoon.reflect.visitor.ForceImportProcessor;
import spoon.reflect.visitor.ImportCleaner;
import spoon.reflect.visitor.ImportConflictDetector;
import spoon.reflect.visitor.PrinterHelper;
import spoon.reflect.visitor.printer.CommentOffset;
// import spoon.reflect.visitor.printer.PrinterHelper;
import weaver.kadabra.exceptions.JavaWeaverException;
import weaver.kadabra.spoon.extensions.launcher.JWEnvironment;
import weaver.kadabra.spoon.extensions.nodes.CtKadabraSnippetElement;
import weaver.kadabra.spoon.extensions.nodes.CtKadabraSnippetStatement;
import weaver.utils.weaving.AnnotationsTable;

public class KadabraPrettyPrinter extends DefaultJavaPrettyPrinter {

    private AnnotationsTable table;
    private PrinterHelper printer;
    private Environment env;

    public KadabraPrettyPrinter(Environment env) {
        super(env);

        if (!(env instanceof JWEnvironment)) {
            throw new RuntimeException(
                    "Kadabra pretty printer requires the use of KadabraEnvironment, otherwise the inserts around some elements, such as methods and fields, will not work.");
        }
        this.env = env;
        try {
            Field printerF = DefaultJavaPrettyPrinter.class.getDeclaredField("printer");
            printerF.setAccessible(true);
            // printer = (PrinterHelper) printerF.get(this);
            printer = ((DefaultTokenWriter) printerF.get(this)).getPrinterHelper();
            printerF.setAccessible(false);
        } catch (Exception e) {
            throw new JavaWeaverException("Could not get the field used for printing", e);
        }

        // table = AnnotationsTable.getStaticTable();
        table = ((JWEnvironment) env).getTable();

        // Enable AutoImport
        if (env.isAutoImports()) {
            List<Processor<CtElement>> preprocessors = Collections
                    .unmodifiableList(Arrays.<Processor<CtElement>> asList(
                            // try to import as much types as possible
                            new ForceImportProcessor(),
                            // remove unused imports first. Do not add new imports at time when conflicts are not
                            // resolved
                            new ImportCleaner().setCanAddImports(false),
                            // solve conflicts, the current imports are relevant too
                            new ImportConflictDetector(),
                            // compute final imports
                            new ImportCleaner().setImportComparator(new DefaultImportComparator())));
            setIgnoreImplicit(false);
            setPreprocessors(preprocessors);

        }

    }

    @Override
    public <R> void visitCtBlock(CtBlock<R> block) {
        enterCtStatement(block);
        if (!block.isImplicit()) {
            printer.write("{");
        }
        printer.incTab();
        for (CtStatement statement : block.getStatements()) {
            if (!statement.isImplicit()) {
                printer.writeln();
                // printer.writeTabs(); // Spoon 6
                writeStatement(statement);
            }
        }
        printer.decTab();
        if (env.isPreserveLineNumbers()) {
            if (!block.isImplicit()) {
                printer.write("}");
            }
        } else {
            printer.writeln();
            // printer.writeTabs(); // Spoon 6
            if (!block.isImplicit()) {
                printer.write("}");
            }
        }
    }

    private void writeStatement(CtStatement statement) {
        scan(statement);

        if (requiresSemiColon(statement)) {
            printer.write(";");
        }

        getElementPrinterHelper().writeComment(statement, CommentOffset.AFTER);
    }

    // @Override
    // public <T> void visitCtClass(CtClass<T> ctClass) {
    // KadabraLog.debug("PRINTING: " + ctClass.getSimpleName());
    // super.visitCtClass(ctClass);
    // }

    // public void setTable(AnnotationsTable table) {
    // this.table = table;
    // }

    // @Override
    // protected void writeStatement(CtStatement e) {
    // scan(e);
    // if (requiresSemiColon(e)) {
    // write(";");
    // }
    // }

    @Override
    public DefaultJavaPrettyPrinter scan(CtElement e) {
        if (!(e instanceof CtTypeMember)) {
            try {
                return super.scan(e);
            } catch (Exception ex) {
                SpecsLogs.info("Could not print " + e + " (" + ex.getMessage() + ")");
                return this;
            }
        }
        CtTypeMember element = (CtTypeMember) e;
        // If no annotations just use normal printer
        // AnnotationsTable table = AnnotationsTable.getStaticTable();
        if (!table.contains(element)) {
            return super.scan(element);
        }

        // Process annotations before target element
        table.getBefore(element).forEach(this::scanLn);
        // if target node contains annotations to replace
        if (table.containsReplace(element)) {

            // Process annotations and do not process target node
            table.getReplace(element).forEach(this::scanLn);
        } else {
            // Process the target node
            super.scan(element);
        }
        // Process annotations before target element
        if (table.containsAfter(element)) {
            table.getReversedAfter(element).forEach(this::lnScan);
        }
        return this;
    }

    private void scanLn(CtElement e) {
        super.scan(e);
        printer.writeln();
        // printer.writeTabs(); // Spoon 6
    }

    private void lnScan(CtElement e) {
        printer.writeln();
        // printer.writeTabs(); // Spoon 6
        super.scan(e);
    }

    @Override
    public void visitCtCodeSnippetStatement(CtCodeSnippetStatement statement) {

        if (statement instanceof CtKadabraSnippetStatement
                || statement instanceof CtKadabraSnippetElement) {

            processSnippet(statement.getValue());

        } else {

            super.visitCtCodeSnippetStatement(statement);
        }
    }

    @Override
    public void visitCtComment(CtComment comment) {

        if (!table.contains(comment)) {

            super.visitCtComment(comment);
            return;
        }

        table.getBefore(comment).forEach(this::scanLn);

        if (table.containsReplace(comment)) {
            table.getReplace(comment).forEach(this::scanLn);
            table.getReversedAfter(comment).forEach(this::scanLn);
        } else {
            super.visitCtComment(comment);
            table.getReversedAfter(comment).forEach(this::lnScan);
        }

    }

    private static boolean requiresSemiColon(CtStatement stmt) {
        // If CtKadabraSnippetStatement, check if it contains ;
        if (stmt instanceof CtKadabraSnippetStatement) {
            return !((CtKadabraSnippetStatement) stmt).getValue().endsWith(";");
        }

        return !((stmt instanceof CtBlock) || (stmt instanceof CtIf) || (stmt instanceof CtFor)
                || (stmt instanceof CtForEach) || (stmt instanceof CtWhile) || (stmt instanceof CtTry)
                || (stmt instanceof CtSwitch) || (stmt instanceof CtSynchronized)
                || (stmt instanceof CtClass) || stmt instanceof CtComment);
    }

    /**
     * A simple approach for indentation
     * 
     * @param snippetStr
     */
    private void processSnippet(String snippetStr) {
        if (snippetStr.trim().isEmpty()) {
            return;
        }
        try (BufferedReader br = new BufferedReader(new StringReader(snippetStr))) {
            String line = br.readLine().trim();
            printer.write(line);
            int indent = indentLevelForNextLine(line);
            while ((line = br.readLine()) != null) {
                line = line.trim();
                int takeOne = line.startsWith("}") ? 1 : 0;
                setIndentLevel(indent - takeOne);
                printer.writeln();
                // printer.writeTabs(); // Spoon 6
                printer.write(line);
                indent = indentLevelForNextLine(line) + takeOne;

            }
        } catch (IOException e) {
            SpecsLogs.warn("Error message:\n", e);
        }

    }

    @Override
    public <T> void visitCtMethod(CtMethod<T> m) {
        // Call original method
        super.visitCtMethod(m);

        // Add a newline at the end
        printer.writeln();
    }

    @Override
    public <T> void visitCtConstructor(CtConstructor<T> constructor) {
        // Call original method
        super.visitCtConstructor(constructor);

        // Add a newline at the end
        printer.writeln();
    }
    //
    // @Override
    // public String printElement(CtElement element) {
    // String errorMessage = "";
    // try {
    // System.out.println("CLONING");
    // // now that pretty-printing can change the model, we only do it on a clone
    // CtElement clone = element.clone();
    //
    // // required: in DJPP some decisions are taken based on the content of the parent
    // if (element.isParentInitialized()) {
    // clone.setParent(element.getParent());
    // }
    // applyPreProcessors(clone);
    // scan(clone);
    // } catch (ParentNotInitializedException ignore) {
    // LOGGER.error(ERROR_MESSAGE_TO_STRING, ignore);
    // errorMessage = ERROR_MESSAGE_TO_STRING;
    // }
    // // in line-preservation mode, newlines are added at the beginning to matches the lines
    // // removing them from the toString() representation
    // return toString().replaceFirst("^\\s+", "") + errorMessage;
    // }

    private void setIndentLevel(int indent) {
        while (indent < 0) {
            printer.decTab();
            indent++;
        }
        while (indent > 0) {
            printer.incTab();
            indent--;
        }
    }

    private static int indentLevelForNextLine(String line) {
        return StringUtils.countMatches(line, "{") - StringUtils.countMatches(line, "}");
    }

    public String getSourceCode(CtElement element) {
        // Reset buffer
        reset();

        return printElement(element);
    }

}
