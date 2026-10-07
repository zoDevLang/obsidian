package net.obi.loader.transform;

/** Hook for changing classes as the game loads them. The patcher plugs in here. */
public interface ClassTransformer {
    /**
     * @param className dotted name, e.g. net.minecraft.client.Minecraft
     * @param classBytes the original class file
     * @return new class bytes, or null if this transformer leaves the class alone
     */
    byte[] transform(String className, byte[] classBytes);
}
