package com.cc.web1.provider.filelistener;

import com.cc.web1.provider.filelistener.FileListener;
import com.cc.web1.provider.filelistener.MapperReloader;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;

public class FileMonitor {

    public static void main(String[] args) throws Exception {
        FileMonitor fileMonitor = new FileMonitor(1000);
        fileMonitor.monitor("D:\\JetBrains\\IDEA\\IdeaProjects\\ddd\\project_web1\\project_web1_provider\\target\\classes\\com\\cc\\web1\\provider\\mapper",
                new FileListener(new MapperReloader()));
        fileMonitor.start();
    }

    private FileAlterationMonitor monitor;

    public FileMonitor(long interval) {
        monitor = new FileAlterationMonitor(interval);
    }

    /**
     * 给文件添加监听
     *
     * @param path     文件路径
     * @param listener 文件监听器
     */
    public void monitor(String path, FileAlterationListener listener) {
        FileAlterationObserver observer = new FileAlterationObserver(new File(path));
        monitor.addObserver(observer);
        observer.addListener(listener);
    }

    public void stop() throws Exception {
        monitor.stop();
    }

    public void start() throws Exception {
        monitor.start();
    }
}