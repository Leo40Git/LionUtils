package adudecalledleo.lionutils.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

/**
 * Immutable variant of {@link PassThroughInventory}. The backing list will not be modified by any operation,
 * excluding possible changes to the stacks contained in the list.
 */
public class ImmutablePassThroughInventory extends PassThroughInventory {
    public ImmutablePassThroughInventory(DefaultedList<ItemStack> stacks) {
        super(stacks);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeStack(int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setStack(int slot, ItemStack stack) { }

    @Override
    public void clear() { }
}
