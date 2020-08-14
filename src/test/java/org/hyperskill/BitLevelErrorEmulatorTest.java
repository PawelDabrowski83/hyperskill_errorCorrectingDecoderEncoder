package org.hyperskill;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class BitLevelErrorEmulatorTest {

    @DisplayName("should invertBit() switches 1/0 at given byte position")
    @ParameterizedTest(name = "{index} => expected={0}, position={1}, byte={2}")
    @MethodSource("invertBitArgumentsProvider")
    void invertBit(int expected, int position, int given) {
        BitLevelErrorEmulator emulator = new BitLevelErrorEmulator(given);
        assertEquals(expected, emulator.invertBit(position).getContent());
    }
    private static Stream<Arguments> invertBitArgumentsProvider() {
        return Stream.of(
                Arguments.of(
                        0b1011_1111,
                        1,
                        0b1111_1111
                ),
                Arguments.of(
                        0b0000_0101,
                        7,
                        0b0000_0100
                ),
                Arguments.of(
                        0b1000_1101,
                        0,
                        0b0000_1101
                ),
                Arguments.of(
                        0b0000_1111,
                        Integer.MIN_VALUE,
                        0b0000_1111
                ),
                Arguments.of(
                        0b1010_0101,
                        10,
                        0b1010_0101
                ),
                Arguments.of(
                        0b1100_0101,
                        Integer.MAX_VALUE,
                        0b1100_0101
                )
        );
    }

}
