package com.twx.javax.script;

import com.twx.BaseTest;
import com.twx.core.util.MessageUtil;
import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Created by vincent.tong on 2017/2/8.
 */
public class ScriptEngineTest extends BaseTest {

    @Test
    public void test001() {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("groovy");
        MessageUtil.onTime("Calling script from Java");
        try {
            engine.eval("println 'hello from Groovy'");
        } catch (ScriptException ex) {
            MessageUtil.onTime(ex);
        }
    }

    @Test
    public void test002() {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("groovy");
        try {
            engine.put("name", "Venkat");
            engine.eval("println \"Hello ${name} from Groovy\"; name += '!' ");
            String name = (String) engine.get("name");
            System.out.println("Back in Java:" + name);
        } catch (ScriptException ex) {
            MessageUtil.onTime(ex);
        }
    }
}
