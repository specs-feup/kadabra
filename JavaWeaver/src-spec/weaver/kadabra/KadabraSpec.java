package weaver.kadabra;

import org.lara.langspec2.dsl.WeaverSpec;

/**
 * Weaver specification for the Kadabra (Java) weaver, translated from the XML
 * specification files (joinPointModel.xml, artifacts.xml and actionModel.xml)
 * into the Java DSL.
 * <p>
 * The weaver prefix is 'J', so the concrete join point classes keep their
 * original names (JMethod, JVar, JLoop, ...), and the LARA-visible join point
 * names are exactly the ones of the old XML model.
 * <p>
 * Excludes base contract (from BaseJoinPointSpec): dump, joinPointType, node,
 * self, children, descendants, scopeNodes, parent, root, code, line, column,
 * toString, equals, compareNodes, same, instanceOf, insert.
 * <p>
 * The 'enum' join point of the old XML model is not declared: it had no
 * attributes, actions or concrete class (enum nodes were represented by 'class'),
 * so it was never reachable.
 */
public class KadabraSpec extends WeaverSpec {

    @Override
    public void define() {
        weaverPrefix("J");
        packageName("weaver.kadabra");
        rootJoinPoint("app");

        // =====================================================================
        // Enum definitions
        // =====================================================================

        enumDef("LoopType")
                .value("for")
                .value("foreach")
                .value("while")
                .value("doWhile")
                .end();

        enumDef("CommentType")
                .value("file")
                .value("inline")
                .value("block")
                .value("javadoc")
                .value("pragma")
                .end();

        enumDef("RefType")
                .value("read")
                .value("write")
                .value("readwrite")
                .value("decl")
                .end();

        // =====================================================================
        // Global attributes and actions (weaver-specific, not in BaseJoinPointSpec)
        // =====================================================================

        global()
                .attribute("srcCode", STRING, "Alias for attribute 'code'")
                .attribute("ast", STRING, "A string representation of the AST corresponding to this node")
                .attribute("numChildren", INT, "Returns the number of children of the node")
                .attribute("child")
                    .tooltip("Returns the child of the node at the given index")
                    .param("index", INT)
                    .returns(jpRef("joinpoint"))
                .attribute("astParent", jpRef("joinpoint"), "Alias of attribute 'parent'")
                .attribute("getAncestor")
                    .param("type", STRING)
                    .returns(jpRef("joinpoint"))
                .attribute("left", array(jpRef("joinpoint")), "Sibling nodes to the left of this node")
                .attribute("right", array(jpRef("joinpoint")), "Sibling nodes to the right of this node")
                .attribute("isStatement", BOOLEAN, "true if this node is considered a statement")
                .attribute("isBlock", BOOLEAN, "true if this node is considered a block of statements")
                .attribute("modifiers", array(STRING),
                        "an array of modifiers (e.g., final, static) applied to this node. If no modifiers are applied, or if the node does not support modifiers, returns an empty array")
                .attribute("hasModifier")
                    .tooltip("true if this node has the given modifier")
                    .param("modifier", STRING)
                    .returns(BOOLEAN)
                .attribute("isFinal", BOOLEAN, "true if this node has the modifier 'final'")
                .attribute("isStatic", BOOLEAN, "true if this node has the modifier 'static'")
                .attribute("annotations", array(jpRef("annotation")), "an array of the annotations of this node")
                .attribute("id", STRING, "unique identifier for node")
                .attribute("isInsideLoopHeader", BOOLEAN, "true if the node is inside a loop header, false otherwise")
                .action("insertBefore")
                    .tooltip("Inserts node before the given node")
                    .param("node", jpRef("joinpoint"))
                    .returns(jpRef("joinpoint"))
                .action("insertBefore")
                    .tooltip("Overload which accepts String")
                    .param("code", STRING)
                    .returns(jpRef("joinpoint"))
                .action("insertAfter")
                    .tooltip("Inserts node after the given node")
                    .param("node", jpRef("joinpoint"))
                    .returns(jpRef("joinpoint"))
                .action("insertAfter")
                    .tooltip("Overload which accepts String")
                    .param("code", STRING)
                    .returns(jpRef("joinpoint"))
                .action("insertReplace")
                    .tooltip("Replaces join point with the given node")
                    .param("jp", jpRef("joinpoint"))
                    .returns(jpRef("joinpoint"))
                .action("insertReplace")
                    .tooltip("Overload which accepts String")
                    .param("code", STRING)
                    .returns(jpRef("joinpoint"))
                .action("replaceWith")
                    .tooltip("Replaces join point with the given node")
                    .param("jp", jpRef("joinpoint"))
                    .returns(jpRef("joinpoint"))
                .action("replaceWith")
                    .tooltip("Overload which accepts String")
                    .param("code", STRING)
                    .returns(jpRef("joinpoint"))
                .action("copy")
                    .tooltip("Copies the node and returns the copy")
                    .returns(jpRef("joinpoint"))
                .action("remove")
                    .tooltip("Deletes the node")
                    .returns(VOID)
                .action("removeAnnotation")
                    .tooltip("Removes an annotation")
                    .param("annotation", jpRef("annotation"))
                    .returns(VOID)
                .action("removeModifier")
                    .param("modifier", STRING)
                    .returns(VOID)
                .action("setModifiers")
                    .param("modifiers", array(STRING))
                    .returns(VOID)
                .action("setLine")
                    .param("value", INT)
                    .returns(VOID)
                .action("setLine")
                    .param("value", STRING)
                    .returns(VOID);

        // =====================================================================
        // Join point definitions
        // =====================================================================

        joinPoint("app")
                .tooltip("Root node that represents the application")
                .attribute("folder", STRING)
                .attribute("showAST")
                    .param("Title", STRING)
                    .returns(STRING)
                .attribute("manifest", jpRef("androidManifest"))
                .attribute("files", array(jpRef("file")))
                .action("newClass")
                    .param("name", STRING)
                    .param("extend", STRING, "\"\"")
                    .param("implement", array(STRING))
                    .returns(jpRef("class"))
                .action("newClass")
                    .param("name", STRING)
                    .returns(jpRef("class"))
                .action("newInterface")
                    .param("name", STRING)
                    .param("extend", array(STRING))
                    .returns(jpRef("interfaceType"))
                .action("newInterface")
                    .param("name", STRING)
                    .returns(jpRef("interfaceType"))
                .action("mapVersions")
                    .param("name", STRING)
                    .param("keyType", STRING)
                    .param("interfaceType", jpRef("interfaceType"))
                    .param("methodName", STRING)
                    .returns(jpRef("class"));

        joinPoint("libClass").extending("NamedType")
                .tooltip("class that is part of a library, included in the classpath");

        joinPoint("libMethod")
                .tooltip("method of a class that is part of a library included in the classpath")
                .defaultAttribute("name")
                .attribute("name", STRING, "the simple name of the type")
                .attribute("declarator", jpRef("NamedType"))
                .attribute("returnType", STRING);

        joinPoint("xmlNode")
                .attribute("elements", array(jpRef("xmlElement")))
                .attribute("elementsByName")
                    .param("name", STRING)
                    .returns(array(jpRef("xmlElement")))
                .attribute("text", STRING)
                .action("setText")
                    .param("text", STRING)
                    .returns(STRING);

        joinPoint("androidManifest").extending("xmlNode")
                .attribute("asJson", STRING);

        joinPoint("xmlElement").extending("xmlNode")
                .defaultAttribute("name")
                .attribute("name", STRING, "the name (i.e., tag) of this element")
                .attribute("attribute")
                    .tooltip("the value associated with the given attribute")
                    .param("name", STRING)
                    .returns(STRING)
                .attribute("attributeNames", array(STRING), "a list of available attributes in this element")
                .action("setAttribute")
                    .param("name", STRING)
                    .param("value", STRING)
                    .returns(STRING);

        joinPoint("file")
                .tooltip("Represents a source-code file")
                .defaultAttribute("name")
                .attribute("name", STRING)
                .attribute("path", STRING)
                .attribute("dir", STRING)
                .attribute("packageName", STRING)
                .attribute("numClasses", INT)
                .attribute("numInterfaces", INT)
                .attribute("mainClass", jpRef("type"),
                        "Main class of the file. Java files must have a top level class with the same name as the file.")
                .action("newClass")
                    .param("name", STRING)
                    .param("extend", STRING, "\"\"")
                    .param("implement", array(STRING))
                    .returns(jpRef("class"))
                .action("newClass")
                    .param("name", STRING)
                    .returns(jpRef("class"))
                .action("newInterface")
                    .param("name", STRING)
                    .param("extend", array(STRING))
                    .returns(jpRef("interfaceType"))
                .action("newInterface")
                    .param("name", STRING)
                    .returns(jpRef("interfaceType"))
                .action("addImport")
                    .param("qualifiedName", STRING)
                    .returns(VOID)
                .action("addClass")
                    .tooltip("insert a given class inside the target")
                    .param("newClass", jpRef("class"))
                    .returns(VOID)
                .action("addInterface")
                    .param("newInterface", jpRef("interfaceType"))
                    .returns(VOID)
                .action("removeInterface")
                    .param("interfaceName", STRING)
                    .returns(jpRef("interfaceType"))
                .action("mapVersions")
                    .param("name", STRING)
                    .param("keyType", STRING)
                    .param("interfaceType", jpRef("interfaceType"))
                    .param("methodName", STRING)
                    .returns(jpRef("class"));

        joinPoint("NamedType")
                .defaultAttribute("name")
                .attribute("name", STRING, "the simple name of the type")
                .attribute("qualifiedName", STRING, "the qualified name of this type, includes packages")
                .attribute("superClass", STRING, "name of the superclass this type extends")
                .attribute("packageName", STRING, "package name of this type")
                .attribute("interfaces", array(STRING), "list of names of interfaces that this type implements")
                .attribute("javadoc", STRING)
                .attribute("isSubtypeOf")
                    .tooltip("verify if the type is extends OR implements the given type")
                    .param("type", STRING)
                    .returns(BOOLEAN_BOXED);

        joinPoint("type")
                .tooltip("base join point that class, interface and enum extend")
                .defaultAttribute("name")
                .attribute("name", STRING, "the simple name of the class")
                .attribute("qualifiedName", STRING, "the qualified name of this class, includes packages")
                .attribute("superClass", STRING, "name of the superclass this class extends")
                .attribute("superClassJp", jpRef("typeReference"),
                        "the superclass this class extends, or undefined if the class extends java.lang.Object")
                .attribute("packageName", STRING, "package name of this class")
                .attribute("interfaces", array(STRING), "list of names of interfaces that this class implements")
                .attribute("interfacesTypes", array(jpRef("interfaceType")),
                        "returns the interface join points that this class implements")
                .attribute("javadoc", STRING)
                .attribute("isSubtypeOf")
                    .tooltip("verify if the type is extends OR implements the given type")
                    .param("type", STRING)
                    .returns(BOOLEAN_BOXED)
                .action("addClass")
                    .tooltip("insert a given class inside the target")
                    .param("newClass", jpRef("class"))
                    .returns(VOID)
                .action("addInterface")
                    .param("newInterface", jpRef("interfaceType"))
                    .returns(VOID)
                .action("removeInterface")
                    .param("interfaceName", STRING)
                    .returns(jpRef("interfaceType"))
                .action("newMethod")
                    .tooltip("add a new method inside the class")
                    .param("modifiers", array(STRING))
                    .param("returnType", STRING)
                    .param("name", STRING)
                    .param("paramLeft", array(STRING))
                    .param("paramRight", array(STRING))
                    .param("code", STRING, "\"\"")
                    .returns(jpRef("method"))
                .action("newMethod")
                    .tooltip("overload which accepts 4 parameters (code is empty string)")
                    .param("modifiers", array(STRING))
                    .param("returnType", STRING)
                    .param("name", STRING)
                    .param("paramLeft", array(STRING))
                    .param("paramRight", array(STRING))
                    .returns(jpRef("method"))
                .action("insertMethod")
                    .param("code", STRING)
                    .returns(VOID)
                .action("insertCode")
                    .param("code", STRING)
                    .returns(VOID)
                .action("newField")
                    .param("modifiers", array(STRING))
                    .param("type", STRING)
                    .param("name", STRING)
                    .param("defaultValue", STRING, "null")
                    .returns(jpRef("field"))
                .action("newField")
                    .param("modifiers", array(STRING))
                    .param("type", STRING)
                    .param("name", STRING)
                    .returns(jpRef("field"))
                .action("addImplement")
                    .param("interfaceType", jpRef("interfaceType"))
                    .returns(VOID);

        joinPoint("class").extending("type")
                .tooltip("join point representation of a class")
                .defaultAttribute("name")
                .attribute("isTopLevel", BOOLEAN)
                .action("newConstructor")
                    .param("modifiers", array(STRING), "['public']")
                    .param("paramLeft", array(STRING), "[]")
                    .param("paramRight", array(STRING), "[]")
                    .returns(jpRef("constructor"))
                .action("newFunctionalClass")
                    .param("interfaceMethod", jpRef("method"))
                    .param("generatorMethod", jpRef("method"))
                    .returns(jpRef("method"))
                .action("insertStatic")
                    .param("code", STRING)
                    .returns(VOID)
                .action("extractInterface")
                    .param("name", STRING)
                    .param("packageName", STRING, "\"\"")
                    .param("method", jpRef("method"))
                    .param("associate", BOOLEAN, "false")
                    .param("newFile", BOOLEAN)
                    .returns(jpRef("interfaceType"))
                .action("mapVersions")
                    .param("name", STRING)
                    .param("keyType", STRING)
                    .param("interfaceType", jpRef("interfaceType"))
                    .param("methodName", STRING)
                    .returns(jpRef("class"));

        joinPoint("interfaceType").extending("type")
                .defaultAttribute("name");

        joinPoint("field").extending("declaration")
                .defaultAttribute("name")
                .attribute("declarator", STRING)
                .attribute("staticAccess", STRING);

        joinPoint("enumValue").extending("field");

        joinPoint("executable")
                .defaultAttribute("name")
                .attribute("name", STRING)
                .attribute("returnType", STRING)
                .attribute("body", jpRef("body"))
                .attribute("params", array(jpRef("declaration")))
                .attribute("returnRef", jpRef("typeReference"))
                .action("setName")
                    .tooltip("Sets the name of this executable, returns the previous name")
                    .param("name", STRING)
                    .returns(STRING);

        joinPoint("method").extending("executable")
                .defaultAttribute("name")
                .attribute("declarator", STRING)
                .attribute("privacy", STRING)
                .attribute("toReference", STRING)
                .attribute("toQualifiedReference", STRING)
                .attribute("isOverriding")
                    .param("method", jpRef("method"))
                    .returns(BOOLEAN)
                .action("addComment")
                    .param("comment", STRING)
                    .returns(VOID)
                .action("addParameter")
                    .param("type", STRING)
                    .param("name", STRING)
                    .returns(VOID)
                .action("createAdapter")
                    .param("adaptMethod", jpRef("method"))
                    .param("name", STRING)
                    .returns(jpRef("class"))
                .action("clone")
                    .param("newName", STRING)
                    .returns(jpRef("method"))
                .action("setPrivacy")
                    .param("privacy", STRING)
                    .returns(VOID);

        joinPoint("constructor").extending("executable")
                .defaultAttribute("name")
                .attribute("declarator", STRING);

        joinPoint("anonymousExec").extending("executable");

        joinPoint("body").extending("statement")
                .attribute("lastStmt", jpRef("statement"),
                        "The last statement of the body, or undefined if it has no statements")
                .action("insertBegin")
                    .param("code", STRING)
                    .returns(VOID)
                .action("insertBegin")
                    .param("statement", jpRef("statement"))
                    .returns(VOID);

        joinPoint("localVariable").extending("statement")
                .defaultAttribute("name")
                .attribute("name", STRING)
                .attribute("type", STRING)
                .attribute("typeReference", jpRef("typeReference"))
                .attribute("isArray", BOOLEAN)
                .attribute("isPrimitive", BOOLEAN)
                .attribute("completeType", STRING)
                .attribute("init", jpRef("expression"),
                        "Initialization of this variable, if present, or undefined of unintialized")
                .action("setInit")
                    .param("init", jpRef("expression"))
                    .returns(VOID);

        joinPoint("declaration")
                .defaultAttribute("name")
                .attribute("name", STRING)
                .attribute("type", STRING)
                .attribute("typeReference", jpRef("typeReference"))
                .attribute("isArray", BOOLEAN)
                .attribute("isPrimitive", BOOLEAN)
                .attribute("completeType", STRING)
                .attribute("init", jpRef("expression"))
                .action("setInit")
                    .param("value", jpRef("expression"))
                    .returns(VOID);

        joinPoint("loop").extending("statement")
                .defaultAttribute("type")
                .attribute("type", enumRef("LoopType"))
                .attribute("rank", STRING)
                .attribute("nestedLevel", INT)
                .attribute("isInnermost", BOOLEAN)
                .attribute("isOutermost", BOOLEAN)
                .attribute("controlVar", STRING)
                .attribute("cond", jpRef("expression"))
                .action("tile")
                    .param("tileName", STRING, "\"\"")
                    .param("block", STRING)
                    .param("unique", BOOLEAN)
                    .param("around", jpRef("joinpoint"), "null")
                    .returns(jpRef("field"))
                .action("tile")
                    .param("block", INT)
                    .returns(VOID)
                .action("interchange")
                    .param("loop2", jpRef("loop"))
                    .returns(VOID);

        joinPoint("if").extending("statement")
                .attribute("rank", STRING)
                .attribute("cond", jpRef("expression"))
                .attribute("then", jpRef("body"))
                .attribute("else", jpRef("body"));

        joinPoint("try").extending("statement")
                .attribute("body", jpRef("body"))
                .attribute("catches", array(jpRef("catch")));

        joinPoint("catch")
                .attribute("body", jpRef("body"));

        joinPoint("statement")
                .defaultAttribute("kind")
                .attribute("kind", STRING)
                .attribute("endLine", INT);

        joinPoint("callStatement").extending("statement")
                .attribute("call", jpRef("call"));

        joinPoint("assignment").extending("statement")
                .defaultAttribute("operator")
                .attribute("operator", STRING)
                .attribute("lhs", jpRef("expression"))
                .attribute("rhs", jpRef("expression"))
                .action("setLhs")
                    .param("lhs", jpRef("expression"))
                    .returns(VOID)
                .action("setRhs")
                    .param("rhs", jpRef("expression"))
                    .returns(VOID);

        joinPoint("opAssignment").extending("assignment")
                .defaultAttribute("operator")
                .action("setOperator")
                    .param("operator", STRING)
                    .returns(VOID);

        joinPoint("expression")
                .attribute("kind", STRING)
                .attribute("type", STRING)
                .attribute("qualifiedType", STRING)
                .attribute("typeReference", jpRef("typeReference"))
                .attribute("test", INT)
                .action("extract")
                    .param("varName", STRING)
                    .param("location", jpRef("statement"))
                    .param("position", STRING)
                    .returns(VOID)
                .action("setTest")
                    .param("test", jpRef("expression"))
                    .returns(VOID)
                .action("setTest")
                    .param("test", INT)
                    .returns(VOID);

        joinPoint("annotation").extending("expression");

        joinPoint("var").extending("expression")
                .defaultAttribute("name")
                .attribute("name", STRING)
                .attribute("reference", enumRef("RefType"))
                .attribute("isArray", BOOLEAN)
                .attribute("isPrimitive", BOOLEAN)
                .attribute("isField", BOOLEAN)
                .attribute("inLoopHeader", BOOLEAN, "Equivalent to the global attribute 'isInsideLoopHeader'")
                .attribute("referenceChain", array(jpRef("joinpoint")),
                        "the chain of references of this variable (e.g., this.field)")
                .attribute("declaration", jpRef("joinpoint"));

        joinPoint("fieldAccess").extending("var")
                .defaultAttribute("name")
                .attribute("base", jpRef("expression"),
                        "The base expression of this fieldAccess. E.g., for the field access a.b.c, returns the expression representing a.b");

        joinPoint("literal").extending("expression")
                .defaultAttribute("value")
                .attribute("value", STRING)
                .action("setValue")
                    .param("value", STRING)
                    .returns(VOID);

        joinPoint("call").extending("expression")
                .defaultAttribute("name")
                .attribute("name", STRING)
                .attribute("decl", jpRef("method"))
                .attribute("simpleDecl", STRING)
                .attribute("qualifiedDecl", STRING)
                .attribute("declarator", STRING)
                .attribute("executable", STRING)
                .attribute("target", STRING)
                .attribute("targetType", jpRef("type"))
                .attribute("returnType", STRING)
                .attribute("returnTypeJp", jpRef("typeReference"))
                .attribute("arguments", array(jpRef("expression")))
                .action("clone")
                    .param("location", jpRef("statement"))
                    .param("position", STRING)
                    .returns(jpRef("call"))
                .action("setArguments")
                    .param("newArguments", array(jpRef("expression")))
                    .returns(VOID)
                .action("setTarget")
                    .param("target", jpRef("expression"), "null")
                    .returns(jpRef("call"))
                .action("setTarget")
                    .param("target", STRING, "null")
                    .returns(jpRef("call"))
                .action("setExecutable")
                    .param("executable", jpRef("method"))
                    .returns(jpRef("call"))
                .action("setArgument")
                    .param("newArgument", jpRef("expression"))
                    .param("index", INT)
                    .returns(VOID);

        joinPoint("new").extending("expression")
                .defaultAttribute("name")
                .attribute("name", STRING)
                .attribute("arguments", array(jpRef("expression")))
                .action("setArguments")
                    .param("newArguments", array(jpRef("expression")))
                    .returns(VOID);

        joinPoint("binaryExpression").extending("expression")
                .defaultAttribute("operator")
                .attribute("operator", STRING)
                .attribute("operands", array(jpRef("expression")))
                .attribute("lhs", jpRef("expression"))
                .attribute("rhs", jpRef("expression"))
                .action("setOperator")
                    .param("operator", STRING)
                    .returns(VOID);

        joinPoint("unaryExpression").extending("expression")
                .defaultAttribute("operator")
                .attribute("operand", jpRef("expression"))
                .attribute("operator", STRING)
                .action("setOperator")
                    .tooltip("Sets the operator of the unary expression. To distinguish between postfix and prefix operator, add an underscore signalling the place of the variable (e.g., _++ for postfix incremment). If no underscore is specified, postfix is assumed.")
                    .param("operator", STRING)
                    .returns(VOID);

        joinPoint("ternary").extending("expression")
                .attribute("condition", jpRef("expression"), "the condition of the ternary expression")
                .attribute("cond", jpRef("expression"), "alias for attribute 'condition'")
                .attribute("then", jpRef("expression"))
                .attribute("else", jpRef("expression"));

        joinPoint("arrayAccess").extending("expression")
                .attribute("reference", enumRef("RefType"));

        joinPoint("this").extending("expression");

        joinPoint("snippetExpr").extending("expression")
                .action("setLine")
                    .param("line", INTEGER)
                    .returns(VOID);

        joinPoint("return").extending("statement");

        joinPoint("comment").extending("statement")
                .defaultAttribute("type")
                .attribute("type", enumRef("CommentType"))
                .attribute("content", STRING);

        joinPoint("pragma").extending("comment")
                .defaultAttribute("name")
                .attribute("name", STRING);

        joinPoint("assert").extending("statement");

        joinPoint("throw").extending("statement");

        joinPoint("switch").extending("statement")
                .attribute("cases", array(jpRef("case")));

        joinPoint("case").extending("statement")
                .attribute("expr", jpRef("expression"),
                        "the expression associated with this case, of undefined if it is the default case")
                .attribute("isDefault", BOOLEAN, "true if this is the default case, false otherwise")
                .attribute("stmts", array(jpRef("statement")), "the statements of this case");

        joinPoint("continue").extending("statement");

        joinPoint("break").extending("statement");

        joinPoint("snippetStmt").extending("statement")
                .action("setLine")
                    .param("line", INTEGER)
                    .returns(VOID);

        joinPoint("reference")
                .tooltip("Points to a named program element reference")
                .defaultAttribute("name")
                .attribute("name", STRING, "Name of the element of the reference")
                .attribute("declaration", jpRef("joinpoint"), "The element that is being referenced")
                .attribute("type", STRING, "Type of the element of the reference");

        joinPoint("typeReference").extending("reference")
                .tooltip("Reference to a type")
                .defaultAttribute("name")
                .attribute("isPrimitive", BOOLEAN, "true if this is a reference to a primitive type, false otherwise")
                .attribute("isArray", BOOLEAN, "true if this is a reference to an array type, false otherwise")
                .attribute("isNumeric", BOOLEAN_BOXED,
                        "true if this is a reference to a numeric type, primitive or class (i.e., byte, Byte, char, Character, short, Short, int, Integer, long, Long, float, Float, double and Double)")
                .attribute("isBoolean", BOOLEAN,
                        "true if this is a reference to a boolean type, primitive or class (i.e., boolean, Boolean)")
                .attribute("packageName", STRING, "package name of this type")
                .attribute("packageNames", array(STRING),
                        "the package name of this type as an array, where each element is a part of the package")
                .attribute("qualifiedName", STRING, "fully qualified name of the type");
    }
}
