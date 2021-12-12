package com.cc.annoprocessor;

import com.google.common.collect.Lists;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.Name;
import com.sun.tools.javac.util.Names;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

/**
 * https://blog.csdn.net/jinlong59421/article/details/106463565
 */
@SupportedAnnotationTypes(value = {"com.cc.annoprocessor.Test1Annotation"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class Test1AnnotationProcessor extends AbstractProcessor {

    private static boolean init = false;

    private Messager messager;
    private JavacTrees trees;
    private Context context;
    private TreeMaker treeMaker;
    private Names names;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        trees = JavacTrees.instance(processingEnv);
        context = ((JavacProcessingEnvironment) processingEnv).getContext();
        treeMaker = TreeMaker.instance(context);
        names = Names.instance(context);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println("Test1AnnotationProcessor process annotations = " + Lists.newArrayList(annotations));
        System.out.println("Test1AnnotationProcessor process roundEnv = " + roundEnv);

//        new JavacFiler();
//        TreeMaker
//        JCTree.JCMethodDecl
        JCTree.JCVariableDecl var = makeVarDef(treeMaker.Modifiers(0), "xiao", memberAccess("java.lang.String"), treeMaker.Literal("methodName"));
        makeAssignment(treeMaker.Ident(getNameFromString("xiao")), treeMaker.Literal("assignment test"));
        treeMaker.Exec(
                treeMaker.Assign(treeMaker.Ident(getNameFromString("xiao")),
                        treeMaker.Binary(
                                JCTree.Tag.PLUS,
                                treeMaker.Literal("-Binary operator one"),
                                treeMaker.Literal("-Binary operator two")
                        ))
        );
        treeMaker.Exec(
                treeMaker.Assignop(
                        JCTree.Tag.PLUS_ASG,
                        treeMaker.Ident(getNameFromString("xiao")),
                        treeMaker.Literal("-Assignop test")
                )
        );
        JCTree.JCVariableDecl jcVariableDecl = makeVarDef(treeMaker.Modifiers(0), "zhen", memberAccess("java.lang.Integer"), treeMaker.Literal(1));

        try {
            if (!init) {
                JavaFileObject source = processingEnv.getFiler().createSourceFile(
                        "com.cc.annoprocessor.generated.TestGene");
                Writer writer = source.openWriter();



//                writer.write("public class TestGene {\n" +
//                        "    \n" +
//                        "}");
//                writer.flush();
//                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        init = true;

        Set<? extends Element> rootElements = roundEnv.getRootElements();
//        Iterator<? extends Element> iterator = rootElements.iterator();
//        while (iterator.hasNext()) {
//            Element next = iterator.next();
//            System.out.println(" -- " + ((TypeElement)next).getQualifiedName());
//        }
        return rootElements.size() == 1 && ((TypeElement) rootElements.toArray(new Element[0])[0]).getQualifiedName().equals(Test1Annotation.class.getTypeName());
    }

    // https://blog.csdn.net/A_zhenzhen/article/details/86065063
    private JCTree.JCVariableDecl makeVarDef(JCTree.JCModifiers modifiers, String name, JCTree.JCExpression vartype, JCTree.JCExpression init) {
        return treeMaker.VarDef(
                modifiers,
                getNameFromString(name), //名字
                vartype, //类型
                init //初始化语句
        );
    }

    private Name getNameFromString(String s) {
        return names.fromString(s);
    }

    private JCTree.JCExpression memberAccess(String components) {
        String[] componentArray = components.split("\\.");
        JCTree.JCExpression expr = treeMaker.Ident(getNameFromString(componentArray[0]));
        for (int i = 1; i < componentArray.length; i++) {
            expr = treeMaker.Select(expr, getNameFromString(componentArray[i]));
        }
        return expr;
    }

    private JCTree.JCExpressionStatement makeAssignment(JCTree.JCExpression lhs, JCTree.JCExpression rhs) {
        return treeMaker.Exec(
                treeMaker.Assign(
                        lhs,
                        rhs
                )
        );
    }

}
