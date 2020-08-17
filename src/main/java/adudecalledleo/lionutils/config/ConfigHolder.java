package adudecalledleo.lionutils.config;

import adudecalledleo.lionutils.ListUtil;
import adudecalledleo.lionutils.LoggerUtil;
import adudecalledleo.lionutils.serialize.GsonHolder;
import adudecalledleo.lionutils.serialize.ObjectFormat;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.Logger;

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
 * @param <T> POJO config type
 * @since 4.0.0
 */
public class ConfigHolder<T extends Config> {
    /**
     * Represents a configuration event.
     */
    public enum Event {
        /**
         * Signals that the configuration POJO has been loaded from the file.
         */
        LOAD,
        /**
         * Signals that the configuration POJO has been saved to the file.
         */
        SAVE
    }

    private final Path configPath;
    private final Class<T> configType;
    private final Supplier<T> defaultFactory;
    private final Logger logger;
    private final ObjectFormat objectFormat;

    private final List<BiConsumer<Event, T>> listeners = new ArrayList<>();
    private final List<BiConsumer<Event, T>> listenersToAdd = new ArrayList<>();
    private final List<BiConsumer<Event, T>> listenersToRemove = new ArrayList<>();

    private T config;

    private ConfigHolder(Path configPath, Class<T> configType,
            Supplier<T> defaultFactory, Logger logger, ObjectFormat objectFormat) {
        this.configPath = configPath;
        this.configType = configType;
        this.defaultFactory = defaultFactory;
        this.logger = logger;
        this.objectFormat = objectFormat;
    }

    /**
     * Builds a {@link ConfigHolder} instance.
     * @param <T> type of config POJO
     */
    public static class Builder<T extends Config> {
        private final Path configPath;
        private final Class<T> configType;
        private final Supplier<T> defaultFactory;
        private Logger logger;
        private ObjectFormat objectFormat;

        /**
         * Constructs a {@code Builder}.
         * @param configPath path to config file, relative to {@linkplain FabricLoader#getConfigDir() the main config directory}
         * @param configType type of config POJO
         * @param defaultFactory factory to create default POJO.<br>
         *                       this should <em>always</em> return a new instance.
         */
        public Builder(Path configPath, Class<T> configType, Supplier<T> defaultFactory) {
            this.configPath = FabricLoader.getInstance().getConfigDir().resolve(configPath);
            this.configType = configType;
            this.defaultFactory = defaultFactory;
            logger = LoggerUtil.NULL_LOGGER;
            objectFormat = GsonHolder.FORMAT;
        }

        /**
         * Constructs a {@code Builder}.
         * @param configName path to config file, relative to {@linkplain FabricLoader#getConfigDir() the main config directory}
         * @param configType type of config POJO
         * @param defaultFactory factory to create default POJO
         */
        public Builder(String configName, Class<T> configType, Supplier<T> defaultFactory) {
            this(Paths.get(configName), configType, defaultFactory);
        }

        /**
         * <p>Sets the {@link Logger} to use.</p>
         * By default, a {@linkplain LoggerUtil#NULL_LOGGER null logger} is used.
         * @param logger logger to use
         */
        public void setLogger(Logger logger) {
            this.logger = logger;
        }

        /**
         * <p>Sets the {@link ObjectFormat} to use to save and load the configuration POJO.</p>
         * By default, {@link GsonHolder#FORMAT} is used.
         * @param objectFormat object format to use
         */
        public void setObjectFormat(ObjectFormat objectFormat) {
            this.objectFormat = objectFormat;
        }

        /**
         * Constructs a {@link ConfigHolder}.
         * @return the configuration holder
         */
        public ConfigHolder<T> build() {
            return new ConfigHolder<>(configPath, configType, defaultFactory, logger, objectFormat);
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
            try {
                config = objectFormat.read(Files.newBufferedReader(configPath), configType);
                if (config != null)
                    config.verify();
            } catch (IOException e) {
                logger.error("Failed loading config from file \"" + configPath.getFileName().toString() +
                        "\", continuing with default values", e);
                config = getDefault();
            } catch (ConfigVerificationException e) {
                logger.error("Loaded invalid config from file \"" + configPath.getFileName().toString() +
                        "\", continuing with default values", e);
                config = getDefault();
            } finally {
                if (config == null)
                    config = getDefault();
                updateAndNotifyListeners(Event.LOAD);
            }
        } else {
            logger.info("Config file doesn't exist, continuing with default values");
            reset();
            save();
        }
    }

    /**
     * Saves the configuration POJO to the file.
     */
    public void save() {
        try (BufferedWriter bw = Files.newBufferedWriter(configPath)) {
            objectFormat.write(config, bw);
        } catch (Exception e) {
            logger.error("Failed saving config to file \"" + configPath.getFileName().toString() + "\"", e);
        } finally {
            updateAndNotifyListeners(Event.SAVE);
        }
    }

    /**
     * Reloads the default configuration POJO.
     */
    public void reset() {
        config = getDefault();
        updateAndNotifyListeners(Event.LOAD);
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

    private void updateAndNotifyListeners(Event event) {
        ListUtil.addAndRemove(listeners, listenersToAdd, listenersToRemove);
        listeners.forEach(listener -> listener.accept(event, config));
    }
}
