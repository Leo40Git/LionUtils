package adudecalledleo.lionutils;

import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.*;

/**
 * Helper class for configuring and obtaining {@link Logger}s.
 */
public final class LoggerUtil {
    private LoggerUtil() {
        InitializerUtil.badConstructor();
    }

    /**
     * <p>Creates a {@link MessageFactory} suited for the current environment.</p>
     * If this is a {@linkplain FabricLoader#isDevelopmentEnvironment() development environment}, simply returns the
     * an instance of the default message factory (which is {@link ParameterizedMessageFactory}).<br>
     * Otherwise, returns a special message factory that appends the logger's name to the start of the message
     * (which also supports parameters).
     * @param loggerName logger name
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

        @Override
        public Message newMessage(String message) {
            return new SimpleMessage(prefix + message);
        }

        @Override
        public Message newMessage(Object message) {
            return new SimpleMessage(prefix + message);
        }

        @Override
        public Message newMessage(CharSequence message) {
            return new SimpleMessage(prefix + message);
        }

        @Override
        public Message newMessage(String message, Object... params) {
            return new ParameterizedMessage(prefix + message, params);
        }
    }

    /**
     * Returns a {@link Logger} with the specified name.
     * @param name logger name
     * @return the logger
     */
    public static Logger getLogger(String name) {
        return LogManager.getLogger(name, createMessageFactory(name));
    }
}
