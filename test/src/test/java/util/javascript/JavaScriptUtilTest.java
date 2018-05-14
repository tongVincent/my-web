package util.javascript;

import com.twx.core.util.javascript.JavaScriptUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by vincent.tong on 2016/10/12.
 */
public class JavaScriptUtilTest {

    @Test
    public void testEval() throws Exception {
        Map<String, String> customer = new LinkedHashMap<>();
        final String city = "上海";
        customer.put("city", city);
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("customer", customer);
        Object result = JavaScriptUtil.eval("customer.city", params, JavaScriptUtil::supportMapConverter);
        Assert.assertEquals(city, result);
    }

}