package adudecalledleo.lionutils.inventory;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

/**
 * Represents an unmodifiable reference to a slot in an {@link Inventory}.
 *
 * @since 5.0.0
 */
public class UnmodifiableSlotRef extends SlotRef {
    /**
     * Constructs an {@code UnmodifiableSlotRef}.
     *
     * @param inventory
     *         inventory to refer to
     * @param slot
     *         index of slot to refer to
     */
    public UnmodifiableSlotRef(Inventory inventory, int slot) {
        super(inventory, slot);
    }

    /**
     * <p>{@inheritDoc}</p>
     * Does nothing here.
     *
     * @param stack
     *         {@inheritDoc}
     */
    @Override
    public void setStack(ItemStack stack) { }

    /**
     * <p>{@inheritDoc}</p>
     * Does nothing here.
     *
     * @param amount
     *         {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public ItemStack splitStack(int amount) {
        return ItemStack.EMPTY;
    }

    /**
     * <p>{@inheritDoc}</p>
     * Does nothing here.
     *
     * @return {@inheritDoc}
     */
    @Override
    public ItemStack removeStack() {
        return ItemStack.EMPTY;
    }
}
