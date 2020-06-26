package xktz.javarunner;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JavaClassLoader extends ClassLoader{

    public Class<?> findClass(String name, String path) {
        byte[] classBytes = null;
        Path pather = null;
        try {
            pather = Paths.get(new URI("file:///" + path));
            classBytes = Files.readAllBytes(pather);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        Class clazz = defineClass(name, classBytes, 0, classBytes.length);
        return clazz;
    }
}
