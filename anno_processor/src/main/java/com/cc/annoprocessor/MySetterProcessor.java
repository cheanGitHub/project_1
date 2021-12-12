package com.cc.annoprocessor;

import com.google.common.collect.Lists;
import com.sun.source.tree.Tree;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.code.TypeTag;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.*;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.LinkedHashSet;
import java.util.Set;

//@AutoService(Processor.class)
public class MySetterProcessor extends AbstractProcessor {

    /**
     * 文件相关的辅助类 用于生成新的源文件、class等
     */
//    private Filer mFiler;
//    @Override
//    public synchronized void init(ProcessingEnvironment processingEnv) {
//        super.init(processingEnv);
//        mFiler = processingEnv.getFiler();
//    }
    // @Override
    public boolean processAA(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(MySetter.class);
//        for (Element e : elements) {
//            // 构建方法 此处使用到了square公司的javapoet库，用来辅助生成 类的代码
//            MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("show")
//                    .addModifiers(Modifier.PUBLIC);
//            methodBuilder.addStatement("String test = \"$N\" ", "hello annotation world!");
//
////            TypeElement typeElement = ((TypeElement)elements.toArray()[0]);

////            JavaFile.builder("", TypeSpec.);
//
//            构建类 */
//            TypeSpec finderClass = TypeSpec.classBuilder("Hello$$Inject")
//                    .addModifiers(Modifier.PUBLIC)
//                    .addMethod(methodBuilder.build())
//                    .build();
//            try {
//                JavaFile.builder("com.win.test", finderClass).build().writeTo(mFiler);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

        // TreeMaker.instance(null).Exec()

        System.out.println("elements = " + elements);

        return false;
    }


    private Messager messager;
    private JavacTrees javacTrees;
    private TreeMaker treeMaker;
    private Names names;

    /**
     * @Description: 1. Message 主要是用来在编译时期打log用的
     * 2. JavacTrees 提供了待处理的抽象语法树
     * 3. TreeMaker 封装了创建AST节点的一些方法
     * 4. Names 提供了创建标识符的方法
     */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.messager = processingEnv.getMessager();
        this.javacTrees = JavacTrees.instance(processingEnv);
        Context context = ((JavacProcessingEnvironment) processingEnv).getContext();
        this.treeMaker = TreeMaker.instance(context);
        this.names = Names.instance(context);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println("MySetterProcessor process annotations = " + Lists.newArrayList(annotations));
        System.out.println("MySetterProcessor process roundEnv = " + roundEnv);

        Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(MySetter.class);
        System.out.println("-------------------- elements = " + elementsAnnotatedWith);

        elementsAnnotatedWith.forEach(e -> {
            JCTree tree = javacTrees.getTree(e);
            tree.accept(new TreeTranslator() {
                @Override
                public void visitClassDef(JCTree.JCClassDecl jcClassDecl) {
                    List<JCTree.JCVariableDecl> jcVariableDeclList = List.nil();

                    // 在抽象树中找出所有的变量
                    for (JCTree jcTree : jcClassDecl.defs) {
                        if (jcTree.getKind().equals(Tree.Kind.VARIABLE)) {
                            JCTree.JCVariableDecl jcVariableDecl = (JCTree.JCVariableDecl) jcTree;
                            jcVariableDeclList = jcVariableDeclList.append(jcVariableDecl);
                        }
                    }

                    // 对于变量进行生成方法的操作
                    jcVariableDeclList.forEach(jcVariableDecl -> {
                        messager.printMessage(Diagnostic.Kind.NOTE, jcVariableDecl.getName() + "has been processed");
                         jcClassDecl.defs = jcClassDecl.defs.prepend(makeSetterMethodDecl(jcVariableDecl));
                         jcClassDecl.defs = jcClassDecl.defs.prepend(makeGetterMethodDecl(jcVariableDecl));
                    });
                    super.visitClassDef(jcClassDecl);
                }
            });
        });

        return true;
    }
    private JCTree.JCMethodDecl makeSetterMethodDecl111(JCTree.JCVariableDecl jcVariableDecl){

        ListBuffer<JCTree.JCStatement> statements = new ListBuffer<>();
        // 生成表达式 例如 this.a = a;
        JCTree.JCExpressionStatement aThis = makeAssignment(treeMaker.Select(treeMaker.Ident(names.fromString("this")), jcVariableDecl.getName()), treeMaker.Ident(jcVariableDecl.getName()));
        statements.append(aThis);
        JCTree.JCBlock block = treeMaker.Block(0, statements.toList());

        // 生成入参
        JCTree.JCVariableDecl param = treeMaker.VarDef(treeMaker.Modifiers(Flags.PARAMETER), jcVariableDecl.getName(), jcVariableDecl.vartype, null);
        param.pos = jcVariableDecl.pos;
        List<JCTree.JCVariableDecl> parameters = List.of(param);

        // 生成返回对象
        JCTree.JCExpression methodType = treeMaker.Type(new Type.JCVoidType());
        // treeMaker.pos = jcVariableDecl.pos;
        return treeMaker.MethodDef(treeMaker.Modifiers(Flags.PUBLIC),getNewSetMethodName(jcVariableDecl.getName()),methodType,List.nil(),parameters,List.nil(),block,null);
    }

    private JCTree.JCMethodDecl makeSetterMethodDecl(JCTree.JCVariableDecl jcVariableDecl) {
        ListBuffer<JCTree.JCStatement> statements = new ListBuffer<>();

        // if (xx == null) {throw new NullPointerException("aa");} else {   }
        JCTree.JCIf jcIf = treeMaker.If(
                treeMaker.Binary(
                        JCTree.Tag.EQ,
                        treeMaker.Ident(names.fromString("name")),
                        treeMaker.Literal(TypeTag.BOT, null) // treeMaker.Literal(10)
                ),
                treeMaker.Throw(
                        treeMaker.NewClass(null,
                                List.nil(), // List.of(memberAccess("java.lang.String")),
                                memberAccess("java.lang.NullPointerException"),
                                List.of(treeMaker.Literal(jcVariableDecl.getName() + " is null")),
                                null
                        )
                ),
                null // treeMaker.Skip()
        );
        statements.append(jcIf);

        // 生成表达式 例如 this.a = a;
        JCTree.JCExpressionStatement aThis = makeAssignment(
                treeMaker.Select(treeMaker.Ident(names.fromString("this")), jcVariableDecl.getName()),
                treeMaker.Ident(jcVariableDecl.getName()));
        statements.append(aThis);

        // return name
        statements.append(treeMaker.Return(treeMaker.Ident(names.fromString("name"))));
        // treeMaker.Select(treeMaker.Ident(names.fromString("this")), jcVariableDecl.getName())));

        // 语句块
        JCTree.JCBlock block = treeMaker.Block(0, statements.toList());

        // 入参列表
        JCTree.JCVariableDecl param = treeMaker.VarDef(treeMaker.Modifiers(Flags.PARAMETER), jcVariableDecl.getName(), jcVariableDecl.vartype, null);
        System.out.println("param.pos = " + param.pos + ", jcVariableDecl.pos = " + jcVariableDecl.pos + ", treeMaker.pos = " + treeMaker.pos);
        param.pos = jcVariableDecl.pos;//设置形参这一句不能少，不然会编译报错(java.lang.AssertionError: Value of x -1)
        List<JCTree.JCVariableDecl> parameters = List.of(param);

        // 返回类型
//        JCTree.JCExpression methodType = treeMaker.Type(new Type.JCVoidType());
        JCTree.JCExpression methodType = treeMaker.Type(param.vartype.type);


        return treeMaker.MethodDef(treeMaker.Modifiers(Flags.PUBLIC),
                getNewSetMethodName(jcVariableDecl.getName()),
                methodType, List.nil(), parameters, List.nil(), block, null);
    }

    private JCTree.JCMethodDecl makeGetterMethodDecl(JCTree.JCVariableDecl jcVariableDecl) {
        ListBuffer<JCTree.JCStatement> statements = new ListBuffer<>();

        // return xxx;
        statements.append(treeMaker.Return(treeMaker.Ident(names.fromString("name"))));
        JCTree.JCBlock block = treeMaker.Block(0, statements.toList());

        // 生成入参
        JCTree.JCVariableDecl param = treeMaker.VarDef(treeMaker.Modifiers(Flags.PARAMETER), jcVariableDecl.getName(), jcVariableDecl.vartype, null);
//        List<JCTree.JCVariableDecl> parameters = List.of(param);

        JCTree.JCExpression methodType = treeMaker.Type(param.vartype.type);
        return treeMaker.MethodDef(treeMaker.Modifiers(Flags.PUBLIC),
                getNewGetMethodName(jcVariableDecl.getName()),
                methodType, List.nil(), List.nil(), List.nil(), block, null);

    }

    private Name getNewSetMethodName(Name name) {
        String s = name.toString();
        return names.fromString("set" + s.substring(0, 1).toUpperCase() + s.substring(1, name.length()));
    }

    private Name getNewGetMethodName(Name name) {
        String s = name.toString();
        return names.fromString("get" + s.substring(0, 1).toUpperCase() + s.substring(1, name.length()));
    }

    private JCTree.JCExpressionStatement makeAssignment(JCTree.JCExpression lhs, JCTree.JCExpression rhs) {
        return treeMaker.Exec(
                treeMaker.Assign(
                        lhs,
                        rhs
                )
        );
    }

    private JCTree.JCExpression memberAccess(String components) {
        String[] componentArray = components.split("\\.");
        JCTree.JCExpression expr = treeMaker.Ident(names.fromString(componentArray[0]));
        for (int i = 1; i < componentArray.length; i++) {
            expr = treeMaker.Select(expr, names.fromString(componentArray[i]));
        }
        return expr;
    }

    // 支持的注解类型
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(MySetter.class.getCanonicalName());
        return types;
    }
}