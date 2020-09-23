package adudecalledleo.lionutils.internal.inventory;

import adudecalledleo.lionutils.InitializerUtil;
import adudecalledleo.lionutils.inventory.InventoryUtil;
import adudecalledleo.lionutils.inventory.UnmodifiableSlotRef;
import net.minecraft.item.ItemStack;

public final class EmptySlotRef extends UnmodifiableSlotRef {
    public static final EmptySlotRef INSTANCE = new EmptySlotRef();

    private EmptySlotRef() {
        super(InventoryUtil.empty(), -1);
        InitializerUtil.singletonCheck(INSTANCE);
    }

    @Override
    public ItemStack getStack() {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }
}
