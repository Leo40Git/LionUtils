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

    int getInt(Object o, long offset);

    void putInt(Object o, long offset, int x);

    Object getObject(Object o, long offset);

    void putObject(Object o, long offset, Object x);

    boolean getBoolean(Object o, long offset);

    void putBoolean(Object o, long offset, boolean x);

    byte getByte(Object o, long offset);

    void putByte(Object o, long offset, byte x);

    short getShort(Object o, long offset);

    void putShort(Object o, long offset, short x);

    char getChar(Object o, long offset);

    void putChar(Object o, long offset, char x);

    long getLong(Object o, long offset);

    void putLong(Object o, long offset, long x);

    float getFloat(Object o, long offset);

    void putFloat(Object o, long offset, float x);

    double getDouble(Object o, long offset);

    void putDouble(Object o, long offset, double x);

    HeapMemory allocate(long size);

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
