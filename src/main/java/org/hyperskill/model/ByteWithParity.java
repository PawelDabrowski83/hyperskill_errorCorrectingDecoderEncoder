package org.hyperskill.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ByteWithParity {
    public static final ByteWithParity ZEROS = new ByteWithParity(List.of(Pair.ZERO_ZERO, Pair.ZERO_ZERO, Pair.ZERO_ZERO));
    public static final ByteWithParity ONES = new ByteWithParity(List.of(Pair.ONE_ONE, Pair.ONE_ONE, Pair.ONE_ONE));

    List<Pair> pairs;
    Pair parity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ByteWithParity aByte = (ByteWithParity) o;
        return Objects.equals(pairs, aByte.pairs) &&
                Objects.equals(parity, aByte.parity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pairs, parity);
    }

    @Override
    public String toString() {
        return "Byte{" +
                "pairs=" + pairs +
                ", parity=" + parity +
                '}';
    }

    public ByteWithParity(List<Pair> pairs) {
        if (pairs == null) {
            pairs = List.of(Pair.ZERO_ZERO, Pair.ZERO_ZERO, Pair.ZERO_ZERO);
        }
        List<Pair> temp = new ArrayList<>(pairs);
        while (temp.size() < 3) {
            temp.add(Pair.ZERO_ZERO);
        }
        this.pairs = temp;
        this.parity = getParity();
    }

    protected Pair getParity() {
        if (pairs == null || pairs.size() == 0) {
            return Pair.ZERO_ZERO;
        }
        boolean result = false;
        for (Pair p : pairs) {
            if (p.getValue() == 1) {
                result = !result;
            }
        }
        return result ? Pair.ONE_ONE : Pair.ZERO_ZERO;
    }
}
