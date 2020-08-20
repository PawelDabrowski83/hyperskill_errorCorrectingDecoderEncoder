package org.hyperskill.model;

import java.util.*;

public class Byte {
    public static final Byte ZEROS = new Byte(List.of(Bit.ZERO), true);
    public static final Byte ONES = new Byte(List.of(Bit.ONE), true);

    List<Bit> bits;

    private Byte(List<Bit> bits) {
        this.bits = bits;
    }
    public Byte(List<Bit> bits, boolean repeating) {
        if (bits == null || bits.size() == 0) {
            bits = new ArrayList<>(List.of(Bit.ZERO));
        }
        if (bits.size() > 8) {
            bits = bits.subList(0,8);
        }
        bits = checkNullBits(bits);

        List<Bit> temp = new ArrayList<>(bits);
        while (temp.size() < 8) {
            if (repeating) {
                temp.add(temp.get(temp.size() - 1));
            } else {
                temp.add(Bit.ZERO);
            }
        }
        new Byte(temp);
    }

    protected List<Bit> checkNullBits(List<Bit> bits) {
        if (bits == null || bits.size() == 0) {
            return Collections.emptyList();
        }
        for (ListIterator<Bit> it = bits.listIterator(); it.hasNext(); ) {
            Bit b = it.next();
            if (b == null) {
                it.set(Bit.ZERO);
            }
        }
        return bits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Byte aByte = (Byte) o;
        return Objects.equals(bits, aByte.bits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bits);
    }

    @Override
    public String toString() {
        return "Byte{" +
                "bits=" + bits +
                '}';
    }
}
