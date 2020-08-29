package adudecalledleo.lionutils.internal.unsafe.impl;

import adudecalledleo.lionutils.unsafe.HeapMemory;
import adudecalledleo.lionutils.unsafe.UnsafeAccess;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

// Written for Java 8 and below, although it appears to work with Java 9 and above too
public class UnsafeAccessImpl implements UnsafeAccess {
    private static Unsafe theUnsafe;

    public UnsafeAccessImpl() {
        if (theUnsafe == null) {
            try {
                Field unsafeHolder = Unsafe.class.getDeclaredField("theUnsafe");
                unsafeHolder.setAccessible(true);
                theUnsafe = (Unsafe) unsafeHolder.get(null);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("Failed to get Unsafe instance", e);
            } catch (ClassCastException e) {
                throw new RuntimeException("Failed to cast Unsafe instance to Unsafe?? Something's wrong with this JVM",
                        e);
            } catch (NullPointerException e) {
                throw new RuntimeException("Unsafe holder field is not static?? Something's wrong with this JVM", e);
            }
        }
    }

    @Override
    public int getInt(Object o, long offset) {
        return theUnsafe.getInt(o, offset);
    }

    @Override
    public void putInt(Object o, long offset, int x) {
        theUnsafe.putInt(o, offset, x);
    }

    @Override
    public Object getObject(Object o, long offset) {
        return theUnsafe.getObject(o, offset);
    }

    @Override
    public void putObject(Object o, long offset, Object x) {
        theUnsafe.putObject(o, offset, x);
    }

    @Override
    public boolean getBoolean(Object o, long offset) {
        return theUnsafe.getBoolean(o, offset);
    }

    @Override
    public void putBoolean(Object o, long offset, boolean x) {
        theUnsafe.putBoolean(o, offset, x);
    }

    @Override
    public byte getByte(Object o, long offset) {
        return theUnsafe.getByte(offset);
    }

    @Override
    public void putByte(Object o, long offset, byte x) {
        theUnsafe.putByte(o, offset, x);
    }

    @Override
    public short getShort(Object o, long offset) {
        return theUnsafe.getShort(o, offset);
    }

    @Override
    public void putShort(Object o, long offset, short x) {
        theUnsafe.putShort(o, offset, x);
    }

    @Override
    public char getChar(Object o, long offset) {
        return theUnsafe.getChar(o, offset);
    }

    @Override
    public void putChar(Object o, long offset, char x) {
        theUnsafe.putChar(o, offset, x);
    }

    @Override
    public long getLong(Object o, long offset) {
        return theUnsafe.getLong(o, offset);
    }

    @Override
    public void putLong(Object o, long offset, long x) {
        theUnsafe.putLong(o, offset, x);
    }

    @Override
    public float getFloat(Object o, long offset) {
        return theUnsafe.getFloat(o, offset);
    }

    @Override
    public void putFloat(Object o, long offset, float x) {
        theUnsafe.putFloat(o, offset, x);
    }

    @Override
    public double getDouble(Object o, long offset) {
        return theUnsafe.getDouble(o, offset);
    }

    @Override
    public void putDouble(Object o, long offset, double x) {
        theUnsafe.putDouble(o, offset, x);
    }

    @Override
    public HeapMemory allocate(long size) {
        return new HeapMemoryImpl(size);
    }

    @Override
    public void setMemory(Object base, long offset, long bytes, byte value) {
        theUnsafe.setMemory(base, offset, bytes, value);
    }

    @Override
    public void copyMemory(Object srcBase, long srcOffset, Object destBase, long destOffset, long bytes) {
        theUnsafe.copyMemory(srcBase, srcOffset, destBase, destOffset, bytes);
    }

    @Override
    public long staticFieldOffset(Field f) {
        return theUnsafe.staticFieldOffset(f);
    }

    @Override
    public long objectFieldOffset(Field f) {
        return theUnsafe.objectFieldOffset(f);
    }

    @Override
    public Object staticFieldBase(Field f) {
        return theUnsafe.staticFieldBase(f);
    }

    @Override
    public boolean shouldBeInitialized(Class<?> c) {
        return theUnsafe.shouldBeInitialized(c);
    }

    @Override
    public void ensureClassInitialized(Class<?> c) {
        theUnsafe.ensureClassInitialized(c);
    }

    @Override
    public int arrayBaseOffset(Class<?> arrayClass) {
        if (arrayClass == byte[].class)
            return Unsafe.ARRAY_BYTE_BASE_OFFSET;
        if (arrayClass == short[].class)
            return Unsafe.ARRAY_SHORT_BASE_OFFSET;
        if (arrayClass == char[].class)
            return Unsafe.ARRAY_CHAR_BASE_OFFSET;
        if (arrayClass == int[].class)
            return Unsafe.ARRAY_INT_BASE_OFFSET;
        if (arrayClass == long[].class)
            return Unsafe.ARRAY_LONG_BASE_OFFSET;
        if (arrayClass == float[].class)
            return Unsafe.ARRAY_FLOAT_BASE_OFFSET;
        if (arrayClass == double[].class)
            return Unsafe.ARRAY_DOUBLE_BASE_OFFSET;
        return Unsafe.ARRAY_OBJECT_BASE_OFFSET;
    }

    @Override
    public int arrayIndexScale(Class<?> arrayClass) {
        if (arrayClass == byte[].class)
            return Unsafe.ARRAY_BYTE_INDEX_SCALE;
        if (arrayClass == short[].class)
            return Unsafe.ARRAY_SHORT_INDEX_SCALE;
        if (arrayClass == char[].class)
            return Unsafe.ARRAY_CHAR_INDEX_SCALE;
        if (arrayClass == int[].class)
            return Unsafe.ARRAY_INT_INDEX_SCALE;
        if (arrayClass == long[].class)
            return Unsafe.ARRAY_LONG_INDEX_SCALE;
        if (arrayClass == float[].class)
            return Unsafe.ARRAY_FLOAT_INDEX_SCALE;
        if (arrayClass == double[].class)
            return Unsafe.ARRAY_DOUBLE_INDEX_SCALE;
        return Unsafe.ARRAY_OBJECT_INDEX_SCALE;
    }

    @Override
    public int addressSize() {
        return Unsafe.ADDRESS_SIZE;
    }

    @Override
    public int pageSize() {
        return theUnsafe.pageSize();
    }

    private final class HeapMemoryImpl implements HeapMemory {
        private long size;
        private long address;
        private boolean valid;

        private HeapMemoryImpl(long size) {
            this.size = size;
            address = theUnsafe.allocateMemory(size);
            valid = true;
            clear();
        }

        @Override
        public long getSize() {
            return size;
        }

        @Override
        public long getAddress() {
            return address;
        }

        @Override
        public boolean isValid() {
            return valid;
        }

        private void checkIsValid() {
            if (!valid)
                throw new IllegalStateException("Native memory is invalid!");
        }

        @Override
        public void set(long bytes, byte value) {
            checkIsValid();
            setMemory(null, address, bytes, value);
        }

        @Override
        public void reallocate(long newSize) {
            checkIsValid();
            address = theUnsafe.reallocateMemory(address, newSize);
            size = newSize;
        }

        @Override
        public void copyFrom(HeapMemory src, long srcOffset, long dstOffset, long bytes) {
            checkIsValid();
            if (!src.isValid())
                throw new IllegalStateException("Source native memory is invalid!");
            copyMemory(null, src.getAddress() + srcOffset, null, address + dstOffset, bytes);
        }

        @Override
        public byte getByte(long offset) {
            checkIsValid();
            return theUnsafe.getByte(address + offset);
        }

        @Override
        public void putByte(long offset, byte x) {
            checkIsValid();
            theUnsafe.putByte(address + offset, x);
        }

        @Override
        public short getShort(long offset) {
            checkIsValid();
            return theUnsafe.getShort(address + offset);
        }

        @Override
        public void putShort(long offset, short x) {
            checkIsValid();
            theUnsafe.putShort(address + offset, x);
        }

        @Override
        public char getChar(long offset) {
            checkIsValid();
            return theUnsafe.getChar(address + offset);
        }

        @Override
        public void putChar(long offset, char x) {
            checkIsValid();
            theUnsafe.putChar(address + offset, x);
        }

        @Override
        public int getInt(long offset) {
            checkIsValid();
            return theUnsafe.getInt(address + offset);
        }

        @Override
        public void putInt(long offset, int x) {
            checkIsValid();
            theUnsafe.putInt(address + offset, x);
        }

        @Override
        public long getLong(long offset) {
            checkIsValid();
            return theUnsafe.getLong(address + offset);
        }

        @Override
        public void putLong(long offset, long x) {
            checkIsValid();
            theUnsafe.putLong(address + offset, x);
        }

        @Override
        public float getFloat(long offset) {
            checkIsValid();
            return theUnsafe.getFloat(address + offset);
        }

        @Override
        public void putFloat(long offset, float x) {
            checkIsValid();
            theUnsafe.putFloat(address + offset, x);
        }

        @Override
        public double getDouble(long offset) {
            checkIsValid();
            return theUnsafe.getDouble(address + offset);
        }

        @Override
        public void putDouble(long offset, double x) {
            checkIsValid();
            theUnsafe.putDouble(address + offset, x);
        }

        @Override
        public long getAddress(long offset) {
            checkIsValid();
            return theUnsafe.getAddress(address + offset);
        }

        @Override
        public void putAddress(long offset, long x) {
            checkIsValid();
            theUnsafe.putAddress(address + offset, x);
        }

        @Override
        public void close() {
            if (!valid)
                return;
            theUnsafe.freeMemory(address);
            valid = false;
        }
    }
}
