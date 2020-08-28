package org.hyperskill;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.hyperskill.HammingEncoder.HammingCode;

public class HammingEncoderTest {

    @Test
    public void shouldHammingEncoderConstructorGivenNullCreateInstance() {
        HammingEncoder emptyEncoder = new HammingEncoder("");
        HammingEncoder nullEncoder = new HammingEncoder(null);
        assertEquals(emptyEncoder, nullEncoder);
    }

    @DisplayName("should encode() turn byte-string representation into byte-strings with hamming code")
    @ParameterizedTest
    @MethodSource("encodeArgumentsProvider")
    void encode(String expected, String given) {
        HammingEncoder encoder = new HammingEncoder(given);
        assertEquals(expected, encoder.encode());
    }
    private static Stream<Arguments> encodeArgumentsProvider() {
        return Stream.of(
                Arguments.of(
                        "",
                        ""
                ),
                Arguments.of(
                        "0100101010011000",
                        "01010100"
                ),
                Arguments.of(
                        "1100110001001010",
                        "01100101"
                ),
                Arguments.of(
                        "0001111010000110",
                        "01110011"
                ),
                Arguments.of(
                        "0001111010011000",
                        "01110100"
                ),
                Arguments.of(
                        "",
                        "011"
                ),
                Arguments.of(
                        "",
                        ";12"
                )
        );
    }

    @DisplayName("should encodeSubstring return byte-string after hamming")
    @ParameterizedTest
    @MethodSource("encodeSubstringArgumentsProvider")
    void encodeSubstring(String expected, String given) {
        HammingEncoder encoder = new HammingEncoder("");
        assertEquals(expected, encoder.encodeSubstring(given));
    }
    private static Stream<Arguments> encodeSubstringArgumentsProvider() {
        return Stream.of(
                Arguments.of(
                        "10110100",
                        "1010"
                ),
                Arguments.of(
                        "01001010",
                        "0101"
                ),
                Arguments.of(
                        "10011000",
                        "0100"
                ),
                Arguments.of(
                        "00011110",
                        "0111"
                ),
                Arguments.of(
                        "11111110",
                        "1111"
                ),
                Arguments.of(
                        "01100110",
                        "1011"
                ),
                Arguments.of(
                        "00000000",
                        "0000"
                )
        );
    }

    @DisplayName("should insertHammingCodePlaceholders() transform 4 data bits into 8-bit pattern with placeholders for parity bits")
    @ParameterizedTest
    @MethodSource("insertHammingCodePlaceholdersArgumentsProvider")
    void insertHammingCodePlaceholders(StringBuilder expected, StringBuilder given) {
        HammingEncoder encoder = new HammingEncoder("");
        assertEquals(expected.toString(), encoder.insertHammingCodePlaceholders(given).toString());
    }
    private static Stream<Arguments> insertHammingCodePlaceholdersArgumentsProvider() {
        return Stream.of(
                Arguments.of(
                        new StringBuilder("00100100"),
                        new StringBuilder("1010")
                ),
                Arguments.of(
                        new StringBuilder("00001000"),
                        new StringBuilder("0100")
                ),
                Arguments.of(
                        new StringBuilder("00101110"),
                        new StringBuilder("1111")
                ),
                Arguments.of(
                        new StringBuilder("00001110"),
                        new StringBuilder("0111")
                )
        );
    }

    @DisplayName("should calculateParity() return proper bit-string for Hamming code")
    @ParameterizedTest
    @MethodSource("calculateParityArgumentsProvider")
    void calculateParity(char expected, String substring, HammingCode hammingCode) {
        HammingEncoder encoder = new HammingEncoder("");
        assertEquals(expected, encoder.calculateParity(substring, hammingCode));
    }
    private static Stream<Arguments> calculateParityArgumentsProvider() {
        return Stream.of(
                Arguments.of(
                        '1',
                        "00100100",
                        HammingCode.P1
                ),
                Arguments.of(
                        '0',
                        "10100100",
                        HammingCode.P2
                ),
                Arguments.of(
                        '1',
                        "10100100",
                        HammingCode.P4
                ),
                Arguments.of(
                        '0',
                        "00001010",
                        HammingCode.P1
                ),
                Arguments.of(
                        '1',
                        "00001010",
                        HammingCode.P2
                ),
                Arguments.of(
                        '0',
                        "01001010",
                        HammingCode.P4
                ),
                Arguments.of(
                        '1',
                        "00001000",
                        HammingCode.P1
                ),
                Arguments.of(
                        '0',
                        "10001000",
                        HammingCode.P2
                ),
                Arguments.of(
                        '1',
                        "10001000",
                        HammingCode.P4
                )
        );
    }




}
