package adudecalledleo.lionutils;

import org.jetbrains.annotations.Contract;

/**
 * Helper class for initializer classes.
 */
public final class InitializerUtil {
    @Contract(value = " -> fail", pure = true)
    private InitializerUtil() {
        badConstructor();
    }

    /**
     * Throws an {@link UnsupportedOperationException}.<br>
     * Intended for use in initializer class constructors to prevent them from being constructed.
     */
    @Contract(value = " -> fail", pure = true)
    public static void badConstructor() {
        throw new UnsupportedOperationException("Tried to invoke constructor for initializer class");
    }
}
