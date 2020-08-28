package org.hyperskill;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

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


}
