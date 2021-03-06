package com.cc.agent;

import com.sun.tools.attach.*;

import java.io.IOException;
import java.util.List;

/**
 * @author rickiyang
 * @date 2019-08-16
 * @Desc
 */
public class AgentMainAppTests {

    public static void main(String[] args) throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        //获取当前系统中所有 运行中的 虚拟机
        System.out.println("TestAgentMain main");
        List<VirtualMachineDescriptor> list = VirtualMachine.list();
        for (VirtualMachineDescriptor vmd : list) {
            //如果虚拟机的名称为 xxx 则 该虚拟机为目标虚拟机，获取该虚拟机的 pid
            //然后加载 agent.jar 发送给该虚拟机
            System.out.println("vmd.displayName = " + vmd.displayName());
            if (vmd.displayName().endsWith("com.cc.agent.AgentTests")) {
                System.out.println("vmd.displayName = " + vmd.displayName() + ", attach");
                VirtualMachine virtualMachine = VirtualMachine.attach(vmd.id());
                virtualMachine.loadAgent("N:\\JavaTools\\IdeaProjects\\spring_boot\\project_1\\common\\target\\common-1.0.0-SNAPSHOT.jar");
                virtualMachine.detach();
                System.out.println("vmd.displayName = " + vmd.displayName() + ", detach");
            }
        }
    }

}