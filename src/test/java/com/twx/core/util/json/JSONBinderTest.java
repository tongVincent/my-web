package com.twx.core.util.json;

import com.twx.test.domain.People;
import com.twx.test.util.MessageUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
        String str = "{\"name\":\"vincent\",\"age\":5}";
        People a = binder.fromJson(str);
        Assert.assertEquals(a.getName(), "vincent");
        Assert.assertEquals(a.getAge(), 5);
    }

    @Test
    public void testFromJSONList() throws Exception {
        String str = "[{\"name\":\"cha\",\"age\":15},{\"name\":\"vincent\",\"age\":5}]";
        List<People> list = binder.fromJson(str, List.class);
        Assert.assertEquals(list.size(), 2);
        Assert.assertEquals(list.get(0).getClass(), People.class);
        Assert.assertEquals(list.get(0).getName(), "cha");
    }

    @Test
    public void testToJSON() throws Exception {
        People a = new People("vincent", 5);
        String result = binder.toJson(a);
        MessageUtil.onTime(result);
        Assert.assertEquals(result, "{\"name\":\"vincent\",\"age\":5}");
    }
}