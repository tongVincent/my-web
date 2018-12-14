package com.twx.core.io.cpdetector;

import org.mozilla.intl.chardet.nsEUCStatistics;
import org.mozilla.intl.chardet.nsICharsetDetectionObserver;
import org.mozilla.intl.chardet.nsICharsetDetector;
import org.mozilla.intl.chardet.nsPSMDetector;
import org.mozilla.intl.chardet.nsVerifier;

public class ByteArrayDetector extends nsPSMDetector implements nsICharsetDetector {

    private nsICharsetDetectionObserver mObserver;

    public ByteArrayDetector(int aItems, nsVerifier[] aVerifierSet, nsEUCStatistics[] aStatisticsSet) {
        super(aItems, aVerifierSet, aStatisticsSet);
    }

    public void Init(nsICharsetDetectionObserver aObserver) {
        mObserver = aObserver;
    }

    public boolean DoIt(byte[] aBuf, int aLen, boolean oDontFeedMe) {
        if (aBuf == null || oDontFeedMe) {
            return false;
        }
        this.HandleData(aBuf, aLen);
        return true;
    }

    public void Done() {
        this.DataEnd();
    }

    public void Report(String charset) {
        if (mObserver != null) {
            mObserver.Notify(charset);
        }
    }

}
