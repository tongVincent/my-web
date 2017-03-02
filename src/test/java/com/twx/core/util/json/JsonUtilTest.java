package com.twx.core.util.json;

import com.twx.core.util.Base64;
import com.twx.core.util.StopWatch;
import com.twx.test.util.MessageUtil;
import org.junit.Assert;
import org.junit.Test;


/**
 * Created by vincent.tong on 2017/3/2.
 */
public class JsonUtilTest {

    @Test
    public void testConvert() throws Exception {

    }

    @Test
    public void testEncodeBase64() throws Exception {
        String decoder = "Man is distinguished, not only by his reason, but by this. 是吗？";

        StopWatch watch = new StopWatch();
        String encoder = JsonUtil.encodeBase64(decoder);
        MessageUtil.onTime(watch.elapsedTime());
        MessageUtil.onTime(encoder);

        watch.reset();
        String expected = Base64.encode(decoder);
        MessageUtil.onTime(watch.elapsedTime());

        Assert.assertEquals(expected, encoder);
    }

    @Test
    public void testDecodeBase64() throws Exception {
        String encoder = "TWFuIGlzIGRpc3Rpbmd1aXNoZWQsIG5vdCBvbmx5IGJ5IGhpcyByZWFzb24sIGJ1dCBieSB0aGlzLiDmmK/lkJfvvJ8=";

        StopWatch watch = new StopWatch();
        String decoder = JsonUtil.decodeBase64(encoder);
        MessageUtil.onTime(watch.elapsedTime());
        MessageUtil.onTime(decoder);

        watch.reset();
        String expected = Base64.decode(encoder);
        MessageUtil.onTime(watch.elapsedTime());

        Assert.assertEquals(expected, decoder);
    }
}