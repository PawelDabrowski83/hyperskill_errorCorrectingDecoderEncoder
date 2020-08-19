package org.hyperskill;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class BitLevelCorrectionTest {

    @DisplayName("Should isParityCorrect() checks if byte has three pairs of bits and one parity pair, that can be damaged")
    @ParameterizedTest(name = "{index} => expected={0}, byte={1}")
    @MethodSource("isParityCorrectArgumentsProvider")
    void isParityCorrect(boolean expected, int binary) {
        assertEquals(expected, BitLevelCorrection.isParityCorrect(binary));
    }
    private static Stream<Arguments> isParityCorrectArgumentsProvider() {
        return Stream.of(
                Arguments.of(false, 0b0011_0000),
                Arguments.of(false, 0b1110_1101),
                Arguments.of(true, 0b0011_1100),
                Arguments.of(true, 0b0000_0000),
                Arguments.of(false, 0b1010_0101),
                Arguments.of(true, 0b1111_1111),
                Arguments.of(false, 0b1011_1111)
        );
    }
}
