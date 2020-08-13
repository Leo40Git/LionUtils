package adudecalledleo.lionutils;

import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import static adudecalledleo.lionutils.serialize.GsonHolder.GSON;

/**
 * Holds a POJO configuration that is serialized and deserialized via JSON
 * ({@linkplain adudecalledleo.lionutils.serialize.GsonHolder#GSON Gson}, to be exact).
 * @param <T> POJO config type
 */
public class ConfigHolder<T> {
    /**
     * Config processing phase.
     */
    public enum Phase {
        /**
         * Before deserializing the config from a file.
         */
        PRE_LOAD,
        /**
         * After deserializing the config from a file.
         */
        POST_LOAD,
        /**
         * Before serializing the config to a file.
         */
        PRE_SAVE,
        /**
         * After serializing the config to a file.
         */
        POST_SAVE
    }

    private final Path configPath;
    private final Class<T> configType;
    private final Supplier<T> defaultFactory;
    private final Logger logger;

    private final List<BiConsumer<Phase, T>> listeners = new ArrayList<>();
    private final List<BiConsumer<Phase, T>> listenersToAdd = new ArrayList<>();
    private final List<BiConsumer<Phase, T>> listenersToRemove = new ArrayList<>();

    private T config;

    private ConfigHolder(Path configPath, Class<T> configType,
            Supplier<T> defaultFactory, Logger logger) {
        this.configPath = configPath;
        this.configType = configType;
        this.defaultFactory = defaultFactory;
        this.logger = logger;
    }

    /**
     * Constructs a {@link ConfigHolder}.
     * @param configPath path to config file, relative to {@linkplain FabricLoader#getConfigDir() the main config directory}
     * @param configType type of config POJO
     * @param defaultFactory factory to create default POJO
     * @param logger logger to use
     * @param <T> type of config POJO
     * @return the configuration handler
     */
    public static <T> ConfigHolder<T> create(Path configPath, Class<T> configType,
            Supplier<T> defaultFactory, Logger logger) {
        return new ConfigHolder<>(FabricLoader.getInstance().getConfigDir().resolve(configPath), configType,
                defaultFactory, logger);
    }

    /**
     * Constructs a {@link ConfigHolder}.
     * @param configName path to config file, relative to {@linkplain FabricLoader#getConfigDir() the main config directory}.<br>
     *                   If this doesn't end with ".json", it will be appended
     * @param configType type of config POJO
     * @param defaultFactory factory to create default POJO
     * @param logger logger to use
     * @param <T> type of config POJO
     * @return the configuration handler
     */
    public static <T> ConfigHolder<T> create(String configName, Class<T> configType,
            Supplier<T> defaultFactory, Logger logger) {
        if (!configName.endsWith(".json"))
            configName += ".json";
        return create(Paths.get(configName), configType, defaultFactory, logger);
    }

    /**
     * Gets the config POJO.
     * @return config POJO
     */
    public T get() {
        if (config == null)
            load();
        return config;
    }

    /**
     * Loads the config POJO from the file.
     */
    public void load() {
        if (configPath.toFile().exists()) {
            updateAndNotifyListeners(Phase.PRE_LOAD);
            try (BufferedReader br = Files.newBufferedReader(configPath)) {
                config = GSON.fromJson(br, configType);
            } catch (Exception e) {
                logger.error("Exception while loading config from file \"" + configPath.getFileName().toString() + "\", " +
                        "continuing with default values", e);
                config = defaultFactory.get();
            } finally {
                updateAndNotifyListeners(Phase.POST_LOAD);
            }
        } else {
            logger.info("Config file does not exist, continuing with default values");
            reset();
            save();
        }
    }

    /**
     * Saves the config POJO to the file.
     */
    public void save() {
        updateAndNotifyListeners(Phase.PRE_SAVE);
        try (BufferedWriter bw = Files.newBufferedWriter(configPath)) {
            GSON.toJson(config, bw);
        } catch (Exception e) {
            logger.error("Exception while saving config to file \"" + configPath.getFileName().toString() + "\"", e);
        } finally {
            updateAndNotifyListeners(Phase.POST_SAVE);
        }
    }

    /**
     * Loads the default config POJO.
     */
    public void reset() {
        updateAndNotifyListeners(Phase.PRE_LOAD);
        config = defaultFactory.get();
        updateAndNotifyListeners(Phase.PRE_LOAD);
    }

    /**
     * Adds a listener that will be called when the config is loaded or saved.
     * @param listener listener to add
     */
    public void addListener(BiConsumer<Phase, T> listener) {
        listenersToAdd.add(listener);
    }

    /**
     * Removes a listener. It will no longer be called when the config is loaded or saved.
     * @param listener listener to remove
     */
    public void removeListener(BiConsumer<Phase, T> listener) {
        listenersToRemove.add(listener);
    }

    private void updateAndNotifyListeners(Phase phase) {
        ListUtil.addAndRemove(listeners, listenersToAdd, listenersToRemove);
        for (BiConsumer<Phase, T> listener : listeners)
            listener.accept(phase, config);
    }
}
