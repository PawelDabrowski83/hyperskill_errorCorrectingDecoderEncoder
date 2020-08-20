package org.hyperskill.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class ByteWithParityTest {

    @DisplayName("should class constructor allow creation of objects with 8 bits only")
    @ParameterizedTest(name = "{index} => expected={0}, given={1}, parity={2}")
    @MethodSource("constructorArgumentsProvider")
    void constructor(ByteWithParity expected, List<Pair> given) {
        assertEquals(expected, new ByteWithParity(given));
    }
    private static Stream<Arguments> constructorArgumentsProvider() {
        return Stream.of(
                Arguments.of(
                        ByteWithParity.ZEROS,
                        List.of(Pair.ZERO_ZERO)
                ),
                Arguments.of(
                        ByteWithParity.ZEROS,
                        List.of(Pair.ZERO_ZERO, Pair.ZERO_ZERO)
                ),
                Arguments.of(
                        ByteWithParity.ZEROS,
                        List.of(Pair.ZERO_ZERO, Pair.ZERO_ZERO, Pair.ZERO_ZERO)
                ),
                Arguments.of(
                        new ByteWithParity(List.of(Pair.ONE_ONE, Pair.ZERO_ZERO, Pair.ZERO_ZERO)),
                        List.of(Pair.ONE_ONE)
                ),
                Arguments.of(
                        new ByteWithParity(List.of(Pair.ONE_ONE, Pair.ONE_ONE, Pair.ZERO_ZERO)),
                        List.of(Pair.ONE_ONE, Pair.ONE_ONE)
                ),
                Arguments.of(
                        ByteWithParity.ONES,
                        List.of(Pair.ONE_ONE, Pair.ONE_ONE, Pair.ONE_ONE)
                )
        );
    }

    @DisplayName("should getParity return object pair with two parity bits")
    @ParameterizedTest(name = "{index} => expected={0}, given={1}")
    @MethodSource("getParityArgumentsProvider")
    void getParity(Pair expected, List<Pair> pairs) {
        ByteWithParity byteObject = new ByteWithParity(pairs);
        assertEquals(expected, byteObject.getParity());
    }
    private static Stream<Arguments> getParityArgumentsProvider() {
        return Stream.of(
                Arguments.of(
                        Pair.ZERO_ZERO,
                        List.of(Pair.ZERO_ZERO, Pair.ZERO_ZERO, Pair.ZERO_ZERO)
                ),
                Arguments.of(
                        Pair.ONE_ONE,
                        List.of(Pair.ZERO_ZERO, Pair.ZERO_ZERO, Pair.ONE_ONE)
                ),
                Arguments.of(
                        Pair.ONE_ONE,
                        List.of(Pair.ZERO_ZERO, Pair.ONE_ONE, Pair.ZERO_ZERO)
                ),
                Arguments.of(
                        Pair.ZERO_ZERO,
                        List.of(Pair.ZERO_ZERO, Pair.ONE_ONE, Pair.ONE_ONE)
                ),
                Arguments.of(
                        Pair.ONE_ONE,
                        List.of(Pair.ONE_ONE, Pair.ZERO_ZERO, Pair.ZERO_ZERO)
                ),
                Arguments.of(
                        Pair.ZERO_ZERO,
                        List.of(Pair.ONE_ONE, Pair.ZERO_ZERO, Pair.ONE_ONE)
                ),
                Arguments.of(
                        Pair.ZERO_ZERO,
                        List.of(Pair.ONE_ONE, Pair.ONE_ONE, Pair.ZERO_ZERO)
                ),
                Arguments.of(
                        Pair.ONE_ONE,
                        List.of(Pair.ONE_ONE, Pair.ONE_ONE, Pair.ONE_ONE)
                )
        );
    }

    @DisplayName("should getValue return int value of descripted byte")
    @ParameterizedTest(name = "{index} => expected={0}, given={1}")
    @MethodSource("getValueArgumentsProvider")
    void getValue(int expected, ByteWithParity given) {
        assertEquals(expected, given.getValue());
    }
    private static Stream<Arguments> getValueArgumentsProvider() {
        return Stream.of(
                Arguments.of(
                        0,
                        ByteWithParity.ZEROS
                ),
                Arguments.of(
                        255,
                        ByteWithParity.ONES
                ),
                Arguments.of(
                        0,
                        new ByteWithParity(null)
                ),
                Arguments.of(
                        224,
                        new ByteWithParity(List.of(Pair.ONE_ONE, Pair.ONE_ZERO, Pair.ZERO_ZERO))
                ),
                Arguments.of(
                        91,
                        new ByteWithParity(List.of(Pair.ZERO_ONE, Pair.ZERO_ONE, Pair.ONE_ZERO))
                ),
                Arguments.of(
                        7,
                        new ByteWithParity(List.of(Pair.ZERO_ZERO, Pair.ZERO_ZERO, Pair.ZERO_ONE))
                )
        );
    }
}
