package xktz.game.script;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xktz.game.attribute.effect.condition.EffectConditionChecker;
import xktz.javarunner.JavaClass;
import xktz.javarunner.NotSuccessException;

import java.io.IOException;
import java.net.URISyntaxException;

public class ConditionScript implements Script {

    private String scriptCode;
    private final String className;

    private JavaClass javaClass;

    private EffectConditionChecker checker;

    public static final String PACKAGE_NAME = "xktz.game.attribute.effect.condition.custom";

    private static final Logger logger = LoggerFactory.getLogger(ConditionScript.class);

    public ConditionScript(String path, String className) {
        try {
            this.scriptCode = ScriptReader.readScript(path, className);
        } catch (IOException e) {
            logger.error(SLF4J_ERROR_FORMAT, className, e.getMessage());
            System.exit(1);
        }
        this.className = className;
    }

    public void init() {
        initClass(className, scriptCode);
        Class<?> checkerClass = javaClass.getClazz();
        try {
            checker = (EffectConditionChecker) checkerClass.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            logger.error(SLF4J_ERROR_FORMAT, className,  e.getMessage());
            System.exit(1);
        } catch (ClassCastException e) {
            logger.error(SLF4J_ERROR_FORMAT, className, ERROR_NOT_IMPLEMENT_EFFECTOR_INTERFACE);
            System.exit(1);
        }
    }

    public EffectConditionChecker getChecker() {
        return checker;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public String getPackageName() {
        return PACKAGE_NAME;
    }

    @Override
    public void setJavaClass(JavaClass javaClass) {
        this.javaClass = javaClass;
    }
}
