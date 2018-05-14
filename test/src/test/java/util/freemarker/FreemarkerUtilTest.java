package util.freemarker;

import com.twx.core.util.freemarker.FreemarkerUtil;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author vincent.tong on 2017/4/22
 */
public class FreemarkerUtilTest {
    @Test
    public void parseDirectoryTemplate2File() throws Exception {

    }

    @Test
    public void parseClassTemplate2File() throws Exception {
        Map<String, Object> model = new HashMap<>();
        model.put("price", 19.00);
        model.put("myList", Arrays.asList(23, 89, 66));
        model.put("myArray", new Object[] {88, 0, 99});
        FreemarkerUtil.parseClassTemplate2File("/test/test.ftl", "test.txt", model);
    }

}