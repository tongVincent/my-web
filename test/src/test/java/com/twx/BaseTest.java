package com.twx;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * Created by vincent.tong on 2016/7/14.
 */
public abstract class BaseTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Override
    public String toString() {
        return "BaseTest{"
            + "thrown=" + thrown
            + '}';
    }

}

