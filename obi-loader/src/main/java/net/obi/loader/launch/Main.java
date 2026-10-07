package net.obi.loader.launch;

import net.obi.loader.Log;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/** Entry point. The version json points mainClass here. */
public final class Main {
    private static final String MC_MAIN = "net.minecraft.client.main.Main";

    private Main() {}

    public static void main(String[] args) throws Throwable {
        Log.info("obi loader booting");

        ObiClassLoader loader = new ObiClassLoader(classpathUrls(), Main.class.getClassLoader());
        Thread.currentThread().setContextClassLoader(loader);

        // later: load obi mods + register patcher transformers here

        Class<?> mc = Class.forName(MC_MAIN, true, loader);
        Method main = mc.getMethod("main", String[].class);
        Log.info("launching minecraft");
        try {
            main.invoke(null, (Object) args);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    private static URL[] classpathUrls() throws Exception {
        List<URL> urls = new ArrayList<>();
        for (String entry : System.getProperty("java.class.path").split(File.pathSeparator)) {
            if (!entry.isEmpty()) urls.add(new File(entry).toURI().toURL());
        }
        return urls.toArray(new URL[0]);
    }
}
