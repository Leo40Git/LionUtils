package adudecalledleo.lionutils;

import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

/**
 * Function for logging.
 */
@FunctionalInterface
public interface LogFunction {
    void log(Level level, String message, Throwable throwable);

    default void log(Level level, String message) {
        log(level, message, null);
    }

    /**
     * Creates a {@link LogFunction} from a {@link Logger}.<br>
     * If this is executed in a {@linkplain FabricLoader#isDevelopmentEnvironment() development environment},
     * simply returns {@link Logger#log(Level, String, Throwable)}, otherwise returns a function that prefixes the message with
     * the logger's name.
     * @param logger base logger
     * @return log function
     */
    static LogFunction create(Logger logger) {
        if (FabricLoader.getInstance().isDevelopmentEnvironment())
            return logger::log;
        else {
            final String loggerName = logger.getName();
            return (level, message, throwable) -> logger.log(level, "[" + loggerName + "] " + message, throwable);
        }
    }
}
