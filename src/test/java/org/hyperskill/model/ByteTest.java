package org.hyperskill.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class ByteTest {

    @DisplayName("should checkNullBits remove null references in List<Bit>")
    @ParameterizedTest(name = "{index} => expected={0}, given={1}")
    @MethodSource("checkNullBitsArgumentsProvider")
    void checkNullBits(List<Bit> expected, List<Bit> given) {
        Byte source = Byte.ZEROS;
        assertEquals(expected, source.checkNullBits(given));
    }
    private static Stream<Arguments> checkNullBitsArgumentsProvider() {
        return Stream.of(
                Arguments.of(
                        Collections.emptyList(),
                        Collections.EMPTY_LIST
                ),
                Arguments.of(
                        Collections.emptyList(),
                        null
                ),
                Arguments.of(
                        List.of(Bit.ONE, Bit.ONE, Bit.ZERO),
                        Arrays.asList(Bit.ONE, Bit.ONE, null)
                ),
                Arguments.of(
                        List.of(Bit.ZERO),
                        Collections.singletonList(Bit.ZERO)
                )
        );
    }

    @DisplayName("should constructor create Byte object with exactly 8 bits")
    @ParameterizedTest(name = "{index} => expected={0}, given={1}, repeat={2}")
    @MethodSource("constructorArgumentsProvider")
    void constructor(Byte expected, List<Bit> given, boolean repeat) {
        assertEquals(expected, new Byte(given, repeat));
    }
    private static Stream<Arguments> constructorArgumentsProvider() {
        return Stream.of(
                Arguments.of(
                        Byte.ZEROS,
                        List.of(Bit.ZERO),
                        true
                ),
                Arguments.of(
                        Byte.ZEROS,
                        List.of(Bit.ZERO),
                        false
                ),
                Arguments.of(
                        Byte.ONES,
                        List.of(Bit.ONE),
                        true
                ),
                Arguments.of(
                        new Byte(List.of(Bit.ONE, Bit.ZERO, Bit.ZERO, Bit.ZERO, Bit.ZERO, Bit.ZERO, Bit.ZERO, Bit.ZERO), false),
                        List.of(Bit.ONE),
                        false
                ),
                Arguments.of(
                        new Byte(List.of(Bit.ONE, Bit.ZERO, Bit.ZERO, Bit.ONE, Bit.ONE, Bit.ZERO, Bit.ZERO, Bit.ZERO), false),
                        List.of(Bit.ONE, Bit.ZERO, Bit.ZERO, Bit.ONE, Bit.ONE, Bit.ZERO),
                        true
                )
        );
    }
}
