package adudecalledleo.lionutils;

import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

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
         * Deserializing the config from a file.
         */
        LOAD,
        /**
         * Serializing the config to a file.
         */
        SAVE
    }

    private final Path configPath;
    private final Class<T> configType;
    private final Supplier<T> defaultFactory;
    private final BiConsumer<Phase, Exception> exceptionHandler;

    private final List<BiConsumer<Phase, T>> listeners = new ArrayList<>();
    private final List<BiConsumer<Phase, T>> listenersToAdd = new ArrayList<>();
    private final List<BiConsumer<Phase, T>> listenersToRemove = new ArrayList<>();

    private T config;

    private ConfigHolder(Path configPath, Class<T> configType,
            Supplier<T> defaultFactory, BiConsumer<Phase, Exception> exceptionHandler) {
        this.configPath = configPath;
        this.configType = configType;
        this.defaultFactory = defaultFactory;
        this.exceptionHandler = exceptionHandler;
    }

    /**
     * Creates a default exception handler that outputs to a {@link Logger}.
     * @param logger logger to output to
     * @return the exception handler
     */
    @Contract(pure = true)
    public static @NotNull BiConsumer<Phase, Exception> createExceptionHandler(Logger logger) {
        return (phase, e) -> {
            String msg = "Exception in config holder";
            switch (phase) {
            case LOAD:
                msg = "Exception while loading config, continuing with default values";
                break;
            case SAVE:
                msg = "Exception while saving config";
                break;
            }
            logger.error(msg, e);
        };
    }

    /**
     * Constructs a {@link ConfigHolder}.
     * @param configPath path to config file, relative to {@linkplain FabricLoader#getConfigDir() the main config directory}
     * @param configType type of config POJO
     * @param defaultFactory factory to create default POJO
     * @param exceptionHandler exception handler
     * @param <T> type of config POJO
     * @return the configuration handler
     */
    @Contract("_, _, _, _ -> new")
    public static <T> @NotNull ConfigHolder<T> create(Path configPath, Class<T> configType,
            Supplier<T> defaultFactory, BiConsumer<Phase, Exception> exceptionHandler) {
        return new ConfigHolder<>(FabricLoader.getInstance().getConfigDir().resolve(configPath), configType,
                defaultFactory, exceptionHandler);
    }

    /**
     * Constructs a {@link ConfigHolder}.
     * @param configName path to config file, relative to {@linkplain FabricLoader#getConfigDir() the main config directory}.<br>
     *                   If this doesn't end with ".json", it will be appended
     * @param configType type of config POJO
     * @param defaultFactory factory to create default POJO
     * @param exceptionHandler exception handler
     * @param <T> type of config POJO
     * @return the configuration handler
     */
    public static <T> @NotNull ConfigHolder<T> create(String configName, Class<T> configType,
            Supplier<T> defaultFactory, BiConsumer<Phase, Exception> exceptionHandler) {
        if (!configName.endsWith(".json"))
            configName += ".json";
        return create(Paths.get(configName), configType, defaultFactory, exceptionHandler);
    }

    /**
     * Gets the config POJO.
     * @return config POJO
     */
    public T getConfig() {
        if (config == null)
            loadConfig();
        return config;
    }

    /**
     * Loads the config POJO from the file.
     */
    public void loadConfig() {
        if (configPath.toFile().exists()) {
            try (BufferedReader br = Files.newBufferedReader(configPath)) {
                config = GSON.fromJson(br, configType);
            } catch (Exception e) {
                exceptionHandler.accept(Phase.LOAD, e);
                config = defaultFactory.get();
            } finally {
                updateAndNotifyListeners(Phase.LOAD);
            }
        } else {
            config = defaultFactory.get();
            saveConfig();
        }
    }

    /**
     * Saves the config POJO to the file.
     */
    public void saveConfig() {
        try (BufferedWriter bw = Files.newBufferedWriter(configPath)) {
            GSON.toJson(config, bw);
        } catch (Exception e) {
            exceptionHandler.accept(Phase.SAVE, e);
        } finally {
            updateAndNotifyListeners(Phase.SAVE);
        }
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
        listeners.addAll(listenersToAdd);
        listenersToAdd.clear();
        listeners.removeAll(listenersToRemove);
        listenersToRemove.clear();
        for (BiConsumer<Phase, T> listener : listeners)
            listener.accept(phase, config);
    }
}
