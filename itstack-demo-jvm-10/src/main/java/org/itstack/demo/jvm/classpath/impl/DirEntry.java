package org.itstack.demo.jvm.classpath.impl;

import org.itstack.demo.jvm.classfile.ClassInfo;
import org.itstack.demo.jvm.classpath.Entry;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/24
 * 目录形式的类路径
 */
public class DirEntry implements Entry {

    private Path absolutePath;

    public DirEntry(String path) {
        //获取绝对路径
        this.absolutePath = Paths.get(path).toAbsolutePath();
    }

    @Override
    public byte[] readClass(String className) throws IOException {
        File classFile = absolutePath.resolve(className).toFile();
        classFile.length();
        classFile.lastModified();

        return Files.readAllBytes(absolutePath.resolve(className));
    }

    @Override
    public ClassInfo readClass2ClassInfo(String className) throws IOException {
        byte[] classData = Files.readAllBytes(absolutePath.resolve(className));
        File classFile = absolutePath.resolve(className).toFile();
        return new ClassInfo(classData, classFile);
    }

    @Override
    public String toString() {
        return this.absolutePath.toString();
    }


}
