package com.cc.web1.provider.filelistener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileListenConfiguration {

    public FileListenConfiguration() {
        System.out.println("FileListenConfiguration init");
    }

    @Bean(initMethod = "start")
    public FileMonitor fileMonitor(@Autowired MapperReloader mapperReloader) throws Exception {
        System.out.println("FileListenConfiguration fileMonitor 1");

        FileMonitor fileMonitor = new FileMonitor(1000);
        fileMonitor.monitor("D:\\JetBrains\\IDEA\\IdeaProjects\\ddd\\project_web1\\project_web1_provider\\target\\classes\\com\\cc\\web1\\provider\\mapper",
                new FileListener(mapperReloader));
        // fileMonitor.start();
        System.out.println("FileListenConfiguration fileMonitor 2");
        return fileMonitor;
    }
}
