package com.twx.core.util;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.io.ByteSource;
import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import com.twx.core.exception.BusinessException;
import com.twx.core.io.cpdetector.CharsetHelp;
import com.twx.core.io.cpdetector.CharsetVerifier;
import org.apache.commons.io.input.BOMInputStream;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by vincent.tong on 2016/8/3.
 */
public abstract class FileUtil {

    /**
     * 行分隔符
     * @return
     */
    public static String lineSeparator() {
        return System.getProperty("line.separator");
    }

    /**
     * 文件分隔符
     * 或者 System.getProperty("file.separator");
     * @return
     */
    public static String fileSeparator() {
        return File.separator;
    }

    /**
     * 路径分隔符
     * @return
     */
    public static String pathSeparator() {
        return System.getProperty("path.separator");
    }

    public static String getPostfix(String fileName) {
        if (fileName == null) {
            return "";
        }

        int index = fileName.lastIndexOf('.');
        if (index != -1) {
            return fileName.substring(index + 1);
        } else {
            return "";
        }
    }

    public static boolean isNotExists(String path) {
        return !StringUtil.hasText(path) || !new File(path).exists();
    }

    /**
     * 读取文件的所有内容
     * @param filePath
     * @return
     */
    public static List<String> readLines(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.isFile()) {
                throw new BusinessException("不是有效的文件");
            }

            return readLines(file.toURI().toURL());
        } catch (MalformedURLException e) {
            throw new BusinessException("读取资源出错", e);
        }
    }

    /**
     * 读取资源url的所有内容
     * @param url
     * @return
     */
    public static List<String> readLines(@Nonnull URL url) {
        try {
            Preconditions.checkNotNull(url);
            Charset charset = getCharset(url, Charsets.UTF_8);

            return new UrlBomByteSource(url).asCharSource(charset).readLines();
        } catch (IOException e) {
            throw new BusinessException("读取资源出错", e);
        }
    }

    /**
     * 读取文件的所有内容
     * @param filePath
     * @param lineJoin
     * @return
     */
    public static String readAll(String filePath, String lineJoin) {
        try {
            File file = new File(filePath);
            if (!file.isFile()) {
                throw new BusinessException("不是有效的文件");
            }

            return readAll(file.toURI().toURL(), lineJoin);
        } catch (MalformedURLException e) {
            throw new BusinessException("读取资源出错", e);
        }
    }

    /**
     * 读取资源url的所有内容
     * @param url
     * @param lineJoin
     * @return
     */
    public static String readAll(@Nonnull URL url, String lineJoin) {
        try {
            Preconditions.checkNotNull(url);
            Charset charset = getCharset(url, Charsets.UTF_8);

            return Joiner.on(lineJoin == null ? "" : lineJoin)
                    .skipNulls()
                    .join(new UrlBomByteSource(url).asCharSource(charset).readLines());
        } catch (IOException e) {
            throw new BusinessException("读取资源出错", e);
        }
    }

    private static final class UrlBomByteSource extends ByteSource {

        private final URL url;

        private UrlBomByteSource(URL url) {
            this.url = checkNotNull(url);
        }

        @Override
        public InputStream openStream() throws IOException {
            return new BOMInputStream(url.openStream());
        }

        @Override
        public String toString() {
            return "UrlBomByteSource(" + url + ")";
        }
    }

    public static Charset getCharset(@Nonnull URL url, Charset defaultCharset) {
        Preconditions.checkNotNull(url);

        return CharsetHelp.getInstance()
                .addCharsetVerifier(CharsetVerifier.UTF8)
                .addCharsetVerifier(CharsetVerifier.GB2312)
                .addCharsetVerifier(CharsetVerifier.GB18030)
                .setNeedGuess(true)
                .detector(read(url), defaultCharset);
    }

    public static byte[] read(@Nonnull URL url) {
        try {
            Preconditions.checkNotNull(url);

            return Resources.asByteSource(url).read();
        } catch (IOException e) {
            throw new BusinessException("读取资源出错", e);
        }
    }

    public static void writeFile(String content, String filePath, Charset charset) {
        write(content, filePath, charset);
    }

    public static void writeFileAppend(String content, String filePath, Charset charset) {
        write(content, filePath, charset, FileWriteMode.APPEND);
    }

    private static void write(String content, String filePath, Charset charset, FileWriteMode... modes) {
        try {
            if (StringUtil.isEmpty(content)) {
                return;
            }

            Files.asCharSink(new File(filePath), charset, modes).write(content);
        } catch (IOException e) {
            throw new BusinessException("写入文件出错", e);
        }
    }

}
