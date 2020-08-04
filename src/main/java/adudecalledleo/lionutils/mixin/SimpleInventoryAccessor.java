package adudecalledleo.lionutils.mixin;

import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * Allows setting the backing stack list.
 */
@Mixin(SimpleInventory.class)
public interface SimpleInventoryAccessor {
    /**
     * Sets the backing stack list.
     * @param stacks stack list
     */
    @Accessor("stacks")
    void setStacks(DefaultedList<ItemStack> stacks);
}
