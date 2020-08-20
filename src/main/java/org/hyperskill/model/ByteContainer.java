package org.hyperskill.model;

import java.util.Arrays;

public class ByteContainer {

    private final ByteWithParity[] bytes;

    public ByteContainer(ByteWithParity[] bytes) {
        if (bytes == null) {
            bytes = new ByteWithParity[0];
        }
        this.bytes = bytes;
    }

    public ByteWithParity[] getBytes() {
        return bytes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ByteContainer that = (ByteContainer) o;
        return Arrays.equals(bytes, that.bytes);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(bytes);
    }

    @Override
    public String toString() {
        return "ByteContainer{" +
                "bytes=" + Arrays.toString(bytes) +
                '}';
    }
}
