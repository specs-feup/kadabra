/**
 * Copyright 2021 SPeCS.
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

package pt.up.fe.specs.kadabra.parser.spoon.elementparser;

import java.util.Collections;
import java.util.List;

import pt.up.fe.specs.kadabra.KadabraNodeFactory;
import pt.up.fe.specs.kadabra.ast.KadabraNode;
import pt.up.fe.specs.kadabra.parser.spoon.datafiller.DataFillers;
import pt.up.fe.specs.util.classmap.FunctionClassMap;
import spoon.reflect.cu.SourcePosition;
import spoon.reflect.declaration.CtElement;

public abstract class SpoonParsers {

    private final MainParser mainParser;

    protected SpoonParsers(MainParser mainParser) {
        this.mainParser = mainParser;
        registerParsers(mainParser.getNodeBuilders());
    }

    protected abstract void registerParsers(FunctionClassMap<CtElement, KadabraNode> parsers);

    protected MainParser parser() {
        return mainParser;
    }

    protected DataFillers dataFillers() {
        return mainParser.getDataFillers();
    }

    protected KadabraNodeFactory factory() {
        return mainParser.getFactory();
    }

    /**
     * Uses the SourcePosition of the elements for comparison.
     * 
     * @param o1
     * @param o2
     * @return
     */
    public static int compare(CtElement o1, CtElement o2) {
        var o1Pos = o1.getPosition();
        var o2Pos = o2.getPosition();

        var o1Line = getLine(o1Pos);
        var o2Line = getLine(o2Pos);

        return Integer.compare(o1Line, o2Line);

        // if (o1Pos instanceof NoSourcePosition && o2Pos instanceof NoSourcePosition) {
        // return 0;
        // }
        //
        // var result = Integer.compare(o1Pos.getLine(), o2Pos.getLine());
        //
        // // If not equal, return result
        // if (result != 0) {
        // return result;
        // }
        //
        // return Integer.compare(o1Pos.getEndLine(), o2Pos.getEndLine());

        // var result = Integer.compare(o1Pos.getSourceStart(), o2Pos.getSourceStart());
        //
        // // If not equal, return result
        // if (result != 0) {
        // return result;
        // }
        //
        // // Otherwise, use source end
        // return Integer.compare(o1Pos.getSourceEnd(), o2Pos.getSourceEnd());
    }

    private static int getLine(SourcePosition pos) {
        if (!pos.isValidPosition()) {
            return -1;
        }
        // if (pos instanceof NoSourcePosition) {
        // return -1;
        // }

        return pos.getLine();
    }

    public static void sort(List<? extends CtElement> elements) {
        Collections.sort(elements, SpoonParsers::compare);
    }

}
