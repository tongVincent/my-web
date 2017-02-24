package com.twx.core.util.json.other;

import com.twx.BaseTest;
import org.junit.Test;

/**
 * Created by vincent.tong on 2017/2/24.
 */
public class ClassToJsonUtilTest extends BaseTest {

    @Test
    public void testToJson() throws Exception {

    }

    @Test
    public void testFromJson() throws Exception {
        String className = "Photo_App";
        String json = "{\n" +
            "    \"creator\": {\n" +
            "        \"name\": \"李时珍\",\n" +
            "        \"role\": \"监理\",\n" +
            "        \"portrait_url\": \"a.jpg\"\n" +
            "    },\n" +
            "    \"operations\": [\"补传照片\"],\n" +
            "    \"type\": \"水电验收单\",\n" +
            "    \"date\": \"2016-11-17 20:30:30\",\n" +
            "    \"note\": \"备注\",\n" +
            "    \"photos\": [\n" +
            "        {\n" +
            "            \"id\": 1,\n" +
            "            \"name\":\"b\",\n" +
            "            \"url\":\"project/photos/b.jpg\",\n" +
            "            \"can_delete\": true\n" +
            "        }\n" +
            "    ]\n" +
            "}";
        System.out.println(ClassToJsonUtil.fromJson(json, className));
    }
}