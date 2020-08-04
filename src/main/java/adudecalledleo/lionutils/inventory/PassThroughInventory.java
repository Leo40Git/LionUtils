package adudecalledleo.lionutils.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

/**
 * {@link Inventory} implementation that's backed by an external {@link DefaultedList} of {@link ItemStack}s.<br>
 * Useful when dealing with player inventories, which are raw lists.
 */
public class PassThroughInventory implements Inventory {
    protected final DefaultedList<ItemStack> stacks;

    public PassThroughInventory(DefaultedList<ItemStack> stacks) {
        this.stacks = stacks;
    }

    @Override
    public int size() {
        return stacks.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : stacks)
            if (!stack.isEmpty())
                return false;
        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        return slot >= 0 && slot < size() ? stacks.get(slot) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return Inventories.splitStack(stacks, slot, amount);
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(stacks, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        if (slot >= 0 && slot < size())
            stacks.set(slot, stack);
    }

    @Override
    public void markDirty() { }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {
        stacks.clear();
        markDirty();
    }
}
