package org.hyperskill.model;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Byte implements OrderedByte{
    public static final Byte ZEROS = new Byte(List.of(Bit.ZERO), true);
    public static final Byte ONES = new Byte(List.of(Bit.ONE), true);

    List<Bit> bits;

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
        this.bits = temp;
    }

    public Byte(int binaryAsNumber) {
        if (binaryAsNumber < 0) {
            binaryAsNumber = 0;
        }
        if (binaryAsNumber > 255) {
            binaryAsNumber %= 255;
        }
        StringBuilder binaryBuilder = new StringBuilder(Integer.toBinaryString(binaryAsNumber));
        while (binaryBuilder.length() < 8) {
            binaryBuilder.insert(0, 0);
        }
        List<Bit> temp = new ArrayList<>();
        String binary = binaryBuilder.toString();
        for (char c : binary.toCharArray()) {
            if (c == '0') {
                temp.add(Bit.ZERO);
            } else {
                temp.add(Bit.ONE);
            }
        }
        this.bits = temp;
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

    public int getValue() {
        if (this.bits == null || this.bits.size() == 0) {
            return 0;
        }
        int sum = 0;
        int counterDescending = 7;
        for (Bit b : this.bits) {
            sum += b.getValue() * Math.pow(2, counterDescending);
            counterDescending--;
        }
        return sum;
    }

    @Override
    public int compareTo(@NotNull OrderedByte o) {
        return Integer.compare(this.getValue(), o.getValue());
    }
}
