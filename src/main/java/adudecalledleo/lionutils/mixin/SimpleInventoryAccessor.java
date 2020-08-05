package adudecalledleo.lionutils.mixin;

import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * Allows getting and setting the backing stack list.
 */
@Mixin(SimpleInventory.class)
public interface SimpleInventoryAccessor {
    /**
     * Gets the backing stack list.
     * @return stack list
     */
    @Accessor
    DefaultedList<ItemStack> getStacks();
    /**
     * Sets the backing stack list.
     * @param stacks stack list
     */
    @Accessor
    void setStacks(DefaultedList<ItemStack> stacks);
}
