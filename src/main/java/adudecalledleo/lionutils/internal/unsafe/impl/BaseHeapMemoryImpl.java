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
}
