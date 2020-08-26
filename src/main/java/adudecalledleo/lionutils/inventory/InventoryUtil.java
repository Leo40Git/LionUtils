package adudecalledleo.lionutils.inventory;

import adudecalledleo.lionutils.InitializerUtil;
import adudecalledleo.lionutils.mixin.SimpleInventoryAccessor;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

import java.util.Arrays;
import java.util.stream.Stream;

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
     * @return the empty inventory
     *
     * @since 5.0.0
     */
    public static UnmodifiableInventory empty() {
        return new EmptyInventory();
    }

    /**
     * Concatenates several inventories into a single combined inventory.
     *
     * @param inventories
     *         inventories to concatenate
     * @return the combined inventory
     *
     * @since 5.0.0
     */
    public static Inventory concat(Inventory... inventories) {
        return new ConcatInventory(inventories);
    }

    /**
     * Concatenates several inventories into a single unmodifiable combined inventory.
     *
     * @param inventories
     *         inventories to concatenate
     * @return the combined inventory
     *
     * @since 5.0.0
     */
    public static UnmodifiableInventory unmodConcat(Inventory... inventories) {
        return new UnmodifiableConcatInventory(inventories);
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

    private static class ConcatInventory implements Inventory {
        private final Inventory[] inventories;
        private final Int2ObjectMap<SlotRef> slotRefCache = new Int2ObjectArrayMap<>();

        public ConcatInventory(Inventory[] inventories) {
            this.inventories = inventories;
        }

        private Stream<Inventory> invStream() {
            return Arrays.stream(inventories);
        }

        private SlotRef locateSlot(int slot) {
            return slotRefCache.computeIfAbsent(slot, key -> {
                for (Inventory inventory : inventories) {
                    if (key >= inventory.size()) {
                        key -= inventory.size();
                        continue;
                    }
                    return new SlotRef(inventory, key);
                }
                return EmptySlotRef.INSTANCE;
            });
        }

        @Override
        public int size() {
            return invStream().mapToInt(Inventory::size).sum();
        }

        @Override
        public boolean isEmpty() {
            return invStream().allMatch(Inventory::isEmpty);
        }

        @Override
        public ItemStack getStack(int slot) {
            return locateSlot(slot).getStack();
        }

        @Override
        public ItemStack removeStack(int slot, int amount) {
            return locateSlot(slot).splitStack(amount);
        }

        @Override
        public ItemStack removeStack(int slot) {
            return locateSlot(slot).removeStack();
        }

        @Override
        public void setStack(int slot, ItemStack stack) {
            locateSlot(slot).setStack(stack);
        }

        @Override
        public void markDirty() {
            invStream().forEach(Inventory::markDirty);
        }

        @Override
        public boolean canPlayerUse(PlayerEntity player) {
            return invStream().allMatch(inv -> inv.canPlayerUse(player));
        }

        @Override
        public void clear() {
            invStream().forEach(Inventory::clear);
        }
    }

    private static class UnmodifiableConcatInventory extends ConcatInventory implements UnmodifiableInventory {
        public UnmodifiableConcatInventory(Inventory[] inventories) {
            super(inventories);
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
}
