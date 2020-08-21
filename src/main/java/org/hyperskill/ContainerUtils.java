package org.hyperskill;

import java.io.*;
import java.util.Arrays;
import java.util.Random;

public class ContainerUtils {

    private final byte[] bytes;

    public ContainerUtils(byte[] bytes) {
        if (bytes == null) {
            bytes = new byte[0];
        }
        this.bytes = bytes;
    }

    public byte[] getBytes() {
        return bytes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContainerUtils that = (ContainerUtils) o;
        return Arrays.equals(bytes, that.bytes);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(bytes);
    }

    @Override
    public String toString() {
        if (bytes.length == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            StringBuilder subBuilder = new StringBuilder(Integer.toBinaryString(b));
            if (subBuilder.length() > 8) {
                subBuilder = new StringBuilder(subBuilder.subSequence(subBuilder.length() - 8, subBuilder.length()));
            }
            while (subBuilder.length() < 8) {
                subBuilder.insert(0, "0");
            }
            builder.append(subBuilder.toString());
        }
        return builder.toString();
    }

    public static ContainerUtils readFile(File file) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Utils.copy(new FileInputStream(file), baos, 100000);
        byte[] bytes = baos.toByteArray();
        return new ContainerUtils(bytes);
    }

    public void writeFile(File file) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        Utils.copy(bais, new FileOutputStream(file), 100000);
    }

    public ContainerUtils randomizeEveryByte(Random random) {
        byte[] buffer = new byte[getBytes().length];
        int counter = 0;
        for (byte b : getBytes()) {
            int randomPosition = Utils.getRandomPosition(8, random);
            BitLevelErrorEmulator emulator = new BitLevelErrorEmulator(b);
            buffer[counter] = (byte) emulator.invertBit(randomPosition).getContent();
            counter++;
        }
        return new ContainerUtils(buffer);
    }

    public ContainerUtils encode(String message) {
        if (message == null || message.length() == 0) {
            return new ContainerUtils(new byte[0]);
        }

        return new ContainerUtils(new byte[0]);
    }

    /**
     * appends parity bit at the end of given string
     * @param bits string with bits
     * @return string with bits, parity attached at the end
     */
    protected String addParity(String bits) {
        if (bits == null || bits.isEmpty()) {
            return "";
        }
        boolean isOne = false;
        for (char c : bits.toCharArray()) {
            if (c == '1') {
                isOne = !isOne;
            }
        }
        char parity = isOne ? '1' : '0';
        char[] result = Arrays.copyOf(bits.toCharArray(), bits.toCharArray().length + 1);
        result[bits.toCharArray().length] = parity;

        return String.valueOf(result);
    }

    protected String doubleChars(String given) {
        if (given == null || given.isEmpty()) {
            return "";
        }
        char[] source = given.toCharArray();
        char[] temp = new char[source.length * 2];
        int counterFrom = 0;
        int counterTo = 0;
        for (char c : source) {
            temp[counterTo] = source[counterFrom];
            counterTo++;
            temp[counterTo] = source[counterFrom];
            counterFrom++;
            counterTo++;
        }
        return String.valueOf(temp);
    }


}
