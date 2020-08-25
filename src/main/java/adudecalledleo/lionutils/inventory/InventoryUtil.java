package adudecalledleo.lionutils.inventory;

import adudecalledleo.lionutils.InitializerUtil;
import adudecalledleo.lionutils.mixin.SimpleInventoryAccessor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

/**
 * Utilities for dealing with {@link Inventory}s and "raw inventories" ({@link DefaultedList}s of {@link ItemStack}s).
 *
 * @since 2.0.0
 */
public final class InventoryUtil {
    private InventoryUtil() {
        InitializerUtil.badConstructor();
    }

    /**
     * Wraps a "raw inventory" into an {@link Inventory}.
     *
     * @param stacks
     *         stack list
     * @return the wrapper inventory
     */
    @SuppressWarnings("ConstantConditions")
    public static Inventory of(DefaultedList<ItemStack> stacks) {
        SimpleInventory inv = new SimpleInventory(stacks.size());
        ((SimpleInventoryAccessor) inv).setStacks(stacks);
        return inv;
    }

    /**
     * Wraps a "raw inventory" into an {@link UnmodifiableInventory}.
     *
     * @param stacks
     *         stack list
     * @return the wrapper inventory
     */
    public static UnmodifiableInventory unmodOf(DefaultedList<ItemStack> stacks) {
        return new UnmodifiableSimpleInventory(stacks);
    }

    /**
     * Wraps a standard {@link Inventory} into an {@link UnmodifiableInventory}.
     *
     * @param inventory
     *         inventory
     * @return the wrapper inventory
     */
    public static UnmodifiableInventory unmodOf(Inventory inventory) {
        if (inventory instanceof SimpleInventoryAccessor)
            return unmodOf(((SimpleInventoryAccessor) inventory).getStacks());
        return new UnmodifiableDelegatingInventory(inventory);
    }

    /**
     * Creates a 0-size inventory.
     *
     * @return the empty inventory.
     *
     * @since 5.0.0
     */
    public static UnmodifiableInventory empty() {
        return new EmptyInventory();
    }

    private static class UnmodifiableSimpleInventory extends SimpleInventory implements UnmodifiableInventory {
        public UnmodifiableSimpleInventory(DefaultedList<ItemStack> stacks) {
            super(stacks.size());
            ((SimpleInventoryAccessor) this).setStacks(stacks);
        }

        @Override
        public ItemStack removeStack(int slot, int amount) {
            return UnmodifiableInventory.super.removeStack(slot, amount);
        }

        @Override
        public ItemStack removeStack(int slot) {
            return UnmodifiableInventory.super.removeStack(slot);
        }

        @Override
        public void setStack(int slot, ItemStack stack) {
            UnmodifiableInventory.super.setStack(slot, stack);
        }

        @Override
        public void clear() {
            UnmodifiableInventory.super.clear();
        }
    }

    private static class UnmodifiableDelegatingInventory implements UnmodifiableInventory {
        private final Inventory delegate;

        private UnmodifiableDelegatingInventory(Inventory delegate) {
            this.delegate = delegate;
        }

        @Override
        public int size() {
            return delegate.size();
        }

        @Override
        public boolean isEmpty() {
            return delegate.isEmpty();
        }

        @Override
        public ItemStack getStack(int slot) {
            return delegate.getStack(slot);
        }

        @Override
        public void markDirty() {
            delegate.markDirty();
        }

        @Override
        public boolean canPlayerUse(PlayerEntity player) {
            return delegate.canPlayerUse(player);
        }
    }

    private static class EmptyInventory implements UnmodifiableInventory {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public ItemStack getStack(int slot) {
            return ItemStack.EMPTY;
        }

        @Override
        public void markDirty() { }

        @Override
        public boolean canPlayerUse(PlayerEntity player) {
            return true;
        }
    }
}
