package com.lulu.classloader;

class FDD {
    static {
        System.out.println("我是静态代码块。。。。");
    }
}
class FDDloaderTest {
    public static void main(String[] args) throws ClassNotFoundException {
        ClassLoader loader = FDD.class.getClassLoader();
        System.out.println(loader);
        //一、 使用ClassLoader.loadClass()来加载类，不会执行初始化块
        loader.loadClass("com.lulu.classloader.FDD");
        // 二、 使用Class.forName()来加载类，默认会执行初始化块
        Class.forName("com.lulu.classloader.FDD");
        // 三、使用Class.forName()来加载类，指定ClassLoader，初始化时不执行静态块 Class.forName("Fdd", false, loader);
        Class.forName("com.lulu.classloader.FDD", false, loader);
    }
}