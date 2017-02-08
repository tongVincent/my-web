package com.twx;

import com.twx.test.util.MessageUtil;
import org.junit.Test;

import java.util.Collections;

/**
 * Created by vincent.tong on 2017/2/8.
 */
public class CollectionsTest extends BaseTest {

    @Test
    public void test001() {
        String str = null;
        MessageUtil.onTime(Collections.singletonList("df"));
        MessageUtil.onTime(Collections.singletonList(null).isEmpty());
    }
}
