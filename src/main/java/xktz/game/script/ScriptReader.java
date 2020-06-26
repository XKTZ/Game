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

    public static String readScript(String scriptPath, String scriptName)
            throws IOException {
        // create the reader
        BufferedReader reader = new BufferedReader(new FileReader(scriptPath));
        // String builder for import
        StringBuilder importValue = new StringBuilder();
        StringBuilder codeValue = new StringBuilder();
        // The implements list and the extends
        List<String> implementsList = new ArrayList<>();
        String extendsObj = "";
        // init the code value
        // starts code
        boolean startsCode = false;
        // temp
        String temp;
        // line count
        int lineCount = 1;
        // read the file
        while ((temp = reader.readLine()) != null) {
            // trim the temp
            String trimedTemp = temp.trim();
            // if length == 0, continue
            if (trimedTemp.length() == 0) {continue;};
            // if it starts with #, do the command
            if (!startsCode && trimedTemp.charAt(0) == COMMAND_SYMBOL) {
                // delete the command symbol and trim again
                trimedTemp = trimedTemp.replace("#", "").trim().replaceAll("[ ]+", " ");
                // split with space
                String[] commands = trimedTemp.split(" ");
                // check if there is command, if not, continue
                if (commands.length == 0) {
                    continue;
                }
                String commandKey = commands[0].toLowerCase();
                // check different commands
                switch (commandKey) {
                    case COMMAND_IMPORT:
                        commandLengthCheck(scriptName, commands, 2, lineCount);
                        // check if there is enough length, if there is no, output and exit
                        // if it is import, add import
                        importValue.append("import ").append(commands[1]).append(END_LINE).append('\n');
                        break;
                    case COMMAND_EXTEND:
                        commandLengthCheck(scriptName, commands, 2, lineCount);
                        // if it is extend
                        // change the extend value
                        extendsObj = commands[1];
                        break;
                    case COMMAND_IMPLEMENT:
                        commandLengthCheck(scriptName, commands, 2, lineCount);
                        // if it is implement, add the implement value
                        implementsList.add(commands[1]);
                        break;
                    case COMMAND_END_COMMAND_1:
                    case COMMAND_END_COMMAND_2:
                        startsCode = true;
                        // init the code part
                        initScriptClassStatement(codeValue, scriptName, extendsObj, implementsList);
                        break;
                }
            } else {
                // if it is not an empty line
                if (temp.length() > 0) {
                    // add into code
                    codeValue.append(temp).append('\n');
                }
            }
            lineCount ++;
        }
        return new StringBuilder().append(importValue).append(codeValue).append('}').toString();
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
