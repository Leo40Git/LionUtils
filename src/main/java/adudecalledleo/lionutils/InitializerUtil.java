package adudecalledleo.lionutils;

/**
 * Helper class for initializer classes and other special classes, such as singletons.
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

    /**
     * If {@code obj} is non-null, throws an {@link UnsupportedOperationException}<br> Intended for use in singleton
     * class constructors to prevent them from being constructed again.
     *
     * @param obj
     *         object to check
     * @param <T>
     *         type of object to check
     * @since 5.0.0
     */
    public static <T> void singletonCheck(T obj) {
        if (obj != null)
            throw new UnsupportedOperationException("Tried to construct another instance of singleton class");
    }
}
