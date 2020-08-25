package adudecalledleo.lionutils.config;

/**
 * Base class for POJOs which don't require a {@link Config#verify()} implementation.
 *
 * @since 4.0.0
 */
public abstract class AbstractConfig implements Config {
    @SuppressWarnings("RedundantThrows")
    @Override
    public void verify() throws ConfigVerificationException { }
}
