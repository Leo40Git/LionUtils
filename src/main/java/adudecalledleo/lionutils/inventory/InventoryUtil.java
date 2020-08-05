package adudecalledleo.lionutils.inventory;

import adudecalledleo.lionutils.InitializerUtil;
import adudecalledleo.lionutils.mixin.SimpleInventoryAccessor;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Utilities for dealing with {@link Inventory}s and "raw inventories" ({@link DefaultedList}s of {@link ItemStack}s).
 */
public final class InventoryUtil {
    @Contract(value = " -> fail", pure = true)
    private InventoryUtil() {
        InitializerUtil.badConstructor();
    }

    /**
     * Wraps a "raw inventory" into an {@link Inventory}.
     * @param stacks raw inventory
     * @return the wrapper inventory
     */
    @SuppressWarnings("ConstantConditions")
    public static @NotNull Inventory of(DefaultedList<ItemStack> stacks) {
        SimpleInventory inv = new SimpleInventory(stacks.size());
        ((SimpleInventoryAccessor) inv).setStacks(stacks);
        return inv;
    }

    /**
     * Wraps a "raw inventory" into an {@link UnmodifiableInventory}.
     * @param stacks raw inventory
     * @return the wrapper inventory
     */
    @Contract("_ -> new")
    public static @NotNull UnmodifiableInventory unmodOf(DefaultedList<ItemStack> stacks) {
        return new UnmodifiableSimpleInventory(stacks);
    }
}
