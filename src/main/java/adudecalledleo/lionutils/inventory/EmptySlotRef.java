package adudecalledleo.lionutils.inventory;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

/**
 * Represents an empty reference that does not refer to an {@link Inventory} or to a slot.
 *
 * @since 5.0.0
 */
public final class EmptySlotRef extends UnmodifiableSlotRef {
    /**
     * The singleton instance of this class.
     */
    public static final EmptySlotRef INSTANCE = new EmptySlotRef();

    private EmptySlotRef() {
        super(null, -1);
    }

    /**
     * <p>{@inheritDoc}</p>
     * Does nothing here.
     *
     * @return {@link ItemStack#EMPTY}
     */
    @Override
    public ItemStack getStack() {
        return ItemStack.EMPTY;
    }
}
