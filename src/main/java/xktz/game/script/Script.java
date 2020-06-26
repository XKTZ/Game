package xktz.game.script;

import org.slf4j.Logger;
import xktz.javarunner.JavaClass;
import xktz.javarunner.NotSuccessException;

import java.io.IOException;
import java.net.URISyntaxException;

public interface Script {
    public static final String SLF4J_ERROR_FORMAT = "Effect script {} error - {}";
    public static final String ERROR_NOT_SUCCESS_CONVERT = "Not success convert";
    public static final String ERROR_NOT_IMPLEMENT_EFFECTOR_INTERFACE = "Script does not implement effector interface";

    public static final String TEMP_DIR = System.getProperty("java.io.tmpdir");


    public default void initClass(String className, String scriptCode) {
        try {
            setJavaClass(new JavaClass(className, getPackageName(), scriptCode, TEMP_DIR));
        } catch (ClassNotFoundException | IOException | URISyntaxException e) {
            getLogger().error(SLF4J_ERROR_FORMAT, className, e.getMessage());
            System.exit(1);
        } catch (NotSuccessException e) {
            getLogger().error(SLF4J_ERROR_FORMAT, className, ERROR_NOT_SUCCESS_CONVERT);
            System.exit(1);
        }
    }

    public Logger getLogger();

    public String getPackageName();

    public void setJavaClass(JavaClass javaClass);
}
