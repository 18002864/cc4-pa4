import java.util.Enumeration;
import java.io.PrintStream;
import java.util.Vector;
import java.util.*;

/** Defines simple phylum Program */
abstract class Program extends TreeNode {

    protected Program(int lineNumber) {
        super(lineNumber);
    }

    public abstract void dump_with_types(PrintStream out, int n);
    public abstract void semant();
    public abstract void cgen(PrintStream s);

}


/** Defines simple phylum Class_ */
abstract class Class_ extends TreeNode {

    protected Class_(int lineNumber) {
        super(lineNumber);
    }

    public abstract void dump_with_types(PrintStream out, int n);
    public abstract AbstractSymbol getName();
    public abstract AbstractSymbol getParent();
    public abstract AbstractSymbol getFilename();
    public abstract Features getFeatures();

}


/** Defines list phylum Classes
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Classes extends ListNode {

    public final static Class elementClass = Class_.class;

    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }

    protected Classes(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }

    /** Creates an empty "Classes" list */
    public Classes(int lineNumber) {
        super(lineNumber);
    }

    /** Appends "Class_" element to this list */
    public Classes appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }

    public TreeNode copy() {
        return new Classes(lineNumber, copyElements());
    }

}


/** Defines simple phylum Feature */
abstract class Feature extends TreeNode {

    protected Feature(int lineNumber) {
        super(lineNumber);
    }

    public abstract void dump_with_types(PrintStream out, int n);

}


/** Defines list phylum Features
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Features extends ListNode {

    public final static Class elementClass = Feature.class;
    /** Returns class of this lists's elements */

    public Class getElementClass() {
        return elementClass;
    }

    protected Features(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }

    /** Creates an empty "Features" list */
    public Features(int lineNumber) {
        super(lineNumber);
    }

    /** Appends "Feature" element to this list */
    public Features appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }

    public TreeNode copy() {
        return new Features(lineNumber, copyElements());
    }

}


/** Defines simple phylum Formal */
abstract class Formal extends TreeNode {

    protected Formal(int lineNumber) {
        super(lineNumber);
    }

    public abstract void dump_with_types(PrintStream out, int n);

}


/** Defines list phylum Formals
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Formals extends ListNode {

    public final static Class elementClass = Formal.class;

    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }

    protected Formals(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }

    /** Creates an empty "Formals" list */
    public Formals(int lineNumber) {
        super(lineNumber);
    }

    /** Appends "Formal" element to this list */
    public Formals appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }

    public TreeNode copy() {
        return new Formals(lineNumber, copyElements());
    }

}


/** Defines simple phylum Expression */
abstract class Expression extends TreeNode {

    protected Expression(int lineNumber) {
        super(lineNumber);
    }

    private AbstractSymbol type = null;

    public AbstractSymbol get_type() { return type; }

    public Expression set_type(AbstractSymbol s) { type = s; return this; }

    public abstract void dump_with_types(PrintStream out, int n);

    public void dump_type(PrintStream out, int n) {
        if (type != null)
            out.println(Utilities.pad(n) + ": " + type.getString());
        else
            out.println(Utilities.pad(n) + ": _no_type");
    }

    public abstract void code(PrintStream s);

}


/** Defines list phylum Expressions
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Expressions extends ListNode {

    public final static Class elementClass = Expression.class;

    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }

    protected Expressions(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }

    /** Creates an empty "Expressions" list */
    public Expressions(int lineNumber) {
        super(lineNumber);
    }

    /** Appends "Expression" element to this list */
    public Expressions appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }

    public TreeNode copy() {
        return new Expressions(lineNumber, copyElements());
    }

}


/** Defines simple phylum Case */
abstract class Case extends TreeNode {

    protected Case(int lineNumber) {
        super(lineNumber);
    }

    public abstract void dump_with_types(PrintStream out, int n);

}


/** Defines list phylum Cases
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Cases extends ListNode {

    public final static Class elementClass = Case.class;

    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }

    protected Cases(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }

    /** Creates an empty "Cases" list */
    public Cases(int lineNumber) {
        super(lineNumber);
    }

    /** Appends "Case" element to this list */
    public Cases appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }

    public TreeNode copy() {
        return new Cases(lineNumber, copyElements());
    }

}


/** Defines AST constructor 'program'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class program extends Program {

    public Classes classes;

    /** Creates "program" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for classes
      */
    public program(int lineNumber, Classes a1) {
        super(lineNumber);
        classes = a1;
    }

    public TreeNode copy() {
        return new program(lineNumber, (Classes)classes.copy());
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "program\n");
        classes.dump(out, n+2);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_program");
        for (Enumeration e = classes.getElements(); e.hasMoreElements(); ) {
        ((Class_)e.nextElement()).dump_with_types(out, n + 1);
        }
    }

    /** This method is the entry point to the semantic checker.  You will
        need to complete it in programming assignment 4.
    <p>
        Your checker should do the following two things:
    <ol>
    <li>Check that the program is semantically correct
    <li>Decorate the abstract syntax tree with type information
        by setting the type field in each Expression node.
        (see tree.h)
    </ol>
    <p>
    You are free to first do (1) and make sure you catch all semantic
        errors. Part (2) can be done in a second stage when you want
    to test the complete compiler.
    */
    public void semant() {
        /* ClassTable constructor may do some semantic analysis */
        ClassTable classTable = new ClassTable(classes);

        /* some semantic analysis code may go here */

        if (classTable.errors()) {
            System.err.println("Compilation halted due to static semantic errors.");
            System.exit(1);
        }
    }

    /** This method is the entry point to the code generator.  All of the work
      * of the code generator takes place within CgenClassTable constructor.
      * @param s the output stream
      * @see CgenClassTable
      * */
    public void cgen(PrintStream s) {
        // spim wants comments to start with '#'
        s.print("# start of generated code\n");
        CgenClassTable codegen_classtable = new CgenClassTable(classes, s);
        s.print("\n# end of generated code\n");
    }

}


/** Defines AST constructor 'class_'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class class_ extends Class_ {

    public AbstractSymbol name;
    public AbstractSymbol parent;
    public Features features;
    public AbstractSymbol filename;

    /** Creates "class_" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for parent
      * @param a2 initial value for features
      * @param a3 initial value for filename
      */
    public class_(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Features a3, AbstractSymbol a4) {
        super(lineNumber);
        name = a1;
        parent = a2;
        features = a3;
        filename = a4;
    }

    public TreeNode copy() {
        return new class_(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(parent), (Features)features.copy(), copy_AbstractSymbol(filename));
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "class_\n");
        dump_AbstractSymbol(out, n+2, name);
        dump_AbstractSymbol(out, n+2, parent);
        features.dump(out, n+2);
        dump_AbstractSymbol(out, n+2, filename);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_class");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, parent);
        out.print(Utilities.pad(n + 2) + "\"");
        Utilities.printEscapedString(out, filename.getString());
        out.println("\"\n" + Utilities.pad(n + 2) + "(");
        for (Enumeration e = features.getElements(); e.hasMoreElements();) {
        ((Feature)e.nextElement()).dump_with_types(out, n + 2);
        }
        out.println(Utilities.pad(n + 2) + ")");
    }

    public AbstractSymbol getName()     { return name; }
    public AbstractSymbol getParent()   { return parent; }
    public AbstractSymbol getFilename() { return filename; }
    public Features getFeatures()       { return features; }

}


/** Defines AST constructor 'method'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class method extends Feature {

    public AbstractSymbol name;
    public Formals formals;
    public AbstractSymbol return_type;
    public Expression expr;

    /** Creates "method" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for formals
      * @param a2 initial value for return_type
      * @param a3 initial value for expr
      */
    public method(int lineNumber, AbstractSymbol a1, Formals a2, AbstractSymbol a3, Expression a4) {
        super(lineNumber);
        name = a1;
        formals = a2;
        return_type = a3;
        expr = a4;
    }

    public TreeNode copy() {
        return new method(lineNumber, copy_AbstractSymbol(name), (Formals)formals.copy(), copy_AbstractSymbol(return_type), (Expression)expr.copy());
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "method\n");
        dump_AbstractSymbol(out, n+2, name);
        formals.dump(out, n+2);
        dump_AbstractSymbol(out, n+2, return_type);
        expr.dump(out, n+2);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_method");
        dump_AbstractSymbol(out, n + 2, name);
        for (Enumeration e = formals.getElements(); e.hasMoreElements();) {
            ((Formal)e.nextElement()).dump_with_types(out, n + 2);
        }
        dump_AbstractSymbol(out, n + 2, return_type);
        expr.dump_with_types(out, n + 2);
    }

     public void code(PrintStream s) {
        
        CgenClassTable.codeGegerationClassTable.enterScope();
        // agregar los formalss
        for (int i = 0; i < formals.getLength(); i++) {
            int offset = 2 + formals.getLength() - i;
            CgenClassTable.codeGegerationClassTable.addId(((formal)formals.getNth(i)).name, new CodeFormal(-offset));
        }

        // guardar fp en el estack
        CgenSupport.emitPush(CgenSupport.FP, s);
        CgenSupport.emitPush(CgenSupport.SELF, s);
        // guardar SP en FP
        CgenSupport.emitMove(CgenSupport.FP, CgenSupport.SP, s);
        // guardar la direccion de retorno
        CgenSupport.emitPush(CgenSupport.RA, s);

        CgenSupport.emitMove(CgenSupport.SELF, CgenSupport.ACC, s);
        CgenClassTable.codeGegerationClassTable.fp = CgenClassTable.codeGegerationClassTable.fp + 1;
        expr.code(s);
        CgenClassTable.codeGegerationClassTable.fp = CgenClassTable.codeGegerationClassTable.fp - 1;

        //restablecer RA
        CgenSupport.emitAddi(CgenSupport.SP, CgenSupport.SP, 4, s);
        CgenSupport.emitLoad(CgenSupport.RA, 0, CgenSupport.SP, s);


        //restablecer SELF
        CgenSupport.emitAddi(CgenSupport.SP, CgenSupport.SP, 4, s);
        CgenSupport.emitLoad(CgenSupport.SELF, 0, CgenSupport.SP, s);
        
        //restablecer FP
        CgenSupport.emitAddi(CgenSupport.SP, CgenSupport.SP, 4, s);
        CgenSupport.emitLoad(CgenSupport.FP, 0, CgenSupport.SP, s);

        // argumentos
        for (int i = 0; i < formals.getLength(); i++) {
             CgenSupport.emitAddi(CgenSupport.SP, CgenSupport.SP, 4, s);
        }

        // return
        CgenSupport.emitReturn(s);
        CgenClassTable.codeGegerationClassTable.exitScope();
    }

}


/** Defines AST constructor 'attr'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class attr extends Feature {

    public AbstractSymbol name;
    public AbstractSymbol type_decl;
    public Expression init;

    /** Creates "attr" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for type_decl
      * @param a2 initial value for init
      */
    public attr(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Expression a3) {
        super(lineNumber);
        name = a1;
        type_decl = a2;
        init = a3;
    }

    public TreeNode copy() {
        return new attr(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(type_decl), (Expression)init.copy());
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "attr\n");
        dump_AbstractSymbol(out, n+2, name);
        dump_AbstractSymbol(out, n+2, type_decl);
        init.dump(out, n+2);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_attr");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, type_decl);
        init.dump_with_types(out, n + 2);
    }

}


/** Defines AST constructor 'formal'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class formal extends Formal {

    public AbstractSymbol name;
    public AbstractSymbol type_decl;

    /** Creates "formal" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for type_decl
      */
    public formal(int lineNumber, AbstractSymbol a1, AbstractSymbol a2) {
        super(lineNumber);
        name = a1;
        type_decl = a2;
    }

    public TreeNode copy() {
        return new formal(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(type_decl));
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "formal\n");
        dump_AbstractSymbol(out, n+2, name);
        dump_AbstractSymbol(out, n+2, type_decl);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_formal");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, type_decl);
    }

}


/** Defines AST constructor 'branch'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class branch extends Case {

    public AbstractSymbol name;
    public AbstractSymbol type_decl;
    public Expression expr;

    /** Creates "branch" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for type_decl
      * @param a2 initial value for expr
      */
    public branch(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Expression a3) {
        super(lineNumber);
        name = a1;
        type_decl = a2;
        expr = a3;
    }

    public TreeNode copy() {
        return new branch(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(type_decl), (Expression)expr.copy());
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "branch\n");
        dump_AbstractSymbol(out, n+2, name);
        dump_AbstractSymbol(out, n+2, type_decl);
        expr.dump(out, n+2);
    }


    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_branch");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, type_decl);
        expr.dump_with_types(out, n + 2);
    }

    public void code(int labelContinuar, PrintStream s) {
      
        CgenSupport.emitLoad(CgenSupport.T1, 0, CgenSupport.ACC, s);
        // crear label para el siguiente branch
        int siguienteBranch = CgenClassTable.codeGegerationClassTable.fp++;
        CgenSupport.emitBlti(CgenSupport.T1, CgenClassTable.codeGegerationClassTable.getMinTag(type_decl), siguienteBranch, s);
        CgenSupport.emitBgti(CgenSupport.T1, CgenClassTable.codeGegerationClassTable.getMaxTag(type_decl), siguienteBranch, s); 
        // push typcase expression object en el stack
        CgenSupport.emitPush(CgenSupport.ACC, s);

        //scope para case
        CgenClassTable.codeGegerationClassTable.enterScope();

        CgenClassTable.codeGegerationClassTable.addId(name, new CodeFormal(CgenClassTable.codeGegerationClassTable.fp));
        CgenClassTable.codeGegerationClassTable.fp = CgenClassTable.codeGegerationClassTable.fp + 1;
        expr.code(s);
        CgenClassTable.codeGegerationClassTable.fp = CgenClassTable.codeGegerationClassTable.fp - 1;

        CgenClassTable.codeGegerationClassTable.exitScope();

        CgenSupport.emitAddi(CgenSupport.SP, CgenSupport.SP, 4, s);
        CgenSupport.emitLoad(CgenSupport.T1, 0, CgenSupport.SP, s);

        // salto a branch
        CgenSupport.emitBranch(labelContinuar, s);
        // siguiente label
        CgenSupport.emitLabelDef(siguienteBranch, s);
    }
}


/** Defines AST constructor 'assign'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class assign extends Expression {

    public AbstractSymbol name;
    public Expression expr;

    /** Creates "assign" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for expr
      */
    public assign(int lineNumber, AbstractSymbol a1, Expression a2) {
        super(lineNumber);
        name = a1;
        expr = a2;
    }

    public TreeNode copy() {
        return new assign(lineNumber, copy_AbstractSymbol(name), (Expression)expr.copy());
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "assign\n");
        dump_AbstractSymbol(out, n+2, name);
        expr.dump(out, n+2);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_assign");
        dump_AbstractSymbol(out, n + 2, name);
        expr.dump_with_types(out, n + 2);
        dump_type(out, n);
    }

    /** Generates code for this expression.  This method is to be completed
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream
      * */
    public void code(PrintStream s) {
        // generar codigo para expr
        expr.code(s);
        // obtener atributo de la tabla
        CodeAttr atributo = (CodeAttr)CgenClassTable.codeGegerationClassTable.lookup(name);
        // emitir codigo
        atributo.emitStore(s);
    }

}


/** Defines AST constructor 'static_dispatch'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class static_dispatch extends Expression {

    public Expression expr;
    public AbstractSymbol type_name;
    public AbstractSymbol name;
    public Expressions actual;

    /** Creates "static_dispatch" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for expr
      * @param a1 initial value for type_name
      * @param a2 initial value for name
      * @param a3 initial value for actual
      */
    public static_dispatch(int lineNumber, Expression a1, AbstractSymbol a2, AbstractSymbol a3, Expressions a4) {
        super(lineNumber);
        expr = a1;
        type_name = a2;
        name = a3;
        actual = a4;
    }

    public TreeNode copy() {
        return new static_dispatch(lineNumber, (Expression)expr.copy(), copy_AbstractSymbol(type_name), copy_AbstractSymbol(name), (Expressions)actual.copy());
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "static_dispatch\n");
        expr.dump(out, n+2);
        dump_AbstractSymbol(out, n+2, type_name);
        dump_AbstractSymbol(out, n+2, name);
        actual.dump(out, n+2);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_static_dispatch");
        expr.dump_with_types(out, n + 2);
        dump_AbstractSymbol(out, n + 2, type_name);
        dump_AbstractSymbol(out, n + 2, name);
        out.println(Utilities.pad(n + 2) + "(");
        for (Enumeration e = actual.getElements(); e.hasMoreElements();) {
            ((Expression)e.nextElement()).dump_with_types(out, n + 2);
        }
        out.println(Utilities.pad(n + 2) + ")");
        dump_type(out, n);
    }

    /** Generates code for this expression.  This method is to be completed
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream
      * */
    public void code(PrintStream s) {
        // codigo para actual
        for (Enumeration expressions = actual.getElements(); expressions.hasMoreElements();) {
            Expression expr = (Expression)expressions.nextElement();
            // generar codigo de los parametros y guardarlos en a0 
            expr.code(s);
            // guardar en el stack
            CgenSupport.emitPush(CgenSupport.ACC, s);
        }
        // codigo para expr
        expr.code(s);

        // obtener el tipo
        if(type_name == TreeConstants.SELF_TYPE)
        {
            type_name = (AbstractSymbol)CgenClassTable.codeGegerationClassTable.lookup(type_name);
        }

        // abort si es void
        int labelContinuar = CgenClassTable.codeGegerationClassTable.fp++;
        //System.out.println("fp: " + labelContinuar);
        CgenSupport.emitBne(CgenSupport.ACC, CgenSupport.ZERO, labelContinuar, s);
        StringSymbol filename = (StringSymbol)AbstractTable.stringtable.lookup(CgenClassTable.codeGegerationClassTable.classMap.get(TreeConstants.Main).filename.toString());
        CgenSupport.emitLoadAddress(CgenSupport.ACC, CgenSupport.STRCONST_PREFIX + filename.obtenerIndex() , s);
        CgenSupport.emitLoadImm(CgenSupport.T1, lineNumber, s);
        CgenSupport.emitJal("_dispatch_abort", s);
        CgenSupport.emitLabelDef(labelContinuar, s);

        // cargar la dispatch table en T1
        CgenSupport.emitLoadAddress(CgenSupport.T1, type_name + CgenSupport.DISPTAB_SUFFIX, s);
        // obtener el offset del metodo en la dispatch table y ponerlo en t1
        CgenSupport.emitLoad(CgenSupport.T1, CgenClassTable.codeGegerationClassTable.getMethodOffset(type_name, name), CgenSupport.T1, s);
        // atender el metodo
        CgenSupport.emitJalr(CgenSupport.T1, s);
    }

}


/** Defines AST constructor 'dispatch'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class dispatch extends Expression {

    public Expression expr;
    public AbstractSymbol name;
    public Expressions actual;

    /** Creates "dispatch" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for expr
      * @param a1 initial value for name
      * @param a2 initial value for actual
      */
    public dispatch(int lineNumber, Expression a1, AbstractSymbol a2, Expressions a3) {
        super(lineNumber);
        expr = a1;
        name = a2;
        actual = a3;
    }

    public TreeNode copy() {
        return new dispatch(lineNumber, (Expression)expr.copy(), copy_AbstractSymbol(name), (Expressions)actual.copy());
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "dispatch\n");
        expr.dump(out, n+2);
        dump_AbstractSymbol(out, n+2, name);
        actual.dump(out, n+2);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_dispatch");
        expr.dump_with_types(out, n + 2);
        dump_AbstractSymbol(out, n + 2, name);
        out.println(Utilities.pad(n + 2) + "(");
        for (Enumeration e = actual.getElements(); e.hasMoreElements();) {
            ((Expression)e.nextElement()).dump_with_types(out, n + 2);
        }
        out.println(Utilities.pad(n + 2) + ")");
        dump_type(out, n);
    }

    /** Generates code for this expression.  This method is to be completed
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream
      * */
    public void code(PrintStream s) {
        // codigo para actual
        for (Enumeration expressions = actual.getElements(); expressions.hasMoreElements();) {
            Expression expr = (Expression)expressions.nextElement();
            // generar codigo de los parametros y guardarlos en a0 
            expr.code(s);
            // guardar en el stack
            CgenSupport.emitPush(CgenSupport.ACC, s);
        }
        // codigo para expr
        expr.code(s);

        // obtener el tipo
        AbstractSymbol tipoExpr = expr.get_type();
        if(tipoExpr == TreeConstants.SELF_TYPE) {   
            tipoExpr = (AbstractSymbol)CgenClassTable.codeGegerationClassTable.lookup(tipoExpr);
        }

        // abort si es void
        int labelContinuar = CgenClassTable.codeGegerationClassTable.fp++;
        //System.out.println("fp: " + labelContinuar);
        CgenSupport.emitBne(CgenSupport.ACC, CgenSupport.ZERO, labelContinuar, s);
        StringSymbol filename = (StringSymbol)AbstractTable.stringtable.lookup(CgenClassTable.codeGegerationClassTable.classMap.get(TreeConstants.Main).filename.toString());
        CgenSupport.emitLoadAddress(CgenSupport.ACC, CgenSupport.STRCONST_PREFIX + filename.obtenerIndex(), s);
        CgenSupport.emitLoadImm(CgenSupport.T1, lineNumber, s);
        CgenSupport.emitJal("_dispatch_abort", s);
        CgenSupport.emitLabelDef(labelContinuar, s);

        // cargar la dispatch table en T1
        CgenSupport.emitLoad(CgenSupport.T1, CgenSupport.DISPTABLE_OFFSET, CgenSupport.ACC, s);
        // obtener el offset del metodo en la dispatch table y ponerlo en t1
        CgenSupport.emitLoad(CgenSupport.T1, CgenClassTable.codeGegerationClassTable.getMethodOffset(tipoExpr, name), CgenSupport.T1, s);
        // atender el metodo
        CgenSupport.emitJalr(CgenSupport.T1, s);
    }

}


/** Defines AST constructor 'cond'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class cond extends Expression {

    public Expression pred;
    public Expression then_exp;
    public Expression else_exp;

    /** Creates "cond" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for pred
      * @param a1 initial value for then_exp
      * @param a2 initial value for else_exp
      */
    public cond(int lineNumber, Expression a1, Expression a2, Expression a3) {
        super(lineNumber);
        pred = a1;
        then_exp = a2;
        else_exp = a3;
    }

    public TreeNode copy() {
        return new cond(lineNumber, (Expression)pred.copy(), (Expression)then_exp.copy(), (Expression)else_exp.copy());
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "cond\n");
        pred.dump(out, n+2);
        then_exp.dump(out, n+2);
        else_exp.dump(out, n+2);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_cond");
        pred.dump_with_types(out, n + 2);
        then_exp.dump_with_types(out, n + 2);
        else_exp.dump_with_types(out, n + 2);
        dump_type(out, n);
    }

    /** Generates code for this expression.  This method is to be completed
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream
      * */
    public void code(PrintStream s) {

        // generar codigo de la condicion
        pred.code(s);
        // poner el resultado en T1
        CgenSupport.emitFetchInt(CgenSupport.T1, CgenSupport.ACC, s);
        // aumentar el fp, para label de alternativa
        int labelAlternativa = CgenClassTable.codeGegerationClassTable.fp++;
        // emitir evaluacion para saltar a la alternativa
        CgenSupport.emitBeqz(CgenSupport.T1, labelAlternativa, s);
        // generar codigo para consecuencia
        then_exp.code(s);
        // aumentar el fp, para el label de la consecuencia
        int labelConsecuencia = CgenClassTable.codeGegerationClassTable.fp++;
        // emitir branch de consecuencia
        CgenSupport.emitBranch(labelConsecuencia, s);
        // codigo de alternativa
        CgenSupport.emitLabelDef(labelAlternativa, s);
        else_exp.code(s);
        // codigo de consecuencia
        CgenSupport.emitLabelDef(labelConsecuencia, s);
    }

}


/** Defines AST constructor 'loop'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class loop extends Expression {

    public Expression pred;
    public Expression body;

    /** Creates "loop" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for pred
      * @param a1 initial value for body
      */
    public loop(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        pred = a1;
        body = a2;
    }

    public TreeNode copy() {
        return new loop(lineNumber, (Expression)pred.copy(), (Expression)body.copy());
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "loop\n");
        pred.dump(out, n+2);
        body.dump(out, n+2);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_loop");
        pred.dump_with_types(out, n + 2);
        body.dump_with_types(out, n + 2);
        dump_type(out, n);
    }

    /** Generates code for this expression.  This method is to be completed
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream
      * */
    public void code(PrintStream s) {
        // label para loop
        int labelIterar = CgenClassTable.codeGegerationClassTable.fp++;
        // emitir label para loop
        CgenSupport.emitLabelDef(labelIterar, s);
        // generar codigo de condicion
        pred.code(s);
        // guardar el resultado en T1
        CgenSupport.emitFetchInt(CgenSupport.T1, CgenSupport.ACC, s);
        // crear label para salir de loop
        int labelExit = CgenClassTable.codeGegerationClassTable.fp++;
        // emitir evaluacion salir de loop 
        CgenSupport.emitBeqz(CgenSupport.T1, labelExit, s);
        // generar codigo del body del loop
        body.code(s);
        // codigo para iterar
        CgenSupport.emitBranch(labelIterar, s);
        // codigo para salir de loop
        CgenSupport.emitLabelDef(labelExit, s);
        // limpiar ACC
        CgenSupport.emitMove(CgenSupport.ACC, CgenSupport.ZERO, s);
    }

}


/** Defines AST constructor 'typcase'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class typcase extends Expression {

    public Expression expr;
    public Cases cases;

    /** Creates "typcase" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for expr
      * @param a1 initial value for cases
      */
    public typcase(int lineNumber, Expression a1, Cases a2) {
        super(lineNumber);
        expr = a1;
        cases = a2;
    }

    public TreeNode copy() {
        return new typcase(lineNumber, (Expression)expr.copy(), (Cases)cases.copy());
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "typcase\n");
        expr.dump(out, n+2);
        cases.dump(out, n+2);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_typcase");
        expr.dump_with_types(out, n + 2);
        for (Enumeration e = cases.getElements(); e.hasMoreElements();) {
        ((Case)e.nextElement()).dump_with_types(out, n + 2);
        }
        dump_type(out, n);
    }

    /** Generates code for this expression.  This method is to be completed
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream
      * */
    public void code(PrintStream s) {
        // generar una lista para las los branch
        List<branch> branches = new ArrayList<branch>();
        for (Enumeration listCases = cases.getElements(); listCases.hasMoreElements();) {
            branch case_ = (branch)listCases.nextElement();
            branches.add(case_);
        }
        // ordenar branch
        Collections.sort(branches, new Comparator<branch>(){
            public int compare(branch first, branch second) {
                // para obtener el tipo mas especifico
                return CgenClassTable.codeGegerationClassTable.getClassHierarchyLevel(second.type_decl) - CgenClassTable.codeGegerationClassTable.getClassHierarchyLevel(first.type_decl);
            }
        });

        // generar codigo para expr
        expr.code(s);
        // do not abort if case type is not void (0 in accu)
        int labelNoVoid = CgenClassTable.codeGegerationClassTable.fp++;
        CgenSupport.emitBne(CgenSupport.ACC, CgenSupport.ZERO, labelNoVoid, s);
        // saltar a abort
        StringSymbol filename = (StringSymbol)AbstractTable.stringtable.lookup(CgenClassTable.codeGegerationClassTable.classMap.get(TreeConstants.Main).filename.toString());
        CgenSupport.emitLoadAddress(CgenSupport.ACC, CgenSupport.STRCONST_PREFIX + filename.obtenerIndex(), s);
        // cargar el numero de linea en t1
        CgenSupport.emitLoadImm(CgenSupport.T1, lineNumber, s);
        CgenSupport.emitJal("_case_abort2", s);
        // codgio de label
        CgenSupport.emitLabelDef(labelNoVoid, s);
        // label para continuar
        int labelContinuar = CgenClassTable.codeGegerationClassTable.fp++;
        // generar codigo para cada branch
        for(branch branch_: branches) {
            branch_.code(labelContinuar, s);
        }
        // salgo a abort
        CgenSupport.emitJal("_case_abort", s);
        // generar codigo para label continuar
        CgenSupport.emitLabelDef(labelContinuar, s);
    }

}


/** Defines AST constructor 'block'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class block extends Expression {

    public Expressions body;

    /** Creates "block" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for body
      */
    public block(int lineNumber, Expressions a1) {
        super(lineNumber);
        body = a1;
    }

    public TreeNode copy() {
        return new block(lineNumber, (Expressions)body.copy());
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "block\n");
        body.dump(out, n+2);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_block");
        for (Enumeration e = body.getElements(); e.hasMoreElements();) {
            ((Expression)e.nextElement()).dump_with_types(out, n + 2);
        }
        dump_type(out, n);
    }

    /** Generates code for this expression.  This method is to be completed
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream
      * */
    public void code(PrintStream s) {
        for (Enumeration bloque = body.getElements(); bloque.hasMoreElements();) {
            // generar codigo
            Expression expr = (Expression)bloque.nextElement();
            expr.code(s);
        }
    }

}


/** Defines AST constructor 'let'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class let extends Expression {

    public AbstractSymbol identifier;
    public AbstractSymbol type_decl;
    public Expression init;
    public Expression body;

    /** Creates "let" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for identifier
      * @param a1 initial value for type_decl
      * @param a2 initial value for init
      * @param a3 initial value for body
      */
    public let(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Expression a3, Expression a4) {
        super(lineNumber);
        identifier = a1;
        type_decl = a2;
        init = a3;
        body = a4;
    }

    public TreeNode copy() {
        return new let(lineNumber, copy_AbstractSymbol(identifier), copy_AbstractSymbol(type_decl), (Expression)init.copy(), (Expression)body.copy());
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "let\n");
        dump_AbstractSymbol(out, n+2, identifier);
        dump_AbstractSymbol(out, n+2, type_decl);
        init.dump(out, n+2);
        body.dump(out, n+2);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_let");
        dump_AbstractSymbol(out, n + 2, identifier);
        dump_AbstractSymbol(out, n + 2, type_decl);
        init.dump_with_types(out, n + 2);
        body.dump_with_types(out, n + 2);
        dump_type(out, n);
    }

    /** Generates code for this expression.  This method is to be completed
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream
      * */
    public void code(PrintStream s) {

        if (init.get_type() == null) {
      
            if (type_decl == TreeConstants.Bool)
            {
                CgenSupport.emitLoadBool(CgenSupport.ACC, new BoolConst(false), s);
            }
            else if (type_decl == TreeConstants.Int) 
            {
                CgenSupport.emitLoadInt(CgenSupport.ACC, (IntSymbol)AbstractTable.inttable.lookup("0"), s);
            } 
            else if (type_decl == TreeConstants.Str) 
            {
                CgenSupport.emitLoadString(CgenSupport.ACC, (StringSymbol)AbstractTable.stringtable.lookup(""), s);
            } else 
            {
                CgenSupport.emitMove(CgenSupport.ACC, CgenSupport.ZERO, s);
            }

        } else {
            init.code(s);
        }
        // guardar acc
        CgenSupport.emitPush(CgenSupport.ACC, s);
        // nuevo escope para let
        CgenClassTable.codeGegerationClassTable.enterScope();
        CgenClassTable.codeGegerationClassTable.addId(identifier, new CodeFormal(CgenClassTable.codeGegerationClassTable.fp));
        CgenClassTable.codeGegerationClassTable.fp = CgenClassTable.codeGegerationClassTable.fp + 1;
        body.code(s);
        CgenClassTable.codeGegerationClassTable.fp = CgenClassTable.codeGegerationClassTable.fp - 1;
        CgenClassTable.codeGegerationClassTable.exitScope();
        // poner valor en a0
        CgenSupport.emitAddi(CgenSupport.SP, CgenSupport.SP, 4, s);
        CgenSupport.emitLoad(CgenSupport.T1, 0, CgenSupport.SP, s);
    }

}


/** Defines AST constructor 'plus'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class plus extends Expression {

    public Expression e1;
    public Expression e2;

    /** Creates "plus" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public plus(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }

    public TreeNode copy() {
        return new plus(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "plus\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_plus");
        e1.dump_with_types(out, n + 2);
        e2.dump_with_types(out, n + 2);
        dump_type(out, n);
    }

    /** Generates code for this expression.  This method is to be completed
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream
      * */
    public void code(PrintStream s) {
        CgenSupport.emitArith(e1, e2, CgenSupport.ADD, s);
    }

}


/** Defines AST constructor 'sub'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class sub extends Expression {

    public Expression e1;
    public Expression e2;
    /** Creates "sub" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public sub(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }

    public TreeNode copy() {
        return new sub(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "sub\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_sub");
        e1.dump_with_types(out, n + 2);
        e2.dump_with_types(out, n + 2);
        dump_type(out, n);
    }

    /** Generates code for this expression.  This method is to be completed
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream
      * */
    public void code(PrintStream s) {
        CgenSupport.emitArith(e1, e2, CgenSupport.SUB, s);
    }

}


/** Defines AST constructor 'mul'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class mul extends Expression {

    public Expression e1;
    public Expression e2;

    /** Creates "mul" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public mul(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }

    public TreeNode copy() {
        return new mul(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "mul\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_mul");
        e1.dump_with_types(out, n + 2);
        e2.dump_with_types(out, n + 2);
        dump_type(out, n);
    }

    /** Generates code for this expression.  This method is to be completed
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream
      * */
    public void code(PrintStream s) {
        CgenSupport.emitArith(e1, e2, CgenSupport.MUL, s);
    }

}


/** Defines AST constructor 'divide'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class divide extends Expression {

    public Expression e1;
    public Expression e2;

    /** Creates "divide" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public divide(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }

    public TreeNode copy() {
        return new divide(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "divide\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_divide");
        e1.dump_with_types(out, n + 2);
        e2.dump_with_types(out, n + 2);
        dump_type(out, n);
    }

    /** Generates code for this expression.  This method is to be completed
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream
      * */
    public void code(PrintStream s) {
        CgenSupport.emitArith(e1, e2, CgenSupport.DIV, s);
    }

}


/** Defines AST constructor 'neg'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class neg extends Expression {

    public Expression e1;

    /** Creates "neg" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      */
    public neg(int lineNumber, Expression a1) {
        super(lineNumber);
        e1 = a1;
    }

    public TreeNode copy() {
        return new neg(lineNumber, (Expression)e1.copy());
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "neg\n");
        e1.dump(out, n+2);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_neg");
        e1.dump_with_types(out, n + 2);
        dump_type(out, n);
    }

    /** Generates code for this expression.  This method is to be completed
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream
      * */
    public void code(PrintStream s) {
        // generar codigo de expr
        e1.code(s);
        // copia de object
        CgenSupport.emitJal("Object.copy", s);
        // cargar el resultado en T1
        CgenSupport.emitFetchInt(CgenSupport.T1, CgenSupport.ACC, s);
        // emitir instruccion
        CgenSupport.emitNeg(CgenSupport.T1, CgenSupport.T1, s);
        // guardar el resultado
        CgenSupport.emitStoreInt(CgenSupport.T1, CgenSupport.ACC, s);
    }

}


/** Defines AST constructor 'lt'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class lt extends Expression {

    public Expression e1;
    public Expression e2;

    /** Creates "lt" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public lt(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }

    public TreeNode copy() {
        return new lt(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "lt\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_lt");
        e1.dump_with_types(out, n + 2);
        e2.dump_with_types(out, n + 2);
        dump_type(out, n);
    }

    /** Generates code for this expression.  This method is to be completed
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream
      * */
    public void code(PrintStream s) {
       
        e1.code(s);
        // guardar en el stack
        CgenSupport.emitPush(CgenSupport.ACC, s);
        CgenClassTable.codeGegerationClassTable.fp = CgenClassTable.codeGegerationClassTable.fp + 1;
        e2.code(s);
        CgenClassTable.codeGegerationClassTable.fp = CgenClassTable.codeGegerationClassTable.fp - 1;
        // mover a T2
        CgenSupport.emitMove(CgenSupport.T2, CgenSupport.ACC, s);
        // poner el resultado de e1 en T1
        CgenSupport.emitAddi(CgenSupport.SP, CgenSupport.SP, 4, s);
        CgenSupport.emitLoad(CgenSupport.T1, 0, CgenSupport.SP, s);
        // cargar en acc
        CgenSupport.emitLoadBool(CgenSupport.ACC, new BoolConst(true), s);
        // guardar valores
        CgenSupport.emitFetchInt(CgenSupport.T1, CgenSupport.T1, s);
        CgenSupport.emitFetchInt(CgenSupport.T2, CgenSupport.T2, s);
        // emitir codigo
        //int labellt = CgenClassTable.codeGegerationClassTable.fp++;
        //CgenSupport.emitBlt(CgenSupport.T1, CgenSupport.T2, labellt, s);

        s.print(CgenSupport.BLT + CgenSupport.T1 + " " + CgenSupport.T2 + " ");
        // get a new label number.. this is a dummy label, if it is true then false won't be written in to ACC
        int labelblt = CgenClassTable.codeGegerationClassTable.fp++;
        // finish printing the label
        CgenSupport.emitLabelRef(labelblt, s);
        // print new line
        s.println("");
        // now seems like code is still execution (no branching), load false's boolean into ACC
        CgenSupport.emitLoadBool(CgenSupport.ACC, new BoolConst(false), s);
        // print label for branching
        CgenSupport.emitLabelDef(labelblt, s);
    }

}


/** Defines AST constructor 'eq'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class eq extends Expression {

    public Expression e1;
    public Expression e2;

    /** Creates "eq" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public eq(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }

    public TreeNode copy() {
        return new eq(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "eq\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_eq");
        e1.dump_with_types(out, n + 2);
        e2.dump_with_types(out, n + 2);
        dump_type(out, n);
    }

    /** Generates code for this expression.  This method is to be completed
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream
      * */
    public void code(PrintStream s) {
        e1.code(s);
        // guardar acc
        CgenSupport.emitPush(CgenSupport.ACC, s);
        CgenClassTable.codeGegerationClassTable.fp = CgenClassTable.codeGegerationClassTable.fp + 1;
        // codigo de e2
        e2.code(s);
        // guardar el rsultado en T2
        CgenSupport.emitMove(CgenSupport.T2, CgenSupport.ACC, s);
        // obtener en t1 el resultado de e1
        CgenClassTable.codeGegerationClassTable.fp = CgenClassTable.codeGegerationClassTable.fp - 1;
        CgenSupport.emitAddi(CgenSupport.SP, CgenSupport.SP, 4, s);
        CgenSupport.emitLoad(CgenSupport.T1, 0, CgenSupport.SP, s);
        // cargar true
        CgenSupport.emitLoadBool(CgenSupport.ACC, new BoolConst(true), s);
        // label para continuar
        int labelContinuar = CgenClassTable.codeGegerationClassTable.fp++;
        
        // si el valor es igual ir a label continuar
        CgenSupport.emitBeq(CgenSupport.T1, CgenSupport.T2, labelContinuar, s);
        // si no cargar falso y evaluar equality_test
        CgenSupport.emitLoadBool(CgenSupport.A1, new BoolConst(false),s);
        CgenSupport.emitJal("equality_test", s);
        // codigo de label continuar
        CgenSupport.emitLabelDef(labelContinuar, s);
    }

}


/** Defines AST constructor 'leq'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class leq extends Expression {

    public Expression e1;
    public Expression e2;

    /** Creates "leq" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public leq(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }

    public TreeNode copy() {
        return new leq(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "leq\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_leq");
        e1.dump_with_types(out, n + 2);
        e2.dump_with_types(out, n + 2);
        dump_type(out, n);
    }

    /** Generates code for this expression.  This method is to be completed
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream
      * */
    public void code(PrintStream s) {
        e1.code(s);
        // guardar en el stack
        CgenSupport.emitPush(CgenSupport.ACC, s);
        CgenClassTable.codeGegerationClassTable.fp = CgenClassTable.codeGegerationClassTable.fp + 1;
        e2.code(s);
        CgenClassTable.codeGegerationClassTable.fp = CgenClassTable.codeGegerationClassTable.fp - 1;
        // mover a T2
        CgenSupport.emitMove(CgenSupport.T2, CgenSupport.ACC, s);
        // poner el resultado de e1 en T1
        CgenSupport.emitAddi(CgenSupport.SP, CgenSupport.SP, 4, s);
        CgenSupport.emitLoad(CgenSupport.T1, 0, CgenSupport.SP, s);
        // cargar en acc
        CgenSupport.emitLoadBool(CgenSupport.ACC, new BoolConst(true), s);
        // guardar valores
        CgenSupport.emitFetchInt(CgenSupport.T1, CgenSupport.T1, s);
        CgenSupport.emitFetchInt(CgenSupport.T2, CgenSupport.T2, s);
        // emitir codigo
        //int labelleq = CgenClassTable.codeGegerationClassTable.fp++;
        //CgenSupport.emitBleq(CgenSupport.T1, CgenSupport.T2, labelleq, s);
        s.print(CgenSupport.BLEQ + CgenSupport.T1 + " " + CgenSupport.T2 + " ");
        // get a new label number.. this is a dummy label, if it is true then false won't be written in to ACC
        int labelblt = CgenClassTable.codeGegerationClassTable.fp++;
        // finish printing the label
        CgenSupport.emitLabelRef(labelblt, s);
        // print new line
        s.println("");
        // now seems like code is still execution (no branching), load false's boolean into ACC
        CgenSupport.emitLoadBool(CgenSupport.ACC, new BoolConst(false), s);
        // print label for branching
        CgenSupport.emitLabelDef(labelblt, s);
    }

}


/** Defines AST constructor 'comp'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class comp extends Expression {

    public Expression e1;

    /** Creates "comp" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      */
    public comp(int lineNumber, Expression a1) {
        super(lineNumber);
        e1 = a1;
    }

    public TreeNode copy() {
        return new comp(lineNumber, (Expression)e1.copy());
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "comp\n");
        e1.dump(out, n+2);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_comp");
        e1.dump_with_types(out, n + 2);
        dump_type(out, n);
    }

    /** Generates code for this expression.  This method is to be completed
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream
      * */
    public void code(PrintStream s) {
        // codigo para e1
        e1.code(s);
        // cargar en t1
        CgenSupport.emitFetchInt(CgenSupport.T1, CgenSupport.ACC, s);
        // cargar true en acc
        CgenSupport.emitLoadBool(CgenSupport.ACC, new BoolConst(true),s);
        // label para continuar
        int labelContinuar = CgenClassTable.codeGegerationClassTable.fp++;
        CgenSupport.emitBeqz(CgenSupport.T1, labelContinuar, s);
        CgenSupport.emitLoadBool(CgenSupport.ACC, new BoolConst(false),s);
        CgenSupport.emitLabelDef(labelContinuar, s);
    }

}


/** Defines AST constructor 'int_const'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class int_const extends Expression {

    public AbstractSymbol token;

    /** Creates "int_const" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for token
      */
    public int_const(int lineNumber, AbstractSymbol a1) {
        super(lineNumber);
        token = a1;
    }

    public TreeNode copy() {
        return new int_const(lineNumber, copy_AbstractSymbol(token));
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "int_const\n");
        dump_AbstractSymbol(out, n+2, token);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_int");
        dump_AbstractSymbol(out, n + 2, token);
        dump_type(out, n);
    }

    /** Generates code for this expression.  This method method is provided
      * to you as an example of code generation.
      * @param s the output stream
      * */
    public void code(PrintStream s) {
        CgenSupport.emitLoadInt(CgenSupport.ACC, (IntSymbol)AbstractTable.inttable.lookup(token.getString()), s);
    }

}


/** Defines AST constructor 'bool_const'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class bool_const extends Expression {

    public Boolean val;

    /** Creates "bool_const" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for val
      */
    public bool_const(int lineNumber, Boolean a1) {
        super(lineNumber);
        val = a1;
    }

    public TreeNode copy() {
        return new bool_const(lineNumber, copy_Boolean(val));
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "bool_const\n");
        dump_Boolean(out, n+2, val);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_bool");
        dump_Boolean(out, n + 2, val);
        dump_type(out, n);
    }

    /** Generates code for this expression.  This method method is provided
      * to you as an example of code generation.
      * @param s the output stream
      * */
    public void code(PrintStream s) {
        CgenSupport.emitLoadBool(CgenSupport.ACC, new BoolConst(val), s);
    }

}


/** Defines AST constructor 'string_const'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class string_const extends Expression {

    public AbstractSymbol token;

    /** Creates "string_const" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for token
      */
    public string_const(int lineNumber, AbstractSymbol a1) {
        super(lineNumber);
        token = a1;
    }

    public TreeNode copy() {
        return new string_const(lineNumber, copy_AbstractSymbol(token));
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "string_const\n");
        dump_AbstractSymbol(out, n+2, token);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_string");
        out.print(Utilities.pad(n + 2) + "\"");
        Utilities.printEscapedString(out, token.getString());
        out.println("\"");
        dump_type(out, n);
    }

    /** Generates code for this expression.  This method method is provided
      * to you as an example of code generation.
      * @param s the output stream
      * */
    public void code(PrintStream s) {
        CgenSupport.emitLoadString(CgenSupport.ACC, (StringSymbol)AbstractTable.stringtable.lookup(token.getString()), s);
    }

}


/** Defines AST constructor 'new_'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class new_ extends Expression {

    public AbstractSymbol type_name;

    /** Creates "new_" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for type_name
      */
    public new_(int lineNumber, AbstractSymbol a1) {
        super(lineNumber);
        type_name = a1;
    }

    public TreeNode copy() {
        return new new_(lineNumber, copy_AbstractSymbol(type_name));
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "new_\n");
        dump_AbstractSymbol(out, n+2, type_name);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_new");
        dump_AbstractSymbol(out, n + 2, type_name);
        dump_type(out, n);
    }

    /** Generates code for this expression.  This method is to be completed
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream
      * */
    public void code(PrintStream s) {
        if (type_name == TreeConstants.SELF_TYPE) {
            // cargar el tag de self en T1
            CgenSupport.emitLoad(CgenSupport.T1, 0, CgenSupport.SELF, s);
            // cargar la direccion del objeto en T2
            CgenSupport.emitLoadAddress(CgenSupport.T2, CgenSupport.CLASSOBJTAB, s);
            // shift * 2 * 4
            CgenSupport.emitSlli(CgenSupport.T1, CgenSupport.T1, 3, s);
            // calcular tag(VER)  
            CgenSupport.emitAddu(CgenSupport.T2, CgenSupport.T2, CgenSupport.T1, s);
            // cargar la direccion del objeto en ACC
            CgenSupport.emitLoad(CgenSupport.ACC, 0, CgenSupport.T2, s);
            // push T2 a stack
            CgenSupport.emitPush(CgenSupport.T2, s);
            // copia de objeto
            CgenSupport.emitJal("Object.copy", s);
            // pop stack a T2
            CgenSupport.emitAddi(CgenSupport.SP, CgenSupport.SP, 4, s);
            CgenSupport.emitLoad(CgenSupport.T2, 0, CgenSupport.SP, s);
            // cargar init
            CgenSupport.emitLoad(CgenSupport.T2, 1, CgenSupport.T2, s);
            // salto a objeto
            CgenSupport.emitJalr(CgenSupport.T2, s);
        } else {
            // cargar el prototipo 
            CgenSupport.emitLoadAddress(CgenSupport.ACC, type_name.toString() + CgenSupport.PROTOBJ_SUFFIX, s);
            // copiar el prototipo
            CgenSupport.emitJal("Object.copy", s);
            // ir a la incializacion del objeto
            CgenSupport.emitJal(type_name.toString() + CgenSupport.CLASSINIT_SUFFIX, s);
        }
    }

}


/** Defines AST constructor 'isvoid'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class isvoid extends Expression {

    public Expression e1;

    /** Creates "isvoid" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      */
    public isvoid(int lineNumber, Expression a1) {
        super(lineNumber);
        e1 = a1;
    }

    public TreeNode copy() {
        return new isvoid(lineNumber, (Expression)e1.copy());
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "isvoid\n");
        e1.dump(out, n+2);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_isvoid");
        e1.dump_with_types(out, n + 2);
        dump_type(out, n);
    }

    /** Generates code for this expression.  This method is to be completed
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream
      * */
    public void code(PrintStream s) {
        e1.code(s);
        // label para void
        int labelVoid = CgenClassTable.codeGegerationClassTable.fp++;
        // si ACC es 0, void
        CgenSupport.emitBeqz(CgenSupport.ACC, labelVoid, s);
        // si ACC no es void, load false
        CgenSupport.emitLoadBool(CgenSupport.ACC, new BoolConst(false), s);
        // label para continuar
        int labelContinuar = CgenClassTable.codeGegerationClassTable.fp++;
        // branch para continuar
        CgenSupport.emitBranch(labelContinuar, s);
        // si es void
        CgenSupport.emitLabelDef(labelVoid, s);
        // load true
        CgenSupport.emitLoadBool(CgenSupport.ACC, new BoolConst(true),s);
        //codigo de label continuar
        CgenSupport.emitLabelDef(labelContinuar, s);
    }

}


/** Defines AST constructor 'no_expr'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class no_expr extends Expression {

    /** Creates "no_expr" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      */
    public no_expr(int lineNumber) {
        super(lineNumber);
    }

    public TreeNode copy() {
        return new no_expr(lineNumber);
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "no_expr\n");
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_no_expr");
        dump_type(out, n);
    }

    /** Generates code for this expression.  This method is to be completed
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream
      * */
    public void code(PrintStream s) {
    }

}


/** Defines AST constructor 'object'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class object extends Expression {

    public AbstractSymbol name;

    /** Creates "object" AST node.
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      */
    public object(int lineNumber, AbstractSymbol a1) {
        super(lineNumber);
        name = a1;
    }

    public TreeNode copy() {
        return new object(lineNumber, copy_AbstractSymbol(name));
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "object\n");
        dump_AbstractSymbol(out, n+2, name);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_object");
        dump_AbstractSymbol(out, n + 2, name);
        dump_type(out, n);
    }

    /** Generates code for this expression.  This method is to be completed
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream
      * */
    public void code(PrintStream s) {
        if (name == TreeConstants.self) {
            CgenSupport.emitMove(CgenSupport.ACC, CgenSupport.SELF, s);
        } else {
            ((CodeAttr)CgenClassTable.codeGegerationClassTable.lookup(name)).emitLoad(s);
        }
    }

}

// clase para poder generar codigo para formals y atributos
abstract class CodeAttr {
    public abstract int offset();
    public abstract void emitLoad(PrintStream str);
    public abstract void emitStore(PrintStream str);
}

// clase para emitir codigo de atributos
class ClassAttr extends CodeAttr{
    private int offset;

    //constructor
    public ClassAttr(int offset) {
        this.offset = CgenSupport.DEFAULT_OBJFIELDS+offset;
    }
    // metodo que obtiene offset
    public int offset(){
        return offset;
    }

    // emitir codigo load
    public void emitLoad(PrintStream str) {
        CgenSupport.emitLoad(CgenSupport.ACC, offset, CgenSupport.SELF, str);
    }

    // emitir codigo para store
    public void emitStore(PrintStream str) {
        CgenSupport.emitStore(CgenSupport.ACC, offset, CgenSupport.SELF, str);
    }

}

class CodeFormal extends CodeAttr{
    private int offset;
    public CodeFormal(int offset) {
        this.offset = offset;
    }
    public int offset(){
        return offset;
    }
    public void emitLoad(PrintStream s) {
        CgenSupport.emitLoad(CgenSupport.ACC, -offset, CgenSupport.FP, s);
    }
    public void emitStore(PrintStream s) {
        CgenSupport.emitStore(CgenSupport.ACC, -offset, CgenSupport.FP, s);
    }
}
