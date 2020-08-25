package adudecalledleo.lionutils.config;

/**
 * Signals that a {@linkplain Config configuration POJO} is invalid.
 *
 * @since 4.0.0
 */
public class ConfigVerificationException extends Exception {
    /**
     * Constructs a {@code ConfigVerificationException}.
     */
    public ConfigVerificationException() {
        super();
    }

    /**
     * Constructs a {@code ConfigVerificationException} with the specified message.
     *
     * @param message
     *         message
     */
    public ConfigVerificationException(String message) {
        super(message);
    }

    /**
     * Constructs a {@code ConfigVerificationException} with the specified message and cause.
     *
     * @param message
     *         message
     * @param cause
     *         cause
     */
    public ConfigVerificationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a {@code ConfigVerificationException} with the specified cause.
     *
     * @param cause
     *         cause
     */
    public ConfigVerificationException(Throwable cause) {
        super(cause);
    }
}
