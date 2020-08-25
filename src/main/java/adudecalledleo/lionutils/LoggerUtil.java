package adudecalledleo.lionutils;

import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.message.*;
import org.apache.logging.log4j.spi.AbstractLogger;

/**
 * Helper class for configuring and obtaining {@link Logger}s.
 *
 * @since 2.0.0
 */
public final class LoggerUtil {
    private LoggerUtil() {
        InitializerUtil.badConstructor();
    }

    /**
     * <p>Creates a {@link MessageFactory} suited for the current environment.</p>
     * If this is a {@linkplain FabricLoader#isDevelopmentEnvironment() development environment}, simply returns the an
     * instance of the default message factory (which is {@link ParameterizedMessageFactory}).<br> Otherwise, returns a
     * special message factory that appends the logger's name to the start of the message (which also supports
     * parameters).
     *
     * @param loggerName
     *         logger name
     * @return message factory
     */
    public static MessageFactory createMessageFactory(String loggerName) {
        if (FabricLoader.getInstance().isDevelopmentEnvironment())
            return new ParameterizedMessageFactory();
        else
            return new PrefixedMessageFactory("[" + loggerName + "] ");
    }

    private static class PrefixedMessageFactory extends AbstractMessageFactory {
        private final String prefix;

        private PrefixedMessageFactory(String prefix) {
            this.prefix = prefix;
        }

        private String p(Object message) {
            return prefix + message;
        }

        @Override
        public Message newMessage(String message) {
            return new SimpleMessage(p(message));
        }

        @Override
        public Message newMessage(Object message) {
            return new SimpleMessage(p(message));
        }

        @Override
        public Message newMessage(CharSequence message) {
            return new SimpleMessage(p(message));
        }

        @Override
        public Message newMessage(String message, Object... params) {
            return new ParameterizedMessage(p(message), params);
        }
    }

    /**
     * Returns a {@link Logger} with the specified name.
     *
     * @param name
     *         logger name
     * @return the logger
     */
    public static Logger getLogger(String name) {
        return LogManager.getLogger(name, createMessageFactory(name));
    }

    /**
     * {@link Logger} that never actually prints messages.
     *
     * @since 4.0.0
     */
    public static final Logger NULL_LOGGER = new NullLogger();

    private static class NullLogger extends AbstractLogger {
        @Override
        public boolean isEnabled(Level level, Marker marker, Message message, Throwable t) {
            return false;
        }

        @Override
        public boolean isEnabled(Level level, Marker marker, CharSequence message, Throwable t) {
            return false;
        }

        @Override
        public boolean isEnabled(Level level, Marker marker, Object message, Throwable t) {
            return false;
        }

        @Override
        public boolean isEnabled(Level level, Marker marker, String message, Throwable t) {
            return false;
        }

        @Override
        public boolean isEnabled(Level level, Marker marker, String message) {
            return false;
        }

        @Override
        public boolean isEnabled(Level level, Marker marker, String message, Object... params) {
            return false;
        }

        @Override
        public boolean isEnabled(Level level, Marker marker, String message, Object p0) {
            return false;
        }

        @Override
        public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1) {
            return false;
        }

        @Override
        public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1, Object p2) {
            return false;
        }

        @Override
        public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1, Object p2,
                Object p3) {
            return false;
        }

        @Override
        public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1, Object p2,
                Object p3, Object p4) {
            return false;
        }

        @Override
        public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1, Object p2,
                Object p3, Object p4, Object p5) {
            return false;
        }

        @Override
        public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1, Object p2,
                Object p3, Object p4, Object p5, Object p6) {
            return false;
        }

        @Override
        public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1, Object p2,
                Object p3, Object p4, Object p5, Object p6, Object p7) {
            return false;
        }

        @Override
        public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1, Object p2,
                Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
            return false;
        }

        @Override
        public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1, Object p2,
                Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
            return false;
        }

        @Override
        public void logMessage(String fqcn, Level level, Marker marker, Message message, Throwable t) { }

        @Override
        public Level getLevel() {
            return Level.OFF;
        }
    }
}
