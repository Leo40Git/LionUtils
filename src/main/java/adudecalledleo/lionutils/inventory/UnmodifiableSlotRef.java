package adudecalledleo.lionutils.inventory;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

import java.util.Objects;

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
     * @return the constructed slot reference
     */
    public static UnmodifiableSlotRef create(Inventory inventory, int slot) {
        Objects.requireNonNull(inventory, "inventory == null!");
        if (slot < 0 || slot >= inventory.size())
            throw new IndexOutOfBoundsException(
                    "slot index " + slot + " is out of bounds for inventory of size" + inventory.size());
        return new UnmodifiableSlotRef(inventory, slot);
    }

    protected UnmodifiableSlotRef(Inventory inventory, int slot) {
        super(inventory, slot);
    }

    /**
     * {@inheritDoc}<p>
     * Does nothing here.
     */
    @Override
    public void setStack(ItemStack stack) { }

    /**
     * {@inheritDoc}<p>
     * Does nothing here.
     */
    @Override
    public ItemStack splitStack(int amount) {
        return ItemStack.EMPTY;
    }

    /**
     * {@inheritDoc}<p>
     * Does nothing here.
     */
    @Override
    public ItemStack removeStack() {
        return ItemStack.EMPTY;
    }
}
