package adudecalledleo.lionutils.config;

import adudecalledleo.lionutils.ListUtil;
import adudecalledleo.lionutils.LoggerUtil;
import adudecalledleo.lionutils.serialize.GsonHolder;
import adudecalledleo.lionutils.serialize.ObjectFormat;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * Holds, loads and saves a POJO configuration.
 * @param <T> POJO configuration type
 * @since 4.0.0
 */
public class ConfigHolder<T extends Config> {
    /**
     * Represents a configuration event.
     */
    public enum Event {
        /**
         * <p>Signals that the configuration POJO has been loaded from the file (or the default POJO was reloaded).</p>
         * Note that it is recommended to make listeners not do anything if this event is invoked, since:
         * <ul>
         *     <li>{@link #VERIFY} will always be invoked after loading a configuration POJO.</li>
         *     <li>{@link Config#verify()} modifying the POJO's values is a valid side-effect of verification.</li>
         *     <li>If {@link Config#verify()} fails, the default configuration POJO will be reloaded after
         *     the {@code LOAD} event is invoked, without invoking it again.</li>
         * </ul>
         */
        LOAD,
        /**
         * Signals that the configuration POJO has been saved to the file.
         */
        SAVE,
        /**
         * Signals that the configuration POJO has been verified after being loaded from the file.
         * @since 5.0.0
         */
        VERIFY
    }

    private final Path configPath;
    private final Class<T> configType;
    private final Supplier<T> defaultFactory;
    private final ObjectFormat objectFormat;
    private final Logger logger;
    private final Marker marker;
    private final BiConsumer<Event, Throwable> exceptionHandler;

    private final List<BiConsumer<Event, T>> listeners = new ArrayList<>();
    private final List<BiConsumer<Event, T>> listenersToAdd = new ArrayList<>();
    private final List<BiConsumer<Event, T>> listenersToRemove = new ArrayList<>();

    private T config;

    private ConfigHolder(Path configPath, Class<T> configType, Supplier<T> defaultFactory, ObjectFormat objectFormat,
            Logger logger, Marker marker, BiConsumer<Event, Throwable> exceptionHandler) {
        this.configPath = configPath;
        this.configType = configType;
        this.defaultFactory = defaultFactory;
        this.objectFormat = objectFormat;
        this.logger = logger;
        this.marker = marker;
        this.exceptionHandler = exceptionHandler;
    }

    /**
     * Constructs a {@link Builder}.
     * @param configPath path to config file, relative to {@linkplain FabricLoader#getConfigDir() the main config directory}
     * @param configType type of config POJO
     * @param defaultFactory factory to create default POJO.<br>
     *                       this should <em>always</em> return a new instance.
     * @param <T> POJO configuration type
     * @return the builder instance
     * @since 4.1.0
     */
    public static <T extends Config> Builder<T> builder(Path configPath, Class<T> configType, Supplier<T> defaultFactory) {
        return new Builder<>(configPath, configType, defaultFactory);
    }

    /**
     * Constructs a {@link Builder}.
     * @param configName path to config file, relative to {@linkplain FabricLoader#getConfigDir() the main config directory}
     * @param configType type of config POJO
     * @param defaultFactory factory to create default POJO.<br>
     *                       this should <em>always</em> return a new instance.
     * @param <T> POJO configuration type
     * @return the builder instance
     * @since 4.1.0
     */
    public static <T extends Config> Builder<T> builder(String configName, Class<T> configType, Supplier<T> defaultFactory) {
        return new Builder<>(configName, configType, defaultFactory);
    }

    /**
     * Builds a {@link ConfigHolder} instance.
     * @param <T> POJO configuration type
     */
    public static class Builder<T extends Config> {
        private static final BiConsumer<Event, Throwable> NOP_EXCEPTION_HANDLER = (event, t) -> { };

        private final Path configPath;
        private final Class<T> configType;
        private final Supplier<T> defaultFactory;
        private ObjectFormat objectFormat = GsonHolder.FORMAT;
        private Logger logger = LoggerUtil.NULL_LOGGER;
        private Marker marker = null;
        private BiConsumer<Event, Throwable> exceptionHandler = NOP_EXCEPTION_HANDLER;

        private Builder(Path configPath, Class<T> configType, Supplier<T> defaultFactory) {
            this.configPath = FabricLoader.getInstance().getConfigDir().resolve(configPath);
            this.configType = configType;
            this.defaultFactory = defaultFactory;
        }

        private Builder(String configName, Class<T> configType, Supplier<T> defaultFactory) {
            this(Paths.get(configName), configType, defaultFactory);
        }

        /**
         * <p>Sets the {@link ObjectFormat} to use to save and load the configuration POJO.</p>
         * By default, {@link GsonHolder#FORMAT} is used.
         * @param objectFormat object format to use
         * @return this builder
         */
        public Builder<T> setObjectFormat(ObjectFormat objectFormat) {
            this.objectFormat = objectFormat;
            return this;
        }

        /**
         * <p>Sets the {@link Logger} to use.</p>
         * By default, a {@linkplain LoggerUtil#NULL_LOGGER null logger} is used.
         * @param logger logger to use
         * @return this builder
         */
        public Builder<T> setLogger(Logger logger) {
            this.logger = logger;
            return this;
        }

        /**
         * Sets the {@link Marker} to use.
         * @param marker marker to use
         * @return this builder
         * @since 5.0.0
         */
        public Builder<T> setMarker(Marker marker) {
            this.marker = marker;
            return this;
        }

        /**
         * <p>Sets the exception handler to call when an exception occurs.</p>
         * By default, a no-op implementation is used.
         * @param exceptionHandler exception handler to use
         * @return this builder
         * @since 5.0.0
         */
        public Builder<T> setExceptionHandler(BiConsumer<Event, Throwable> exceptionHandler) {
            this.exceptionHandler = exceptionHandler;
            return this;
        }

        /**
         * Constructs a {@link ConfigHolder}.
         * @return the configuration holder
         */
        public ConfigHolder<T> build() {
            return new ConfigHolder<>(configPath, configType, defaultFactory, objectFormat,
                    logger, marker, exceptionHandler);
        }
    }

    /**
     * Gets the configuration POJO.
     * @return the configuration POJO
     */
    public T get() {
        if (config == null)
            load();
        return config;
    }

    /**
     * Gets an instance of the default configuration POJO.
     * @return the default POJO
     */
    public T getDefault() {
        return defaultFactory.get();
    }

    /**
     * Loads the configuration POJO from the file.
     */
    public void load() {
        if (configPath.toFile().exists()) {
            if (load0()) {
                try {
                    config.verify();
                } catch (ConfigVerificationException e) {
                    logAndHandleException("Loaded invalid config from file \"" + configPath.getFileName().toString() +
                            "\", continuing with default values", e, Event.VERIFY);
                    config = getDefault();
                }
            }
            updateAndNotifyListeners(Event.VERIFY);
        } else {
            logger.info(marker, "Config file doesn't exist, continuing with default values");
            reset();
            save();
        }
    }

    // internal delegate that actually loads the POJO
    // returns true if config.verify() needs to be called, false otherwise
    private boolean load0() {
        boolean needsVerify = true;
        try (BufferedReader br = Files.newBufferedReader(configPath)) {
            config = objectFormat.read(br, configType);
        } catch (IOException e) {
            logAndHandleException("Failed loading config from file \"" + configPath.getFileName().toString() +
                    "\", continuing with default values", e, Event.LOAD);
            config = getDefault();
            needsVerify = false;
        }
        updateAndNotifyListeners(Event.LOAD);
        return needsVerify;
    }

    /**
     * Saves the configuration POJO to the file.
     */
    public void save() {
        try (BufferedWriter bw = Files.newBufferedWriter(configPath)) {
            objectFormat.write(config, bw);
        } catch (Exception e) {
            logAndHandleException("Failed saving config to file \"" + configPath.getFileName().toString() + "\"",
                    e, Event.SAVE);
        }
        updateAndNotifyListeners(Event.SAVE);
    }

    /**
     * Reloads the default configuration POJO.
     */
    public void reset() {
        config = getDefault();
        updateAndNotifyListeners(Event.LOAD);
        updateAndNotifyListeners(Event.VERIFY);
    }

    /**
     * Adds a listener that will be called when the config is loaded or saved.
     * @param listener listener to add
     */
    public void addListener(BiConsumer<Event, T> listener) {
        listenersToAdd.add(listener);
    }

    /**
     * Removes a listener. It will no longer be called when the config is loaded or saved.
     * @param listener listener to remove
     */
    public void removeListener(BiConsumer<Event, T> listener) {
        listenersToRemove.add(listener);
    }

    private void logAndHandleException(String msg, Throwable t, Event event) {
        logger.error(marker, msg, t);
        exceptionHandler.accept(event, t);
    }

    private void updateAndNotifyListeners(Event event) {
        ListUtil.addAndRemove(listeners, listenersToAdd, listenersToRemove);
        listeners.forEach(listener -> listener.accept(event, config));
    }
}
