package xktz.game.script;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ScriptReader {

    public static char COMMAND_SYMBOL = '#';
    public static char END_LINE = ';';
    public static String PUBLIC_KEY = "public";

    private static final String COMMAND_IMPORT = "import";
    private static final String COMMAND_EXTEND = "extends";
    private static final String COMMAND_IMPLEMENT = "implements";
    private static final String COMMAND_END_COMMAND_1 = "endc";
    private static final String COMMAND_END_COMMAND_2 = "endcommand";

    private static final Logger logger = LoggerFactory.getLogger(ScriptReader.class);

    public static String readScript(String scriptPath)
            throws IOException {
        // create the reader
        BufferedReader reader = new BufferedReader(new FileReader(scriptPath));
        StringBuilder codeValue = new StringBuilder();
        // temp
        String temp;
        // read the file
        while ((temp = reader.readLine()) != null) {
            codeValue.append(temp);
        }
        reader.close();
        return codeValue.toString();
    }

    /**
     * Init the script class statement
     * @param stringBuilder string builder
     * @param className name of class
     * @param scriptExtend the class script extends
     * @param scriptImplement the interface class implements
     */
    private static void initScriptClassStatement(StringBuilder stringBuilder, String className,
                                                 String scriptExtend, List<String> scriptImplement) {
        stringBuilder.append(PUBLIC_KEY).append(" class ").append(className).append(" ");
        if (scriptExtend.length() > 0) {
            stringBuilder.append("extends ");
            stringBuilder.append(scriptExtend).append(" ");
        }
        if (scriptImplement.size() > 0) {
            stringBuilder.append("implements ");
            addStringInIterableIntoStringBuilderWithSymbol(stringBuilder, scriptImplement, " ");
        }
        stringBuilder.append('{').append('\n');
    }

    private static void commandLengthCheck(String scriptName, String[] list, int len, int lineCnt) {
         if (list.length < len) {
             logger.error("No enough length for command in line {} in script {}", lineCnt, scriptName);
             System.exit(1);
         }
    }

    private static void addStringInIterableIntoStringBuilderWithSymbol(StringBuilder sb, Iterable<String> iterable, String symbol) {
        for (String str: iterable) {
            sb.append(str).append(symbol);
        }
    }
}
