package org.hyperskill.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.Assert.assertArrayEquals;

public class ByteUtilsTest {

    @DisplayName("should boxByteArray() convert byte[] to Byte[]")
    @ParameterizedTest(name = "{index} => expected={0}, given={1}")
    @MethodSource("boxByteArrayArgumentsProvider")
    void boxByteArray(OrderedByte[] expected, byte[] given) {
        assertArrayEquals(expected, ByteUtils.boxByteArray(given));
    }
    private static Stream<Arguments> boxByteArrayArgumentsProvider() {
        return Stream.of(
                Arguments.of(
                        new Byte[0],
                        new byte[0]
                ),
                Arguments.of(
                        new Byte[]{Byte.ZEROS},
                        new byte[]{(byte) 0b0000_0000}
                ),
                Arguments.of(
                        new Byte[]{new Byte(0b0111_1111)},
                        new byte[]{(byte) 0b111_1111}
                ),
                Arguments.of(
                        new Byte[]{new Byte(0b0111_1111), new Byte(0b0111_1111), Byte.ZEROS},
                        new byte[]{(byte) 0b111_1111, (byte) 0b111_1111, (byte) 0b000_0000}
                ),
                Arguments.of(
                        new Byte[]{new Byte(0b0010_0011), new Byte(0b0100_0101)},
                        new byte[]{(byte) 0b010_0011, (byte) 0b100_0101}
                )
        );
    }
}
