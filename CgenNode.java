/*
Copyright (c) 2000 The Regents of the University of California.
All rights reserved.

Permission to use, copy, modify, and distribute this software for any
purpose, without fee, and without written agreement is hereby granted,
provided that the above copyright notice and the following two
paragraphs appear in all copies of this software.

IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR
DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES ARISING OUT
OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF THE UNIVERSITY OF
CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
AND FITNESS FOR A PARTICULAR PURPOSE.  THE SOFTWARE PROVIDED HEREUNDER IS
ON AN "AS IS" BASIS, AND THE UNIVERSITY OF CALIFORNIA HAS NO OBLIGATION TO
PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR MODIFICATIONS.
*/

// This is a project skeleton file

import java.io.PrintStream;
import java.util.*;

class CgenNode extends class_ implements Comparable<CgenNode>{

    /** The parent of this node in the inheritance tree */
    private CgenNode parent;

    /** The children of this node in the inheritance tree */
    private Vector children;

    /** Indicates a basic class */
    final static int Basic = 0;

    /** Indicates a class that came from a Cool program */
    final static int NotBasic = 1;

    /** Does this node correspond to a basic class? */
    private int basic_status;

    // // variables para llevar control de los offset
    // public int metodoOffset;
    // public int atributoOffset;

    // // map para relacionar atributo con su objeto y su offset
    // public Map<AbstractSymbol, attr> atributos;
    // public Map<AbstractSymbol, Integer> atributosOffset;

    // // map para relacionar metodo con su objeto y su offset
    // public Map<AbstractSymbol, method> metodos;
    // public Map<AbstractSymbol, Integer> metodosOffset;

    // // map para realacionar el metodo con su clase, util para los metodos estaticos
    // public Map<AbstractSymbol, AbstractSymbol> metodoxClase;

    public StringSymbol nodeName;
    
    public int tag;

    public int maxTag;

    static int globalTag = 0;


    // variables para llevar control de los offset
    public int methodOffsetNumber;

    public int attributeOffsetNumber;

    // map para relacionar attribute con su objeto y su offset
    public Map<AbstractSymbol, attr> attributesMap;
    public Map<AbstractSymbol, Integer> attributesOffsetMap;

    // map para relacionar metodo con su objeto y su offset
    public Map<AbstractSymbol, method> methodsMap;
    public Map<AbstractSymbol, Integer> methodsOffsetMap;

    // map para realacionar el metodo con su clase, util para los methodsMap estaticos
    public Map<AbstractSymbol, AbstractSymbol> methodClassMap;


    // para asignar el tag correspondiente
    static int tagGlobal = 0;

    public int compareTo(CgenNode nodo) {
        return tag - nodo.tag;
    }

    /** Constructs a new CgenNode to represent class "c".
     * @param c the class
     * @param basic_status is this class basic or not
     * @param table the class table
     * */
    CgenNode(Class_ c, int basic_status, CgenClassTable table) {
        super(0, c.getName(), c.getParent(), c.getFeatures(), c.getFilename());
        this.parent = null;
        this.children = new Vector();
        this.basic_status = basic_status;
        this.nodeName = (StringSymbol)AbstractTable.stringtable.addString(name.getString().trim());
    }

    void addChild(CgenNode child) {
        children.addElement(child);
    }

    /** Gets the children of this class
     * @return the children
     * */
    Enumeration getChildren() {
        return children.elements();
    }

    /** Sets the parent of this class.
     * @param parent the parent
     * */
    void setParentNd(CgenNode parent) {
        if (this.parent != null) {
            Utilities.fatalError("parent already set in CgenNode.setParent()");
        }
        if (parent == null) {
            Utilities.fatalError("null parent in CgenNode.setParent()");
        }
        this.parent = parent;
    }

    /** Gets the parent of this class
     * @return the parent
     * */
    CgenNode getParentNd() {
        return parent;
    }

    /** Returns true is this is a basic class.
     * @return true or false
     * */
    boolean basic() {
        return basic_status == Basic;
    }

    // metodo que se encarga de calcular offset para features
    // public void offsetFeatures(){
        
    //     // inicializar maps
    //     atributos = new LinkedHashMap<AbstractSymbol, attr>();
    //     metodos = new LinkedHashMap<AbstractSymbol, method>();
    //     metodosOffset = new LinkedHashMap<AbstractSymbol, Integer>();   
    //     atributosOffset = new LinkedHashMap<AbstractSymbol, Integer>();
    //     metodoxClase = new LinkedHashMap<AbstractSymbol, AbstractSymbol>();

    //     // iniciar offset
    //     metodoOffset = 0;
    //     atributoOffset = 0;

    //     //System.out.println("-----***** Analizando clase " + getName() + " *****-----");

    //     // si la clase no es object entonces por el arbol de herencia
    //     // tenemos que insertar los metodos de los padres en los hijos
    //     if(getName() != TreeConstants.Object_) {
            
    //         // como linkedHashMap nos mantiene el orden de los metodos
    //         // de como fuereon insertados al hacer un putall se mantendra 
    //         // el orden en los hijos
    //         metodosOffset.putAll(parent.metodosOffset); 
    //         // el map metodoxClase del padre
    //         metodoxClase.putAll(parent.metodoxClase);
    //         // iniciar offset de metodos, para hijo
    //         metodoOffset = parent.metodoOffset;
    //         // iniciar offset de atributos, para hijo
    //         atributoOffset = parent.atributoOffset;

    //     }

    //     // recorrer los features de la clase
    //     for(Enumeration listFeatures = features.getElements(); listFeatures.hasMoreElements();) {
    //         Feature feature = (Feature)listFeatures.nextElement();

    //         // verificar si es metodo o atributo
    //         if(feature instanceof method){
    //             method metodo = (method)feature;
    //             metodos.put(metodo.name, metodo);
    //             // aqui relacionamos el metodo con la clase
    //             metodoxClase.put(metodo.name, getName());

    //             // verificar que el metodo, no sea heredado, si ya existe
    //             // quiere decir que es heredado y no debemos alterar su offset
    //             // o duplicarlo
    //             if(!metodosOffset.containsKey(metodo.name)){
    //                 //System.out.println("metodo: " + metodo.name + " offset: " + metodoOffset);
    //                 metodosOffset.put(metodo.name, metodoOffset);
    //                 metodoOffset++;
    //             }
    //         } 
    //         else
    //         {
    //             attr atributo = (attr)feature;
    //             atributos.put(atributo.name, atributo);
    //             //System.out.println("Atributo: " + atributo.name + " offset: " + atributoOffset);
    //             atributosOffset.put(atributo.name, atributoOffset);
    //             atributoOffset++;
    //         }
    //     }

    //     // acceder a los hijos
    //     for(Enumeration hijos = getChildren(); hijos.hasMoreElements();){
    //         CgenNode nodoHijo = (CgenNode)hijos.nextElement();
    //         nodoHijo.offsetFeatures();
    //     }
    // }

    private void initializeMaps() {
        attributesMap = new LinkedHashMap<>();
        methodsMap = new LinkedHashMap<>();
        methodsOffsetMap = new LinkedHashMap<>();
        attributesOffsetMap = new LinkedHashMap<>();
        methodClassMap = new LinkedHashMap<>();
    }
    
    private void initializeOffsets() {
        methodOffsetNumber = 0;
        attributeOffsetNumber = 0;
    }

    public void offsetFeatures() {

        initializeMaps();
        initializeOffsets();

        if(getName() != TreeConstants.Object_) {
            

            methodsOffsetMap.putAll(parent.methodsOffsetMap); 

            methodClassMap.putAll(parent.methodClassMap);

            methodOffsetNumber = parent.methodOffsetNumber;

            attributeOffsetNumber = parent.attributeOffsetNumber;

        }

        for(Enumeration listFeatures = features.getElements(); listFeatures.hasMoreElements();) {
            Feature feature = (Feature)listFeatures.nextElement();

            // verificar si es metodo o attribute
            if(feature instanceof method){
                method metodo = (method)feature;
                methodsMap.put(metodo.name, metodo);
                // aqui relacionamos el metodo con la clase
                methodClassMap.put(metodo.name, getName());

                // verificar que el metodo, no sea heredado, si ya existe
                // quiere decir que es heredado y no debemos alterar su offset
                // o duplicarlo
                if(!methodsOffsetMap.containsKey(metodo.name)){
                    //System.out.println("metodo: " + metodo.name + " offset: " + metodoOffset);
                    methodsOffsetMap.put(metodo.name, methodOffsetNumber);
                    methodOffsetNumber++;
                }
            } 
            else
            {
                attr attribute = (attr)feature;
                attributesMap.put(attribute.name, attribute);
                //System.out.println("attribute: " + attribute.name + " offset: " + attributeOffsetNumber);
                attributesOffsetMap.put(attribute.name, attributeOffsetNumber);
                attributeOffsetNumber++;
            }
        }

        // acceder a los hijos
        for(Enumeration hijos = getChildren(); hijos.hasMoreElements();){
            CgenNode nodoHijo = (CgenNode)hijos.nextElement();
            nodoHijo.offsetFeatures();
        }

    }



    // public void asignarTag() {
        
    //     // asegurar el tag para la recursividad
    //     tag = tagGlobal;
    //     maxTag = tagGlobal;
    //     tagGlobal++;
        
    //     //System.out.println("Clase: " + getName() + " tag:" + tag);
    //     // racceder a los hijos
    //     for(Enumeration hijos = getChildren(); hijos.hasMoreElements();) {
    //         CgenNode nodoHijo = (CgenNode)hijos.nextElement();
    //         // acceder a los hijos y asignarles tag
    //         nodoHijo.asignarTag();
    //         maxTag = nodoHijo.maxTag;
    //     }

    // }

    public void assignTags() {
        
        tag = globalTag;
        maxTag = globalTag;
        globalTag++;
        
        for(Enumeration childrens = getChildren(); childrens.hasMoreElements();) {
            CgenNode child = (CgenNode)childrens.nextElement();
            child.assignTags();
            maxTag = child.maxTag;
        }
    }
    

    // public void codeProtObj(PrintStream s) {

    //     s.println(CgenSupport.WORD + tag);             
    //     s.println(CgenSupport.WORD + ((atributoOffset) + CgenSupport.DEFAULT_OBJFIELDS));  
    //     s.print(CgenSupport.WORD);  
    //     CgenSupport.emitDispTableRef(name, s);  
    //     s.println();                    
    //     recursivoCodeProtObj(s);
    // }

    // public void recursivoCodeProtObj(PrintStream s) {

    //     if(name != TreeConstants.Object_)
    //     {
    //         parent.recursivoCodeProtObj(s);
    //     }
    
    //     for(AbstractSymbol name : atributos.keySet()) {
            
    //         AbstractSymbol type = atributos.get(name).type_decl;
    //         s.print(CgenSupport.WORD);
    //         if(type == TreeConstants.Int)
    //         {
    //             ((IntSymbol)AbstractTable.inttable.addInt(0)).codeRef(s);
    //         }                
    //         else if(type == TreeConstants.Bool)
    //         {
    //             s.print(CgenSupport.BOOLCONST_PREFIX + "0");
    //         }
    //         else if(type == TreeConstants.Str){
    //             ((StringSymbol)AbstractTable.stringtable.addString("")).codeRef(s);
    //         }
    //         else{
    //             s.print("0");
    //         }
    //         s.println();
    //     }   
    // }

    // public void codeInitObj(PrintStream str){

    //     CgenClassTable tablaRef = CgenClassTable.codeGegerationClassTable;

    //     // crear scope para poder accesar a la informacion mas facil
    //     tablaRef.enterScope();

    //     // agregar atributos al scope
    //     // variable para recursion
    //     CgenNode nodo = this;
    //     while(nodo.getName() != TreeConstants.Object_) {
    //         for(AbstractSymbol nombreAtributo : nodo.atributosOffset.keySet()) {

    //             tablaRef.addId(nombreAtributo, new ClassAttr(nodo.atributosOffset.get(nombreAtributo)));

    //         }
    //         nodo = nodo.parent;
    //     }
    //     tablaRef.addId(TreeConstants.SELF_TYPE, this.getName());

    //     // este nodo
    //     // nodeName stringSymbol, getName() devuelve AbstractSymbol
    //     //System.out.println("referencia de this " + this.nodeName);

    //     // codigo para inicialiar objeto, preparar y guardar en stack
    //     CgenSupport.emitInitObjectPrologo(str);

    //     // codigo para inicializador de padre
    //     if(this.getName() != TreeConstants.Object_){
    //         CgenSupport.emitJal(parent.nodeName + CgenSupport.CLASSINIT_SUFFIX, str);
    //     }

    //     // codigo para inicializa atributos del objeto
    //     for(AbstractSymbol nombreAtributo : atributos.keySet()) {
    //         attr atributo = atributos.get(nombreAtributo);
            
    //         // inicializar atributo
    //         if((atributo.init instanceof no_expr) != true) {
    //             // generar codigo de expression - cool-tree.java (pendiente)
    //             atributo.init.code(str);
    //             CgenSupport.emitStore(CgenSupport.ACC, (atributosOffset.get(nombreAtributo) + CgenSupport.DEFAULT_OBJFIELDS), CgenSupport.SELF, str);
                
                
    //         }
    //     }

    //     // regresar a self pointer
    //     CgenSupport.emitMove(CgenSupport.ACC, CgenSupport.SELF, str);
    //     // codigo para restaurar stack
    //     CgenSupport.emitInitObjectRestore(str);

    //     tablaRef.exitScope();

    // }

    public void generateMethodCode(PrintStream str){

        // implementado en runtime.s
        if(this.getName() == TreeConstants.Object_ || this.getName() == TreeConstants.IO ||
           this.getName() == TreeConstants.Int || this.getName() == TreeConstants.Str) 
        {
            
            return;
        }

        CgenClassTable tablaRef = CgenClassTable.codeGegerationClassTable;

        // crear scope para poder accesar a la informacion mas facil
        tablaRef.enterScope();

        // agregar atributos al scope
        // variable para recursion
        CgenNode nodo = this;
        while(nodo.getName() != TreeConstants.Object_) {
            for(AbstractSymbol nombreAtributo : nodo.attributesOffsetMap.keySet()) {

                tablaRef.addId(nombreAtributo, new ClassAttr(nodo.attributesOffsetMap.get(nombreAtributo)));

            }
            nodo = nodo.parent;
        }
        tablaRef.addId(TreeConstants.SELF_TYPE, this.getName());

        // emitir codigo de cada metodo
        for(method metodo : methodsMap.values()) {
                
            CgenSupport.emitMethodRef(this.getName(), metodo.name, str);
            str.print(CgenSupport.LABEL);
            metodo.code(str);

        }

        tablaRef.exitScope();

    }




    public void generatePrototypeObject(PrintStream s) {

        s.println(CgenSupport.WORD + tag);             
        s.println(CgenSupport.WORD + ((attributeOffsetNumber) + CgenSupport.DEFAULT_OBJFIELDS));  
        s.print(CgenSupport.WORD);  
        CgenSupport.emitDispTableRef(name, s);  
        s.println();                    
        generatePrototypeObjectRecursively(s);
    }

    public void generatePrototypeObjectRecursively(PrintStream s) {

        if(name != TreeConstants.Object_)
        {
            parent.generatePrototypeObjectRecursively(s);
        }
    
        for(AbstractSymbol name : attributesMap.keySet()) {
            
            AbstractSymbol type = attributesMap.get(name).type_decl;
            s.print(CgenSupport.WORD);
            if(type == TreeConstants.Int)
            {
                ((IntSymbol)AbstractTable.inttable.addInt(0)).codeRef(s);
            }                
            else if(type == TreeConstants.Bool)
            {
                s.print(CgenSupport.BOOLCONST_PREFIX + "0");
            }
            else if(type == TreeConstants.Str){
                ((StringSymbol)AbstractTable.stringtable.addString("")).codeRef(s);
            }
            else{
                s.print("0");
            }
            s.println();
        }   
    }

    public void generateObjectInitializer(PrintStream str){

        CgenClassTable cGClassTable = CgenClassTable.codeGegerationClassTable;

        // crear scope para poder accesar a la informacion mas facil
        cGClassTable.enterScope();

        // agregar attributesMap al scope
        // variable para recursion
        CgenNode node = this;
        while(node.getName() != TreeConstants.Object_) {
            for(AbstractSymbol attributeName : node.attributesMap.keySet()) {

                cGClassTable.addId(attributeName, new ClassAttr(node.attributesOffsetMap.get(attributeName)));

            }
            node = node.parent;
        }
        cGClassTable.addId(TreeConstants.SELF_TYPE, this.getName());

        // este node
        // nodeName stringSymbol, getName() devuelve AbstractSymbol
        //System.out.println("referencia de this " + this.nodeName);

        // codigo para inicialiar objeto, preparar y guardar en stack
        CgenSupport.emitObjectInitPrologue(str);

        // codigo para inicializador de padre
        if(this.getName() != TreeConstants.Object_){
            CgenSupport.emitJal(parent.nodeName + CgenSupport.CLASSINIT_SUFFIX, str);
        }

        // codigo para inicializa attributesMap del objeto
        for(AbstractSymbol attributeName : attributesMap.keySet()) {
            attr attribute = attributesMap.get(attributeName);
            
            // inicializar attribute
            if((attribute.init instanceof no_expr) != true) {
                // generar codigo de expression - cool-tree.java (pendiente)
                attribute.init.code(str);
                CgenSupport.emitStore(CgenSupport.ACC, (attributesOffsetMap.get(attributeName) + CgenSupport.DEFAULT_OBJFIELDS), CgenSupport.SELF, str);
                
                
            }
        }

        // regresar a self pointer
        CgenSupport.emitMove(CgenSupport.ACC, CgenSupport.SELF, str);
        // codigo para restaurar stack
        CgenSupport.emitStackRestore(str);

        cGClassTable.exitScope();

    }


}

