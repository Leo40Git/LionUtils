package adudecalledleo.lionutils.inventory;

import adudecalledleo.lionutils.internal.inventory.EmptySlotRef;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Clearable;

import java.util.Objects;

/**
 * Represents a reference to a slot in an {@link Inventory}.
 *
 * @since 5.0.0
 */
public class SlotRef implements Clearable {
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
     * @return the constructed slot reference
     */
    public static SlotRef create(Inventory inventory, int slot) {
        Objects.requireNonNull(inventory, "inventory == null!");
        if (slot < 0 || slot >= inventory.size())
            throw new IllegalArgumentException(
                    "slot index " + slot + " is out of bounds for inventory of size" + inventory.size());
        return new SlotRef(inventory, slot);
    }

    /**
     * Constructs an empty {@code SlotRef}.
     *
     * @return the empty {@code SlotRef}
     *
     * @since 6.0.0
     */
    public static UnmodifiableSlotRef empty() {
        return EmptySlotRef.INSTANCE;
    }

    protected SlotRef(Inventory inventory, int slot) {
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

    /**
     * Checks if this reference is empty.
     *
     * @return {@code true} if reference is empty, {@code false} otherwise.
     */
    public boolean isEmpty() {
        return false;
    }

    /**
     * Delegates to {@link #removeStack()}.
     */
    @Override
    public void clear() {
        removeStack();
    }
}
