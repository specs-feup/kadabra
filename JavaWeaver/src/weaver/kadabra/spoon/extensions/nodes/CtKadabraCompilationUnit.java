/**
 * Copyright 2017 SPeCS.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package weaver.kadabra.spoon.extensions.nodes;

import java.util.List;

import spoon.compiler.Environment;
import spoon.reflect.cu.CompilationUnit;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtType;
import spoon.reflect.visitor.CtVisitor;
import spoon.reflect.visitor.PrettyPrinter;
import spoon.support.reflect.declaration.CtElementImpl;
import weaver.kadabra.exceptions.JavaWeaverException;
import weaver.kadabra.spoon.extensions.printer.KadabraPrettyPrinter;

/**
 * This class works as just a wrapper for the {@link CompilationUnit} class so it can be used as a {@link CtElement}
 * 
 * @deprecated
 * 
 * @author tiago
 *
 */
@Deprecated
public class CtKadabraCompilationUnit extends CtElementImpl {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private CompilationUnit cu;

    public CtKadabraCompilationUnit(CompilationUnit cu) {
        this.setCu(cu);
        this.setFactory(cu.getFactory());
    }

    @Override
    public void accept(CtVisitor visitor) {
        throw new JavaWeaverException("Visitor should not be used in the " + getClass().getName() + " class");
    }

    public CompilationUnit getCu() {
        return cu;
    }

    public void setCu(CompilationUnit cu) {
        this.cu = cu;
    }

    @Override
    public int hashCode() {
        return 1 + cu.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CtKadabraCompilationUnit)) {
            return false;
        }
        CtKadabraCompilationUnit other = (CtKadabraCompilationUnit) o;
        List<CtType<?>> declaredTypes = cu.getDeclaredTypes();
        List<CtType<?>> otherDeclaredTypes = other.cu.getDeclaredTypes();

        boolean ret = declaredTypes.equals(otherDeclaredTypes);
        if (ret && !this.getFactory().getEnvironment().checksAreSkipped() && this.hashCode() != o.hashCode()) {
            throw new IllegalStateException("violation of equal/hashcode contract between \n" + this.toString()
                    + "\nand\n" + o.toString() + "\n");
        }
        return ret;
    }

    // public static <T> boolean listEqualsIgnoreOrder(List<T> list1, List<T> list2) {
    // return new HashSet<>(list1).equals(new HashSet<>(list2));
    // }

    @Override
    public String toString() {
        List<CtType<?>> toBePrinted = cu.getDeclaredTypes();
        Environment environment = getFactory().getEnvironment();
        PrettyPrinter printer = new KadabraPrettyPrinter(environment);
        // PrettyPrinter printer = JavaWeaver.getJavaWeaver().getPrinter();
        printer.calculate(cu, toBePrinted);
        return printer.getResult();
    }
}
