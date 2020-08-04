package adudecalledleo.lionutils.inventory;

import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

/**
 * Simple implementation of an unmodifiable inventory.
 * @see SimpleInventory
 * @see UnmodifiableInventory
 */
public class UnmodifiableSimpleInventory extends SimpleInventory implements UnmodifiableInventory {
    public UnmodifiableSimpleInventory(ItemStack... stacks) {
        super(stacks);
    }

    public UnmodifiableSimpleInventory(DefaultedList<ItemStack> stacks) {
        this(stacks.toArray(new ItemStack[0]));
    }
}
