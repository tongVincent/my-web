package com.twx.core.io.cpdetector;

import org.mozilla.intl.chardet.GB2312Statistics;
import org.mozilla.intl.chardet.nsEUCStatistics;
import org.mozilla.intl.chardet.nsGB18030Verifier;
import org.mozilla.intl.chardet.nsGB2312Verifier;
import org.mozilla.intl.chardet.nsUTF8Verifier;
import org.mozilla.intl.chardet.nsVerifier;

public enum CharsetVerifier {
    UTF8(new nsUTF8Verifier()),
    GB2312(new nsGB2312Verifier(), new GB2312Statistics()),
    GB18030(new nsGB18030Verifier()),
    ;

    private nsVerifier verifier;
    private nsEUCStatistics statistics;

    CharsetVerifier(nsVerifier verifier) {
        this.verifier = verifier;
    }

    CharsetVerifier(nsVerifier verifier, nsEUCStatistics statistics) {
        this.verifier = verifier;
        this.statistics = statistics;
    }

    public nsVerifier getVerifier() {
        return verifier;
    }

    public nsEUCStatistics getStatistics() {
        return statistics;
    }
}
