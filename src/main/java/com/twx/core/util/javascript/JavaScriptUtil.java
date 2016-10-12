package com.twx.core.util.javascript;

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
public class JavaScriptUtil {
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

    public static ScriptEngine getScriptEngine(Map<String, Object> params, BiFunction<ScriptEngine, Map.Entry<String, Object>, Object> function) {
        ScriptEngine engine = getScriptEngine();
        LOGGER.info("设置js引擎参数begin");
        if (!CollectionUtils.isEmpty(params)) {
            params.entrySet().forEach(entry ->
                    engine.put(entry.getKey(), function == null ? entry.getValue() : function.apply(engine, entry))
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
}
