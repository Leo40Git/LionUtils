package adudecalledleo.lionutils.internal.unsafe.impl;

import adudecalledleo.lionutils.unsafe.HeapMemory;

abstract class BaseHeapMemoryImpl implements HeapMemory {
    protected long size;
    protected long address;
    protected boolean isValid;

    protected BaseHeapMemoryImpl(long size) {
        this.size = size;
        isValid = true;
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
        return isValid;
    }

    protected void checkIsValid() {
        if (!isValid)
            throw new IllegalStateException("Native memory is invalid!");
    }

    protected void checkBounds(long offset) {
        checkIsValid();
        if (offset < 0)
            throw new IllegalArgumentException("Offset is negative!");
        if ((address + offset) >= (address + size))
            throw new IllegalArgumentException("Offset of " + offset + " is out of bounds for size " + size);
    }

    protected void checkCopyFrom(HeapMemory src, long srcOffset, long dstOffset, long bytes) {
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
        if ((srcAddress + srcOffset + bytes) >= (srcAddress + srcSize)) throw new IllegalArgumentException(
                "Source offset of " + srcOffset + " with byte count " + bytes +
                        " is out of bounds for source of size " + srcSize);
        if ((address + dstOffset + bytes) > (address + size)) throw new IllegalArgumentException(
                "Destination offset of " + srcOffset + " with byte count " + bytes +
                        " is out of bounds for destination of size " + srcSize);
    }
}
