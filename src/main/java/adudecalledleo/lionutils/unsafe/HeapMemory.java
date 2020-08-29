package adudecalledleo.lionutils.unsafe;

public interface HeapMemory extends AutoCloseable {
    long getSize();

    long getAddress();

    boolean isValid();

    void set(long offset, long bytes, byte value);

    default void clear() {
        set(0, getSize(), (byte) 0);
    }

    void reallocate(long newSize);

    void copyFrom(HeapMemory src, long srcOffset, long dstOffset, long bytes);

    byte getByte(long offset);

    void putByte(long offset, byte x);

    short getShort(long offset);

    void putShort(long offset, short x);

    char getChar(long offset);

    void putChar(long offset, char x);

    int getInt(long offset);

    void putInt(long offset, int x);

    long getLong(long offset);

    void putLong(long offset, long x);

    float getFloat(long offset);

    void putFloat(long offset, float x);

    double getDouble(long offset);

    void putDouble(long offset, double x);

    long getAddress(long offset);

    void putAddress(long offset, long x);
}
