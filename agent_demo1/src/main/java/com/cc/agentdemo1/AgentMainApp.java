package com.cc.agentdemo1;

import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class AgentMainApp {

    //采用attach机制，被代理的目标程序VM有可能很早之前已经启动，当然其所有类已经被加载完成，
    // 这个时候需要借助Instrumentation#retransformClasses(Class<?>... classes)让对应的类可以重新转换，
    // 从而激活重新转换的类执行ClassFileTransformer列表中的回调

    // 同样，agentmain 方法中带Instrumentation参数的方法也比不带优先级更高。开发者必须在 manifest 文件里面设置“Agent-Class”来指定包含 agentmain 函数的类。
//    public static void agentmain(String args, Instrumentation instrumentation) throws Exception {
//        System.out.println("agentmain args = " + args + ", instrumentation = " + instrumentation);
//        instrumentation.addTransformer((ClassLoader loader,
//                                        String className,
//                                        Class<?> classBeingRedefined,
//                                        ProtectionDomain protectionDomain,
//                                        byte[] classfileBuffer) -> {
//            System.out.println("agentmain className = " + className);
//
//            return classfileBuffer;
//        });
//
//        instrumentation.retransformClasses(Class.forName("org.mybatis.spring.SqlSessionFactoryBean"));
//    }
}
