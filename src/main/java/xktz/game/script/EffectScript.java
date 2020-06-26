package xktz.game.script;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xktz.game.attribute.effect.content.Effector;
import xktz.javarunner.JavaClass;
import xktz.javarunner.NotSuccessException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

public class EffectScript implements Script{

    private String scriptCode;
    private final String className;

    private JavaClass javaClass;

    private Effector effector;

    public static final String PACKAGE_NAME = "xktz.game.attribute.effect.content.custom";

    private static final Logger logger = LoggerFactory.getLogger(EffectScript.class);

    public EffectScript(String path, String className) {
        try {
            this.scriptCode = ScriptReader.readScript(path, className);
        } catch (IOException ioe) {
            logger.error(SLF4J_ERROR_FORMAT, className, ioe.getMessage());
            System.exit(1);
        }
        this.className = className;
        init();
    }

    public void init() {
        // get the class
        initClass(className, scriptCode);
        Class<?> effectorClass = javaClass.getClazz();
        try {
            effector = (Effector) effectorClass.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            logger.error(SLF4J_ERROR_FORMAT, className,  e.getMessage());
            System.exit(1);
        } catch (ClassCastException e) {
            logger.error(SLF4J_ERROR_FORMAT, className, ERROR_NOT_IMPLEMENT_EFFECTOR_INTERFACE);
            System.exit(1);
        }
    }

    public Effector getEffector() {
        return effector;
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
