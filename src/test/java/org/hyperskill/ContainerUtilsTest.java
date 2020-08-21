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
                )
        );
    }
}
