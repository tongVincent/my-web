package com.twx.core.io.cpdetector;

import info.monitorenter.cpdetector.io.AbstractCodepageDetector;
import info.monitorenter.cpdetector.io.UnknownCharset;
import info.monitorenter.cpdetector.io.UnsupportedCharset;
import org.mozilla.intl.chardet.nsDetector;
import org.mozilla.intl.chardet.nsICharsetDetectionObserver;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

/**
 * 对info.monitorenter.cpdetector.io.JChardetFacade的宽松的一种版本，可以自由指定语言类型
 */
public final class JChardetFacadeLoose extends AbstractCodepageDetector implements nsICharsetDetectionObserver {

    private static nsDetector det;

    private byte[] buf = new byte[4096];

    private Charset codpage = null;

    private boolean m_guessing = true;

    private int amountOfVerifiers;

    /**
     *
     * @param langFlag nsPSMDetector里的静态常量
     */
    public JChardetFacadeLoose(int langFlag) {
        super();
        det = new nsDetector(langFlag);
        det.Init(this);
        this.amountOfVerifiers = det.getProbableCharsets().length;
    }

    /*
     * (non-Javadoc)
     *
     * @see cpdetector.io.ICodepageDetector#detectCodepage(java.io.InputStream)
     */
    public synchronized Charset detectCodepage(InputStream in, int length) throws IOException {
        this.Reset();
        int len;
        int read = 0;
        boolean done;
        Charset ret;
        do {
            len = in.read(buf, 0, Math.min(buf.length, length - read));
            if (len > 0) {
                read += len;
            }
            done = det.DoIt(buf, len, false);
        } while (len > 0 && !done);
        det.DataEnd();
        if (this.codpage == null) {
            if (this.m_guessing) {
                ret = guess();
            } else {
                ret = UnknownCharset.getInstance();
            }
        } else {
            ret = this.codpage;
        }
        return ret;
    }

    /**
     *
     */
    private Charset guess() {
        Charset ret = null;
        String[] possibilities = det.getProbableCharsets();
        /*
         * Detect US-ASCII by the fact, that no exclusion of any Charset was
         * possible.
         */
        if (possibilities.length == this.amountOfVerifiers) {
            ret = Charset.forName("US-ASCII");
        } else {
            // He should better return an Array of length zero!
            String check = possibilities[0];
            if (check.equalsIgnoreCase("nomatch")) {
                ret = UnknownCharset.getInstance();
            } else {
                for (int i = 0; ret == null && i < possibilities.length; i++) {
                    try {
                        ret = Charset.forName(possibilities[i]);
                    } catch (UnsupportedCharsetException uce) {
                        ret = UnsupportedCharset.forName(possibilities[i]);
                    }
                }
            }
        }
        return ret;

    }

    /**
     * @see org.mozilla.intl.chardet.nsICharsetDetectionObserver#Notify(java.lang.String)
     */
    public void Notify(final String charset) {
        this.codpage = Charset.forName(charset);
    }

    private void Reset() {
        det.Reset();
        this.codpage = null;
    }

    /**
     * @return Returns the m_guessing.
     */
    public boolean isGuessing() {
        return m_guessing;
    }

    /**
     * <p>
     * If it was impossible to narrow down possible results to one, an internal
     * set of possible character encodings exists. By setting guessing to true,
     * the call to {@link #detectCodepage(InputStream, int)} and
     * {@link #detectCodepage(URL)} will return an arbitrary possible Charset.
     * </p>
     * <p>
     * Currently the following precedence is implemented to choose the possible
     * Charset:
     * <ol>
     * <li> If US-ASCII is possible, it is chosen.
     * <li> If US-ASCII is not possible, the first supported one in the set of
     * possible charsets is returned. No information about the semantics of the
     * order in that list is available. If no possibility is supported, an
     * instance of {@link UnsupportedCharset} is returned.
     * </ol>
     * ASCII indeed is never detected as possible: No internal verifier exists for
     * ASCII, as all Charsets support ASCII. The possibility of ASCII is detected,
     * when no Charset has been excluded: The amount of possible Charsets is equal
     * to the amount of all detectable Charsets.
     * </p>
     *
     * @param guessing The guessing to set.
     */
    public synchronized void setGuessing(final boolean guessing) {
        this.m_guessing = guessing;
    }
}
