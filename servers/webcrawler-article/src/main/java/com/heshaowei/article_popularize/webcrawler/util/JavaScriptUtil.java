package com.heshaowei.article_popularize.webcrawler.util;

import com.google.common.collect.Maps;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Map;

public class JavaScriptUtil {

    public static ScriptObjectMirror eval(String script, String key){
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        try {
            engine.eval(script);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return (ScriptObjectMirror)engine.get(key);
    }

    public static Map<String, Object> scriptObjectMap(ScriptObjectMirror mirror){
        Map<String, Object> map = Maps.newHashMap();
        mirror.entrySet().forEach(entry -> {
            String key = entry.getKey();
            Object value = entry.getValue();
            if(value instanceof ScriptObjectMirror){
                value = scriptObjectMap((ScriptObjectMirror)value);
            }
            map.put(key, value);
        });

        return map;
    }
}
