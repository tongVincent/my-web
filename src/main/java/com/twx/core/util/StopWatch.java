package com.twx.core.util;

import java.time.Clock;

/**
 * Created by vincent.tong on 2016/6/30.
 */
public final class StopWatch {
    private long start;
    private Clock clock = Clock.systemUTC();

    public StopWatch() {
        reset();
    }

    public void reset() {
        start = clock.millis();
    }

    public long elapsedTime() {
        return clock.millis() - start;
    }
}
