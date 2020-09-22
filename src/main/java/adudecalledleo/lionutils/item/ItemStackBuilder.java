package adudecalledleo.lionutils.item;

import adudecalledleo.lionutils.network.GameProfileUtil;
import com.mojang.authlib.GameProfile;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;

import java.util.*;
import java.util.function.Consumer;

/**
 * Helper class for building an {@code ItemStack}.
 *
 * @since 6.0.0
 */
public final class ItemStackBuilder {
    private Item item;
    private int count;
    private boolean damageSet;
    private int damage;
    private Text customName;
    private final ArrayList<Text> lore;
    private boolean unbreakable;
    private final EnumSet<ItemStack.TooltipSection> hiddenTooltipSections;
    private final EnchantMapBuilder enchantMapBuilder;
    private Consumer<CompoundTag> tagMutator;

    private ItemStackBuilder() {
        item = Items.AIR;
        count = 1;
        lore = new ArrayList<>();
        hiddenTooltipSections = EnumSet.noneOf(ItemStack.TooltipSection.class);
        enchantMapBuilder = EnchantMapBuilder.create();
    }

    /**
     * Creates a new {@code ItemStackBuilder}.
     * @return new builder instance
     */
    public static ItemStackBuilder create() {
        return new ItemStackBuilder();
    }

    /**
     * Sets the resulting stack's item.
     * @param item item to set
     * @return this builder
     */
    public ItemStackBuilder setItem(Item item) {
        this.item = item;
        return this;
    }

    /**
     * Sets the resulting stack's item.
     * @param itemConvertible item to set
     * @return this builder
     */
    public ItemStackBuilder setItem(ItemConvertible itemConvertible) {
        return setItem(itemConvertible.asItem());
    }

    /**
     * Sets the resulting stack's size (count).
     * @param count count to set
     * @return this builder
     */
    public ItemStackBuilder setCount(int count) {
        this.count = count;
        return this;
    }

    /**
     * Sets the resulting stack's damage.<p>
     * Does not take effect if the {@linkplain #setItem(Item) item} is not damageable.
     * @param damage damage to set
     * @return this builder
     */
    public ItemStackBuilder setDamage(int damage) {
        damageSet = true;
        this.damage = damage;
        return this;
    }

    /**
     * Sets the resulting stack's custom name.
     * @param customName custom name to set
     * @return this builder
     */
    public ItemStackBuilder setCustomName(Text customName) {
        this.customName = customName;
        return this;
    }

    /**
     * Adds an enchantment to the resulting stack.
     * @param enchantment enchantment to add
     * @param level level of enchantment to add
     * @return this builder
     */
    public ItemStackBuilder addEnchantment(Enchantment enchantment, int level) {
        enchantMapBuilder.add(enchantment, level);
        return this;
    }

    /**
     * Adds a line of lore to the resulting stack.
     * @param line line of lore to add
     * @return this builder
     */
    public ItemStackBuilder addLore(Text line) {
        lore.add(line);
        return this;
    }

    /**
     * Add lines of lore to the resulting stack.
     * @param lines lines of lore to add
     * @return this builder
     */
    public ItemStackBuilder addLore(Iterable<Text> lines) {
        for (Text line : lines)
            lore.add(line);
        return this;
    }

    /**
     * Add lines of lore to the resulting stack.
     * @param lines lines of lore to add
     * @return this builder
     */
    public ItemStackBuilder addLore(Text... lines) {
        Collections.addAll(lore, lines);
        return this;
    }

    /**
     * Flags the resulting stack as unbreakable.<p>
     * Does not take effect if the {@linkplain #setItem(Item) item} is not damageable.
     * @return this builder
     */
    public ItemStackBuilder unbreakable() {
        unbreakable = true;
        return this;
    }

    /**
     * Hides a tooltip section on the resulting stack.
     * @param tooltipSection tooltip section to hide
     * @return this builder
     */
    public ItemStackBuilder hideTooltipSection(ItemStack.TooltipSection tooltipSection) {
        hiddenTooltipSections.add(tooltipSection);
        return this;
    }

    /**
     * Hides tooltip sections on the resulting stack.
     * @param tooltipSections tooltip sections to hide
     * @return this builder
     */
    public ItemStackBuilder hideTooltipSections(ItemStack.TooltipSection... tooltipSections) {
        Collections.addAll(hiddenTooltipSections, tooltipSections);
        return this;
    }

    /**
     * Sets the tag mutator.<p>
     * This mutator is invoked with the result of {@link ItemStack#getOrCreateTag()} <em>after</em> the item is built,
     * meaning this mutator can override the builder's other settings.<p>
     * @param tagMutator tag mutator to use
     * @return this builder
     */
    public ItemStackBuilder setTagMutator(Consumer<CompoundTag> tagMutator) {
        this.tagMutator = tagMutator;
        return this;
    }

    /**
     * Sets the tag to copy to the resulting stack's tag.<p>
     * This tag is copied via <code>{@linkplain ItemStack#getOrCreateTag()}.{@linkplain CompoundTag#copyFrom(CompoundTag)
     * copyFrom(tag)}</code>.<p>
     * This copying is done <em>after</em> the item is built,
     * meaning this tag can override the builder's other settings.<p>
     * <strong>NOTE:</strong> This overwrites the {@linkplain #setTagMutator(Consumer) tag mutator}!
     * @param source tag to copy from
     * @return this builder
     */
    public ItemStackBuilder copyFromTag(CompoundTag source) {
        return setTagMutator(tag -> tag.copyFrom(source));
    }

    /**
     * Configures this builder to build heads of the specified player.<p>
     * <strong>NOTE:</strong> This overwrites the {@linkplain #setItem(Item) item}, {@linkplain #setDamage(int) damage}
     * and {@linkplain #setTagMutator(Consumer) tag mutator}!
     * @param profile profile of player to build heads of
     * @return this builder
     */
    public ItemStackBuilder playerHead(GameProfile profile) {
        item = Items.PLAYER_HEAD;
        damageSet = false;
        return copyFromTag(GameProfileUtil.createPlayerHeadTag(profile));
    }

    /**
     * Builds a new {@code ItemStack}.
     * @return the newly built stack
     */
    public ItemStack build() {
        ItemStack stack = new ItemStack(item, count);
        if (stack.isEmpty())
            return ItemStack.EMPTY;
        if (stack.isDamageable()) {
            if (unbreakable)
                ItemStackUtil.setUnbreakable(stack, true);
            else if (damageSet)
                stack.setDamage(damage);
        }
        stack.setCustomName(customName);
        EnchantmentHelper.set(enchantMapBuilder.build(), stack);
        ItemStackUtil.setLore(stack, lore);
        ItemStackUtil.setHiddenTooltipSections(stack, hiddenTooltipSections);
        if (tagMutator != null)
            tagMutator.accept(stack.getOrCreateTag());
        return stack;
    }
}
