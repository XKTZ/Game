package xktz.javarunner;


public class ImportClass {

    private String packageValue;

    private String className;

    public ImportClass(String packageValue, String className) {
        if (packageValue.charAt(packageValue.length() - 1) != '.') {
            packageValue += ".";
        }
        this.packageValue = packageValue;
        this.className = className;
    }

    public ImportClass(Iterable<String> packages, String className) {
        this.packageValue = join(packages, ".");
        this.className = className;
    }

    private String join(Iterable<String> list, String joiner) {
        StringBuilder result = new StringBuilder();
        // iterate to add
        for (String str : list) {
            result.append(str);
            result.append(joiner);
        }
        // change the string
        return result.toString();
    }

    public String getImportStr() {
        return this.packageValue + this.className;
    }
}
