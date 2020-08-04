package adudecalledleo.lionutils;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Helper class for compressing and decompressing {@link CompoundTag}s for networking.
 */
public final class NbtCompress {
    private NbtCompress() {
        InitializerUtil.badConstructor();
    }

    /**
     * Compresses a {@link CompoundTag} into a byte array.
     * @param in source tag
     * @return compressed data
     * @throws IOException if an I/O problem occurs.
     */
    public static byte[] compress(CompoundTag in) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        NbtIo.writeCompressed(in, baos);
        return baos.toByteArray();
    }

    /**
     * Decompresses a {@link CompoundTag} from a byte array.
     * @param in compressed data
     * @return decompressed tag
     * @throws IOException if an I/O problem occurs.
     */
    public static CompoundTag decompress(byte[] in) throws IOException {
        return NbtIo.readCompressed(new ByteArrayInputStream(in));
    }
}
