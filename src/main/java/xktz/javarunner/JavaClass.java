package xktz.javarunner;

import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class JavaClass {

    // class name
    private String className;
    // package name
    private String packageName;
    // class path
    private String classPath;
    // the import list
    private Iterable<ImportClass> importList;
    // code
    private String codeIn;
    // import string
    private String importString;
    // extends and implement string
    private String extendsName, implementsName;
    // total
    private String total;
    // the class
    private Class<?> clazz;
    // save path
    private String savePath;

    // flag
    private static final String FLAG = "-d";
    // the java compiler
    private static final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    // file manager
    private static final StandardJavaFileManager MANAGER = compiler.getStandardFileManager(null, null, null);
    // prefix
    private static final String PREFIX = ".java";
    // class loader
    private static final JavaClassLoader CLASS_LOADER = new JavaClassLoader();


    // empty
    public static final Iterable EMPTY_ITER = new ArrayList(0);

    public JavaClass(String className, Iterable<ImportClass> importList, String packageName, String codeIn, Iterable<String> extendsName, Iterable<String> implementsName, String classSvPosition)
            throws NotSuccessException, IOException, ClassNotFoundException, URISyntaxException {
        this.className = className;
        this.packageName = packageName;
        this.importList = importList;
        this.codeIn = codeIn;
        this.extendsName = join(extendsName, ",");
        this.implementsName = join(implementsName, ",");
        this.savePath = classSvPosition;
        // init all
        init();
    }

    public JavaClass(String className, String packageName, String totalCode, String classSvPosition) throws ClassNotFoundException, IOException, NotSuccessException, URISyntaxException {
        this.className = className;
        this.packageName = packageName;
        this.total = totalCode;
        this.savePath = classSvPosition;
        initClassPath();
        initClazz();
    }

    /**
     * Get a method by method name
     *
     * @param methodName the method name
     * @return method
     * @throws NoSuchMethodException no such method
     */
    public Method getMethod(String methodName) throws NoSuchMethodException {
        // get the method in class
        return clazz.getMethod(methodName);
    }


    private void init() throws ClassNotFoundException, IOException, NotSuccessException, URISyntaxException {
        initClassPath();
        initImportString();
        initTotalCode();
        initClazz();
    }

    private void initClassPath() {
        classPath = packageName;
        if (!packageName.endsWith(".") && packageName.length() > 0) {
            classPath += ".";
        }
        classPath += className;
    }

    private void initImportString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (ImportClass importing : importList) {
            stringBuilder.append(String.format("import %s;\n", importing.getImportStr()));
            System.out.println(String.format("import %s;\n", importing.getImportStr()));
        }
        importString = stringBuilder.toString();
    }

    private void initTotalCode() {
        this.total = codeIn;
    }

    private void initClazz() throws IOException, NotSuccessException, ClassNotFoundException, URISyntaxException {
        // the java object list
        Iterable<? extends JavaFileObject> files = Arrays.asList(new StrSrcJavaObject(classPath, total));
        // the out dir
        String outDir = new File(savePath).getAbsolutePath() + File.separator;
        // the file dir
        String fileDir = new File(savePath).getAbsolutePath().replace('\\', '/') + "/" + className + ".class";
        // the options
        Iterable<String> options = Arrays.asList(FLAG, outDir);
        JavaCompiler.CompilationTask task = compiler.getTask(null, MANAGER, null, options, null, files);
        boolean result = task.call();
        if (!result) {
            throw new NotSuccessException();
        }
        this.clazz = CLASS_LOADER.findClass(className, fileDir);
        // delete the file
        new File(fileDir).delete();
    }

    private String join(Iterable<String> list, String joiner) {
        StringBuilder result = new StringBuilder();
        int cnt = 0;
        // iterate to add
        for (String str : list) {
            result.append(str);
            result.append(joiner);
            cnt++;
        }
        // if there is no value, return empty
        if (cnt == 0) {
            return "";
        }
        // change the string
        String resultStr = result.toString();
        resultStr = resultStr.substring(0, resultStr.lastIndexOf(joiner));
        return resultStr;
    }


    private static class StrSrcJavaObject extends SimpleJavaFileObject {
        private String content;

        StrSrcJavaObject(String name, String content) {
            super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
            this.content = content;
        }

        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return content;
        }
    }

    public Class<?> getClazz() {
        return clazz;
    }

}
