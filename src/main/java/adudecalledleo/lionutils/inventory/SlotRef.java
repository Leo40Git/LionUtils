package adudecalledleo.lionutils.inventory;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

/**
 * Represents a reference to a slot in an {@link Inventory}.
 *
 * @since 5.0.0
 */
public class SlotRef {
    /**
     * The {@link Inventory} being referred to.
     */
    public final Inventory inventory;
    /**
     * The index of the slot being referred to.
     */
    public final int slot;

    /**
     * Constructs a {@code SlotRef}.
     *
     * @param inventory
     *         inventory to refer to
     * @param slot
     *         index of slot to refer to
     */
    public SlotRef(Inventory inventory, int slot) {
        this.inventory = inventory;
        this.slot = slot;
    }

    /**
     * Fetches the stack stored in the referenced slot.
     *
     * @return the stack in the slot
     */
    public ItemStack getStack() {
        return inventory.getStack(slot);
    }

    /**
     * Stores the stack in the referenced slot.
     *
     * @param stack
     *         new stack to store
     */
    public void setStack(ItemStack stack) {
        inventory.setStack(slot, stack);
    }

    /**
     * Removes a specific number of items from the referenced slot.
     *
     * @param amount
     *         amount to remove
     * @return the removed items as a stack
     */
    public ItemStack splitStack(int amount) {
        return inventory.removeStack(slot, amount);
    }

    /**
     * Removes the stack stored in the referenced slot.
     *
     * @return the stack that was previously stored in the slot
     */
    public ItemStack removeStack() {
        return inventory.removeStack(slot);
    }
}
