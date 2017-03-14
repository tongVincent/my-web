package com.twx;

import com.twx.core.util.FileUtil;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * Created by vincent.tong on 2017/2/8.
 */
public class FileIOTest extends BaseTest {

    // 列出指定目录下的所有子目录，git pull命令，另一种方法见test002
    @Test
    public void test001() throws IOException {
        String directory = "D:\\fuzhou";
        if (!FileUtil.exists(directory)) {
            return;
        }

        Files.list(Paths.get(directory))
                .filter(Files::isDirectory)
                .map(path -> path.getName(path.getNameCount() - 1))
                .forEach(pathName -> System.out.println("cd " + pathName + "\n" + "git pull\ncd ..\n"));
    }

    // 列出指定目录下的所有子目录，git pull命令，另一种方法见test001
    @Test
    public void test002() throws IOException {
        String directory = "D:\\sgcloud";
        if (!FileUtil.exists(directory)) {
            return;
        }

        Files.list(Paths.get(directory))
                .filter(Files::isDirectory)
                .forEach(path -> System.out.println("cd " + path.getFileName() + "\n" + "git pull\ncd ..\n"));
    }

    // 列出指定目录下的所有子目录，mvn clean install命令
    @Test
    public void test003() throws IOException {
        String directory = "D:\\sgcloud";
        if (!FileUtil.exists(directory)) {
            return;
        }

        Files.list(Paths.get(directory))
                .filter(Files::isDirectory)
                .map(path -> {
                    Optional<String> childPath = Optional.empty();
                    try {
                        childPath = Files.list(path)
                                .filter(Files::isDirectory)
                                .filter(p -> p.getFileName().toString().contains("interface"))
                                .map(p -> p.getFileName().toString()).findFirst();
                    } catch (IOException ignored) {

                    }
                    String parentName = path.getFileName().toString();
                    return childPath.isPresent() ? parentName + '/' + childPath.get() : parentName;
                })
                .forEach(pathName -> System.out.println("cd " + pathName + "\n" + "mvn clean install\ncd ..\ncd ..\n"));
    }
}
