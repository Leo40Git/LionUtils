package adudecalledleo.lionutils;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.util.Identifier;

import java.util.Map;

/**
 * Constructs {@link Identifier}s in a single namespace. Ensures that identifiers with the same path are identical.<br>
 * A must-have for content mods.
 */
public class IdentifierFactory {
    private final String namespace;
    private final Map<String, Identifier> idMap = new Object2ObjectLinkedOpenHashMap<>();

    public IdentifierFactory(String namespace) {
        this.namespace = namespace;
    }

    public Identifier create(String path) {
        return idMap.computeIfAbsent(path, key -> new Identifier(namespace, key));
    }
}
