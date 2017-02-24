package com.twx.core.util.json;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.twx.test.util.MessageUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by vincent.tong on 2016/11/16.
 */
public class GsonUtilTest {

    @Test
    public void testToJson() throws Exception {
        A a = new A(5, "vincent");
        String result = GsonUtil.toJson(a);
        MessageUtil.onTime(result);
        Assert.assertEquals("{\"my-name\":\"vincent\",\"age\":5}", result);
    }

    @Test
    public void testFromJson() throws Exception {
        String str = "{\"my-name\":\"vincent\",\"age\":5}";
        A a = GsonUtil.fromJson(str, A.class);
        Assert.assertEquals("vincent", a.getName());
        Assert.assertEquals(5, a.getAge());
    }

    @Test
    public void testFromJson1() throws Exception {
        String str = "{\"my-name\":\"vincent\",\"age\":5}";
        A a = GsonUtil.fromJson(str, new TypeToken<A>() {
        });
        Assert.assertEquals("vincent", a.getName());
        Assert.assertEquals(5, a.getAge());
    }

    @Test
    public void testFromJson2() throws Exception {
        String str = "[{\"my-name\":\"cha\",\"age\":15},{\"my-name\":\"vincent\",\"age\":5}]";
        List<A> list = GsonUtil.fromJson(str, new TypeToken<List<A>>() {
        });
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(A.class, list.get(0).getClass());
    }
}

class A {
    @SerializedName("my-name")
    private String name;
    private int age;

    public A(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}