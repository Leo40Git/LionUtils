package adudecalledleo.lionutils.unsafe;

/**
 * Represents a block of memory allocated on the heap by {@link UnsafeAccess}.
 *
 * @since 5.0.0
 */
public interface HeapMemory extends AutoCloseable {
    /**
     * Gets the memory block's size in bytes.
     *
     * @return the block size
     */
    long size();

    /**
     * Gets the memory block's address on the heap.
     *
     * @return the block address
     */
    long address();

    /**
     * <p>Checks if the memory block is valid or not.</p>
     * A memory block is valid after being allocated and invalidated after being {@linkplain #close() freed}.
     *
     * @return {@code true} if valid, {@code false} otherwise
     */
    boolean isValid();

    /**
     * Sets all bytes in a specific section of the memory block to a specific value.
     *
     * @param offset
     *         offset to start from
     * @param bytes
     *         amount of bytes to set
     * @param value
     *         value to set bytes to
     */
    void set(long offset, long bytes, byte value);

    /**
     * Reallocates the memory block with a new size.
     *
     * @param newSize
     *         new block size
     */
    void reallocate(long newSize);

    /**
     * Copies a specific amount of bytes from the source memory block to this memory block.
     *
     * @param src
     *         source memory block
     * @param srcOffset
     *         source offset
     * @param dstOffset
     *         destination offset
     * @param bytes
     *         amount of bytes to copy
     */
    void copyFrom(HeapMemory src, long srcOffset, long dstOffset, long bytes);

    /**
     * Gets a {@code byte} from a specific offset.
     *
     * @param offset
     *         offset
     * @return the {@code byte} at that offset
     */
    byte getByte(long offset);

    /**
     * Puts a {@code byte} at a specific offset.
     *
     * @param offset
     *         offset
     * @param x
     *         {@code byte} to put at that offset
     */
    void putByte(long offset, byte x);

    /**
     * Gets a {@code short} from a specific offset.
     *
     * @param offset
     *         offset
     * @return the {@code short} at that offset
     */
    short getShort(long offset);

    /**
     * Puts a {@code short} at a specific offset.
     *
     * @param offset
     *         offset
     * @param x
     *         {@code short} to put at that offset
     */
    void putShort(long offset, short x);

    /**
     * Gets a {@code char} from a specific offset.
     *
     * @param offset
     *         offset
     * @return the {@code char} at that offset
     */
    char getChar(long offset);

    /**
     * Puts a {@code char} at a specific offset.
     *
     * @param offset
     *         offset
     * @param x
     *         {@code char} to put at that offset
     */
    void putChar(long offset, char x);

    /**
     * Gets an {@code int} from a specific offset.
     *
     * @param offset
     *         offset
     * @return the {@code int} at that offset
     */
    int getInt(long offset);

    /**
     * Puts an {@code int} at a specific offset.
     *
     * @param offset
     *         offset
     * @param x
     *         {@code int} to put at that offset
     */
    void putInt(long offset, int x);

    /**
     * Gets a {@code long} from a specific offset.
     *
     * @param offset
     *         offset
     * @return the {@code long} at that offset
     */
    long getLong(long offset);

    /**
     * Puts a {@code long} at a specific offset.
     *
     * @param offset
     *         offset
     * @param x
     *         {@code long} to put at that offset
     */
    void putLong(long offset, long x);

    /**
     * Gets a {@code float} from a specific offset.
     *
     * @param offset
     *         offset
     * @return the {@code float} at that offset
     */
    float getFloat(long offset);

    /**
     * Puts a {@code float} at a specific offset.
     *
     * @param offset
     *         offset
     * @param x
     *         {@code float} to put at that offset
     */
    void putFloat(long offset, float x);

    /**
     * Gets a {@code double} from a specific offset.
     *
     * @param offset
     *         offset
     * @return the {@code double} at that offset
     */
    double getDouble(long offset);

    /**
     * Puts a {@code double} at a specific offset.
     *
     * @param offset
     *         offset
     * @param x
     *         {@code double} to put at that offset
     */
    void putDouble(long offset, double x);

    /**
     * Gets an address from a specific offset.
     *
     * @param offset
     *         offset
     * @return the address at that offset
     */
    long getAddress(long offset);

    /**
     * Puts an address at a specific offset.
     *
     * @param offset
     *         offset
     * @param x
     *         address to put at that offset
     */
    void putAddress(long offset, long x);

    /**
     * Frees the block of memory and {@linkplain #isValid() invalidates this instance}.
     */
    @Override
    void close();
}
