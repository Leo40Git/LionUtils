package adudecalledleo.lionutils.item;

import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Helper class for building a map of {@code Enchantment}s to levels, for use with
 * {@link net.minecraft.enchantment.EnchantmentHelper#set(Map, ItemStack) EnchantmentHelper.set(Map, ItemStack)}.
 *
 * @since 6.0.0
 */
public final class EnchantMapBuilder {
    private final Object2IntArrayMap<Enchantment> map;

    private EnchantMapBuilder() {
        map = new Object2IntArrayMap<>();
    }

    /**
     * Creates a new {@code EnchantMapBuilder}.
     * @return new builder instance
     */
    public static EnchantMapBuilder create() {
        return new EnchantMapBuilder();
    }

    /**
     * Adds an enchantment-level pair entry to the builder.
     * @param enchantment enchantment
     * @param level level of enchantment
     */
    public void add(Enchantment enchantment, int level) {
        map.put(enchantment, level);
    }

    /**
     * Builds the final {@code Enchantment}s to levels map.
     * @return the {@code Enchantment}s to levels map
     */
    public Map<Enchantment, Integer> build() {
        HashMap<Enchantment, Integer> finalMap = new HashMap<>();
        for (Object2IntArrayMap.Entry<Enchantment> entry : map.object2IntEntrySet())
            finalMap.put(entry.getKey(), entry.getIntValue());
        return finalMap;
    }

    /**
     * Creates a {@linkplain Collections#singletonMap(Object, Object) singleton map} of
     * {@code Enchantment}s to levels containing the specified entry.
     * @param enchantment enchantment
     * @param level level of enchantment
     * @return the {@code Enchantment}s to levels map
     */
    public static Map<Enchantment, Integer> of(Enchantment enchantment, int level) {
        return Collections.singletonMap(enchantment, level);
    }
}
