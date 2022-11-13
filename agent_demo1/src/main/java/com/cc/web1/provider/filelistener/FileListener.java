package com.cc.web1.provider.filelistener;

import com.cc.web1.provider.filelistener.MapperReloader;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;

/**
 * https://www.bilibili.com/read/cv17388512/ 出处：bilibili
 */
public class FileListener extends FileAlterationListenerAdaptor {

    private MapperReloader mapperReloader;

    public FileListener(MapperReloader mapperReloader) {
        this.mapperReloader = mapperReloader;
    }

    @Override
    public void onStart(FileAlterationObserver observer) {
        super.onStart(observer);
//        System.out.println("onStart");
    }

    @Override
    public void onDirectoryCreate(File directory) {
        System.out.println("onDirectoryCreate = " + directory.getAbsolutePath());
    }

    @Override
    public void onDirectoryChange(File directory) {
        System.out.println("onDirectoryChange = " + directory.getAbsolutePath());
    }

    @Override
    public void onDirectoryDelete(File directory) {
        System.out.println("onDirectoryDelete = " + directory.getAbsolutePath());
    }

    @Override
    public void onFileCreate(File file) {
        String compressedPath = file.getAbsolutePath();
        System.out.println("onFileCreate = " + compressedPath);
    }

    @Override
    public void onFileChange(File file) {
        String compressedPath = file.getAbsolutePath();
        System.out.println("onFileChange = " + compressedPath);

        mapperReloader.reloadMapper();
    }

    @Override
    public void onFileDelete(File file) {
        System.out.println("onFileDelete = " + file.getAbsolutePath());
    }

    @Override
    public void onStop(FileAlterationObserver observer) {
        super.onStop(observer);
//        System.out.println("onStop");
    }
}