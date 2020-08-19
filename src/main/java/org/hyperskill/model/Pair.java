package org.hyperskill.model;

import java.util.List;
import java.util.Objects;

public class Pair {
    public static final Pair ZERO_ZERO = new Pair(Bit.ZERO, Bit.ZERO);
    public static final Pair ONE_ONE = new Pair(Bit.ONE, Bit.ONE);
    public static final Pair ZERO_ONE = new Pair(Bit.ZERO, Bit.ONE);
    public static final Pair ONE_ZERO = new Pair(Bit.ONE, Bit.ZERO);

    List<Bit> pair;

    private Pair(Bit b1, Bit b2) {
        if (b1 == null) {
            b1 = Bit.ZERO;
        }
        if (b2 == null) {
            b2 = Bit.ZERO;
        }
        this.pair = List.of(b1, b2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair1 = (Pair) o;
        return pair.equals(pair1.pair);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pair);
    }

    @Override
    public String toString() {
        return "Pair{" + pair + '}';
    }

    public int getValue() {
        if (Pair.ZERO_ZERO.equals(this)) {
            return 0;
        }
        return 1;
    }
}
