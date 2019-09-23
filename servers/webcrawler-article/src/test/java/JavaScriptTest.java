import jdk.nashorn.api.scripting.ScriptObjectMirror;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaScriptTest {
    public static void main(String[] args) {
        Matcher matcher = Pattern.compile("\\d+").matcher("https://www.toutiao.com/group/6738286894032880141/");
        System.out.println(matcher.group(1));
    }
}
