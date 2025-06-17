/**
 * Copyright 2022 SPeCS.
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

package pt.up.fe.specs.kadabra.ast.data;

/**
 * A modifier on the declaration of a program (e.g., public, private, static)
 * 
 * @author Joao Bispo
 *
 */
public enum ModifierKind {

    /**
     * The modifier <tt>public</tt>
     */
    PUBLIC,
    /**
     * The modifier <tt>protected</tt>
     */
    PROTECTED,
    /**
     * The modifier <tt>private</tt>
     */
    PRIVATE,
    /**
     * The modifier <tt>abstract</tt>
     */
    ABSTRACT,
    /**
     * The modifier <tt>static</tt>
     */
    STATIC,
    /**
     * The modifier <tt>final</tt>
     */
    FINAL,
    /**
     * The modifier <tt>transient</tt>
     */
    TRANSIENT,
    /**
     * The modifier <tt>volatile</tt>
     */
    VOLATILE,
    /**
     * The modifier <tt>synchronized</tt>
     */
    SYNCHRONIZED,
    /**
     * The modifier <tt>native</tt>
     */
    NATIVE,
    /**
     * The modifier <tt>strictfp</tt>
     */
    STRICTFP;
}
