package adudecalledleo.lionutils.item;

import adudecalledleo.lionutils.InitializerUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.text.Text;

import java.util.List;

/**
 * Helper class for dealing with {@link ItemStack}s.
 *
 * @since 5.1.0
 */
public final class ItemStackUtil {
    private ItemStackUtil() {
        InitializerUtil.badConstructor();
    }

    private static ListTag getOrCreateLoreListTag(ItemStack stack) {
        CompoundTag displayTag;
        ListTag loreListTag;
        displayTag = stack.getOrCreateSubTag("display");
        if (displayTag.contains("Lore", /* NbtType.LIST */ 9))
            loreListTag = displayTag.getList("Lore", /* NbtType.STRING */ 8);
        else
            displayTag.put("Lore", loreListTag = new ListTag());
        return loreListTag;
    }

    private static void appendLore0(ListTag loreListTag, Text... lore) {
        for (Text line : lore)
            loreListTag.add(StringTag.of(Text.Serializer.toJson(line)));
    }

    private static Text[] list2ArrayText(List<Text> list) {
        if (list == null)
            return null;
        Text[] array = new Text[list.size()];
        array = list.toArray(array);
        return array;
    }

    /**
     * Removes all lore from an {@link ItemStack}.
     *
     * @param stack
     *         stack to remove lore from
     * @return the given stack
     */
    public static ItemStack removeLore(ItemStack stack) {
        CompoundTag displayTag = stack.getSubTag("display");
        if (displayTag != null)
            displayTag.remove("Lore");
        return stack;
    }

    /**
     * Appends lines of lore to an {@link ItemStack}.
     *
     * @param stack
     *         stack to add lore to
     * @param lore
     *         lore to add
     * @return the given stack
     */
    public static ItemStack appendLore(ItemStack stack, Text... lore) {
        ListTag loreListTag = getOrCreateLoreListTag(stack);
        appendLore0(loreListTag, lore);
        return stack;
    }

    /**
     * Appends lines of lore to an {@link ItemStack}.
     *
     * @param stack
     *         stack to add lore to
     * @param lore
     *         lore to add
     * @return the given stack
     */
    public static ItemStack appendLore(ItemStack stack, List<Text> lore) {
        return appendLore(stack, list2ArrayText(lore));
    }

    /**
     * Sets an {@link ItemStack}'s lore.
     *
     * @param stack
     *         stack to set lore on
     * @param lore
     *         lore to set
     * @return the given stack
     */
    public static ItemStack setLore(ItemStack stack, Text... lore) {
        if (lore == null || lore.length == 0)
            return removeLore(stack);
        ListTag loreListTag = getOrCreateLoreListTag(stack);
        loreListTag.clear();
        appendLore0(loreListTag, lore);
        return stack;
    }

    /**
     * Sets an {@link ItemStack}'s lore.
     *
     * @param stack
     *         stack to set lore on
     * @param lore
     *         lore to set
     * @return the given stack
     */
    public static ItemStack setLore(ItemStack stack, List<Text> lore) {
        return setLore(stack, list2ArrayText(lore));
    }

    private static int combineHideFlags(ItemStack.TooltipSection... tooltipSections) {
        int flags = 0;
        for (ItemStack.TooltipSection tooltipSection : tooltipSections)
            flags |= tooltipSection.getFlag();
        return flags;
    }

    /**
     * Sets an {@link ItemStack}'s hide flags.
     *
     * @param stack
     *         stack to set hide flags on
     * @param tooltipSections
     *         hide flags to set
     * @return the given stack
     */
    public static ItemStack setHideFlags(ItemStack stack, ItemStack.TooltipSection... tooltipSections) {
        int flags = combineHideFlags(tooltipSections);
        CompoundTag tag = stack.getOrCreateTag();
        tag.putInt("HideFlags", flags);
        return stack;
    }

    /**
     * Adds to an {@link ItemStack}'s hide flags.
     *
     * @param stack
     *         stack to add hide flags to
     * @param tooltipSections
     *         hide flags to add
     * @return the given stack
     */
    public static ItemStack addHideFlags(ItemStack stack, ItemStack.TooltipSection... tooltipSections) {
        int flags = combineHideFlags(tooltipSections);
        CompoundTag tag = stack.getOrCreateTag();
        tag.putInt("HideFlags", tag.getInt("HideFlags") | flags);
        return stack;
    }
}
