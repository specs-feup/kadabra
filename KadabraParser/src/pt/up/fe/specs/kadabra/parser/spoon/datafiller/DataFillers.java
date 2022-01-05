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

package pt.up.fe.specs.kadabra.parser.spoon.datafiller;

import pt.up.fe.specs.kadabra.parser.spoon.elementparser.MainParser;

public class DataFillers {

    private final MainParser generalParser;
    private final ElementDataFiller elementFiller;
    private final DeclDataFiller declFiller;
    private final StmtDataFiller stmtFiller;
    private final ExprDataFiller exprFiller;

    public DataFillers(MainParser mainParser) {
        this.generalParser = mainParser;

        this.elementFiller = new ElementDataFiller(mainParser);
        this.declFiller = new DeclDataFiller(mainParser);
        this.stmtFiller = new StmtDataFiller(mainParser);
        this.exprFiller = new ExprDataFiller(mainParser);
    }

    public DeclDataFiller decl() {
        return declFiller;
    }

    public StmtDataFiller stmt() {
        return stmtFiller;
    }

    public ExprDataFiller expr() {
        return exprFiller;
    }

    public ElementDataFiller element() {
        return elementFiller;
    }

    public static String extractQualifiedPrefix(String qualifiedName, String simpleName) {
        if (!qualifiedName.endsWith(simpleName)) {
            throw new RuntimeException("Expected qualified name to have the simple name at the end. Qualified name: '"
                    + qualifiedName + "'; Simple name: '" + simpleName + "'");
        }

        var qualifiedPrefix = qualifiedName.substring(0, qualifiedName.length() - simpleName.length());

        // if (qualifiedPrefix.endsWith(".")) {
        // If last character is not an alphabetic character, assume it is a separator
        if (!qualifiedPrefix.isBlank()
                && !Character.isAlphabetic(qualifiedPrefix.charAt(qualifiedPrefix.length() - 1))) {

            qualifiedPrefix = qualifiedPrefix.substring(0, qualifiedPrefix.length() - 1);
        }

        // System.out.println("PREFIX: " + qualifiedPrefix);

        return qualifiedPrefix;
    }

}
