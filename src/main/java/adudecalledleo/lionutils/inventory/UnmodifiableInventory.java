package adudecalledleo.lionutils.inventory;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

/**
 * Unmodifiable variant of {@link Inventory}.
 */
public interface UnmodifiableInventory extends Inventory {
    @Override
    default ItemStack removeStack(int slot, int amount) {
        return ItemStack.EMPTY;
    }

    @Override
    default ItemStack removeStack(int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    default void setStack(int slot, ItemStack stack) { }

    @Override
    default void clear() { }
}
