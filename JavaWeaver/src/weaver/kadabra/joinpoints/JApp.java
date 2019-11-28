/**
 * Copyright 2015 SPeCS Research Group.
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

package weaver.kadabra.joinpoints;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.WindowConstants;

import org.lara.interpreter.weaver.interf.JoinPoint;

import spoon.Launcher;
import spoon.reflect.cu.CompilationUnit;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtInterface;
import spoon.reflect.factory.Factory;
import spoon.support.gui.SpoonModelTree;
import weaver.kadabra.abstracts.joinpoints.AApp;
import weaver.kadabra.abstracts.joinpoints.AClass;
import weaver.kadabra.abstracts.joinpoints.AFile;
import weaver.kadabra.abstracts.joinpoints.AInterface;
import weaver.kadabra.abstracts.joinpoints.AJoinPoint;
import weaver.kadabra.abstracts.joinpoints.ALibClass;
import weaver.kadabra.util.KadabraLog;
import weaver.utils.generators.MapGenerator;
import weaver.utils.weaving.ActionUtils;
import weaver.utils.weaving.SelectUtils;

public class JApp extends AApp {

    public final Launcher spoon;
    private List<JLibClass> libClasses;

    private JApp(Launcher spoon) {
        this.spoon = spoon;

    }

    public static JApp newInstance(Launcher spoon) {
        return new JApp(spoon);
    }

    @Override
    public List<? extends AFile> selectFile() {
        final List<JFile> files = spoon.getFactory().CompilationUnit().getMap().values().stream().map(JFile::new)
                .collect(Collectors.toList());
        return files;
    }

    @Override
    public List<? extends ALibClass> selectLibClass() {
        if (libClasses == null) {
            KadabraLog.warning("Loading classpath, this may take a while...");
            libClasses = SelectUtils.selectLibClasses(this);
            KadabraLog.warning("done!");
        }
        return libClasses;
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
        CompilationUnit cu = ActionUtils.compilationUnitWithClass(name, extend, implement,
                spoon.getModelBuilder().getBinaryOutputDirectory(), spoon.getFactory(), getWeaverProfiler());
        CtClass<?> mainClass = (CtClass<?>) cu.getMainType();
        AClass newInstance = JClass.newInstance(mainClass, cu);
        return newInstance;

    }

    @Override
    public AInterface newInterfaceImpl(String name) {
        return newInterfaceImpl(name, null);
    }

    @Override
    public AInterface newInterfaceImpl(String name, String[] extend) {
        final CtInterface<Object> newInterface = ActionUtils.compilationUnitWithInterface(name, extend,
                spoon.getModelBuilder().getBinaryOutputDirectory(),
                spoon.getFactory(),
                getWeaverProfiler());
        JInterface<Object> newInstance = JInterface.newInstance(newInterface);
        return newInstance;
    }

    @Override
    public AClass mapVersionsImpl(String name, String keyType, AInterface _interface, String methodName) {

        File outDir = spoon.getModelBuilder().getBinaryOutputDirectory();
        Factory factory = spoon.getFactory();
        CompilationUnit cu = MapGenerator.generate(factory, name, keyType, _interface, methodName, outDir,
                getWeaverProfiler());
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
        return selectFile().stream()
                .map(AFile::getSrcCodeImpl)
                .collect(Collectors.joining("\n" + separator + "\n"));
    }

    @Override
    public AJoinPoint[] getChildrenArrayImpl() {
        return selectFile().toArray(size -> new AJoinPoint[size]);
    }

    @Override
    public AJoinPoint childImpl(Integer index) {
        return selectFile().get(index);
    }

    @Override
    public Integer getNumChildrenImpl() {
        return selectFile().size();
    }
}
