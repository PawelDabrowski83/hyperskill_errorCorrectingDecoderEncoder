package org.hyperskill;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ContainerUtilsTest {



    @DisplayName("should getBytes() work")
    @ParameterizedTest(name = "{index} => actual={0}")
    @CsvFileSource(resources = "/test.csv")
    void getBytes(String actual) {
        ContainerUtils container = new ContainerUtils(actual.getBytes());
        assertEquals(Arrays.toString("test".getBytes()), Arrays.toString(container.getBytes()));
    }

    @DisplayName("should readFile work")
    @ParameterizedTest(name = "{index} => actual={0}")
    @MethodSource("readFileArgumentsProvider")
    void readFile(String actual) {
        String filePath = "src/test/resources/test.csv";
        File file = new File(filePath);
        try {
            assertArrayEquals(actual.getBytes(), ContainerUtils.readFile(file).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File not found");
        }
    }
    private static Stream<Arguments> readFileArgumentsProvider() {
        return Stream.of(
                Arguments.of("test")
        );
    }

    @DisplayName("should randomizeEveryByte() invert random bit in given byte array")
    @ParameterizedTest(name = "{index} => expected={0}, seed={1}, given={2}")
    @MethodSource("randomizeEveryByteArgumentsProvider")
    void randomizeEveryByte(ContainerUtils expected, int seed, ContainerUtils given) {
        Random random = new Random(seed);
        assertArrayEquals(expected.getBytes(), given.randomizeEveryByte(random).getBytes());
    }
    private static Stream<Arguments> randomizeEveryByteArgumentsProvider() {
        return Stream.of(
                Arguments.of(
                        new ContainerUtils(new byte[]{
                                0b01110000, 0b00100101, 0b01110111, 0b01111100
                        }),
                        0,
                        new ContainerUtils(new byte[]{
                                't', 'e', 's', 't'
                        })
                ),
                Arguments.of(
                        new ContainerUtils(new byte[]{
                                0b01110000, 0b01110101, 0b00110011, 0b01010100
                        }),
                        1,
                        new ContainerUtils(new byte[]{
                                't', 'e', 's', 't'
                        })
                )
        );
    }

    @DisplayName("should writeFile() saves object to file with given name")
    @ParameterizedTest(name = "{index} => expected={0}, filepath={1}, source={2}")
    @MethodSource("writeFileArgumentsProvider")
    void writeFile(byte[] expected, String filepath, ContainerUtils source) {
        File file = new File(filepath);
        ContainerUtils actual = new ContainerUtils(new byte[0]);
        try {
            source.writeFile(file);
            actual = ContainerUtils.readFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertArrayEquals(expected, actual.getBytes());
    }
    private static Stream<Arguments> writeFileArgumentsProvider() {
        return Stream.of(
                Arguments.of(
                        new byte[]{'t', 'e', 's', 't'},
                        "src/test/resources/written.txt",
                        new ContainerUtils(new byte[]{'t', 'e', 's', 't'})
                )
        );
    }

    @DisplayName("should toString produce clear byte strings without any other characters separated by spaces")
    @ParameterizedTest(name = "{index} => expected ={0}, given={1}")
    @MethodSource("toStringArgumentsProvider")
    void toString(String expected, byte[] bytes) {
        ContainerUtils containerUtils = new ContainerUtils(bytes);
        assertEquals(expected, containerUtils.toString());
    }
    private static Stream<Arguments> toStringArgumentsProvider() {
        return Stream.of(
                Arguments.of(
                        "0000000000000000",
                        new byte[]{0, 0}
                ),
                Arguments.of(
                        "",
                        new byte[0]
                ),
                Arguments.of(
                        "00000001",
                        new byte[]{1}
                ),
                Arguments.of(
                        "00000111",
                        new byte[]{7}
                ),
                Arguments.of(
                        "11110110",
                        new byte[]{(byte) -10}
                ),
                Arguments.of(
                        "11111111",
                        new byte[]{(byte) 255}
                ),
                Arguments.of(
                        "000001110001000111111101",
                        new byte[]{(byte) 7, (byte) 17, (byte) -3}
                ),
                Arguments.of(
                        "00100100011011110110110101100101001000000111001001100001011011100110010000110000011011010010000001101101011001010111001101110011010000010110011101100101",
                        new byte[]{36, 111, 109, 101, 32, 114, 97, 110, 100, 48, 109, 32, 109, 101, 115, 115, 65, 103, 101}
                )
        );
    }

    @DisplayName("should encode() duplicate each bit in byte-string and adds parity byte each 3 bits")
    @ParameterizedTest(name = "{index} => expected={0}, given={1}")
    @MethodSource("encodeArgumentsProvider")
    void encode(String expected, String given) {
        ContainerUtils containerUtils = new ContainerUtils(new byte[0]);
        assertEquals(expected, containerUtils.encode(given));
    }
    private static Stream<Arguments> encodeArgumentsProvider() {
        return Stream.of(
                Arguments.of(
                        "111100001100110000000000",
                        "11010100"
                ),
                Arguments.of(
                        "",
                        ""
                ),
                Arguments.of(
                        "000000000000111111000011",
                        "00000110"
                ),
                Arguments.of(
                        "111100000000000011110000001111000000111111000011",
                        "1100001100110011"
                ),
                Arguments.of(
                        "1111000000001111111111110000000011000011000000001100110011000011",
                        "110001111000100000101100"
                ),
                Arguments.of(
                        "000011110011110000000000",
                        "00101100"
                ),
                Arguments.of(
                        "1111000000111100",
                        "110011"
                ),
                Arguments.of(
                        "000011110000111100000000111100001111111111001100110011001100110000111100000011110011001100110011000000000000111111110000001100110011110000000000001100111111000011111111000011111100001111000011000011111100001100000000111100001111000011000011110000110000000000111100001111000011001111110000001100111100110011110000001111000011110011000011111100001100001100000000110011001100001111111111001111000000111100110011",
                        "00100100011011110110110101100101001000000111001001100001011011100110010000110000011011010010000001101101011001010111001101110011010000010110011101100101"
                ),
                Arguments.of(
                        "110011001100001111111111001111000000111100110011",
                        "10110011101100101"
                )
        );
    }

    @DisplayName("should addParity() adds parity bit as string")
    @ParameterizedTest(name = "{index} => expected={0}, given={1}")
    @MethodSource("addParityArgumentsProvider")
    void addParity(String expected, String given) {
        ContainerUtils containerUtils = new ContainerUtils(new byte[0]);
        assertEquals(expected, containerUtils.addParity(given));
    }
    private static Stream<Arguments> addParityArgumentsProvider() {
        return Stream.of(
                Arguments.of("0000", "000"),
                Arguments.of("0011", "001"),
                Arguments.of("0101", "010"),
                Arguments.of("0110", "011"),
                Arguments.of("1001", "100"),
                Arguments.of("1010", "101"),
                Arguments.of("1100", "110"),
                Arguments.of("1111", "111"),
                Arguments.of("", "")
        );
    }

    @DisplayName("should doubleChars() return string with doubled chars")
    @ParameterizedTest(name = "{index} => expected={0}, given={1}")
    @MethodSource("doubleCharsArgumentsProvider")
    void doubleChars(String expected, String given) {
        ContainerUtils containerUtils = new ContainerUtils(new byte[0]);
        assertEquals(expected, containerUtils.doubleChars(given));
    }
    private static Stream<Arguments> doubleCharsArgumentsProvider() {
        return Stream.of(
                Arguments.of(
                        "00110011",
                        "0101"
                ),
                Arguments.of(
                        "00000000",
                        "0000"
                ),
                Arguments.of(
                        "00110011",
                        "0101"
                ),
                Arguments.of(
                        "",
                        ""
                )
        );
    }

    @DisplayName("should invertBit() switch one bit determined randomly")
    @ParameterizedTest(name = "{index} => expected={0}, given={1}, seed={2}")
    @MethodSource("invertBitArgumentsProvider")
    void invertBit(String expected, String given, int seed) {
        Random random = new Random(seed);
        ContainerUtils containerUtils = new ContainerUtils(new byte[0]);
        assertEquals(expected, containerUtils.invertBit(given, random));
    }
    private static Stream<Arguments> invertBitArgumentsProvider() {
        return Stream.of(
                Arguments.of(
                        "00000100",
                        "00000000",
                        0
                ),
                Arguments.of(
                        "10101010",
                        "10101110",
                        0
                ),
                Arguments.of(
                        "",
                        "",
                        0
                )
        );
    }

    @DisplayName("should send simulate transmission errors with every byte by inverting random bit")
    @ParameterizedTest(name = "{index} => expected={0}, given={1}, seed={2}")
    @MethodSource("sendArgumentsProvider")
    void send(String expected, String given, int seed) {
        Random random = new Random(seed);
        ContainerUtils containerUtils = new ContainerUtils(new byte[0]);
        assertEquals(expected, containerUtils.send(given, random));
    }
    private static Stream<Arguments> sendArgumentsProvider() {
        return Stream.of(
                Arguments.of(
                        "00110111",
                        "00110011",
                        0
                ),
                Arguments.of(
                        "11110100111010100000101111000100",
                        "11110000101010100000111111001100",
                        0
                ),
                Arguments.of(
                        "",
                        "",
                        0
                ),
                Arguments.of(
                        "",
                        "1010",
                        0
                )
        );
    }

    @DisplayName("should decode() revert encoded message that may contain some transmission errors")
    @ParameterizedTest(name = "{index} => expected={0}, source={1}")
    @MethodSource("decodeArgumentsProvider")
    void decode(String expected, String source) {
        ContainerUtils containerUtils = new ContainerUtils(new byte[0]);
        assertEquals(expected, containerUtils.decode(source));
    }
    private static Stream<Arguments> decodeArgumentsProvider() {
        return Stream.of(
                Arguments.of(
                        "101",
                        "11001101"
                ),
                Arguments.of(
                        "",
                        ""
                ),
                Arguments.of(
                        "1100100011011111",
                        "111100000111001100001111110010001111111111000011"
                ),
                Arguments.of(
                        "1101010011010000",
                        "111100001100111000001111110011000000000000000000"
                ),
                Arguments.of(
                        "10101111",
                        "110011000011110011110000"
                ),
                Arguments.of(
                        "00110110",
                        "000011111100110011000011"
                ),
                Arguments.of(
                        "1101010011010000",
                        "110100001100111000001011110011000000000010000000"
                ),
                Arguments.of(
                        "10101111",
                        "111011000011110011111000"
                ),
                Arguments.of(
                        "00110110",
                        "000011111000110011000111"
                )
        );
    }

    @DisplayName("should decodeByte() decrypt encoded message")
    @ParameterizedTest(name = "{index} => expected={0}, given={1}")
    @MethodSource("decodeByteArgumentsProvider")
    void decodeByte(String expected, String given) {
        ContainerUtils containerUtils = new ContainerUtils(new byte[0]);
        assertEquals(expected, containerUtils.decodeByte(given));
    }
    private static Stream<Arguments> decodeByteArgumentsProvider() {
        return Stream.of(
                Arguments.of(
                        "",
                        ""
                ),
                Arguments.of(
                        "101",
                        "11001000"
                ),
                Arguments.of(
                        "010",
                        "00111011"
                ),
                Arguments.of(
                        "000",
                        "00010000"
                ),
                Arguments.of(
                        "110",
                        "01110000"
                ),
                Arguments.of(
                        "010",
                        "00110010"
                ),
                Arguments.of(
                        "011",
                        "00111100"
                )
        );
    }

    @DisplayName("should decodeByteWithParity() return 3 bits of decoded message or empty if decoding failed")
    @ParameterizedTest(name = "{index} => expected={0}, given={1}")
    @MethodSource("decodeByteWithParityArgumentsProvider")
    void decodeByteWithParity(String expected, String given) {
        ContainerUtils containerUtils = new ContainerUtils(new byte[0]);
        assertEquals(expected, containerUtils.decodeByteWithParity(given));
    }
    private static Stream<Arguments> decodeByteWithParityArgumentsProvider() {
        return Stream.of(
                Arguments.of(
                        "",
                        ""
                ),
                Arguments.of(
                        "000",
                        "?000"
                ),
                Arguments.of(
                        "100",
                        "1?01"
                ),
                Arguments.of(
                        "111",
                        "?111"
                ),
                Arguments.of(
                        "101",
                        "101?"
                ),
                Arguments.of(
                        "011",
                        "0?10"
                ),
                Arguments.of(
                        "110",
                        "11?0"
                )
        );
    }
}
