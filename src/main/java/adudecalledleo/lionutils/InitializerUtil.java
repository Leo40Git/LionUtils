package adudecalledleo.lionutils;

/**
 * Helper class for initializer classes.
 *
 * @since 1.0.0
 */
public final class InitializerUtil {
    private InitializerUtil() {
        badConstructor();
    }

    /**
     * Throws an {@link UnsupportedOperationException}.<br> Intended for use in initializer class constructors to
     * prevent them from being constructed.
     */
    public static void badConstructor() {
        throw new UnsupportedOperationException("Tried to invoke constructor for initializer class");
    }
}
