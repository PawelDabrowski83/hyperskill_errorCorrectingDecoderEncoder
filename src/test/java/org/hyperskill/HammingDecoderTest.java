package org.hyperskill;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class HammingDecoderTest {

    @DisplayName("should decode return string with decoded message")
    @ParameterizedTest
    @MethodSource("decodeArgumentsProvider")
    void decode(String expected, String given) {
        HammingDecoder decoder = new HammingDecoder(given);
        assertEquals(expected, decoder.decode());
    }
    private static Stream<Arguments> decodeArgumentsProvider() {
        return Stream.of(
                Arguments.of(
                        "01010100",
                        "0100101010011000"
                ),
                Arguments.of(
                        "01100101",
                        "1100110001001010"
                ),
                Arguments.of(
                        "01110011",
                        "0001111010000110"
                ),
                Arguments.of(
                        "01110100",
                        "0001111010011000"
                ),
                Arguments.of(
                        "",
                        ""
                ),
                Arguments.of(
                        "01000100",
                        "1000100010001000"
                )
        );
    }

    @DisplayName("should correctNoise() corrects byte-strings with hamming code")
    @ParameterizedTest
    @MethodSource("correctNoiseArgumentsProvider")
    void correctNoise(String expected, String given) {
        HammingDecoder decoder = new HammingDecoder("");
        assertEquals(expected, decoder.correctNoise(given));
    }
    private static Stream<Arguments> correctNoiseArgumentsProvider() {
        return Stream.of(
                Arguments.of(
                        "01001010",
                        "01011010"
                ),
                Arguments.of(
                        "10011000",
                        "10001000"
                ),
                Arguments.of(
                        "11001100",
                        "10001100"
                ),
                Arguments.of(
                        "01001010",
                        "01001110"
                ),
                Arguments.of(
                        "00011110",
                        "00010110"
                ),
                Arguments.of(
                        "10000110",
                        "10100110"
                ),
                Arguments.of(
                        "00011110",
                        "00111110"
                ),
                Arguments.of(
                        "10011000",
                        "10010000"
                ),
                Arguments.of(
                        "10011000",
                        "10011000"
                ),
                Arguments.of(
                        "",
                        ""
                ),
                Arguments.of(
                        "10011000",
                        "10001000"
                ),
                Arguments.of(
                        "1001100010011000",
                        "1000100010001000"
                )
        );
    }

    @DisplayName("should decodeByte return 4 data bits from 8-bit hamming byte")
    @ParameterizedTest
    @MethodSource("decodeByteArgumentsProvider")
    void decodeByte(String expected, String given) {
        HammingDecoder decoder = new HammingDecoder("");
        assertEquals(expected, decoder.decodeByte(given));
    }
    private static Stream<Arguments> decodeByteArgumentsProvider() {
        return Stream.of(
                Arguments.of(
                        "1010",
                        "10110100"
                ),
                Arguments.of(
                        "0101",
                        "01001010"
                ),
                Arguments.of(
                        "1111",
                        "11111110"
                ),
                Arguments.of(
                        "0000",
                        "00000000"
                ),
                Arguments.of(
                        "1011",
                        "01100110"
                )
        );
    }
}
