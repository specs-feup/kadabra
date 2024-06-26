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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.NotImplementedException;
import org.lara.interpreter.weaver.interf.JoinPoint;

import pt.up.fe.specs.util.SpecsIo;
import pt.up.fe.specs.util.SpecsLogs;
import spoon.reflect.code.CtComment;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtCompilationUnit;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtInterface;
import spoon.reflect.declaration.CtType;
import spoon.support.reflect.declaration.CtImportImpl;
import spoon.support.reflect.reference.CtTypeReferenceImpl;
import spoon.support.visitor.equals.EqualsVisitor;
import weaver.kadabra.abstracts.AJavaWeaverJoinPoint;
import weaver.kadabra.abstracts.joinpoints.AClass;
import weaver.kadabra.abstracts.joinpoints.AFile;
import weaver.kadabra.abstracts.joinpoints.AInterface;
import weaver.kadabra.abstracts.joinpoints.AJoinPoint;
import weaver.kadabra.abstracts.joinpoints.APragma;
import weaver.kadabra.abstracts.joinpoints.AType;
import weaver.utils.generators.MapGenerator;
import weaver.utils.weaving.ActionUtils;
import weaver.utils.weaving.SelectUtils;
import weaver.utils.weaving.converters.CtElement2JoinPoint;
import weaver.utils.weaving.converters.CtType2AType;

public class JFile extends AFile {

    private final CtCompilationUnit node;

    public JFile(CtCompilationUnit node) {
        this.node = node;
    }

    // public JFile(CtKadabraCompilationUnit node) {
    // this.node = node.getCu();
    // }

    // @Override
    // public String getCodeImpl() {
    // return node.toString();
    // }

    @Override
    public void addImportImpl(String qualifiedName) {
        var imports = node.getImports();
        // System.out.println("IMPORTS BEFORE:\n" + imports);
        var packageReferece = new CtTypeReferenceImpl();
        packageReferece.setSimpleName(qualifiedName);
        // System.out.println("Reference:" + packageReferece);
        var newImport = new CtImportImpl().setReference(packageReferece);

        // System.out.println("NEW IMPORT: " + newImport);
        imports.add(newImport);

        node.setImports(new ArrayList<>(imports));
        // System.out.println("IMPORTS AFTER:\n" + node.getImports());
    }

    /*
    @Override
    public void addImportImpl(String qualifiedName) {
        var importList = node.getImports();
        // Add to a set, keeping order
        var updatedSet = new LinkedHashSet<>(importList);
    
        // CtImportImpl
        var ref = new CtTypeReferenceImpl<>();
        ref.setSimpleName(qualifiedName);
        var newImport = new CtImportImpl();
        newImport.setReference(ref);
        updatedSet.add(newImport);
        node.setImports(updatedSet);
    
        // // If new import, replace
        // if (updatedSet.add(newImport)) {
        // node.setImports(updatedSet);
        // }
    
        // ref.setPackage(new CtPackageReferenceImpl())
        // System.out.println("REF: " + ref);
        // ref.setSimpleName(qualifiedName);
        // TODO Auto-generated method stub
        // super.addImportImpl(qualifiedName);
    }
    */

    @Override
    public boolean compareNodes(AJoinPoint aJoinPoint) {

        if (!(aJoinPoint instanceof JFile)) {
            return false;
        }
        // Verify source file equality
        CtCompilationUnit other = ((JFile) aJoinPoint).node;
        if (!node.getFile().equals(other.getFile())) {
            return false;
        }

        // Use a biscan visitor to verify if both contains the same types
        List<CtType<?>> elements = node.getDeclaredTypes();
        List<CtType<?>> others = other.getDeclaredTypes();

        for (Iterator<? extends CtElement> firstIt = elements.iterator(), secondIt = others.iterator(); (firstIt
                .hasNext()) && (secondIt.hasNext());) {
            boolean isNotEqual = EqualsVisitor.equals(firstIt.next(), secondIt.next());
            if (isNotEqual) {
                return false;
            }
        }

        return true;
        // EqualsVisitor v = new EqualsVisitor();
        // // System.out.println("NODE TYPES: " + StringUtils.join(node.getDeclaredTypes(), CtType::getSimpleName,
        // ","));
        // // System.out.println("OTHER TYPES: " + StringUtils.join(other.getDeclaredTypes(), CtType::getSimpleName,
        // ","));
        // boolean isNotEqual = v.biScan(node.getDeclaredTypes(), other.getDeclaredTypes());
        // if (isNotEqual) {
        // return false;
        // }
        //
        // return true;
    }

    @Override
    public String getPackageImpl() {
        CtType<?> type = node.getMainType();
        return type.getPackage().getQualifiedName();
    }

    @Override
    public String getNameImpl() {
        return node.getFile().getName();
    }

    @Override
    public String getPathImpl() {
        return node.getFile().getAbsolutePath();
    }

    @Override
    public String getDirImpl() {
        return node.getFile().getParent();
    }

    @Override
    public Integer getNumClassesImpl() {
        final int classes = (int) streamOfClasses().count();
        return classes;
    }

    @Override
    public Integer getNumInterfacesImpl() {
        final int interfs = (int) streamOfInterfaces().count();
        return interfs;
    }

    @Override
    public CtCompilationUnit getNode() {
        return node;
    }

    @Override
    public boolean same(JoinPoint iJoinPoint) {

        if (!(iJoinPoint instanceof JFile)) {
            return false;
        }
        JFile other = (JFile) iJoinPoint;
        return node.equals(other.node);
    }

    @Override
    public List<? extends AType> selectType() {

        List<AType> classes = streamOfTypes().map(CtType2AType::convert)
                .collect(Collectors.toList());
        return classes;
    }

    @Override
    public List<? extends AClass> selectClass() {

        List<JClass<?>> classes = streamOfClasses()
                .map(cl -> JClass.newInstance(cl, node))
                .collect(Collectors.toList());

        return classes;
    }

    @Override
    public List<? extends AInterface> selectInterface() {

        return streamOfInterfaces()
                .<JInterface<?>> map(JInterface::newInstance)
                .collect(Collectors.toList());
    }

    @Override
    public List<? extends APragma> selectPragma() {
        return selectComment().stream()
                .filter(c -> JPragma.isPragma(c.getNode()))
                .map(JPragma::newInstance)
                .collect(Collectors.toList());
    }

    @Override
    public List<JComment> selectComment() {
        List<JComment> comments = new ArrayList<>();

        Stream<CtType<?>> allTypes = streamOfTypes();
        allTypes.forEach(type -> {
            final List<JComment> currentComments = SelectUtils.select(type, CtComment.class, JComment::newInstance);
            comments.addAll(currentComments);
        });

        return comments;
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
        return node.getDeclaredTypes().stream();
    }

    // @Override
    // public void addImportImpl(String _class) {
    // // System.out.println("for file: " + getName() + " importing: " + _class);
    // CtTypeReference<?> reference = node.getFactory().Class().get(_class).getReference();
    // Import newImport = node.getFactory().CompilationUnit().createImport(reference);
    // ((CompilationUnitImpl) node).getManualImports().add(newImport);
    // }

    @Override
    public AClass newClassImpl(String name, String extend, String[] implement) {
        final CtClass<Object> newClass = ActionUtils.newClass(name, extend, implement, node.getFactory(),
                getWeaverProfiler());
        node.getDeclaredTypes().add(newClass);
        JClass<Object> newInstance = JClass.newInstance(newClass, node);
        return newInstance;
    }

    @Override
    public AClass newClassImpl(String name) {
        return newClassImpl(name, null, null);
    }

    @Override
    public AInterface newInterfaceImpl(String name, String[] extend) {
        final CtInterface<Object> newInterface = ActionUtils.newInterface(name, extend, node.getFactory(),
                getWeaverProfiler());
        node.getDeclaredTypes().add(newInterface);
        JInterface<Object> newInstance = JInterface.newInstance(newInterface);
        return newInstance;
    }

    @Override
    public AInterface newInterfaceImpl(String name) {
        return newInterfaceImpl(name, null);
    }

    @Override
    public void addClassImpl(AClass newClass) {
        add((CtType<?>) newClass.getNode());
    }

    @Override
    public void addInterfaceImpl(AInterface newInterface) {
        add((CtType<?>) newInterface.getNode());
    }

    @Override
    public AInterface removeInterfaceImpl(String interfaceName) {
        throw new NotImplementedException("Not implemented yet");
    }

    private void add(CtType<?> type) {
        node.getDeclaredTypes().add(type);
    }

    @Override
    public AClass mapVersionsImpl(String name, String keyType, AInterface _interface, String methodName) {

        CtClass<?> newClass = MapGenerator.generate(node.getFactory(), name, keyType, _interface, methodName,
                getWeaverProfiler());
        node.getDeclaredTypes().add(newClass);
        AClass newInstance = JClass.newInstance(newClass, node);
        return newInstance;
    }

    @Override
    public AJoinPoint getParentImpl() {
        return (AJoinPoint) getWeaverEngine().getRootJp();
    }

    @Override
    public AJoinPoint[] getChildrenArrayImpl() {
        List<AJoinPoint> children = new ArrayList<>();

        for (var file : getNode().getDeclaredTypes()) {
            AJavaWeaverJoinPoint type = CtElement2JoinPoint.convertTry(file).orElse(null);
            if (type == null) {
                continue;
            }

            children.add(type);
        }

        return children.toArray(new AJoinPoint[0]);
    }

    // @Override
    // public AJoinPoint childImpl(Integer index) {
    // return getChildrenArrayImpl()[index];
    // }

    // @Override
    // public Integer getNumChildrenImpl() {
    // return getChildrenArrayImpl().length;
    // }

    @Override
    public String toString() {
        return getNameImpl();
    }

    @Override
    public AType getMainClassImpl() {

        var fileName = SpecsIo.removeExtension(getNameImpl());

        var declaredTypes = node.getDeclaredTypes();

        if (declaredTypes.isEmpty()) {
            SpecsLogs
                    .info("file.mainClass: class '" + fileName + "' does not have declared types, returning undefined");
            return null;
        }

        // Name of file can be empty, in that case just return the first type if present, otherwise throw exception
        if (fileName.isBlank()) {
            SpecsLogs.info("file.mainClass: file name is empty, returning first class that is found in file");
            return CtElement2JoinPoint.convert(declaredTypes.get(0), AType.class);
        }

        var declaredTypesNames = new ArrayList<String>();
        for (var declaredType : declaredTypes) {
            if (fileName.equals(declaredType.getSimpleName())) {
                return CtElement2JoinPoint.convert(declaredType, AType.class);
            }
        }

        throw new RuntimeException("Could not find a class with name '" + fileName + "' inside file " + getNameImpl()
                + ". Available types: " + declaredTypesNames);

    }
}
