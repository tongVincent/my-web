package com.twx;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by vincent.tong on 2017/2/8.
 */
public class FileIOTest extends BaseTest {

    // 列出指定目录下的所有子目录
    @Test
    public void test001() throws IOException {
        String directory = "D:\\fuzhou";
        Files.list(Paths.get(directory))
            .filter(Files::isDirectory)
            .map(path -> path.getName(path.getNameCount() - 1))
            .forEach(pathName -> System.out.println("cd " + pathName + "\n" + "git pull\ncd ..\n"));
    }
}
