/**
 * Copyright 2015 SPeCS Research Group.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package weaver.kadabra.joinpoints;

import weaver.kadabra.abstracts.joinpoints.AApp;
import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtInterface;
import spoon.reflect.factory.Factory;
import spoon.support.gui.SpoonModelTree;
import weaver.kadabra.JWeaver;
import weaver.kadabra.abstracts.joinpoints.*;
import weaver.kadabra.spoon.extensions.nodes.CtApp;
import weaver.utils.android.AndroidResources;
import weaver.utils.generators.MapGenerator;
import weaver.utils.weaving.ActionUtils;
import javax.swing.*;
import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class JApp<Self extends JApp<Self>> extends AApp<Self> {

    public final Launcher spoon;
    private final Set<File> sources;
    private final AndroidResources androidResources;

    public JApp(CtApp node, JWeaver weaver) {
        this(node, Collections.emptyList(), weaver);
    }

    public JApp(CtApp node, List sources, JWeaver weaver) {
        super(node, weaver);
        this.spoon = node.spoon;
        this.sources = new HashSet<>(sources);
        this.androidResources = AndroidResources.newInstance(sources);
    }

    public static JApp<?> newInstance(Launcher spoon, List<File> sources, JWeaver weaver) {
        var app = new JApp<>(new CtApp(spoon), sources, weaver);

        return app;
    }

    public static JApp<?> newInstance(CtApp app, JWeaver weaver) {
        return new JApp<>(app, weaver);
    }

    public AndroidResources getAndroidResources() {
        return androidResources;
    }

    private List<JFile<?>> retrieveFiles() {

        final List<JFile<?>> files = spoon.getFactory().CompilationUnit().getMap().values().stream()
                .map(cu -> new JFile<>(cu, getWeaverEngine()))
                .collect(Collectors.toList());

        return files;
    }

    @Override
    public String getFolderImpl() {
        final StringBuilder ret = new StringBuilder();
        spoon.getModelBuilder().getInputSources().forEach(f -> ret.append(f.getAbsolutePath()));
        return ret.toString();
    }

    @Override
    public AClass<?> newClassImpl(String name) {
        return newClassImpl(name, null, null);
    }

    @Override
    public AClass<?> newClassImpl(String name, String extend, String[] implement) {
        if (name == null || name.isEmpty()) {
            throw new NullPointerException("the name of the new class cannot be null or empty");
        }
        var cu = ActionUtils.compilationUnitWithClass(name, extend, implement,
                spoon.getModelBuilder().getBinaryOutputDirectory(), spoon.getFactory());
        CtClass<?> mainClass = (CtClass<?>) cu.getMainType();
        AClass<?> newInstance = new JClass<>(mainClass, cu, getWeaverEngine());
        return newInstance;

    }

    @Override
    public AInterfaceType<?> newInterfaceImpl(String name) {
        return newInterfaceImpl(name, null);
    }

    @Override
    public AInterfaceType<?> newInterfaceImpl(String name, String[] extend) {
        final CtInterface<?> newInterface = ActionUtils.compilationUnitWithInterface(name, extend,
                spoon.getModelBuilder().getBinaryOutputDirectory(),
                spoon.getFactory());
        JInterfaceType<?> newInstance = new JInterfaceType<>(newInterface, getWeaverEngine());
        return newInstance;
    }

    @Override
    public AClass<?> mapVersionsImpl(String name, String keyType, AInterfaceType<?> _interface, String methodName) {

        File outDir = spoon.getModelBuilder().getBinaryOutputDirectory();
        Factory factory = spoon.getFactory();
        var cu = MapGenerator.generate(factory, name, keyType, _interface, methodName, outDir);
        JClass<?> newInstance = new JClass<>((CtClass<?>) cu.getMainType(), cu, getWeaverEngine());
        return newInstance;
    }

    @Override
    public String getShowASTImpl(String Title) {
        // SpoonModelTree window starts in the constructor
        SpoonModelTree tree = new SpoonModelTree(spoon.getFactory());
        tree.setTitle(Title);
        tree.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        return "";
    }

    @Override
    public CtElement getNodeImpl() {
        // App has no AST node of its own
        return null;
    }

    /**
     * App is the root node, so it has not parent.
     */
    @Override
    public AJoinpoint<?> getParentImpl() {
        return null;
    }

    @Override
    public boolean getCompareNodesImpl(AJoinpoint<?> aJoinPoint) {
        return this == aJoinPoint;
    }

    public Launcher getSpoon() {
        // TODO Auto-generated method stub
        return spoon;
    }

    @Override
    public String getToStringImpl() {
        return "Java application"; // from " + getFolderImpl();
    }

    @Override
    public String getCodeImpl() {
        String separator = "/************/";
        return retrieveFiles().stream()
                .map(AFile::getSrcCodeImpl)
                .collect(Collectors.joining("\n" + separator + "\n", "", "\n"));
    }

    @Override
    public AJoinpoint<?>[] getChildrenImpl() {
        return retrieveFiles().toArray(size -> new AJoinpoint[size]);
    }

    @Override
    public AAndroidManifest<?> getManifestImpl() {
        var manifest = androidResources.getAndroidManifest();
        return manifest != null ? new JAndroidManifest<>(manifest, getWeaverEngine()) : null;
    }

    @Override
    public AFile<?>[] getFilesImpl() {
        return retrieveFiles().toArray(size -> new AFile[size]);
    }

}
