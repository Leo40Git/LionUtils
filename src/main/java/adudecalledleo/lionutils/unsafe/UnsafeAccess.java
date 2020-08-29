package adudecalledleo.lionutils.unsafe;

import adudecalledleo.lionutils.internal.unsafe.UnsafeAccessProvider;

import java.lang.reflect.Field;

public interface UnsafeAccess {
    static boolean isAvailable() {
        return UnsafeAccessProvider.initAndCheckAvailable();
    }

    static UnsafeAccess get() {
        return UnsafeAccessProvider.getUnsafeAccess();
    }

    NativeMemory allocate(long size);

    byte getByte(long address);

    void putByte(long address, byte x);

    short getShort(long address);

    void putShort(long address, short x);

    char getChar(long address);

    void putChar(long address, char x);

    int getInt(long address);

    void putInt(long address, int x);

    long getLong(long address);

    void putLong(long address, long x);

    float getFloat(long address);

    void putFloat(long address, float x);

    double getDouble(long address);

    void putDouble(long address, double x);

    long getAddress(long address);

    void putAddress(long address, long x);

    void setMemory(Object base, long offset, long bytes, byte value);

    void copyMemory(Object srcBase, long srcOffset, Object destBase, long destOffset, long bytes);

    long staticFieldOffset(Field f);

    long objectFieldOffset(Field f);

    Object staticFieldBase(Field f);

    boolean shouldBeInitialized(Class<?> c);

    void ensureClassInitialized(Class<?> c);

    default void initializeClass(Class<?> c) {
        if (shouldBeInitialized(c))
            ensureClassInitialized(c);
    }

    int arrayBaseOffset(Class<?> arrayClass);

    int arrayIndexScale(Class<?> arrayClass);

    int addressSize();

    int pageSize();
}
