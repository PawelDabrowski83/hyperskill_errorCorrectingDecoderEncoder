package org.hyperskill.model;

import java.util.Objects;

public class Bit {

    public static final Bit ONE = new Bit(1);
    public static final Bit ZERO = new Bit(0);

    private final int value;

    private Bit(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bit bit = (Bit) o;
        return value == bit.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return String.valueOf(getValue());
    }
}
