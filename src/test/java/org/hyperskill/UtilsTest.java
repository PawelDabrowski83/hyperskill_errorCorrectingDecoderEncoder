package org.hyperskill;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Random;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class UtilsTest {

    @DisplayName("Should getRandomPosition() returns proper numbers for random with given seed")
    @ParameterizedTest(name = "{index} => expected={0}, seed={1}, length={2}")
    @MethodSource("getRandomPositionArgumentsProvider")
    void getRandomPosition(int expected, int seed, int length) {
        Random random = new Random(seed);
        assertEquals(expected, Utils.getRandomPosition(length, random));
    }
    private static Stream<Arguments> getRandomPositionArgumentsProvider() {
        return Stream.of(
                Arguments.of(5, 0, 8),
                Arguments.of(2, -8, 8),
                Arguments.of(5, 1, 8),
                Arguments.of(-1, 1, 0),
                Arguments.of(-1, 0, Integer.MIN_VALUE),
                Arguments.of(62, Integer.MAX_VALUE, 100),
                Arguments.of(1331656284, Integer.MAX_VALUE, Integer.MAX_VALUE)
        );
    }

}
