package com.cc.annoprocessor;

import com.alibaba.dubbo.common.json.JSON;
import com.cc.annoprocessor.Test1Annotation;
import lombok.Setter;

@Setter
@Test1Annotation(value = "aabb")
public class Tests {

    private String user;

    public java.lang.String setName(String name) {
        if (name == null) throw new java.lang.NullPointerException("name is null");
        return name;
    }
    public static void main(String[] args) {

        TestMySetter testMySetter = new TestMySetter();
//        testMySetter.setName("aabb");

//        new Tests().setUser("xx");

//        test();
    }

    private static void test(String s) {
        if (s == null) {
            throw new NullPointerException("abc");
        }
        System.out.println("Tests test");
    }
}
// -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005
// N:\Java8\bin\java.exe -Dmaven.multiModuleProjectDirectory=N:\JavaTools\IdeaProjects\spring_boot\project_1\common -Dmaven.home=N:\JavaTools\apache-maven-3.3.9 -Dclassworlds.conf=N:\JavaTools\apache-maven-3.3.9\bin\m2.conf "-Dmaven.ext.class.path=N:\JavaTools\IntelliJ IDEA 2020.1\plugins\maven\lib\maven-event-listener.jar" "-javaagent:N:\JavaTools\IntelliJ IDEA 2020.1\lib\idea_rt.jar=63428:N:\JavaTools\IntelliJ IDEA 2020.1\bin" -Dfile.encoding=UTF-8 -classpath N:\JavaTools\apache-maven-3.3.9\boot\plexus-classworlds-2.5.2.jar org.codehaus.classworlds.Launcher -Didea.version2020.1 -s N:\JavaTools\apache-maven-3.3.9\conf\settings.xml -Dmaven.repo.local=N:\JavaTools\MavenRepository -DskipTests=true install
//