package org.hyperskill.model;

import org.junit.Test;

public class PairTest {

    @Test(expected = UnsupportedOperationException.class)
    public void shouldPairNotAllowThirdBit() {
        Pair pair = Pair.ZERO_ZERO;
        pair.pair.add(Bit.ONE);
    }
}
