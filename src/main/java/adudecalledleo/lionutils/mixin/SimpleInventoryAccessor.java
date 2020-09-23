package adudecalledleo.lionutils.mixin;

import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * Allows getting and setting the backing stack list.
 *
 * @since 2.0.0
 */
@Mixin(SimpleInventory.class)
public interface SimpleInventoryAccessor {
    /**
     * Gets the backing stack list.
     *
     * @return stack list
     */
    @Accessor
    DefaultedList<ItemStack> getStacks();

    /**
     * Sets the backing stack list.<p>
     * Make sure to update the {@linkplain #setSize(int) inventory's size} as well!
     *
     * @param stacks
     *         stack list
     */
    @Accessor
    void setStacks(DefaultedList<ItemStack> stacks);

    /**
     * Sets the inventory's size.
     *
     * @param size
     *         inventory size
     * @since 6.0.0
     */
    @Accessor
    void setSize(int size);
}
