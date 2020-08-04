package adudecalledleo.lionutils;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.function.Function;

/**
 * Constructs {@link Identifier}s in a single namespace. Ensures that identifiers with the same path are identical.<br>
 * A must-have for content mods.
 */
public class IdentifierFactory {
    private final Map<String, Identifier> idsByPath = new Object2ObjectLinkedOpenHashMap<>();
    private final Function<String, Identifier> mapper;

    /**
     * Constructs a new {@link IdentifierFactory}.
     * @param namespace factory namespace
     */
    public IdentifierFactory(String namespace) {
        mapper = path -> new Identifier(namespace, path);
    }

    /**
     * Gets an {@link Identifier}. If the specified identifier doesn't exist yet, creates it.
     * @param path path of identifier
     * @return identifier with factory namespace and specified path
     */
    public Identifier get(String path) {
        return idsByPath.computeIfAbsent(path, mapper);
    }
}
