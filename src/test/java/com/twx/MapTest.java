package com.twx;

import com.twx.test.util.MessageUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vincent.tong on 2017/2/8.
 */
public class MapTest extends BaseTest {

    @Test
    public void test001() {
        Map<String, String> map = new HashMap<>();
        map.put(null, null);
        MessageUtil.onTime(map.containsKey(null));
    }
}
