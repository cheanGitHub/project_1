package com.cc.agentdemo1;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.LoaderClassPath;
import sun.net.www.protocol.jar.URLJarFile;

import java.io.File;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.ProtectionDomain;

public class PremainApp {

    public static void premain(String arg, Instrumentation instrumentation) throws Exception {
        System.out.println("agent premain , args = " + arg + ", instrumentation = " + instrumentation);

        String file = PremainApp.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        URLJarFile urlJarFile = new URLJarFile(new File(file));
        instrumentation.appendToSystemClassLoaderSearch(urlJarFile);

        // 注册
        instrumentation.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String classFile, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classFileBuffer) {
                // return getBytesAgentTests(loader, classFile, classBeingRedefined, protectionDomain, classFileBuffer);
                return getBytesSqlSessionFactoryBean(loader, classFile, classBeingRedefined, protectionDomain, classFileBuffer);
            }
        });
    }

    /**
     * 修改类：org.mybatis.spring.SqlSessionFactoryBean#getObject
     * 实现热加载 mybatis mapper
     */
    private static byte[] getBytesSqlSessionFactoryBean(ClassLoader loader, String classFile, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classFileBuffer) {
//        if (classPool == null) {
//            classPool = new ClassPool(); // ClassPool.getDefault(); //
//        }
//
//        if (classFile.replaceAll("/", ".").equals(mainClass)) {
//            try {
////                String classPath = protectionDomain.getCodeSource().getLocation().getFile().replaceFirst("/", "") + "com/cc/web1/provider/filelistener/";
////                System.out.println("protectionDomain classPath = " + classPath);
////                new File(classPath).mkdirs();
//
////                copyClassFile("/fileListener/FileListenConfiguration.class", classPath + "FileListenConfiguration.class");
////                copyClassFile("/fileListener/FileListener.class", classPath + "FileListener.class");
////                copyClassFile("/fileListener/FileMonitor.class", classPath + "FileMonitor.class");
////                copyClassFile("/fileListener/MapperReloader.class", classPath + "MapperReloader.class");
//
//                System.out.println("loader 1 = " + loader);
//                classPool.insertClassPath(new LoaderClassPath(loader));
////                CtClass ctClass = classPool.get(classFile.replace("/", "."));
////                return ctClass.toBytecode();
//                return classFileBuffer;
//            } catch (Throwable e) {
//                e.printStackTrace();
//            }
//            return null;
//        }

        if (classFile.equals("org/mybatis/spring/SqlSessionFactoryBean")) {
            System.out.println("loader 2 = " + loader);
            System.out.println("classFile = " + classFile);
            System.out.println("classBeingRedefined = " + classBeingRedefined);
            System.out.println("protectionDomain = " + protectionDomain);
            System.out.println("classFileBuffer = " + classFileBuffer);

            try {
                ClassPool classPool = new ClassPool();
                classPool.insertClassPath(new LoaderClassPath(loader));

                CtClass ctClass = classPool.get(classFile.replace("/", "."));
                CtMethod ctMethod = ctClass.getDeclaredMethod("getObject");
                ctMethod.insertBefore("System.out.println(\"before set = \" + com.cc.web1.provider.filelistener.MapperReloader.sqlSessionFactoryBean );");
                ctMethod.insertBefore("com.cc.web1.provider.filelistener.MapperReloader.sqlSessionFactoryBean = this;");
                ctMethod.insertBefore("System.out.println(\"after set = \" + com.cc.web1.provider.filelistener.MapperReloader.sqlSessionFactoryBean );");

                // 返回重新编译的字节码
                byte[] bytes = ctClass.toBytecode();
//                System.out.println("after bytes = " + new String(bytes, Charset.defaultCharset()));
//                Files.write(Paths.get("D:\\JetBrains\\IDEA\\IdeaProjects\\ddd\\project_web1\\docs\\SqlSessionFactoryBean.class"),
//                        bytes, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
                return bytes;

            } catch (Throwable e) {
                System.out.println("getBytesSqlSessionFactoryBean = 100");
                e.printStackTrace();
            }
        }
        return null;
    }

    private static void copyClassFile(String source, String dest) throws Exception {
//        Files.write(
//                Paths.get(dest),
//                IOUtils.readFully(PremainApp.class.getResourceAsStream(source), -1, true));
//        classPool.makeClas(PremainApp.class.getResourceAsStream(source)).toClass();

//                System.out.println("ctClass1.getPackageName() = " + ctClass1.getPackageName());
//                System.out.println("ctClass1.getName() = " + ctClass1.getName());
//                Class aClass1 = ctClass1.toClass();
//                //Class<?> aClass1 = loader.loadClass(ctClass1.getName());
//                System.out.println("aClass1 = " + aClass1);
//                System.out.println("aClass1.getPackage() = " + aClass1.getPackage());
//                System.out.println("aClass1.getAnnotations() = " + Arrays.toString(aClass1.getAnnotations()));
//                //Class<?> clazz = new Loader(classPool).loadClass("com.zhi.Person");
    }

    /**
     * 修改 AgentTests
     */
    private static byte[] getBytesAgentTests(ClassLoader loader, String classFile, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classFileBuffer) {
        if (classFile.equals("com/cc/agent/AgentTests")) {
            System.out.println("loader = " + loader);
            System.out.println("classFile = " + classFile);
            System.out.println("classBeingRedefined = " + classBeingRedefined);
            System.out.println("protectionDomain = " + protectionDomain);
            System.out.println("classFileBuffer = " + classFileBuffer);

            try {
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

                Path path = Paths.get("N:\\JavaTools\\IdeaProjects\\spring_boot\\project_1\\docs\\agent_demo1\\AgentTests.class");
                Files.deleteIfExists(path);
                Files.write(path, bytes, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);

                return bytes;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


}