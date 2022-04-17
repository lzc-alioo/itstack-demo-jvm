package org.itstack.demo.jvm.classfile;

import org.itstack.demo.jvm.util.Md5Util;

import java.io.File;

/**
 * @author: 悟心
 * @time: 2022/4/17 12:04
 * @description:
 */
public class ClassInfo {

    public byte[] classData;
    public long lastModified;
    public long length;
    public String md5;
    public String classFromPath;

    public ClassInfo(byte[] classData, File classFile) {
        this.classData = classData;
        this.lastModified = classFile.lastModified();
        this.length = classFile.length();
        this.classFromPath = classFile.getPath();
        this.md5 = Md5Util.encode(classData);

    }
}
