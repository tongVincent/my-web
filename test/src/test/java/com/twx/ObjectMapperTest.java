package com.twx;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.twx.core.util.CharacterEncodings;
import com.twx.core.util.json.JSONBinder;
import com.twx.test.domain.People;
import com.twx.core.util.MessageUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vincent.tong on 2017/2/8.
 */
public class ObjectMapperTest extends BaseTest {


    @Test
    public void test001() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonSource = "[{\"name\":\"cha\",\"age\":15},{\"name\":\"vincent\",\"age\":5}]";

        List<People> results = mapper.readValue(jsonSource,
            new TypeReference<List<People>>() { } );
        MessageUtil.onTime(results);
    }

    @Test
    public void test002() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonSource = "{\"name\":\"cha\",\"age\":15}";

        People results = mapper.readValue(jsonSource, People.class);
        MessageUtil.onTime(results);
    }

    @Test
    public void test003() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String base64 = "TWFuIGlzIGRpc3Rpbmd1aXNoZWQsIG5vdCBvbmx5IGJ5IGhpcyByZWFzb24sIGJ1dCBieSB0aGlz";
        byte[] binary = mapper.convertValue(base64, byte[].class);
        MessageUtil.onTime(new String(binary));
    }

    @Test
    public void test004() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String str = "Man is distinguished, not only by his reason, but by this";
        String base64 = mapper.convertValue(str.getBytes(CharacterEncodings.CHARSET_UTF_8), String.class);
        MessageUtil.onTime(base64);
    }

    @Test
    public void test005() throws IOException {
        String jsonSource = "[{\"name\":\"cha\",\"age\":15},{\"name\":\"vincent\",\"age\":5}]";

        List<People> results = JSONBinder.binder(People.class).fromJson(jsonSource, List.class);
        MessageUtil.onTime(results);
    }

    @Test
    public void test006() throws IOException {
        Map<People, String> map = new HashMap<>();
        map.put(new People("cha", 5), "here");

        String results = JSONBinder.binder(People.class).toJson(map);
        MessageUtil.onTime(results);
    }

    @Test
    public void test007() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        ObjectNode obj = mapper.createObjectNode();
        obj.with("other").with("my").put("type", "").putArray("d").addObject().put("ddds", "");
        MessageUtil.onTime(mapper.writeValueAsString(obj));
    }
}
