package com.twx.core.util.json;

import com.twx.test.domain.People;
import com.twx.test.util.MessageUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by vincent.tong on 2017/2/8.
 */
public class JSONBinderTest {
    private JSONBinder<People> binder;

    @Before
    public void bind() {
        binder = JSONBinder.binder(People.class);
    }

    @Test
    public void testFromJSON() throws Exception {
        String str = "{\"name\":\"vincent\",\"age\":5,\"money\":44760.30}";
        People a = binder.fromJson(str);
        Assert.assertEquals("vincent", a.getName());
        Assert.assertEquals(5, a.getAge());
    }

    @Test
    public void testFromJSONList() throws Exception {
        String str = "[{\"name\":\"cha\",\"age\":15},{\"name\":\"vincent\",\"age\":5}]";
        List<People> list = binder.fromJson(str, List.class);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(People.class, list.get(0).getClass());
        Assert.assertEquals("cha", list.get(0).getName());
    }

    @Test
    public void testFromJSONList2() throws Exception {
        String str = "[{\"name\":\"cha\",\"age\":15},{\"name\":\"vincent\",\"age\":5}]";
        List<Object> list = JSONBinder.binder(Object.class).fromJson(str, List.class);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(LinkedHashMap.class, list.get(0).getClass());
    }

    @Test
    public void testToJSON() throws Exception {
        People a = new People("vincent", 5);
        a.setMoney(new BigDecimal("44760.30"));
        String result = binder.toJson(a);
        MessageUtil.onTime(result);
        Assert.assertEquals("{\"name\":\"vincent\",\"age\":5,\"money\":44760.30,\"myName\":\"vincent\"}", result);
    }

    @Test
    public void testToJSONList() throws Exception {
        People a = new People("vincent", 5);
        a.setMoney(new BigDecimal("44760.30"));
        People b = new People("vincent2", 22);
        b.setMoney(new BigDecimal("111.30"));

        List<People> list = new ArrayList<>();
        list.add(a);
        list.add(b);

        String result = binder.toJson(list);
        MessageUtil.onTime(result);
        Assert.assertEquals("[{\"name\":\"vincent\",\"age\":5,\"money\":44760.30,\"myName\":\"vincent\"},{\"name\":\"vincent2\",\"age\":22,\"money\":111.30,\"myName\":\"vincent2\"}]", result);
    }

    @Test
    public void testIndentOutput() throws Exception {
        People a = new People("vincent", 5);
        String result = binder.indentOutput().toJson(a);
        MessageUtil.onTime(result);
        Assert.assertEquals("{\r\n"
            + "  \"name\" : \"vincent\",\r\n"
            + "  \"age\" : 5,\r\n"
            + "  \"myName\" : \"vincent\"\r\n"
            + "}", result);
    }

    @Test
    public void test() throws Exception {
        Object obj = 5;
        MessageUtil.onTime(binder.toJson(obj));

        People a = new People("vincent", 5);
        a.setBirthDay(new Date());
        MessageUtil.onTime(binder.toJson(a));
    }
}