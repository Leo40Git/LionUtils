package adudecalledleo.lionutils.item;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Helper class for building potions (and tipped arrows).
 *
 * @since 6.1.0
 */
public final class PotionBuilder {
    private Item item;
    private int count;
    private Potion potion;
    private boolean customColorSet;
    private int customColor;
    private ArrayList<StatusEffectInstance> customEffects;
    private Text customName;
    private ArrayList<Text> lore;

    private PotionBuilder() {
        item = Items.POTION;
        count = 1;
        potion = Potions.EMPTY;
    }

    /**
     * Creates a new {@code PotionBuilder}.
     *
     * @return a new builder instance
     */
    public static PotionBuilder create() {
        return new PotionBuilder();
    }

    /**
     * Configures this builder to create splash potion stacks.
     *
     * @return this builder
     */
    public PotionBuilder splash() {
        item = Items.SPLASH_POTION;
        return this;
    }

    /**
     * Configures this builder to create lingering potion stacks.
     *
     * @return this builder
     */
    public PotionBuilder lingering() {
        item = Items.LINGERING_POTION;
        return this;
    }

    /**
     * Configures this builder to create tipped arrow stacks.
     *
     * @return this builder
     */
    public PotionBuilder tippedArrow() {
        item = Items.TIPPED_ARROW;
        return this;
    }

    /**
     * Sets the resulting stack's size (count).<p>
     * While this will <em>always</em> work, it only makes sense to use if this builder is configured to create
     * {@linkplain #tippedArrow() tipped arrows}.
     *
     * @param count
     *         count to set
     * @return this builder
     */
    public PotionBuilder setCount(int count) {
        this.count = count;
        return this;
    }

    /**
     * Sets the resulting potion's preset.
     *
     * @param potion
     *         potion preset to use
     * @return this builder
     */
    public PotionBuilder setPotion(Potion potion) {
        this.potion = potion;
        return this;
    }

    /**
     * Sets a custom color for the resulting potion.
     *
     * @param color
     *         color to use
     * @return this builder
     */
    public PotionBuilder setCustomColor(int color) {
        customColorSet = true;
        customColor = color;
        return this;
    }

    private void initCustomEffects() {
        if (customEffects == null)
            customEffects = new ArrayList<>();
    }

    /**
     * Adds a custom effect to the resulting potion.
     *
     * @param effect
     *         effect to add
     * @return this builder
     */
    public PotionBuilder addCustomEffect(StatusEffectInstance effect) {
        initCustomEffects();
        customEffects.add(effect);
        return this;
    }

    /**
     * Adds custom effects to the resulting potion.
     *
     * @param effects
     *         effects to add
     * @return this builder
     */
    public PotionBuilder addCustomEffects(Collection<StatusEffectInstance> effects) {
        initCustomEffects();
        customEffects.addAll(effects);
        return this;
    }

    /**
     * Adds custom effects to the resulting potion.
     *
     * @param effects
     *         effects to add
     * @return this builder
     */
    public PotionBuilder addCustomEffects(StatusEffectInstance... effects) {
        initCustomEffects();
        Collections.addAll(customEffects, effects);
        return this;
    }

    /**
     * Adds a custom effect to the resulting potion.
     *
     * @param effect
     *         type of effect to add
     * @param duration
     *         duration of effect to add
     * @return this builder
     */
    public PotionBuilder addCustomEffect(StatusEffect effect, int duration) {
        return addCustomEffect(new StatusEffectInstance(effect, duration));
    }

    /**
     * Adds a custom effect to the resulting potion.
     *
     * @param effect
     *         type of effect to add
     * @param duration
     *         duration of effect to add
     * @param amplifier
     *         amplifier (level) of effect to add
     * @return this builder
     */
    public PotionBuilder addCustomEffect(StatusEffect effect, int duration, int amplifier) {
        return addCustomEffect(new StatusEffectInstance(effect, duration, amplifier));
    }

    /**
     * Sets the resulting potion's custom name.
     *
     * @param customName
     *         custom name to set
     * @return this builder
     */
    public PotionBuilder setCustomName(Text customName) {
        this.customName = customName;
        return this;
    }

    private void initLore() {
        if (lore == null)
            lore = new ArrayList<>();
    }

    /**
     * Adds a line of lore to the resulting potion.
     *
     * @param line
     *         line of lore to add
     * @return this builder
     */
    public PotionBuilder addLore(Text line) {
        initLore();
        lore.add(line);
        return this;
    }

    /**
     * Add lines of lore to the resulting potion.
     *
     * @param lines
     *         lines of lore to add
     * @return this builder
     */
    public PotionBuilder addLore(Collection<Text> lines) {
        initLore();
        lore.addAll(lines);
        return this;
    }

    /**
     * Add lines of lore to the resulting potion.
     *
     * @param lines
     *         lines of lore to add
     * @return this builder
     */
    public PotionBuilder addLore(Text... lines) {
        initLore();
        Collections.addAll(lore, lines);
        return this;
    }

    /**
     * Builds a new {@code ItemStack} representing the resulting potion.
     *
     * @return a new stack
     */
    public ItemStack build() {
        ItemStack potionStack = new ItemStack(item, count);
        PotionUtil.setPotion(potionStack, potion);
        if (customColorSet)
            potionStack.getOrCreateTag().putInt("CustomPotionColor", customColor);
        if (customEffects != null)
            PotionUtil.setCustomPotionEffects(potionStack, customEffects);
        if (customName != null)
            potionStack.setCustomName(customName);
        if (lore != null)
            ItemStackUtil.setLore(potionStack, lore);
        return potionStack;
    }
}
