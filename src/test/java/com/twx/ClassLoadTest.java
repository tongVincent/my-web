package com.twx;

import com.twx.test.util.MessageUtil;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.StringTokenizer;

/**
 * @author wenxin.tong
 * @since 2018/4/25
 */
public class ClassLoadTest extends BaseTest {

    /**
     * 测试扩展类加载器的加载位置（在java_home下的/jre/lib/ext下），
     * 以及各种类加载器的父类加载器
     */
    @Test
    public void test001() {
        String s = System.getProperty("java.ext.dirs");
        File[] dirs;
        if (s != null) {
            StringTokenizer st =
                new StringTokenizer(s, File.pathSeparator);
            int count = st.countTokens();
            dirs = new File[count];
            for (int i = 0; i < count; i++) {
                dirs[i] = new File(st.nextToken());
            }
        } else {
            dirs = new File[0];
        }
        MessageUtil.onTime(dirs);

        System.out.println("系统默认的AppClassLoader: " + ClassLoader.getSystemClassLoader());
        System.out.println("AppClassLoader的父类加载器: " + ClassLoader.getSystemClassLoader().getParent());
        System.out.println("ExtClassLoader的父类加载器: " + ClassLoader.getSystemClassLoader().getParent().getParent());
    }

    /**
     * 测试java.lang.Class#getClassLoader返回的加载器，返回的是定义加载器。
     * @throws ClassNotFoundException
     */
    @Test
    public void test002() throws ClassNotFoundException {
        FileSystemClassLoader loader = new FileSystemClassLoader("");
        Class<?> clz = loader.loadClass("com.twx.ClassTest");
        MessageUtil.onTime(clz.getClassLoader());
        clz = loader.loadClass("com.twx.ClassTest");
        MessageUtil.onTime(clz.getClassLoader());
    }
}

class FileSystemClassLoader extends ClassLoader {

    private String rootDir;

    public FileSystemClassLoader(String rootDir) {
        this.rootDir = rootDir;
    }

    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classData = getClassData(name);
        if (classData == null) {
            throw new ClassNotFoundException();
        } else {
            return defineClass(name, classData, 0, classData.length);
        }
    }

    private byte[] getClassData(String className) {
        String path = classNameToPath(className);
        try {
            InputStream ins = new FileInputStream(path);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int bufferSize = 4096;
            byte[] buffer = new byte[bufferSize];
            int bytesNumRead = 0;
            while ((bytesNumRead = ins.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesNumRead);
            }
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String classNameToPath(String className) {
        return rootDir + File.separatorChar
            + className.replace('.', File.separatorChar) + ".class";
    }
}
