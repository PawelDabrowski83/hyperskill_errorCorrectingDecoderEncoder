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
    void boxByteArray(ByteWithParity[] expected, byte[] given) {
        assertArrayEquals(expected, ByteUtils.boxByteArray(given));
    }
    private static Stream<Arguments> boxByteArrayArgumentsProvider() {
        return Stream.of(
                Arguments.of(
                        new ByteWithParity[0],
                        new byte[0]
                ),
                Arguments.of(
                        new ByteWithParity[]{ByteWithParity.ZEROS},
                        new byte[]{(byte) 0b0000_0000}
                ),
                Arguments.of(
                        new ByteWithParity[]{ByteWithParity.ONES},
                        new byte[]{(byte) 0b1111_1111}
                ),
                Arguments.of(
                )
        );
    }
}
