package com.cc;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.LoaderClassPath;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.nio.charset.Charset;
import java.security.ProtectionDomain;

public class AgentApplication {

    public static void premain(String arg, Instrumentation instrumentation) {
        System.out.println("agent premain , args = " + arg);

        // 注册我们的文件下载函数
        instrumentation.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String classFile, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classFileBuffer) throws IllegalClassFormatException {
                if (classFile.equals("com/huawei/AgentTests")) {
                    System.out.println("loader = " + loader);
                    System.out.println("classFile = " + classFile);
                    System.out.println("classBeingRedefined = " + classBeingRedefined);
                    System.out.println("protectionDomain = " + protectionDomain);
                    System.out.println("classFileBuffer = " + classFileBuffer);

                    try {
//                        // 加载 javassist 类池
//                        ClassPool cp = ClassPool.getDefault();
//
//                        // 因为 Jenkins 是 WAR 包发布，还需要把 loader 补充进去，不然下面 javassist 解析字节码会报告找不到 import 的类
//                        //cp.appendClassPath(new LoaderClassPath(loader));
//
//                        // 把字节码载入进去，生成 CtClass
//                        CtClass cc = cp.makeClass(new ByteArrayInputStream(classFileBuffer));
//
//                        // 定位到方法 verifySignature
//                        CtMethod cm = cc.getDeclaredMethod("main");
//                        System.out.println(cm);


                        // 修改方法代码体
                        // cm.setBody("{ System.out.println(\"main agent\"); }");

                        ClassPool classPool = new ClassPool();
                        classPool.insertClassPath(new LoaderClassPath(loader));
                        CtClass ctClass = classPool.get(classFile.replace("/", "."));
                        CtMethod ctMethod = ctClass.getDeclaredMethod("main");

                        //插入本地变量
                        ctMethod.addLocalVariable("begin", CtClass.longType);
                        ctMethod.addLocalVariable("end", CtClass.longType);

                        ctMethod.insertBefore("begin=System.currentTimeMillis();System.out.println(\"begin=\"+begin);");
                        //前面插入：最后插入的放最上面
                        ctMethod.insertBefore("System.out.println( \"埋点开始-1\" );");

                        ctMethod.insertAfter("end=System.currentTimeMillis();System.out.println(\"end=\"+end);");
                        ctMethod.insertAfter("System.out.println(\"性能:\"+(end-begin)+\"毫秒\");");

                        //后面插入：最后插入的放最下面
                        ctMethod.insertAfter("System.out.println( \"埋点结束-1\" );");


                        // 返回重新编译的字节码
                        byte[] bytes = ctClass.toBytecode();
                        System.out.println(new String(bytes, Charset.defaultCharset()));
                        return bytes;

                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }

                return null;
            }
        });
    }
}