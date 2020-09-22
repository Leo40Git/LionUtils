package adudecalledleo.lionutils.serialize;

import adudecalledleo.lionutils.InitializerUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;

import java.util.Arrays;
import java.util.List;

/**
 * Helper class for dealing with reading from and writing to NBT tags.
 *
 * @since 2.0.0
 */
public final class NbtUtil {
    private NbtUtil() {
        InitializerUtil.utilCtor();
    }

    /**
     * Checks if a {@link CompoundTag} contains a {@link BlockPos}.
     *
     * @param tag
     *         source tag
     * @param key
     *         key to check
     * @return {@code true} if block position is present, {@code false} otherwise
     *
     * @since 3.0.0
     */
    public static boolean containsBlockPos(CompoundTag tag, String key) {
        return tag.contains(key, /* NbtType.LONG */ 4);
    }

    /**
     * Reads a {@link BlockPos} from a {@link CompoundTag}.
     *
     * @param tag
     *         source tag
     * @param key
     *         key to read from
     * @return the block position, or {@code null} if the key isn't associated with one
     */
    public static BlockPos getBlockPos(CompoundTag tag, String key) {
        if (!containsBlockPos(tag, key))
            return null;
        return BlockPos.fromLong(tag.getLong(key));
    }

    /**
     * Writes a {@link BlockPos} to a {@link CompoundTag}.
     *
     * @param tag
     *         destination tag
     * @param key
     *         key to write to
     * @param pos
     *         block position to write
     */
    public static void putBlockPos(CompoundTag tag, String key, BlockPos pos) {
        tag.putLong(key, pos.asLong());
    }

    /**
     * Checks if a {@link CompoundTag} contains a {@link BlockPos} array.
     *
     * @param tag
     *         source tag
     * @param key
     *         key to check
     * @return {@code true} if block position array is present, {@code false} otherwise
     *
     * @since 3.0.0
     */
    public static boolean containsBlockPosArray(CompoundTag tag, String key) {
        return tag.contains(key, /* NbtType.LONG_ARRAY */ 12);
    }

    /**
     * Reads a {@link BlockPos} array from a {@link CompoundTag}.
     *
     * @param tag
     *         source tag
     * @param key
     *         key to read from
     * @return the block position array, or an empty array if the key isn't associated with one
     */
    public static BlockPos[] getBlockPosArray(CompoundTag tag, String key) {
        if (!containsBlockPosArray(tag, key))
            return new BlockPos[0];
        return Arrays.stream(tag.getLongArray(key)).mapToObj(BlockPos::fromLong).toArray(BlockPos[]::new);
    }

    /**
     * Writes a {@link BlockPos} array to a {@link CompoundTag}.
     *
     * @param tag
     *         destination tag
     * @param key
     *         key to write to
     * @param posArr
     *         block position array to write
     */
    public static void putBlockPosArray(CompoundTag tag, String key, BlockPos... posArr) {
        tag.putLongArray(key, Arrays.stream(posArr).mapToLong(BlockPos::asLong).toArray());
    }

    /**
     * Writes a {@link BlockPos} list to a {@link CompoundTag}. The list is treated as an array.
     *
     * @param tag
     *         destination tag
     * @param key
     *         key to write to
     * @param posList
     *         block position list to write
     * @see #putBlockPosArray(CompoundTag, String, BlockPos...)
     */
    public static void putBlockPosArray(CompoundTag tag, String key, List<BlockPos> posList) {
        putBlockPosArray(tag, key, posList.toArray(new BlockPos[0]));
    }

    private static <T extends Enum<T>> T getEnum0(Class<T> enumType, CompoundTag tag, String key) {
        try {
            return T.valueOf(enumType, tag.getString(key));
        } catch (IllegalArgumentException ignored) {
            return null;
        }
    }

    /**
     * Checks if a {@link CompoundTag} contains an {@code enum} value.
     *
     * @param enumType
     *         type of {@code enum} value
     * @param tag
     *         source tag
     * @param key
     *         key to check
     * @param <T>
     *         type of {@code enum} value
     * @return {@code true} if {@code enum} value is present, {@code false} otherwise
     *
     * @since 5.1.0
     */
    public static <T extends Enum<T>> boolean containsEnum(Class<T> enumType, CompoundTag tag, String key) {
        if (!tag.contains(key, /* NbtType.STRING */ 8))
            return false;
        return getEnum0(enumType, tag, key) != null;
    }

    /**
     * Reads an {@code enum} value from a {@link CompoundTag}.
     *
     * @param enumType
     *         type of {@code enum} value
     * @param tag
     *         source tag
     * @param key
     *         key to read from
     * @param <T>
     *         type of {@code enum} value
     * @return the {@code enum} value, or {@code null} if the key isn't associated with one
     *
     * @since 5.1.0
     */
    public static <T extends Enum<T>> T getEnum(Class<T> enumType, CompoundTag tag, String key) {
        if (!tag.contains(key, /* NbtType.STRING */ 8))
            return null;
        return getEnum0(enumType, tag, key);
    }

    /**
     * Writes an {@code enum} value to a {@link CompoundTag}.
     *
     * @param tag
     *         destination tag
     * @param key
     *         key to write to
     * @param value
     *         {@code enum} value to write
     * @param <T>
     *         type of {@code enum} value
     * @since 5.1.0
     */
    public static <T extends Enum<T>> void putEnum(CompoundTag tag, String key, T value) {
        tag.putString(key, value.name());
    }

    /**
     * Writes a {@link BlockPos} to a {@link CompoundTag}.
     *
     * @param tag
     *         destination tag
     * @param key
     *         key to write to
     * @param pos
     *         block position to write
     * @deprecated Use {@link #putBlockPos(CompoundTag, String, BlockPos)} instead.
     */
    @Deprecated
    public static void setBlockPos(CompoundTag tag, String key, BlockPos pos) {
        putBlockPos(tag, key, pos);
    }

    /**
     * Writes a {@link BlockPos} array to a {@link CompoundTag}.
     *
     * @param tag
     *         destination tag
     * @param key
     *         key to write to
     * @param posArr
     *         block position array to write
     * @deprecated Use {@link #putBlockPosArray(CompoundTag, String, BlockPos...)} instead.
     */
    @Deprecated
    public static void setBlockPosArray(CompoundTag tag, String key, BlockPos... posArr) {
        putBlockPosArray(tag, key, posArr);
    }

    /**
     * Writes a {@link BlockPos} list to a {@link CompoundTag}. The list is treated as an array.
     *
     * @param tag
     *         destination tag
     * @param key
     *         key to write to
     * @param posList
     *         block position list to write
     * @see #setBlockPosArray(CompoundTag, String, BlockPos...)
     * @deprecated Use {@link #putBlockPosArray(CompoundTag, String, List)} instead.
     */
    @Deprecated
    public static void setBlockPosArray(CompoundTag tag, String key, List<BlockPos> posList) {
        putBlockPosArray(tag, key, posList);
    }
}
