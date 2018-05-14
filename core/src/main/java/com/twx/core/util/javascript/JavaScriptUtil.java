package com.twx.core.util.javascript;

import com.twx.core.util.StringUtil;
import com.twx.core.util.json.JSONBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.function.BiFunction;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Created by vincent.tong on 2016/10/12.
 */
public abstract class JavaScriptUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JavaScriptUtil.class);

    public static Object eval(String script) {
        return eval(script, null, null);
    }

    public static Object eval(String script, Map<String, Object> params) {
        return eval(script, params, null);
    }

    public static Object eval(String script, Map<String, Object> params, BiFunction<ScriptEngine, Map.Entry<String, Object>, Object> function) {
        try {
            ScriptEngine engine = getScriptEngine(params, function);
            return engine.eval(script);
        } catch (ScriptException e) {
            LOGGER.error("解析规则的过程中出错了", e);
            throw new RuntimeException("解析规则的过程中出错了", e);
        }
    }

    public static ScriptEngine getScriptEngine(Map<String, Object> params) {
        return getScriptEngine(params, null);
    }

    public static ScriptEngine getScriptEngine(Map<String, Object> params, BiFunction<ScriptEngine, Map.Entry<String, Object>, Object> converter) {
        ScriptEngine engine = getScriptEngine();
        LOGGER.info("设置js引擎参数begin");
        if (!CollectionUtils.isEmpty(params)) {
            params.entrySet().forEach(entry ->
                    engine.put(entry.getKey(), converter == null ? entry.getValue() : converter.apply(engine, entry))
            );
        }
        LOGGER.info("设置js引擎参数end");
        return engine;
    }

    public static ScriptEngine getScriptEngine() {
        LOGGER.info("获取js引擎begin");
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        LOGGER.info("获取js引擎end");
        return engine;
    }

    /**
     * 目前ScriptEngine不支持与java的Map的相互交换
     * java的Map在js中只能像在java中那样访问，不能用js的Map的访问方式。
     * js的Map映射到java中的jdk.nashorn.api.scripting.ScriptObjectMirror，此类不能自己创建。但此类实现了Map接口
     *
     * 下面的方法是把java中的Map转换成js中的Map
     * @param engine
     * @param entry
     * @return
     */
    public static Object supportMapConverter(ScriptEngine engine, Map.Entry<String, Object> entry) {
        if (entry.getValue() instanceof Map) {
            final String script = "JSON.parse({})";
            engine.put(entry.getKey(), JSONBinder.binder(Object.class).toJson(entry.getValue()));
            try {
                return engine.eval(StringUtil.format(script, entry.getKey()));
            } catch (ScriptException e) {
                LOGGER.error("转换Java的Map到JavaScript的Map的过程中出错了", e);
                throw new RuntimeException("转换Java的Map到JavaScript的Map的过程中出错了", e);
            }
        }
        return entry.getValue();
    }
}
