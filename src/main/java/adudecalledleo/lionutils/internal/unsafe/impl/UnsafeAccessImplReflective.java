package adudecalledleo.lionutils.internal.unsafe.impl;

import adudecalledleo.lionutils.unsafe.HeapMemory;
import adudecalledleo.lionutils.unsafe.UnsafeAccess;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

// unlike the direct impl, here we store Method instances for each and every method that is exposed via UnsafeAccess
// this avoids having to get the Unsafe instance directly, which is good since the holder field can change names very easily
public class UnsafeAccessImplReflective implements UnsafeAccess {
    // UnsafeAccess
    private final Method getBooleanMethod;
    private final Method putBooleanMethod;
    private final Method getByteMethod;
    private final Method putByteMethod;
    private final Method getShortMethod;
    private final Method putShortMethod;
    private final Method getCharMethod;
    private final Method putCharMethod;
    private final Method getIntMethod;
    private final Method putIntMethod;
    private final Method getLongMethod;
    private final Method putLongMethod;
    private final Method getFloatMethod;
    private final Method putFloatMethod;
    private final Method getDoubleMethod;
    private final Method putDoubleMethod;
    private final Method getObjectMethod;
    private final Method putObjectMethod;
    private final Method getBooleanVolatileMethod;
    private final Method putBooleanVolatileMethod;
    private final Method getByteVolatileMethod;
    private final Method putByteVolatileMethod;
    private final Method getShortVolatileMethod;
    private final Method putShortVolatileMethod;
    private final Method getCharVolatileMethod;
    private final Method putCharVolatileMethod;
    private final Method getIntVolatileMethod;
    private final Method putIntVolatileMethod;
    private final Method getLongVolatileMethod;
    private final Method putLongVolatileMethod;
    private final Method getFloatVolatileMethod;
    private final Method putFloatVolatileMethod;
    private final Method getDoubleVolatileMethod;
    private final Method putDoubleVolatileMethod;
    private final Method getObjectVolatileMethod;
    private final Method putObjectVolatileMethod;
    private final Method setMemoryMethod;
    private final Method copyMemoryMethod;
    private final Method staticFieldOffsetMethod;
    private final Method objectFieldOffsetMethod;
    private final Method staticFieldBaseMethod;
    private final Method shouldBeInitializedMethod;
    private final Method ensureClassInitializedMethod;
    private final Method arrayBaseOffsetMethod;
    private final Method arrayIndexScaleMethod;
    private final Method addressSizeMethod;
    private final Method pageSizeMethod;

    // HeapMemory
    private final Method getByteHeapMethod;
    private final Method putByteHeapMethod;
    private final Method getShortHeapMethod;
    private final Method putShortHeapMethod;
    private final Method getCharHeapMethod;
    private final Method putCharHeapMethod;
    private final Method getIntHeapMethod;
    private final Method putIntHeapMethod;
    private final Method getLongHeapMethod;
    private final Method putLongHeapMethod;
    private final Method getFloatHeapMethod;
    private final Method putFloatHeapMethod;
    private final Method getDoubleHeapMethod;
    private final Method putDoubleHeapMethod;
    private final Method getAddressHeapMethod;
    private final Method putAddressHeapMethod;
    private final Method allocateMemoryMethod;
    private final Method reallocateMemoryMethod;
    private final Method freeMemoryMethod;

    public UnsafeAccessImplReflective() {
        getBooleanMethod = getMethod("getBoolean", Object.class, long.class);
        putBooleanMethod = getMethod("putBoolean", Object.class, long.class, boolean.class);
        getByteMethod = getMethod("getByte", Object.class, long.class);
        putByteMethod = getMethod("putByte", Object.class, long.class, byte.class);
        getShortMethod = getMethod("getShort", Object.class, long.class);
        putShortMethod = getMethod("putShort", Object.class, long.class, short.class);
        getCharMethod = getMethod("getChar", Object.class, long.class);
        putCharMethod = getMethod("putChar", Object.class, long.class, char.class);
        getIntMethod = getMethod("getInt", Object.class, long.class);
        putIntMethod = getMethod("putInt", Object.class, long.class, int.class);
        getLongMethod = getMethod("getLong", Object.class, long.class);
        putLongMethod = getMethod("putLong", Object.class, long.class, long.class);
        getFloatMethod = getMethod("getFloat", Object.class, long.class);
        putFloatMethod = getMethod("putFloat", Object.class, long.class, float.class);
        getDoubleMethod = getMethod("getDouble", Object.class, long.class);
        putDoubleMethod = getMethod("putDouble", Object.class, long.class, double.class);
        getObjectMethod = getMethod("getObject", Object.class, long.class);
        putObjectMethod = getMethod("putObject", Object.class, long.class, Object.class);
        getBooleanVolatileMethod = getMethod("getBooleanVolatile", Object.class, long.class);
        putBooleanVolatileMethod = getMethod("putBooleanVolatile", Object.class, long.class, boolean.class);
        getByteVolatileMethod = getMethod("getByteVolatile", Object.class, long.class);
        putByteVolatileMethod = getMethod("putByteVolatile", Object.class, long.class, byte.class);
        getShortVolatileMethod = getMethod("getShortVolatile", Object.class, long.class);
        putShortVolatileMethod = getMethod("putShortVolatile", Object.class, long.class, short.class);
        getCharVolatileMethod = getMethod("getCharVolatile", Object.class, long.class);
        putCharVolatileMethod = getMethod("putCharVolatile", Object.class, long.class, char.class);
        getIntVolatileMethod = getMethod("getIntVolatile", Object.class, long.class);
        putIntVolatileMethod = getMethod("putIntVolatile", Object.class, long.class, int.class);
        getLongVolatileMethod = getMethod("getLongVolatile", Object.class, long.class);
        putLongVolatileMethod = getMethod("putLongVolatile", Object.class, long.class, long.class);
        getFloatVolatileMethod = getMethod("getFloatVolatile", Object.class, long.class);
        putFloatVolatileMethod = getMethod("putFloatVolatile", Object.class, long.class, float.class);
        getDoubleVolatileMethod = getMethod("getDoubleVolatile", Object.class, long.class);
        putDoubleVolatileMethod = getMethod("putDoubleVolatile", Object.class, long.class, double.class);
        getObjectVolatileMethod = getMethod("getObjectVolatile", Object.class, long.class);
        putObjectVolatileMethod = getMethod("putObjectVolatile", Object.class, long.class, Object.class);
        setMemoryMethod = getMethod("setMemory", Object.class, long.class, long.class, byte.class);
        copyMemoryMethod = getMethod("copyMemory", Object.class, long.class, Object.class, long.class, long.class);
        staticFieldOffsetMethod = getMethod("staticFieldOffset", Field.class);
        objectFieldOffsetMethod = getMethod("objectFieldOffset", Field.class);
        staticFieldBaseMethod = getMethod("staticFieldBase", Field.class);
        shouldBeInitializedMethod = getMethod("shouldBeInitialized", Class.class);
        ensureClassInitializedMethod = getMethod("ensureClassInitialized", Class.class);
        arrayBaseOffsetMethod = getMethod("arrayBaseOffset", Class.class);
        arrayIndexScaleMethod = getMethod("arrayIndexScale", Class.class);
        addressSizeMethod = getMethod("addressSize");
        pageSizeMethod = getMethod("pageSize");

        getByteHeapMethod = getMethod("getByte", long.class);
        putByteHeapMethod = getMethod("putByte", long.class, byte.class);
        getShortHeapMethod = getMethod("getShort", long.class);
        putShortHeapMethod = getMethod("putShort", long.class, short.class);
        getCharHeapMethod = getMethod("getChar", long.class);
        putCharHeapMethod = getMethod("putChar", long.class, char.class);
        getIntHeapMethod = getMethod("getInt", long.class);
        putIntHeapMethod = getMethod("putInt", long.class, int.class);
        getLongHeapMethod = getMethod("getLong", long.class);
        putLongHeapMethod = getMethod("putLong", long.class, long.class);
        getFloatHeapMethod = getMethod("getFloat", long.class);
        putFloatHeapMethod = getMethod("putFloat", long.class, float.class);
        getDoubleHeapMethod = getMethod("getDouble", long.class);
        putDoubleHeapMethod = getMethod("putDouble", long.class, double.class);
        getAddressHeapMethod = getMethod("getAddress", long.class);
        putAddressHeapMethod = getMethod("putAddress", long.class, long.class);
        allocateMemoryMethod = getMethod("allocateMemory", long.class);
        reallocateMemoryMethod = getMethod("reallocateMemory", long.class, long.class);
        freeMemoryMethod = getMethod("freeMemory", long.class);
    }

    private Method getMethod(String name, Class<?>... paramTypes) {
        try {
            return sun.misc.Unsafe.class.getDeclaredMethod(name, paramTypes);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Failed to get method", e);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T invokeMethod(Method method, Object... args) {
        try {
            return (T) method.invoke(null, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Failed to invoke method", e);
        }
    }

    @Override
    public boolean getBoolean(Object o, long offset) {
        return invokeMethod(getBooleanMethod, o, offset);
    }


    @Override
    public void putBoolean(Object o, long offset, boolean x) {
        invokeMethod(putBooleanMethod, o, offset, x);
    }


    @Override
    public byte getByte(Object o, long offset) {
        return invokeMethod(getByteMethod, o, offset);
    }


    @Override
    public void putByte(Object o, long offset, byte x) {
        invokeMethod(putByteMethod, o, offset, x);
    }


    @Override
    public short getShort(Object o, long offset) {
        return invokeMethod(getShortMethod, o, offset);
    }


    @Override
    public void putShort(Object o, long offset, short x) {
        invokeMethod(putShortMethod, o, offset, x);
    }


    @Override
    public char getChar(Object o, long offset) {
        return invokeMethod(getCharMethod, o, offset);
    }


    @Override
    public void putChar(Object o, long offset, char x) {
        invokeMethod(putCharMethod, o, offset, x);
    }


    @Override
    public int getInt(Object o, long offset) {
        return invokeMethod(getIntMethod, o, offset);
    }


    @Override
    public void putInt(Object o, long offset, int x) {
        invokeMethod(putIntMethod, o, offset, x);
    }


    @Override
    public long getLong(Object o, long offset) {
        return invokeMethod(getLongMethod, o, offset);
    }


    @Override
    public void putLong(Object o, long offset, long x) {
        invokeMethod(putLongMethod, o, offset, x);
    }


    @Override
    public float getFloat(Object o, long offset) {
        return invokeMethod(getFloatMethod, o, offset);
    }


    @Override
    public void putFloat(Object o, long offset, float x) {
        invokeMethod(putFloatMethod, o, offset, x);
    }


    @Override
    public double getDouble(Object o, long offset) {
        return invokeMethod(getDoubleMethod, o, offset);
    }


    @Override
    public void putDouble(Object o, long offset, double x) {
        invokeMethod(putDoubleMethod, o, offset, x);
    }


    @Override
    public Object getObject(Object o, long offset) {
        return invokeMethod(getObjectMethod, o, offset);
    }


    @Override
    public void putObject(Object o, long offset, Object x) {
        invokeMethod(putObjectMethod, o, offset, x);
    }


    @Override
    public boolean getBooleanVolatile(Object o, long offset) {
        return invokeMethod(getBooleanVolatileMethod, o, offset);
    }


    @Override
    public void putBooleanVolatile(Object o, long offset, boolean x) {
        invokeMethod(putBooleanVolatileMethod, o, offset, x);
    }


    @Override
    public byte getByteVolatile(Object o, long offset) {
        return invokeMethod(getByteVolatileMethod, o, offset);
    }


    @Override
    public void putByteVolatile(Object o, long offset, byte x) {
        invokeMethod(putByteVolatileMethod, o, offset, x);
    }


    @Override
    public short getShortVolatile(Object o, long offset) {
        return invokeMethod(getShortVolatileMethod, o, offset);
    }


    @Override
    public void putShortVolatile(Object o, long offset, short x) {
        invokeMethod(putShortVolatileMethod, o, offset, x);
    }


    @Override
    public char getCharVolatile(Object o, long offset) {
        return invokeMethod(getCharVolatileMethod, o, offset);
    }


    @Override
    public void putCharVolatile(Object o, long offset, char x) {
        invokeMethod(putCharVolatileMethod, o, offset, x);
    }


    @Override
    public int getIntVolatile(Object o, long offset) {
        return invokeMethod(getIntVolatileMethod, o, offset);
    }


    @Override
    public void putIntVolatile(Object o, long offset, int x) {
        invokeMethod(putIntVolatileMethod, o, offset, x);
    }


    @Override
    public long getLongVolatile(Object o, long offset) {
        return invokeMethod(getLongVolatileMethod, o, offset);
    }


    @Override
    public void putLongVolatile(Object o, long offset, long x) {
        invokeMethod(putLongVolatileMethod, o, offset, x);
    }


    @Override
    public float getFloatVolatile(Object o, long offset) {
        return invokeMethod(getFloatVolatileMethod, o, offset);
    }


    @Override
    public void putFloatVolatile(Object o, long offset, float x) {
        invokeMethod(putFloatVolatileMethod, o, offset, x);
    }


    @Override
    public double getDoubleVolatile(Object o, long offset) {
        return invokeMethod(getDoubleVolatileMethod, o, offset);
    }


    @Override
    public void putDoubleVolatile(Object o, long offset, double x) {
        invokeMethod(putDoubleVolatileMethod, o, offset, x);
    }


    @Override
    public Object getObjectVolatile(Object o, long offset) {
        return invokeMethod(getObjectVolatileMethod, o, offset);
    }


    @Override
    public void putObjectVolatile(Object o, long offset, Object x) {
        invokeMethod(putObjectVolatileMethod, o, offset, x);
    }


    @Override
    public void setMemory(Object base, long offset, long bytes, byte value) {
        invokeMethod(setMemoryMethod, base, offset, bytes, value);
    }


    @Override
    public void copyMemory(Object srcBase, long srcOffset, Object destBase, long destOffset, long bytes) {
        invokeMethod(copyMemoryMethod, srcBase, srcOffset, destBase, destOffset, bytes);
    }


    @Override
    public long staticFieldOffset(Field f) {
        return invokeMethod(staticFieldOffsetMethod, f);
    }


    @Override
    public long objectFieldOffset(Field f) {
        return invokeMethod(objectFieldOffsetMethod, f);
    }


    @Override
    public Object staticFieldBase(Field f) {
        return invokeMethod(staticFieldBaseMethod, f);
    }


    @Override
    public boolean shouldBeInitialized(Class<?> c) {
        return invokeMethod(shouldBeInitializedMethod, c);
    }


    @Override
    public void ensureClassInitialized(Class<?> c) {
        invokeMethod(ensureClassInitializedMethod, c);
    }


    @Override
    public int arrayBaseOffset(Class<?> arrayClass) {
        // Unsafe.arrayBaseOffset completely halts the JVM with an nonexistent exception (java.lang.InvalidClassException)
        // if given a non-array class (would be completely fine if the exception actually existed)
        // luckily, Unsafe already has constants for every possible return value
        if (!arrayClass.isArray())
            throw new IllegalArgumentException("Class is not an array class!");
        return invokeMethod(arrayBaseOffsetMethod, arrayClass);
    }


    @Override
    public int arrayIndexScale(Class<?> arrayClass) {
        // Unsafe.arrayIndexScale completely halts the JVM with an nonexistent exception (java.lang.InvalidClassException)
        // if given a non-array class (would be completely fine if the exception actually existed)
        // luckily, Unsafe already has constants for every possible return value
        if (!arrayClass.isArray())
            throw new IllegalArgumentException("Class is not an array class!");
        return invokeMethod(arrayIndexScaleMethod, arrayClass);
    }


    @Override
    public int addressSize() {
        return invokeMethod(addressSizeMethod);
    }


    @Override
    public int pageSize() {
        return invokeMethod(pageSizeMethod);
    }

    @Override
    public HeapMemory allocate(long size) {
        return new HeapMemoryImpl(size);
    }

    private final class HeapMemoryImpl extends BaseHeapMemoryImpl {
        private HeapMemoryImpl(long size) {
            super(size);
            address = invokeMethod(allocateMemoryMethod, size);
            clear();
        }

        @Override
        public void set(long offset, long bytes, byte value) {
            checkBounds(offset);
            setMemory(null, offset, bytes, value);
        }

        @Override
        public void reallocate(long newSize) {
            checkIsValid();
            address = invokeMethod(reallocateMemoryMethod, address, newSize);
            size = newSize;
        }

        @Override
        public void copyFrom(HeapMemory src, long srcOffset, long dstOffset, long bytes) {
            if (!src.isValid())
                throw new IllegalStateException("Source native memory is invalid!");
            if (!isValid())
                throw new IllegalArgumentException("Destination native memory is invalid!");
            if (srcOffset < 0)
                throw new IllegalArgumentException("Source offset is negative!");
            if (dstOffset < 0)
                throw new IllegalArgumentException("Destination offset is negative");
            long srcAddress = src.getAddress();
            long srcSize = src.getSize();
            if ((srcAddress + srcOffset) >= (srcAddress + srcSize)) {
                throw new IllegalArgumentException(
                        "Source offset of " + srcOffset + " is out of bounds for source of size " + srcSize);
            }
            if ((address + dstOffset) > (address + size)) {
                throw new IllegalArgumentException(
                        "Destination offset of " + srcOffset + " is out of bounds for destination of size " + srcSize);
            }
            copyMemory(null, srcAddress + srcOffset, null, address + dstOffset, bytes);
        }

        @Override
        public byte getByte(long offset) {
            checkBounds(offset);
            return invokeMethod(getByteHeapMethod, address + offset);
        }

        @Override
        public void putByte(long offset, byte x) {
            checkBounds(offset);
            invokeMethod(putByteHeapMethod, address + offset, x);
        }

        @Override
        public short getShort(long offset) {
            checkBounds(offset);
            return invokeMethod(getShortHeapMethod, address + offset);
        }

        @Override
        public void putShort(long offset, short x) {
            checkBounds(offset);
            invokeMethod(putShortHeapMethod, address + offset);
        }

        @Override
        public char getChar(long offset) {
            checkBounds(offset);
            return invokeMethod(getCharHeapMethod, address + offset);
        }

        @Override
        public void putChar(long offset, char x) {
            checkBounds(offset);
            invokeMethod(putCharHeapMethod, address + offset);
        }

        @Override
        public int getInt(long offset) {
            checkBounds(offset);
            return invokeMethod(getIntHeapMethod, address + offset);
        }

        @Override
        public void putInt(long offset, int x) {
            checkBounds(offset);
            invokeMethod(putIntHeapMethod, address + offset, x);
        }

        @Override
        public long getLong(long offset) {
            checkBounds(offset);
            return invokeMethod(getLongHeapMethod, address + offset);
        }

        @Override
        public void putLong(long offset, long x) {
            checkBounds(offset);
            invokeMethod(putLongHeapMethod, address + offset, x);
        }

        @Override
        public float getFloat(long offset) {
            checkBounds(offset);
            return invokeMethod(getFloatHeapMethod, address + offset);
        }

        @Override
        public void putFloat(long offset, float x) {
            checkBounds(offset);
            invokeMethod(putFloatHeapMethod, address + offset, x);
        }

        @Override
        public double getDouble(long offset) {
            checkBounds(offset);
            return invokeMethod(getDoubleHeapMethod, address + offset);
        }

        @Override
        public void putDouble(long offset, double x) {
            checkBounds(offset);
            invokeMethod(putDoubleHeapMethod, address + offset, x);
        }

        @Override
        public long getAddress(long offset) {
            checkBounds(offset);
            return invokeMethod(getAddressHeapMethod, address + offset);
        }

        @Override
        public void putAddress(long offset, long x) {
            checkBounds(offset);
            invokeMethod(putAddressHeapMethod, address + offset, x);
        }

        @Override
        public void close() {
            if (!isValid)
                return;
            try {
                freeMemoryMethod.invoke(null, address);
            } catch (IllegalAccessException | InvocationTargetException ignored) { }
            isValid = false;
        }
    }
}
