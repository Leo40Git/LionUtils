package adudecalledleo.lionutils.internal.unsafe.impl;

import adudecalledleo.lionutils.unsafe.NativeMemory;
import adudecalledleo.lionutils.unsafe.UnsafeAccess;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeAccessImpl8D implements UnsafeAccess {
    private static Unsafe theUnsafe;

    public UnsafeAccessImpl8D() {
        if (theUnsafe == null) {
            try {
                Field unsafeHolder = Unsafe.class.getDeclaredField("theUnsafe");
                unsafeHolder.setAccessible(true);
                theUnsafe = (Unsafe) unsafeHolder.get(null);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("Failed to get Unsafe instance", e);
            } catch (ClassCastException e) {
                throw new RuntimeException("Failed to cast Unsafe instance to Unsafe?? Something's wrong with this JVM", e);
            } catch (NullPointerException e) {
                throw new RuntimeException("Unsafe holder field is not static?? Something's wrong with this JVM", e);
            }
        }
    }

    @Override
    public NativeMemory allocate(long size) {
        return new NativeMemoryImpl(size);
    }

    @Override
    public byte getByte(long address) {
        return theUnsafe.getByte(address);
    }

    @Override
    public void putByte(long address, byte x) {
        theUnsafe.putByte(address, x);
    }

    @Override
    public short getShort(long address) {
        return theUnsafe.getShort(address);
    }

    @Override
    public void putShort(long address, short x) {
        theUnsafe.putShort(address, x);
    }

    @Override
    public char getChar(long address) {
        return theUnsafe.getChar(address);
    }

    @Override
    public void putChar(long address, char x) {
        theUnsafe.putChar(address, x);
    }

    @Override
    public int getInt(long address) {
        return theUnsafe.getInt(address);
    }

    @Override
    public void putInt(long address, int x) {
        theUnsafe.putInt(address, x);
    }

    @Override
    public long getLong(long address) {
        return theUnsafe.getLong(address);
    }

    @Override
    public void putLong(long address, long x) {
        theUnsafe.putLong(address, x);
    }

    @Override
    public float getFloat(long address) {
        return theUnsafe.getFloat(address);
    }

    @Override
    public void putFloat(long address, float x) {
        theUnsafe.putFloat(address, x);
    }

    @Override
    public double getDouble(long address) {
        return theUnsafe.getDouble(address);
    }

    @Override
    public void putDouble(long address, double x) {
        theUnsafe.putDouble(address, x);
    }

    @Override
    public long getAddress(long address) {
        return theUnsafe.getAddress(address);
    }

    @Override
    public void putAddress(long address, long x) {
        theUnsafe.putAddress(address, x);
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

    private final class NativeMemoryImpl implements NativeMemory {
        private long size;
        private long address;
        private boolean valid;

        private NativeMemoryImpl(long size) {
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
        public void clear() {
            set(size, (byte) 0);
        }

        @Override
        public void reallocate(long newSize) {
            checkIsValid();
            address = theUnsafe.reallocateMemory(address, newSize);
            size = newSize;
        }

        @Override
        public void copyFrom(NativeMemory src, long srcOffset, long dstOffset, long bytes) {
            checkIsValid();
            if (!src.isValid())
                throw new IllegalStateException("Source native memory is invalid!");
            copyMemory(null, src.getAddress() + srcOffset, null, address + dstOffset, bytes);
        }

        @Override
        public byte getByte(long offset) {
            checkIsValid();
            return UnsafeAccessImpl8D.this.getByte(address + offset);
        }

        @Override
        public void putByte(long offset, byte x) {
            checkIsValid();
            UnsafeAccessImpl8D.this.putByte(address + offset, x);
        }

        @Override
        public short getShort(long offset) {
            checkIsValid();
            return UnsafeAccessImpl8D.this.getShort(address + offset);
        }

        @Override
        public void putShort(long offset, short x) {
            checkIsValid();
            UnsafeAccessImpl8D.this.putShort(address + offset, x);
        }

        @Override
        public char getChar(long offset) {
            checkIsValid();
            return UnsafeAccessImpl8D.this.getChar(address + offset);
        }

        @Override
        public void putChar(long offset, char x) {
            checkIsValid();
            UnsafeAccessImpl8D.this.putChar(address + offset, x);
        }

        @Override
        public int getInt(long offset) {
            checkIsValid();
            return UnsafeAccessImpl8D.this.getInt(address + offset);
        }

        @Override
        public void putInt(long offset, int x) {
            checkIsValid();
            UnsafeAccessImpl8D.this.putInt(address + offset, x);
        }

        @Override
        public long getLong(long offset) {
            checkIsValid();
            return UnsafeAccessImpl8D.this.getLong(address + offset);
        }

        @Override
        public void putLong(long offset, long x) {
            checkIsValid();
            UnsafeAccessImpl8D.this.putLong(address + offset, x);
        }

        @Override
        public float getFloat(long offset) {
            checkIsValid();
            return UnsafeAccessImpl8D.this.getFloat(address + offset);
        }

        @Override
        public void putFloat(long offset, float x) {
            checkIsValid();
            UnsafeAccessImpl8D.this.putFloat(address + offset, x);
        }

        @Override
        public double getDouble(long offset) {
            checkIsValid();
            return UnsafeAccessImpl8D.this.getDouble(address + offset);
        }

        @Override
        public void putDouble(long offset, double x) {
            checkIsValid();
            UnsafeAccessImpl8D.this.putDouble(address + offset, x);
        }

        @Override
        public long getAddress(long offset) {
            checkIsValid();
            return UnsafeAccessImpl8D.this.getAddress(address + offset);
        }

        @Override
        public void putAddress(long offset, long x) {
            checkIsValid();
            UnsafeAccessImpl8D.this.putAddress(address + offset, x);
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
