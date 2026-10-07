package net.obi.loader;

public final class Log {
    private Log() {}

    public static void info(String msg) {
        System.out.println("[obi] " + msg);
    }

    public static void error(String msg, Throwable t) {
        System.err.println("[obi] ERROR: " + msg);
        if (t != null) t.printStackTrace();
    }
}
