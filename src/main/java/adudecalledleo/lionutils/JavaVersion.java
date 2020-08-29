package adudecalledleo.lionutils;

public final class JavaVersion {
    private JavaVersion() {
        InitializerUtil.badConstructor();
    }

    private static final int value;

    static {
        String version = System.getProperty("java.version");
        if (version.startsWith("1.")) {
            version = version.substring(2, 3);
        } else {
            int dot = version.indexOf(".");
            if (dot != -1) { version = version.substring(0, dot); }
        }
        value = Integer.parseInt(version);
    }

    public static int get() {
        return value;
    }
}
