package adudecalledleo.lionutils.unsafe;

import adudecalledleo.lionutils.internal.unsafe.UnsafeAccessProvider;

import java.lang.reflect.Field;

/**
 * Represents a proxy to the internal {@code Unsafe} class.
 *
 * @since 5.0.0
 */
public interface UnsafeAccess {
    /**
     * Checks if the proxy is available, AKA if {@code Unsafe} can be accessed.
     *
     * @return {@code true} if proxy is available, {@code false} otherwise
     */
    static boolean isAvailable() {
        return UnsafeAccessProvider.initAndCheckAvailable();
    }

    /**
     * Gets the current {@code Unsafe} proxy.
     *
     * @return current {@code Unsafe} proxy
     */
    static UnsafeAccess get() {
        return UnsafeAccessProvider.getUnsafeAccess();
    }

    /**
     * Gets a {@code boolean} from the base {@code Object}'s memory.
     *
     * @param o
     *         base object
     * @param offset
     *         offset
     * @return {@code boolean} at that offset
     */
    boolean getBoolean(Object o, long offset);

    /**
     * Puts a {@code boolean} in the base {@code Object}'s memory.
     *
     * @param o
     *         base object
     * @param offset
     *         offset
     * @param x
     *         {@code boolean} to put at that offset
     */
    void putBoolean(Object o, long offset, boolean x);

    /**
     * Gets a {@code byte} from the base {@code Object}'s memory.
     *
     * @param o
     *         base object
     * @param offset
     *         offset
     * @return {@code byte} at that offset
     */
    byte getByte(Object o, long offset);

    /**
     * Puts a {@code byte} in the base {@code Object}'s memory.
     *
     * @param o
     *         base object
     * @param offset
     *         offset
     * @param x
     *         {@code byte} to put at that offset
     */
    void putByte(Object o, long offset, byte x);

    /**
     * Gets a {@code short} from the base {@code Object}'s memory.
     *
     * @param o
     *         base object
     * @param offset
     *         offset
     * @return {@code short} at that offset
     */
    short getShort(Object o, long offset);

    /**
     * Puts a {@code short} in the base {@code Object}'s memory.
     *
     * @param o
     *         base object
     * @param offset
     *         offset
     * @param x
     *         {@code short} to put at that offset
     */
    void putShort(Object o, long offset, short x);

    /**
     * Gets a {@code char} from the base {@code Object}'s memory.
     *
     * @param o
     *         base object
     * @param offset
     *         offset
     * @return {@code char} at that offset
     */
    char getChar(Object o, long offset);

    /**
     * Puts a {@code char} in the base {@code Object}'s memory.
     *
     * @param o
     *         base object
     * @param offset
     *         offset
     * @param x
     *         {@code char} to put at that offset
     */
    void putChar(Object o, long offset, char x);

    /**
     * Gets an {@code int} from the base {@code Object}'s memory.
     *
     * @param o
     *         base object
     * @param offset
     *         offset
     * @return {@code int} at that offset
     */
    int getInt(Object o, long offset);

    /**
     * Puts an {@code int} in the base {@code Object}'s memory.
     *
     * @param o
     *         base object
     * @param offset
     *         offset
     * @param x
     *         {@code int} to put at that offset
     */
    void putInt(Object o, long offset, int x);

    /**
     * Gets a {@code long} from the base {@code Object}'s memory.
     *
     * @param o
     *         base object
     * @param offset
     *         offset
     * @return {@code long} at that offset
     */
    long getLong(Object o, long offset);

    /**
     * Puts a {@code long} in the base {@code Object}'s memory.
     *
     * @param o
     *         base object
     * @param offset
     *         offset
     * @param x
     *         {@code long} to put at that offset
     */
    void putLong(Object o, long offset, long x);

    /**
     * Gets a {@code float} from the base {@code Object}'s memory.
     *
     * @param o
     *         base object
     * @param offset
     *         offset
     * @return {@code float} at that offset
     */
    float getFloat(Object o, long offset);

    /**
     * Puts a {@code float} in the base {@code Object}'s memory.
     *
     * @param o
     *         base object
     * @param offset
     *         offset
     * @param x
     *         {@code float} to put at that offset
     */
    void putFloat(Object o, long offset, float x);

    /**
     * Gets a {@code double} from the base {@code Object}'s memory.
     *
     * @param o
     *         base object
     * @param offset
     *         offset
     * @return {@code double} at that offset
     */
    double getDouble(Object o, long offset);

    /**
     * Puts a {@code double} in the base {@code Object}'s memory.
     *
     * @param o
     *         base object
     * @param offset
     *         offset
     * @param x
     *         {@code double} to put at that offset
     */
    void putDouble(Object o, long offset, double x);

    /**
     * Gets a pointer to an {@code Object} from the base {@code Object}'s memory.
     *
     * @param o
     *         base object
     * @param offset
     *         offset
     * @return {@code Object} at that offset
     */
    Object getObject(Object o, long offset);

    /**
     * Puts a pointer to an {@code Object} in the base {@code Object}'s memory.
     *
     * @param o
     *         base object
     * @param offset
     *         offset
     * @param x
     *         {@code Object} to put at that offset
     */
    void putObject(Object o, long offset, Object x);

    /**
     * Gets a {@code boolean} from the base {@code Object}'s memory, with volatile load semantics.
     *
     * @param o
     *         base object
     * @param offset
     *         offset
     * @return {@code boolean} at that offset
     */
    boolean getBooleanVolatile(Object o, long offset);

    /**
     * Puts a {@code boolean} in the base {@code Object}'s memory, with volatile load semantics.
     *
     * @param o
     *         base object
     * @param offset
     *         offset
     * @param x
     *         {@code boolean} to put at that offset
     */
    void putBooleanVolatile(Object o, long offset, boolean x);

    /**
     * Gets a {@code byte} from the base {@code Object}'s memory, with volatile load semantics.
     *
     * @param o
     *         base object
     * @param offset
     *         offset
     * @return {@code byte} at that offset
     */
    byte getByteVolatile(Object o, long offset);

    /**
     * Puts a {@code byte} in the base {@code Object}'s memory, with volatile load semantics.
     *
     * @param o
     *         base object
     * @param offset
     *         offset
     * @param x
     *         {@code byte} to put at that offset
     */
    void putByteVolatile(Object o, long offset, byte x);

    /**
     * Gets a {@code short} from the base {@code Object}'s memory, with volatile load semantics.
     *
     * @param o
     *         base object
     * @param offset
     *         offset
     * @return {@code short} at that offset
     */
    short getShortVolatile(Object o, long offset);

    /**
     * Puts a {@code short} in the base {@code Object}'s memory, with volatile load semantics.
     *
     * @param o
     *         base object
     * @param offset
     *         offset
     * @param x
     *         {@code short} to put at that offset
     */
    void putShortVolatile(Object o, long offset, short x);

    /**
     * Gets a {@code char} from the base {@code Object}'s memory, with volatile load semantics.
     *
     * @param o
     *         base object
     * @param offset
     *         offset
     * @return {@code char} at that offset
     */
    char getCharVolatile(Object o, long offset);

    /**
     * Puts a {@code char} in the base {@code Object}'s memory, with volatile load semantics.
     *
     * @param o
     *         base object
     * @param offset
     *         offset
     * @param x
     *         {@code char} to put at that offset
     */
    void putCharVolatile(Object o, long offset, char x);

    /**
     * Gets an {@code int} from the base {@code Object}'s memory, with volatile load semantics.
     *
     * @param o
     *         base object
     * @param offset
     *         offset
     * @return {@code int} at that offset
     */
    int getIntVolatile(Object o, long offset);

    /**
     * Puts an {@code int} in the base {@code Object}'s memory, with volatile load semantics.
     *
     * @param o
     *         base object
     * @param offset
     *         offset
     * @param x
     *         {@code int} to put at that offset
     */
    void putIntVolatile(Object o, long offset, int x);

    /**
     * Gets a {@code long} from the base {@code Object}'s memory, with volatile load semantics.
     *
     * @param o
     *         base object
     * @param offset
     *         offset
     * @return {@code long} at that offset
     */
    long getLongVolatile(Object o, long offset);

    /**
     * Puts a {@code long} in the base {@code Object}'s memory, with volatile load semantics.
     *
     * @param o
     *         base object
     * @param offset
     *         offset
     * @param x
     *         {@code long} to put at that offset
     */
    void putLongVolatile(Object o, long offset, long x);

    /**
     * Gets a {@code float} from the base {@code Object}'s memory, with volatile load semantics.
     *
     * @param o
     *         base object
     * @param offset
     *         offset
     * @return {@code float} at that offset
     */
    float getFloatVolatile(Object o, long offset);

    /**
     * Puts a {@code float} in the base {@code Object}'s memory, with volatile load semantics.
     *
     * @param o
     *         base object
     * @param offset
     *         offset
     * @param x
     *         {@code float} to put at that offset
     */
    void putFloatVolatile(Object o, long offset, float x);

    /**
     * Gets a {@code double} from the base {@code Object}'s memory, with volatile load semantics.
     *
     * @param o
     *         base object
     * @param offset
     *         offset
     * @return {@code double} at that offset
     */
    double getDoubleVolatile(Object o, long offset);

    /**
     * Puts a {@code double} in the base {@code Object}'s memory, with volatile load semantics.
     *
     * @param o
     *         base object
     * @param offset
     *         offset
     * @param x
     *         {@code double} to put at that offset
     */
    void putDoubleVolatile(Object o, long offset, double x);

    /**
     * Gets a pointer to an {@code Object} from the base {@code Object}'s memory, with volatile load semantics.
     *
     * @param o
     *         base object
     * @param offset
     *         offset
     * @return {@code Object} at that offset
     */
    Object getObjectVolatile(Object o, long offset);

    /**
     * Puts a pointer to an {@code Object} in the base {@code Object}'s memory, with volatile load semantics.
     *
     * @param o
     *         base object
     * @param offset
     *         offset
     * @param x
     *         {@code Object} to put at that offset
     */
    void putObjectVolatile(Object o, long offset, Object x);

    /**
     * Sets all bytes in a section of the base {@code Object}'s memory to a specific value.
     *
     * @param base
     *         base object
     * @param offset
     *         offset to start from
     * @param bytes
     *         amount of bytes to set
     * @param value
     *         value to set bytes to
     */
    void setMemory(Object base, long offset, long bytes, byte value);

    /**
     * Copies a section of bytes from one {@code Object}'s memory to another {@code Object}'s memory.
     *
     * @param srcBase
     *         source base object
     * @param srcOffset
     *         source offset
     * @param destBase
     *         destination base object
     * @param destOffset
     *         destination offset
     * @param bytes
     *         amount of bytes to copy
     */
    void copyMemory(Object srcBase, long srcOffset, Object destBase, long destOffset, long bytes);

    /**
     * Gets the offset of a {@code static} field, relative to {@linkplain #staticFieldBase(Field) its base
     * <code>Object</code>}.
     *
     * @param f
     *         static field
     * @return offset of static field
     */
    long staticFieldOffset(Field f);

    /**
     * Gets the offset of an instance field, relative to its base {@code Object}.
     *
     * @param f
     *         instance field
     * @return offset of instance field
     */
    long objectFieldOffset(Field f);

    /**
     * Gets the base {@code Object} of a {@code static} field. Only use this for poking/peeking at the field!
     *
     * @param f
     *         {@code static} field
     * @return base object of static field
     */
    Object staticFieldBase(Field f);

    /**
     * Checks if a class should be initialized via {@link #ensureClassInitialized(Class)}.
     *
     * @param c
     *         class to check
     * @return {@code true} if should be initialized, {@code false} otherwise
     */
    boolean shouldBeInitialized(Class<?> c);

    /**
     * Ensures that a class is initialized (all static initializer blocks invoked).
     *
     * @param c
     *         class to initialize
     */
    void ensureClassInitialized(Class<?> c);

    /**
     * <p>Initializes a class, if needed.</p>
     * Equivalent to:<pre>
     * if ({@link #shouldBeInitialized(Class) shouldBeInitialized}(c))
     *     {@link #ensureClassInitialized(Class) ensureClassInitialized}(c);
     * </pre>
     *
     * @param c
     *         class to initialize
     */
    default void initializeIfNeeded(Class<?> c) {
        if (shouldBeInitialized(c))
            ensureClassInitialized(c);
    }

    /**
     * Gets the base offset of an array class, AKA the offset to the first element.
     *
     * @param arrayClass
     *         array class
     * @return base offset of array class
     */
    int arrayBaseOffset(Class<?> arrayClass);

    /**
     * Gets the index scale of an array class, AKA the amount of bytes between each element.
     *
     * @param arrayClass
     *         array class
     * @return scale index of array class
     */
    int arrayIndexScale(Class<?> arrayClass);

    /**
     * Gets the current address size in bytes.
     *
     * @return current address size
     */
    int addressSize();

    /**
     * Gets the current page size in bytes.
     *
     * @return current page size
     */
    int pageSize();

    /**
     * Allocates a block of memory on the heap.
     *
     * @param size
     *         size of block to allocate
     * @return a {@link HeapMemory} instance representing allocated block
     */
    HeapMemory allocate(long size);
}
