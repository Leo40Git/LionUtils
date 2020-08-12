package adudecalledleo.lionutils.serialize;

import adudecalledleo.lionutils.InitializerUtil;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.util.math.BlockPos;

import java.util.Arrays;
import java.util.List;

/**
 * Helper class for dealing with reading from and writing to NBT tags.
 */
public final class NbtUtil {
    private NbtUtil() {
        InitializerUtil.badConstructor();
    }

    /**
     * Converts an NBT tag into a {@link JsonElement}.
     * @param tag source tag
     * @return the JSON element
     */
    public static JsonElement toJson(Tag tag) {
        return NbtOps.INSTANCE.convertTo(JsonOps.INSTANCE, tag);
    }

    /**
     * Converts a {@link JsonElement} into an NBT tag.
     * @param element source elemnt
     * @return the NBT tag
     */
    public static Tag fromJson(JsonElement element) {
        return JsonOps.INSTANCE.convertTo(NbtOps.INSTANCE, element);
    }

    /**
     * Reads a {@link BlockPos} from a {@link CompoundTag}.
     * @param tag source tag
     * @param key key to read from
     * @return the block position, or {@code null} if the key isn't associated with one
     */
    public static BlockPos getBlockPos(CompoundTag tag, String key) {
        if (!tag.contains(key, /* NbtType.LONG */ 4))
            return null;
        return BlockPos.fromLong(tag.getLong(key));
    }

    /**
     * Writes a {@link BlockPos} to a {@link CompoundTag}.
     * @param tag destination tag
     * @param key key to write to
     * @param pos block position to write
     */
    public static void setBlockPos(CompoundTag tag, String key, BlockPos pos) {
        tag.putLong(key, pos.asLong());
    }

    private static final BlockPos[] EMPTY_POS_ARRAY = new BlockPos[0];

    /**
     * Reads a {@link BlockPos} array from a {@link CompoundTag}.
     * @param tag source tag
     * @param key key to read from
     * @return the block position array, or an empty array if the key isn't associated with one
     */
    public static BlockPos[] getBlockPosArray(CompoundTag tag, String key) {
        if (!tag.contains(key, /* NbtType.LONG_ARRAY */ 12))
            return EMPTY_POS_ARRAY;
        return Arrays.stream(tag.getLongArray(key)).mapToObj(BlockPos::fromLong).toArray(BlockPos[]::new);
    }

    /**
     * Writes a {@link BlockPos} array to a {@link CompoundTag}.
     * @param tag destination tag
     * @param key key to write to
     * @param posArr block position array to write
     */
    public static void setBlockPosArray(CompoundTag tag, String key, BlockPos... posArr) {
        tag.putLongArray(key, Arrays.stream(posArr).mapToLong(BlockPos::asLong).toArray());
    }

    /**
     * Writes a {@link BlockPos} list to a {@link CompoundTag}. The list is treated as an array.
     * @param tag destination tag
     * @param key key to write to
     * @param posList block position list to write
     * @see #setBlockPosArray(CompoundTag, String, BlockPos...)
     */
    public static void setBlockPosArray(CompoundTag tag, String key, List<BlockPos> posList) {
        setBlockPosArray(tag, key, posList.toArray(new BlockPos[0]));
    }
}
