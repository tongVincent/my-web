package com.twx.core.util;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.io.Resources;
import com.twx.BaseTest;
import com.twx.core.io.cpdetector.CharsetHelp;
import com.twx.core.io.cpdetector.CharsetVerifier;
import com.twx.core.io.cpdetector.JChardetFacadeLoose;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.UnicodeDetector;
import org.junit.Test;
import org.mozilla.intl.chardet.nsPSMDetector;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

public class FileUtilTest extends BaseTest {

    /**
     * 经过下面的2个测试方法，可以知道：如果编码有bom，则前面3个或4个字节为bom码，
     * 如果转换成字符串的时候，使用的编码正确，则这些bom码对应的字符为空白字符。
     */
    @Test
    public void test001() {
        URL url = FileUtilTest.class.getResource("my.txt");
        String message = FileUtil.readAll(url, null);
        MessageUtil.onTime(message);
        MessageUtil.onTime(message.getBytes(Charsets.UTF_8).length);
    }

    @Test
    public void test002() {
        URL url = FileUtilTest.class.getResource("my.txt");
        byte[] bytes = FileUtil.read(url);
        MessageUtil.onTime(bytes.length);
        String message = new String(bytes, Charsets.UTF_8);
        MessageUtil.onTime(message);
        MessageUtil.onTime(message.getBytes(Charsets.UTF_8).length);
    }

    @Test
    public void test003() throws IOException {
        CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
//        detector.add(new ParsingDetector(false));
        detector.add(UnicodeDetector.getInstance());
//        detector.add(new ByteOrderMarkDetector());
        detector.add(new JChardetFacadeLoose(nsPSMDetector.CHINESE));
//        detector.add(ASCIIDetector.getInstance());
        URL url = FileUtilTest.class.getResource("test22.txt");
        Charset charset = detector.detectCodepage(url);
        MessageUtil.onTime(charset == null ? "null" : charset.name());

        String content = Joiner.on("")
                .skipNulls()
                .join(Resources.asCharSource(url, Charset.forName("GB18030")).readLines());
        MessageUtil.onTime(content);
    }

    @Test
    public void test004() throws IOException {
        MessageUtil.onTime("开始");
        URL url = FileUtilTest.class.getResource("test22.txt");
        Charset charset = CharsetHelp.getInstance()
                .addCharsetVerifier(CharsetVerifier.UTF8)
                .addCharsetVerifier(CharsetVerifier.GB2312)
                .addCharsetVerifier(CharsetVerifier.GB18030)
                .setNeedGuess(true)
                .detector(Resources.asByteSource(url).read());

        MessageUtil.onTime(charset == null ? "null" : charset.name());

        if (charset == null) {
            return;
        }

        String content = Joiner.on("")
                .skipNulls()
                .join(Resources.asCharSource(url, charset).readLines());
        MessageUtil.onTime(content);
    }

    @Test
    public void test005() throws IOException {
        MessageUtil.onTime("开始");
        URL url = FileUtilTest.class.getResource("test22.txt");
        MessageUtil.onTime(FileUtil.readAll(url, " "));
    }
}
