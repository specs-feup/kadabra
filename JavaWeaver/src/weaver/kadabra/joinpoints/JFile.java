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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.lang3.NotImplementedException;

import pt.up.fe.specs.util.SpecsIo;
import pt.up.fe.specs.util.SpecsLogs;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtCompilationUnit;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtInterface;
import spoon.reflect.declaration.CtType;
import spoon.support.visitor.equals.EqualsVisitor;
import weaver.kadabra.JWeaver;
import weaver.kadabra.abstracts.joinpoints.AClass;
import weaver.kadabra.abstracts.joinpoints.AInterfaceType;
import weaver.kadabra.abstracts.joinpoints.AJoinpoint;
import weaver.kadabra.abstracts.joinpoints.AType;
import weaver.kadabra.abstracts.joinpoints.AFile;
import weaver.utils.generators.MapGenerator;
import weaver.utils.weaving.ActionUtils;
import weaver.utils.weaving.converters.CtElement2JoinPoint;

public class JFile<Self extends JFile<Self>> extends AFile<Self> {

    public JFile(CtCompilationUnit node, JWeaver weaver) {
        super(node, weaver);
    }

    @Override
    public void addImportImpl(String qualifiedName) {
        var imports = getNodeImpl().getImports();
        var packageReferece = new spoon.support.reflect.reference.CtTypeReferenceImpl();
        packageReferece.setSimpleName(qualifiedName);
        var newImport = new spoon.support.reflect.declaration.CtImportImpl().setReference(packageReferece);

        imports.add(newImport);

        getNodeImpl().setImports(new ArrayList<>(imports));
    }

    @Override
    public boolean getCompareNodesImpl(AJoinpoint<?> aJoinPoint) {

        if (!(aJoinPoint instanceof JFile<?>)) {
            return false;
        }
        // Verify source file equality
        CtCompilationUnit other = ((JFile<?>) aJoinPoint).getNodeImpl();
        if (!getNodeImpl().getFile().equals(other.getFile())) {
            return false;
        }

        // Use a biscan visitor to verify if both contains the same types
        List<CtType<?>> elements = getNodeImpl().getDeclaredTypes();
        List<CtType<?>> others = other.getDeclaredTypes();

        for (Iterator<? extends CtElement> firstIt = elements.iterator(), secondIt = others.iterator(); (firstIt
                .hasNext()) && (secondIt.hasNext());) {
            boolean isNotEqual = EqualsVisitor.equals(firstIt.next(), secondIt.next());
            if (isNotEqual) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String getPackageNameImpl() {
        CtType<?> type = getNodeImpl().getMainType();
        return type.getPackage().getQualifiedName();
    }

    @Override
    public String getNameImpl() {
        return getNodeImpl().getFile().getName();
    }

    @Override
    public String getPathImpl() {
        return getNodeImpl().getFile().getAbsolutePath();
    }

    @Override
    public String getDirImpl() {
        return getNodeImpl().getFile().getParent();
    }

    @Override
    public int getNumClassesImpl() {
        final int classes = (int) streamOfClasses().count();
        return classes;
    }

    @Override
    public int getNumInterfacesImpl() {
        final int interfs = (int) streamOfInterfaces().count();
        return interfs;
    }

    @Override
    public CtCompilationUnit getNodeImpl() {
        return (CtCompilationUnit) super.getNodeImpl();
    }

    private Stream<CtInterface<?>> streamOfInterfaces() {
        Stream<CtInterface<?>> filter = streamOfTypes().filter(c -> c instanceof CtInterface<?>)
                .map(CtInterface.class::cast);
        return filter;
    }

    private Stream<CtClass<?>> streamOfClasses() {
        Stream<CtClass<?>> filter = streamOfTypes().filter(c -> c instanceof CtClass<?>).map(CtClass.class::cast);
        return filter;
    }

    private Stream<CtType<?>> streamOfTypes() {
        return getNodeImpl().getDeclaredTypes().stream();
    }

    @Override
    public AClass<?> newClassImpl(String name, String extend, String[] implement) {
        final CtClass<?> newClass = ActionUtils.newClass(name, extend, implement, getNodeImpl().getFactory());
        getNodeImpl().getDeclaredTypes().add(newClass);
        JClass<?> newInstance = new JClass<>(newClass, getNodeImpl(), getWeaverEngine());
        return newInstance;
    }

    @Override
    public AClass<?> newClassImpl(String name) {
        return newClassImpl(name, null, null);
    }

    @Override
    public AInterfaceType<?> newInterfaceImpl(String name, String[] extend) {
        final CtInterface<?> newInterface = ActionUtils.newInterface(name, extend, getNodeImpl().getFactory());
        getNodeImpl().getDeclaredTypes().add(newInterface);
        JInterfaceType<?> newInstance = new JInterfaceType<>(newInterface, getWeaverEngine());
        return newInstance;
    }

    @Override
    public AInterfaceType<?> newInterfaceImpl(String name) {
        return newInterfaceImpl(name, null);
    }

    @Override
    public void addClassImpl(AClass<?> newClass) {
        add((CtType<?>) newClass.getNodeImpl());
    }

    @Override
    public void addInterfaceImpl(AInterfaceType<?> newInterface) {
        add((CtType<?>) newInterface.getNodeImpl());
    }

    @Override
    public AInterfaceType<?> removeInterfaceImpl(String interfaceName) {
        throw new NotImplementedException("Not implemented yet");
    }

    private void add(CtType<?> type) {
        getNodeImpl().getDeclaredTypes().add(type);
    }

    @Override
    public AClass<?> mapVersionsImpl(String name, String keyType, AInterfaceType<?> _interface, String methodName) {

        CtClass<?> newClass = MapGenerator.generate(getNodeImpl().getFactory(), name, keyType, _interface, methodName);
        getNodeImpl().getDeclaredTypes().add(newClass);
        AClass<?> newInstance = new JClass<>(newClass, getNodeImpl(), getWeaverEngine());
        return newInstance;
    }

    @Override
    public AJoinpoint<?> getParentImpl() {
        return getWeaverEngine().getRootJp();
    }

    @Override
    public AJoinpoint<?>[] getChildrenImpl() {
        List<AJoinpoint<?>> children = new ArrayList<>();

        for (var file : getNodeImpl().getDeclaredTypes()) {
            var type = CtElement2JoinPoint.convertTry(file, getWeaverEngine()).orElse(null);
            if (type == null) {
                continue;
            }

            children.add(type);
        }

        return children.toArray(new AJoinpoint[0]);
    }

    @Override
    public String getToStringImpl() {
        return getNameImpl();
    }

    @Override
    public AType<?> getMainClassImpl() {

        var fileName = SpecsIo.removeExtension(getNameImpl());

        var declaredTypes = getNodeImpl().getDeclaredTypes();

        if (declaredTypes.isEmpty()) {
            SpecsLogs
                    .info("file.mainClass: class '" + fileName + "' does not have declared types, returning undefined");
            return null;
        }

        // Name of file can be empty, in that case just return the first type if
        // present, otherwise throw exception
        if (fileName.isBlank()) {
            SpecsLogs.info("file.mainClass: file name is empty, returning first class that is found in file");
            return CtElement2JoinPoint.convert(declaredTypes.get(0), getWeaverEngine(), AType.class);
        }

        var declaredTypesNames = new ArrayList<String>();
        for (var declaredType : declaredTypes) {
            if (fileName.equals(declaredType.getSimpleName())) {
                return CtElement2JoinPoint.convert(declaredType, getWeaverEngine(), AType.class);
            }
        }

        throw new RuntimeException("Could not find a class with name '" + fileName + "' inside file " + getNameImpl()
                + ". Available types: " + declaredTypesNames);

    }
}
