package org.hyperskill;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Random;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class SymbolLevelErrorEmulatorTest {

    @DisplayName("Should createRandomCharError() switch random char in given string with a random char")
    @ParameterizedTest(name = "{index} => expected={0}, seed={1}, text={2}")
    @MethodSource("createRandomCharErrorArgumentsProvider")
    void createRandomCharError(String expected, int seed, SymbolLevelErrorEmulator symbolLevelErrorEmulator) {
        Random random = new Random(seed);
        random.nextDouble();
        assertEquals(expected, symbolLevelErrorEmulator.createRandomCharError(random));
    }
    private static Stream<Arguments> createRandomCharErrorArgumentsProvider() {
        return Stream.of(
                Arguments.of(
                        "Al[ ma kota",
                        0,
                        new SymbolLevelErrorEmulator("Ala ma kota")
                ),
                Arguments.of(
                        "[ot",
                        0,
                        new SymbolLevelErrorEmulator("kot")
                ),
                Arguments.of(
                        "[ies",
                        0,
                        new SymbolLevelErrorEmulator("pies")
                ),
                Arguments.of(
                        "Lorem[ipsum avangardum",
                        0,
                        new SymbolLevelErrorEmulator("Lorem ipsum avangardum")
                ),
                Arguments.of(
                        "",
                        0,
                        new SymbolLevelErrorEmulator("")
                )
        );
    }

    @DisplayName("should createRandomChar() generate a random printable character")
    @ParameterizedTest(name = "{index} => expected={0}, seed={1}")
    @MethodSource("createRandomCharArgumentsProvider")
    void createRandomChar(char expected, int seed) {
        Random random = new Random(seed);
        random.nextDouble();
        assertEquals(expected, SymbolLevelErrorEmulator.createRandomChar(random));
    }
    private static Stream<Arguments> createRandomCharArgumentsProvider() {
        return Stream.of(
                Arguments.of('6', 0),
                Arguments.of('F', 1),
                Arguments.of('t', 2),
                Arguments.of('!', -1)
        );
    }
}
