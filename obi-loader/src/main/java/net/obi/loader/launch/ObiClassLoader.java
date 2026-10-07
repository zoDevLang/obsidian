package net.obi.loader.launch;

import net.obi.loader.transform.ClassTransformer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/** Loads game classes itself so every one can go through the transformers first. */
public final class ObiClassLoader extends URLClassLoader {
    static {
        ClassLoader.registerAsParallelCapable();
    }

    /** These always come from the parent so loader classes stay shared. */
    private static final String[] PARENT_FIRST = {
            "java.", "javax.", "jdk.", "sun.", "com.sun.", "net.obi.loader."
    };

    private final List<ClassTransformer> transformers = new CopyOnWriteArrayList<>();

    public ObiClassLoader(URL[] urls, ClassLoader parent) {
        super("obi", urls, parent);
    }

    public void addTransformer(ClassTransformer transformer) {
        transformers.add(transformer);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            Class<?> c = findLoadedClass(name);
            if (c == null) {
                c = isParentFirst(name) ? super.loadClass(name, false) : loadGameClass(name);
            }
            if (resolve) resolveClass(c);
            return c;
        }
    }

    private Class<?> loadGameClass(String name) throws ClassNotFoundException {
        byte[] bytes = readClassBytes(name);
        if (bytes == null) {
            return super.loadClass(name, false);
        }
        bytes = applyTransformers(name, bytes);
        return defineClass(name, bytes, 0, bytes.length);
    }

    private byte[] applyTransformers(String name, byte[] bytes) {
        for (ClassTransformer t : transformers) {
            byte[] out = t.transform(name, bytes);
            if (out != null) bytes = out;
        }
        return bytes;
    }

    private byte[] readClassBytes(String name) {
        URL url = findResource(name.replace('.', '/') + ".class");
        if (url == null) return null;
        try (InputStream in = url.openStream()) {
            return in.readAllBytes();
        } catch (IOException e) {
            return null;
        }
    }

    private static boolean isParentFirst(String name) {
        for (String prefix : PARENT_FIRST) {
            if (name.startsWith(prefix)) return true;
        }
        return false;
    }
}
