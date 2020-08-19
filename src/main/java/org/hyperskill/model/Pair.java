package org.hyperskill.model;

import java.util.List;
import java.util.Objects;

public class Pair {

    List<Bit> pair;

    public Pair(Bit b1, Bit b2) {
        if (b1 == null) {
            b1 = Bit.ZERO;
        }
        if (b2 == null) {
            b2 = Bit.ZERO;
        }
        this.pair = List.of(b1, b2);
    }

    public Pair(Bit bit) {
        if (bit == null) {
            bit = Bit.ZERO;
        }
        this.pair = List.of(bit, bit);
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
        return "Pair{" +
                "pair=" + pair +
                '}';
    }
}
