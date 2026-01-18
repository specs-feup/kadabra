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
 * specific language governing permissions and limitations under the License. under the License.
 */

package weaver.kadabra.joinpoints;

import org.lara.interpreter.weaver.interf.JoinPoint;
import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtInterface;
import spoon.reflect.factory.Factory;
import spoon.support.gui.SpoonModelTree;
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

public class JApp extends AApp {

    public final Launcher spoon;
    private final Set<File> sources;
    private final AndroidResources androidResources;

    private JApp(Launcher spoon, List<File> sources) {
        this.spoon = spoon;
        this.sources = new HashSet<>(sources);
        this.androidResources = AndroidResources.newInstance(sources);
    }

    public static JApp newInstance(Launcher spoon, List<File> sources) {
        var app = new JApp(spoon, sources);

        return app;
    }

    public static JApp newInstance(CtApp app) {
        return new JApp(app.spoon, Collections.emptyList());
    }

    public AndroidResources getAndroidResources() {
        return androidResources;
    }

    private List<? extends AFile> retrieveFiles() {

        final List<JFile> files = spoon.getFactory().CompilationUnit().getMap().values().stream()
                .map(JFile::new)
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
    public AClass newClassImpl(String name) {
        return newClassImpl(name, null, null);
    }

    @Override
    public AClass newClassImpl(String name, String extend, String[] implement) {
        if (name == null || name.isEmpty()) {

            throw new NullPointerException("the name of the new class cannot be null or empty");
        }
        // KadabraLog.debug("GENERATING NEW CLASS: " + name);
        var cu = ActionUtils.compilationUnitWithClass(name, extend, implement,
                spoon.getModelBuilder().getBinaryOutputDirectory(), spoon.getFactory());
        CtClass<?> mainClass = (CtClass<?>) cu.getMainType();
        AClass newInstance = JClass.newInstance(mainClass, cu);
        return newInstance;

    }

    @Override
    public AInterfaceType newInterfaceImpl(String name) {
        return newInterfaceImpl(name, null);
    }

    @Override
    public AInterfaceType newInterfaceImpl(String name, String[] extend) {
        final CtInterface<Object> newInterface = ActionUtils.compilationUnitWithInterface(name, extend,
                spoon.getModelBuilder().getBinaryOutputDirectory(),
                spoon.getFactory());
        JInterfaceType<Object> newInstance = JInterfaceType.newInstance(newInterface);
        return newInstance;
    }

    @Override
    public AClass mapVersionsImpl(String name, String keyType, AInterfaceType _interface, String methodName) {

        File outDir = spoon.getModelBuilder().getBinaryOutputDirectory();
        Factory factory = spoon.getFactory();
        var cu = MapGenerator.generate(factory, name, keyType, _interface, methodName, outDir);
        JClass<?> newInstance = JClass.newInstance((CtClass<?>) cu.getMainType(), cu);
        return newInstance;
    }

    @Override
    public String showASTImpl(String title) {
        // SpoonModelTree window starts in the constructor
        SpoonModelTree tree = new SpoonModelTree(spoon.getFactory());
        tree.setTitle(title);
        // tree.isDefaultLookAndFeelDecorated();
        tree.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        return "";
    }

    @Override
    public CtElement getNode() {
        return null;
    }

    /**
     * App is the root node, so it has not parent.
     */
    @Override
    public AJoinPoint getParentImpl() {
        return null;
    }

    @Override
    public boolean compareNodes(AJoinPoint aJoinPoint) {
        return equals(aJoinPoint);
    }

    public Launcher getSpoon() {
        // TODO Auto-generated method stub
        return spoon;
    }

    @Override
    public String toString() {
        return "Java application"; // from " + getFolderImpl();
    }

    @Override
    public boolean same(JoinPoint iJoinPoint) {
        if (this.get_class().equals(iJoinPoint.get_class())) {

            return this.compareNodes((AJoinPoint) iJoinPoint);
        }
        return false;
    }

    @Override
    public String getCodeImpl() {
        String separator = "/************/";
        return retrieveFiles().stream()
                .map(AFile::getSrcCodeImpl)
                .collect(Collectors.joining("\n" + separator + "\n", "", "\n"));
    }

    @Override
    public AJoinPoint[] getChildrenArrayImpl() {
        return retrieveFiles().toArray(size -> new AJoinPoint[size]);
    }

    @Override
    public AAndroidManifest getManifestImpl() {
        var manifest = androidResources.getAndroidManifest();
        // var elementAttrs = manifest.getElementsByName("uses-permission").stream()
        // .map(element -> element.getAttribute("android:name"))
        // .collect(Collectors.joining(", "));
        // System.out.println("ELEMENTS: " + elementAttrs);
        return manifest != null ? new JAndroidManifest(manifest) : null;
    }

    // @Override
    // public Stream<JoinPoint> getJpChildrenStream() {
    // return spoon.getFactory().CompilationUnit().getMap().values().stream().map(JFile::new);
    // }
    //
    @Override
    public AFile[] getFilesArrayImpl() {
        return retrieveFiles().toArray(size -> new AFile[size]);
        // return getJpChildrenStream().toArray(size -> new AFile[size]);
    }

}
