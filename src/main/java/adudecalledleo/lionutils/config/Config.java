package adudecalledleo.lionutils.config;

/**
 * Represents a configuration POJO.
 * @since 4.0.0
 */
public interface Config {
    /**
     * <p>Verifies the information stored in this POJO.</p>
     * <p>This is called by {@link ConfigHolder} after loading the POJO from a file or reloading the default POJO,
     * before any other listeners are notified.</p>
     * If this throws a {@link ConfigVerificationException}, the default configuration POJO will be reloaded.
     * @throws ConfigVerificationException if this POJO is invalid.
     */
    void verify() throws ConfigVerificationException;
}
