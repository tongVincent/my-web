package com.twx.core.util.json;

import com.twx.BaseTest;
import com.twx.core.util.Base64;
import com.twx.core.util.StopWatch;
import com.twx.test.domain.Car;
import com.twx.test.domain.People;
import com.twx.test.domain.Student;
import com.twx.test.util.MessageUtil;
import org.junit.Assert;
import org.junit.Test;


/**
 * Created by vincent.tong on 2017/3/2.
 */
public class JsonUtilTest extends BaseTest {

    @Test
    public void testConvert() throws Exception {
        People p = new People();
        p.setAge(5);
        p.setName("vincent");
        Student stu = JsonUtil.convert(p, Student.class);
        Assert.assertEquals(p.getAge(), stu.getAge());
        Assert.assertEquals(p.getName(), stu.getName());
    }

    /**
     * 测试如果源对象与目标类没有相同的属性时候的行为：依然会new一个目标对象出来
     * 如果源对象没有属性，则会抛出IllegalArgumentException异常，见{@link #testConvert3()}
     * @throws Exception
     */
    @Test
    public void testConvert2() throws Exception {
        Object p = new Car();
        Student stu = JsonUtil.convert(p, Student.class);
        Assert.assertNotNull(stu);
    }

    /**
     * Object对象没有属性，所以会抛出IllegalArgumentException异常
     * @throws Exception
     */
    @Test
    public void testConvert3() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        Object p = new Object();
        JsonUtil.convert(p, Student.class);
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