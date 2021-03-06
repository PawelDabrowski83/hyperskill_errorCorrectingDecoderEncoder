package org.hyperskill;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class SymbolLevelCorrectionTest {

    @DisplayName("should multiplyMessage() return text with every char multiplied")
    @ParameterizedTest(name = "{index} => expected={0}, given={1}, multiplier={2}")
    @MethodSource("multiplyMessageArgumentsProvider")
    void multiplyMessage(String expected, String given, int multiplier) {
        SymbolLevelCorrection symbolLevelCorrection = new SymbolLevelCorrection(given);
        assertEquals(expected, symbolLevelCorrection.multiplyMessage(multiplier).getText());
    }
    private static Stream<Arguments> multiplyMessageArgumentsProvider() {
        return Stream.of(
                Arguments.of(
                        "aaabbbccc",
                        "abc",
                        3
                ),
                Arguments.of(
                        "",
                        "",
                        5
                ),
                Arguments.of(
                        ";;;",
                        ";",
                        3
                ),
                Arguments.of(
                        "AAAlllaaa   mmmaaa   kkkoootttaaa",
                        "Ala ma kota",
                        3
                ),
                Arguments.of(
                        "---111",
                        "-1",
                        3
                ),
                Arguments.of(
                        "abc",
                        "abc",
                        1
                ),
                Arguments.of(
                        "azaliż",
                        "azaliż",
                        -1
                ),
                Arguments.of(
                        "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$",
                        "$",
                        100
                )
        );
    }

    @DisplayName("should multiplyChar() returns string with multiplied char")
    @ParameterizedTest(name = "{index} => expected={0}, given={1}, multiplier={2}")
    @MethodSource("multiplyCharArgumentsProvider")
    void multiplyChar(String expected, char given, int multiplier) {
        assertEquals(expected, SymbolLevelCorrection.multiplyChar(given, multiplier));
    }
    private static Stream<Arguments> multiplyCharArgumentsProvider() {
        return Stream.of(
                Arguments.of(
                        "aaaaa",
                        'a',
                        5
                ),
                Arguments.of(
                        "zzz",
                        'z',
                        3
                ),
                Arguments.of(
                        "",
                        'z',
                        0
                ),
                Arguments.of(
                        ";;;;;;;;",
                        ';',
                        8
                ),
                Arguments.of(
                        "                ",
                        ' ',
                        16
                ),
                Arguments.of(
                        "a",
                        'a',
                        Integer.MIN_VALUE
                )
        );
    }

    @DisplayName("should findRepeatedChar returns char repeated more than once in given string")
    @ParameterizedTest(name = "{index} => expected={0}, text={1}")
    @MethodSource("findRepeatedCharArgumentsProvider")
    void findRepeatedChar(char expected, String given) {
        assertEquals(expected, SymbolLevelCorrection.findRepeatedChar(given));
    }
    private static Stream<Arguments> findRepeatedCharArgumentsProvider() {
        return Stream.of(
                Arguments.of(
                        'a',
                        "aaka"
                ),
                Arguments.of(
                        'z',
                        "azzzzzzzzzzzzzzzzzzzzzzz"
                ),
                Arguments.of(
                        ';',
                        ";;a"
                )
        );
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldFindRepeatedCharGivenNullThrowException() {
        SymbolLevelCorrection.findRepeatedChar(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldFindRepeatedCharGivenEmptyThrowException() {
        SymbolLevelCorrection.findRepeatedChar("");
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldFindRepeatedCharGivenShortStringThrowException() {
        SymbolLevelCorrection.findRepeatedChar("aa");
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldFindRepeatedCharGivenStringWithoutRepetitionThrowException() {
        SymbolLevelCorrection.findRepeatedChar("abcd");
    }

    @DisplayName("should decodeMultipliedMessage returns message without char multiplication and errors")
    @ParameterizedTest(name = "{index} => expected={0}, text={1}, multiplier={2}")
    @MethodSource("decodeMultipliedMessageArgumentsProvider")
    void decodeMultipliedMessage(SymbolLevelCorrection expected, SymbolLevelCorrection given, int multiplier) {
        assertEquals(expected, given.decodeMultipliedMessage(multiplier));
    }
    private static Stream<Arguments> decodeMultipliedMessageArgumentsProvider() {
        return Stream.of(
                Arguments.of(
                        new SymbolLevelCorrection("abc"),
                        new SymbolLevelCorrection("axa$bbcc|"),
                        3
                ),
                Arguments.of(
                        new SymbolLevelCorrection(";a;"),
                        new SymbolLevelCorrection("y;;a5a;;["),
                        3
                ),
                Arguments.of(
                        new SymbolLevelCorrection("kotek"),
                        new SymbolLevelCorrection("Dkkkóooott$tee8ekkk3"),
                        4
                ),
                Arguments.of(
                        new SymbolLevelCorrection("test"),
                        new SymbolLevelCorrection("test"),
                        1
                ),
                Arguments.of(
                        new SymbolLevelCorrection("abc"),
                        new SymbolLevelCorrection("abc"),
                        Integer.MIN_VALUE
                )
        );
    }
}
