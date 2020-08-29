package com.lulu.classloader;

import org.w3c.dom.ls.LSOutput;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;

public class MyClassLoader extends ClassLoader {

    private String libPath;

    public MyClassLoader(String path) {
        libPath = path;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String fileName = getFileName(name);
        File file = new File(libPath, fileName);
        try {
            FileInputStream is = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int len = 0;
            try {
                while ((len = is.read()) != -1) {
                    bos.write(len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] data = bos.toByteArray();
            is.close();
            bos.close();
            return defineClass(name, data, 0, data.length);//获取要加载 的class文件名
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.findClass(name);
    }

    private String getFileName(String name) {
        int index = name.lastIndexOf('.');
        if (index == -1) {
            return name + ".class";
        } else {
            return name.substring(index + 1) + ".class";
        }
    }


    public static void main(String[] args) throws Exception {
        MyClassLoader loader = new MyClassLoader("res/JarTest");
        Class<?> testJarClass = loader.loadClass("TestJarClass");
        Object o = testJarClass.newInstance();
        Method testPrint = testJarClass.getMethod("testPrint");
        testPrint.invoke(o);
    }
}
