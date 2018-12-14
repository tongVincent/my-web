package com.twx.core.io.cpdetector;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.mozilla.intl.chardet.nsEUCStatistics;
import org.mozilla.intl.chardet.nsICharsetDetectionObserver;
import org.mozilla.intl.chardet.nsVerifier;

import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.List;

public class CharsetHelp implements nsICharsetDetectionObserver {

    public static CharsetHelp getInstance() {
        return new CharsetHelp();
    }

    private List<CharsetVerifier> verifierList = Lists.newArrayList();
    private Charset charset;
    /**
     * 是否需要获取verifierList中第一个有效的编码
     */
    private boolean needGuess;

    public CharsetHelp addCharsetVerifier(CharsetVerifier verifier) {
        if (verifier != null) {
            verifierList.add(verifier);
        }
        return this;
    }

    /**
     * 对字节数组进行编码探测，如果探测失败，返回null
     * @param buf
     * @return
     */
    public Charset detector(byte[] buf) {
        return detector(buf, null);
    }

    /**
     * 对字节数组进行编码探测，如果探测失败，返回defaultCharset
     * @param buf
     * @param defaultCharset 如果探测失败，则返回
     * @return
     */
    public Charset detector(byte[] buf, Charset defaultCharset) {
        Preconditions.checkNotNull(buf);
        if (verifierList.isEmpty()) {
            return defaultCharset;
        }

        ByteArrayDetector detector = newByteArrayDetector();
        detector.Init(this);
        detector.DoIt(buf, buf.length, false);
        detector.DataEnd();

        if (charset == null && needGuess) {
            charset = guess(detector);
        }

        return charset == null ? defaultCharset : charset;
    }

    /**
     * 获取verifierList中第一个有效的编码
     * @param detector
     * @return
     */
    private Charset guess(ByteArrayDetector detector) {
        String[] possibilities = detector.getProbableCharsets();
        String check = possibilities[0];
        if ("nomatch".equalsIgnoreCase(check)) {
            return null;
        }

        for (String name : possibilities) {
            try {
                return Charset.forName(name);
            } catch (UnsupportedCharsetException ignored) {
            }
        }

        return null;
    }

    /**
     * 根据verifierList创建编码探测器
     * @return
     */
    private ByteArrayDetector newByteArrayDetector() {
        int size = verifierList.size();
        nsVerifier[] verifiers = new nsVerifier[size];
        nsEUCStatistics[] statistics = new nsEUCStatistics[size];

        for(int i = 0; i < size; i++) {
            CharsetVerifier charsetVerifier = verifierList.get(i);
            verifiers[i] = charsetVerifier.getVerifier();
            statistics[i] = charsetVerifier.getStatistics();
        }

        return new ByteArrayDetector(size, verifiers, statistics);
    }

    /**
     * 编码确认后的 监听回调函数
     * @param charset
     */
    @Override
    public void Notify(String charset) {
        this.charset = Charset.forName(charset);
    }

    public boolean isNeedGuess() {
        return needGuess;
    }

    public CharsetHelp setNeedGuess(boolean needGuess) {
        this.needGuess = needGuess;
        return this;
    }
}
