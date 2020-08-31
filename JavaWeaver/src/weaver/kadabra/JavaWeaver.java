package weaver.kadabra;

import static weaver.specification.JavaWeaverResource.ACTIONS;
import static weaver.specification.JavaWeaverResource.ARTIFACTS;
import static weaver.specification.JavaWeaverResource.JOINPOINTS;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.io.FileUtils;
import org.lara.interpreter.joptions.config.interpreter.LaraiKeys;
import org.lara.interpreter.weaver.interf.AGear;
import org.lara.interpreter.weaver.interf.JoinPoint;
import org.lara.interpreter.weaver.options.WeaverOption;
import org.lara.interpreter.weaver.options.WeaverOptionBuilder;
import org.lara.language.specification.LanguageSpecification;
import org.suikasoft.jOptions.Interfaces.DataStore;

import kadabra.resources.KadabraAPIResources;
import kadabra.resources.LaraAPIResources;
import pt.up.fe.specs.kadabra.weaver.LaraCoreApiResource;
import pt.up.fe.specs.kadabra.weaver.LaraWeaverApiResource;
import pt.up.fe.specs.spoon.SpoonFactory;
import pt.up.fe.specs.util.SpecsCollections;
import pt.up.fe.specs.util.SpecsIo;
import pt.up.fe.specs.util.SpecsLogs;
import pt.up.fe.specs.util.providers.ResourceProvider;
import pt.up.fe.specs.util.utilities.LineStream;
import spoon.Launcher;
import spoon.OutputType;
import spoon.compiler.Environment;
import spoon.support.JavaOutputProcessor;
import weaver.kadabra.abstracts.weaver.AJavaWeaver;
import weaver.kadabra.exceptions.JavaWeaverException;
import weaver.kadabra.gears.KadabraMetrics;
import weaver.kadabra.gears.LoggingGear;
import weaver.kadabra.gears.Report;
import weaver.kadabra.joinpoints.JApp;
import weaver.kadabra.spoon.extensions.launcher.JWSpoonLauncher;
import weaver.kadabra.spoon.extensions.printer.KadabraPrettyPrinter;
import weaver.kadabra.util.KadabraLog;
import weaver.options.JavaWeaverKeys;
import weaver.options.JavaWeaverOption;
import weaver.utils.SpoonUtils;
import weaver.utils.processors.IfProcessor;
import weaver.utils.weaving.AnnotationsTable;

/**
 * LARA Weaving Engine that uses Spoon as the Java compiler/processor <br>
 * Weaver Implementation for JavaWeaver<br>
 * Since the generated abstract classes are always overwritten, their implementation should be done by extending those
 * abstract classes with user-defined classes.<br>
 * The abstract class {@link weaver.abstracts.AJoinPoint} can be used to add user-defined methods and fields which the
 * user intends to add for all join points and are not intended to be used in LARA aspects.
 *
 * @author Lara C.
 */
public class JavaWeaver extends AJavaWeaver {
    static {
        KadabraLog.setDebug(true);
    }

    // private static final Set<String> WEAVER_NAMES = SpecsCollections.asSet("kadabra");
    private static final Set<String> LANGUAGES = SpecsCollections.asSet("java");

    // Fields
    private DataStore args;
    private File outputDir;
    private JWSpoonLauncher spoon;
    private JApp jApp;
    private AnnotationsTable table;
    private List<File> classPath;
    private boolean clearOutputFolder;
    private boolean prettyPrint;
    private File currentOutputDir;
    private KadabraPrettyPrinter sourceCodePrinter;

    private boolean noClassPath = false; // Continues even if an error of missing lib occurs
    private OutputType outType = OutputType.COMPILATION_UNITS;
    private final LoggingGear loggingGear;
    private final Report reportGear;
    // private final JavaWeaverGear dependeciesGear = new JavaWeaverGear();
    private File temp;

    public JavaWeaver() {
        loggingGear = new LoggingGear();
        loggingGear.setActive(false);
        reportGear = new Report();
        reportGear.setActive(false);
        table = AnnotationsTable.getStaticTable();
        this.setWeaverProfiler(new KadabraMetrics());
    }

    /**
     * Set a file/folder in the weaver if it is valid file/folder type for the weaver.
     *
     * @param sources
     *            the file with the source code
     * @param outputDir
     *            output directory for the generated file(s)
     * @param args
     *            arguments to start the weaver
     * @return true if the file type is valid
     */
    @Override
    public boolean begin(List<File> sources, File outputDir, DataStore args) {
        this.args = args;
        classPath = new ArrayList<>();
        this.outputDir = outputDir;
        clearOutputFolder = false;
        reset();
        parseOptions(args);

        // if (prettyPrint) {
        // tempOutFolder = new File(IoUtils.getCanonicalPath(outputDir) + "_temp");
        // outputDir = tempOutFolder;
        // }

        if (prettyPrint) {

            this.outputDir.mkdirs();
            temp = getTemporaryWeaverFolder();// new File("_jw_temp");
            outputDir = temp;
            // this.setOutputProcessor(temp, spoon, spoon.getEnvironment());
            //
            // this.setInputSources(Arrays.asList(temp), spoon);
        }

        // Pass only Java files to spoon
        // Method can do some processing, such as filtering duplicate classes
        var javaSources = getJavaSources(sources);
        spoon = newSpoon(javaSources, outputDir);

        // spoon = newSpoon(sources, outputDir);
        this.currentOutputDir = outputDir;
        buildAndProcess();
        /* turning off path verifier as it is giving errors for new classes and code */
        // spoon.getEnvironment().setNoClasspath(true);
        // spoon.getEnvironment().setNoClasspath(false);
        jApp = JApp.newInstance(spoon, sources);
        // spoon.getEnvironment().setAutoImports(false);

        return true;
    }

    private List<File> getJavaSources(List<File> sources) {
        List<File> javaSources = new ArrayList<>();
        Map<String, File> seenTypes = new HashMap<>();
        for (var source : sources) {
            var javaFiles = SpecsIo.getFilesRecursive(source, Arrays.asList("java"));

            for (var javaFile : javaFiles) {
                // Extract Java package
                String packageDecl = getPackage(javaFile).orElse("");

                var separator = packageDecl.isEmpty() ? "" : ".";

                var javaKey = packageDecl + separator + javaFile.getName();

                if (seenTypes.containsKey(javaKey)) {

                    SpecsLogs.info("Found duplicate class '" + javaFile.getAbsolutePath() + "', already added '"
                            + seenTypes.get(javaKey) + "'");
                    continue;
                }

                javaSources.add(javaFile);
                seenTypes.put(javaKey, javaFile);
            }
        }

        return javaSources;
    }

    private Optional<String> getPackage(File javaFile) {
        // Read each line until 'package' is found
        try (var lines = LineStream.newInstance(javaFile)) {
            while (lines.hasNextLine()) {
                var line = lines.nextLine().strip().toLowerCase();

                // Found package
                if (line.startsWith("package ")) {
                    var packageName = line.substring("package ".length()).strip();
                    int colonIndex = packageName.indexOf(';');
                    if (colonIndex == -1) {
                        SpecsLogs.info("Found package, but could not find ';': " + line);
                        return Optional.empty();
                    }

                    return Optional.of(packageName.substring(0, colonIndex).strip());
                }

                // Stop when import, class, interface or modifier is found
                if (line.startsWith("import ") || line.startsWith("public ") || line.startsWith("class ")
                        || line.startsWith("interface ") || line.startsWith("enum ")) {
                    break;
                }

                // Ignore other lines
            }
        }

        return Optional.empty();
    }

    /**
     * Return a JoinPoint instance of the language root, i.e., an instance of AApp
     *
     * @return an instance of the join point root/program
     */
    @Override
    public JoinPoint select() {
        return jApp;
    }

    /**
     * Closes the weaver to the specified output directory location, if the weaver generates new file(s)
     *
     * @return if close was successful
     */
    @Override
    public boolean close() {

        if (clearOutputFolder) {
            if (!args.hasValue(LaraiKeys.OUTPUT_FOLDER)) {
                KadabraLog.warning("No output folder defined, skipping cleaning");
            } else {
                try {
                    FileUtils.cleanDirectory(outputDir);
                } catch (final IOException e) {
                    KadabraLog.warning("Output folder could not be cleaned before code generation: " + e.getMessage());
                }
            }

        }

        if (args.get(JavaWeaverKeys.WRITE_CODE)) {
            if (prettyPrint) {
                spoon.prettyprint();
                spoon = newSpoon(Arrays.asList(temp), outputDir);
                // spoon.getEnvironment().setNoClasspath(true);
                // spoon.getEnvironment().setNoClasspath(false);
                buildAndProcess();
                spoon.prettyprint();
            } else {
                // System.out.println("PRESERVE? " + spoon.getEnvironment().isPreserveLineNumbers());
                spoon.prettyprint();
            }
        }

        // Write XML files
        jApp.getAndroidResources().write(outputDir);

        // writeCode(temp, outputDir, true);

        if (reportGear.isActive()) {

            KadabraLog.info("REPORT: ");
            KadabraLog.info("Advised Join Points: " + reportGear.getAdvisedJoinPoints().size());
        }
        return true;
    }

    @Override
    public void writeCode(File outputFolder) {
        writeCode(currentOutputDir, outputFolder);
    }

    public void writeCode(File inputFolder, File outputFolder) {
        if (clearOutputFolder) {
            try {
                FileUtils.cleanDirectory(outputFolder);
            } catch (final IOException e) {
                KadabraLog.warning("Output folder could not be cleaned before code generation: " + e.getMessage());
            }
        }

        // Generate code to current output dir
        spoon.prettyprint();

        var newSpoon = newSpoon(Arrays.asList(inputFolder), outputFolder);
        // newSpoon.getEnvironment().setNoClasspath(true);
        // newSpoon.getEnvironment().setNoClasspath(false);
        buildAndProcess(newSpoon);
        newSpoon.prettyprint();

        // Write XML files
        jApp.getAndroidResources().write(outputFolder);
    }

    public boolean closeOld() {

        if (clearOutputFolder) {
            try {
                FileUtils.cleanDirectory(outputDir);
            } catch (final IOException e) {
                KadabraLog.warning("Output folder could not be cleaned before code generation: " + e.getMessage());
            }
        }
        spoon.prettyprint();

        if (prettyPrint) {
            callAstyle();
            /*
             * if recompile
            Launcher tempSpoon = newSpoon(Arrays.asList(tempOutFolder), outputDir);
            tempSpoon.buildModel();
            tempSpoon.prettyprint();
            try {
            FileUtils.cleanDirectory(tempOutFolder);
            } catch (final IOException e) {
            KadabraLog.warning("Temporary Output folder could not be cleaned: " + e.getMessage());
            }*/
        }
        // // spoon.getEnvironment().setNoClasspath(true);
        // SpoonCompiler compiler = spoon.createCompiler(Arrays.asList(new FileSystemFolder(outputDir)));
        // // compiler.set
        // System.out.println("rebuild");
        // compiler.build();
        // System.out.println("generating");
        // compiler.generateProcessedSourceFiles(OutputType.COMPILATION_UNITS, null);
        // // spoon.prettyprint();
        // }

        if (reportGear.isActive()) {

            KadabraLog.info("REPORT: ");
            KadabraLog.info("Advised Join Points: " + reportGear.getAdvisedJoinPoints().size());
        }
        // if (dependeciesGear.isTimerUsed()) {
        // String contents = IoUtils.getResource(JavaWeaverGear.TIMER_CLASS);
        //
        // File timerFile = IoUtils.safeFolder(new File(outputDir, "weaver/kadabra/monitor"));
        // timerFile = new File(timerFile, "KadabraTimer.java");
        // IoUtils.write(timerFile, contents);
        // }
        return true;
    }

    /**
     * Returns a list of Gears associated to this weaver engine
     *
     * @return a list of implementations of {@link AGear} or null if no gears are available
     */
    @Override
    public List<AGear> getGears() {
        List<AGear> gears = new ArrayList<>();
        gears.add(reportGear);
        gears.add(loggingGear);
        // gears.add(dependeciesGear);
        return gears;
    }

    private void reset() {
        SpoonUtils.resetCounter();
        table.reset();
        /*RESET GEARS*/
        loggingGear.reset();
        reportGear.reset();
    }

    /**
     * Instantiates a new {@link JWSpoonLauncher} based on the given input sources and the properties of this
     * {@link JavaWeaver} instance.
     *
     * @param sources
     *            the input sources to parse
     * @return
     */
    private JWSpoonLauncher newSpoon(List<File> sources, File outputDir) {
        // Launcher.LOGGER.setLevel(Level.INFO);
        // Logger.getRootLogger().setLevel(Level.TRACE);

        // try {
        // Launcher.LOGGER.addAppender(new FileAppender(new SimpleLayout(), "logg.txt"));
        // } catch (IOException e1) {
        // SpecsLogs.msgWarn("Error message:\n", e1);
        // }

        // try {
        // BasicConfigurator.configure(new FileAppender(new SimpleLayout(), "logg.txt"));
        // } catch (IOException e1) {
        // SpecsLogs.msgWarn("Error message:\n", e1);
        // }

        JWSpoonLauncher spoon = new JWSpoonLauncher(sources);

        // String[] spoonArgs = { "--level", "ERROR" };
        // spoon.setArgs(spoonArgs);

        Environment environment = spoon.getFactory().getEnvironment();
        // System.out.println("LEVE: " + environment.getLevel());
        // environment.setLevel("WARN");
        // System.out.println("NEW LEVE: " + environment.getLevel());
        // environment.setLevel("WARN");
        // System.out.println("LEVEL: " + Launcher.LOGGER.getLevel());
        // Launcher.LOGGER.setLevel(Level.TRACE);
        // System.out.println("LEVEL NEW: " + Launcher.LOGGER.getLevel());

        spoon.setArgs(new String[] { "--output-type", outType.toString() }); // required to define the type of output...

        if (!classPath.isEmpty()) {

            try {
                List<String> filesStr = classPath.stream().map(SpecsIo::getCanonicalPath).collect(Collectors.toList());
                String[] classPathArray = filesStr.toArray(new String[0]);
                // KadabraLog.info(filesStr);
                environment.setSourceClasspath(classPathArray);
                // KadabraLog.info(Arrays.toString(environment.getSourceClasspath());
            } catch (Exception e) {
                throw new JavaWeaverException("setting the classpath", e);
            }
        }
        // environment.disableConsistencyChecks(); // Spoon 8
        // environment.setSelfChecks(false); // Spoon 6
        // environment.setSelfChecks(true);
        // environment.setAutoImports(true);
        environment.setCommentEnabled(true);
        // environment.setGenerateJavadoc(true);

        setOutputProcessor(outputDir, spoon, environment);
        // the output type: compilation units)
        environment.setNoClasspath(noClassPath);
        // environment.setNoClasspath(false);
        setInputSources(sources, spoon);
        spoon.addProcessor(new IfProcessor());
        // spoon.addProcessor(new CommentProcessor());

        // Set fully qualified names
        // environment.setAutoImports(true);
        // environment.setAutoImports(!args.get(JavaWeaverKeys.FULLY_QUALIFIED_NAMES));
        if (args.get(JavaWeaverKeys.FULLY_QUALIFIED_NAMES)) {
            environment.setAutoImports(false);
        } else {
            environment.setAutoImports(true);
        }

        // environment.setPreserveLineNumbers(false);

        environment.setCopyResources(args.get(JavaWeaverKeys.COPY_RESOURCES));

        environment.setComplianceLevel(args.get(JavaWeaverKeys.JAVA_COMPLIANCE_LEVEL));

        // Set pretty printer
        this.sourceCodePrinter = SpoonUtils.createSourcePrinter(environment);

        return spoon;
    }

    public KadabraPrettyPrinter getSourceCodePrinter() {
        return sourceCodePrinter;
    }

    private static void setInputSources(List<File> sources, Launcher spoon) {
        for (File source : sources) {
            spoon.addInputResource(SpecsIo.getCanonicalPath(source));
        }
    }

    private static void setOutputProcessor(File outputDir, Launcher spoon, Environment environment) {
        // JavaOutputProcessor outProcessor = spoon.createOutputWriter(outputDir, environment); // Spoon 6
        JavaOutputProcessor outProcessor = spoon.createOutputWriter(); // Spoon 8
        environment.setDefaultFileGenerator(outProcessor); // Define output folder (needed for the output type: classes)

        // spoon.setOutputDirectory(IoUtils.getCanonicalPath(outputDir)); // Define output folder AGAIN (needed for the
        spoon.setSourceOutputDirectory(SpecsIo.getCanonicalPath(outputDir));// Define output folder AGAIN
        spoon.setBinaryOutputDirectory(SpecsIo.getCanonicalPath(outputDir)); // Define output folder AGAIN (needed for
    }

    /**
     * Parse the options given to the weaver
     *
     * @param args
     */
    private void parseOptions(DataStore args) {

        // System.out.println("IN JavaWeaver.parseOptions\n" + args);
        if (args.hasValue(JavaWeaverKeys.CLEAR_OUTPUT_FOLDER)) {
            clearOutputFolder = args.get(JavaWeaverKeys.CLEAR_OUTPUT_FOLDER);
        }
        if (args.hasValue(JavaWeaverKeys.NO_CLASSPATH)) {
            noClassPath = args.get(JavaWeaverKeys.NO_CLASSPATH);
        }
        if (args.hasValue(JavaWeaverKeys.INCLUDE_DIRS)) {
            classPath = args.get(JavaWeaverKeys.INCLUDE_DIRS).getFiles();
        }
        if (args.hasValue(JavaWeaverKeys.OUTPUT_TYPE)) {
            outType = args.get(JavaWeaverKeys.OUTPUT_TYPE);
        }
        if (args.hasValue(JavaWeaverKeys.SHOW_LOG_INFO)) {
            loggingGear.setActive(args.get(JavaWeaverKeys.SHOW_LOG_INFO));
        }
        if (args.hasValue(JavaWeaverKeys.FORMAT)) {
            prettyPrint = args.get(JavaWeaverKeys.FORMAT);
        }

        if (args.hasValue(JavaWeaverKeys.REPORT)) {

            reportGear.setActive(args.get(JavaWeaverKeys.REPORT));
        }

    }

    private void buildAndProcess() {
        spoon.buildModel();
        spoon.process();
    }

    private void buildAndProcess(Launcher spoonLauncher) {
        spoonLauncher.buildModel();
        spoonLauncher.process();
    }

    /**
     * @return the clearOutputFolder
     */
    public boolean isClearOutputFolder() {
        return clearOutputFolder;
    }

    /**
     * @param clearOutputFolder
     *            the clearOutputFolder to set
     */
    public void setClearOutputFolder(boolean clearOutputFolder) {
        this.clearOutputFolder = clearOutputFolder;
    }

    public boolean isNoClassPath() {
        return noClassPath;
    }

    public void setNoClassPath(boolean noClassPath) {
        this.noClassPath = noClassPath;
    }

    @Override
    public List<WeaverOption> getOptions() {
        return WeaverOptionBuilder.enum2List(JavaWeaverOption.class, JavaWeaverOption::getOption);
    }

    @Override
    public LanguageSpecification getLanguageSpecification() {
        return LanguageSpecification.newInstance(JOINPOINTS, ARTIFACTS, ACTIONS, true);
    }

    @Override
    public String getName() {
        return "KADABRA";
    }

    @Override
    public List<ResourceProvider> getAspectsAPI() {
        return ResourceProvider.getResourcesFromEnum(
                Arrays.asList(LaraAPIResources.class, KadabraAPIResources.class, LaraCoreApiResource.class,
                        LaraWeaverApiResource.class));

        // return ResourceProvider.getResources(LaraAPIResources.class);
    }

    private void callAstyle() {

        CommandLine astyle;
        if (System.getProperty("os.name").startsWith("Windows")) {
            astyle = new CommandLine("CMD");
            astyle.addArgument("/C");
            astyle.addArgument("astyle");
            // }
        } else {
            astyle = new CommandLine("astyle");
        }
        astyle.addArgument("-nq"); // q:quiet, n:no backup files

        List<File> files = SpecsIo.getFilesRecursive(outputDir, "java");
        files.forEach(x -> astyle.addArgument(x.getAbsolutePath()));

        // astyle.addArgument(Driver.getOptionValue("outdir") + "/*.c");
        // astyle.addArgument(Driver.getOptionValue("outdir") + "/*.h");

        SpecsLogs.msgLib("[Kadabra] Formatting code: " + astyle.toString());
        DefaultExecutor executor = new DefaultExecutor();
        try {
            executor.setWorkingDirectory(outputDir);
            executor.execute(astyle);
        } catch (IOException e) {
            SpecsLogs.msgWarn("[Kadabra] Formatting code: " + e.getMessage());
        }
    }

    /**
     * Get weaver with this type
     **/
    public static JavaWeaver getJavaWeaver() {
        return (JavaWeaver) getThreadLocalWeaver();
    }

    public static SpoonFactory getFactory() {
        return new SpoonFactory(getJavaWeaver().spoon.getFactory());
    }

    @Override
    public ResourceProvider getIcon() {
        return () -> "specs/kadabra/kadabra_icon_300dpi.png";
    }

    // @Override
    // public Set<String> getWeaverNames() {
    // return WEAVER_NAMES;
    // }

    @Override
    public Set<String> getLanguages() {
        return LANGUAGES;
    }

}
