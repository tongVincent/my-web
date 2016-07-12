package com.twx.core.util;

import com.twx.test.util.MessageUtil;
import junit.framework.TestCase;

/**
 * Created by vincent.tong on 2016/6/30.
 */
public class StopWatchTest extends TestCase {

    public void testElapsedTime() throws Exception {
        StopWatch w = new StopWatch();
        Thread.sleep(2000);
        MessageUtil.onTime(w.elapsedTime() + "ms");
    }
}