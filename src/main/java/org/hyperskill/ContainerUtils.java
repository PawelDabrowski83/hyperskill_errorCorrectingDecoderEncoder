package org.hyperskill;

import java.io.*;
import java.util.Arrays;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContainerUtils {
    public static final Pattern BYTE_PATTERN = Pattern.compile("([01]{8})");

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

    public String encode(String message) {
        if (message == null || message.length() == 0) {
            return "";
        }
        StringBuilder source = new StringBuilder(message);
        StringBuilder target = new StringBuilder();
        while(source.length() > 3) {
            String section = source.substring(0,3);
            source.delete(0, 3);
            section = addParity(section);
            section = doubleChars(section);
            target.append(section);
        }
        if (source.length() > 0) {
            while (source.length() < 3) {
                source.insert(source.length() - 1, "0");
            }
            String section = source.toString();
            section = addParity(section);
            section = doubleChars(section);
            target.append(section);
        }
        return target.toString();
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

    /**
     * send simulates transmition errors, changes random bit in every byte. bytes are represented by strings
     * @param message string representing byte message
     * @param random for testing with determined seed
     * @return string as byte message with transmition errors
     */
    public String send(String message, Random random) {
        if (random == null) {
            random = new Random();
        }
        if (message == null || message.isEmpty() || message.length() % 8 != 0) {
            return "";
        }
        Matcher matcher = BYTE_PATTERN.matcher(message);
        StringBuilder target = new StringBuilder();
        while (matcher.find()) {
            target.append(invertBit(matcher.group(), random));
        }
        return target.toString();
    }

    public String invertBit(String message, Random random) {
        if (message.isEmpty()) {
            return "";
        }
        StringBuilder builder = new StringBuilder(message);
        int randomPosition = Utils.getRandomPosition(8, random);
        char inverted = builder.charAt(randomPosition) == '1' ? '0' : '1';
        builder.setCharAt(randomPosition, inverted);
        return builder.toString();
    }

    public String decode(String message) {
        if (message == null || message.isEmpty()) {
            return "";
        }
        StringBuilder source = new StringBuilder(message);
        StringBuilder target = new StringBuilder();
        while (source.length() >= 8) {
            String substring = source.substring(0,8);
            source.delete(0, 8);
            target.append(decodeByte(substring));
        }
        if (source.length() > 0) {
            target.append(decodeByte(source.toString()));
        }
        return target.toString();
    }

    /**
     * Reduce parity bits from encoded message, allows one transmission error for each byte
     * @param given string representation of encoded byte
     * @return 3 bits of information
     */
    protected String decodeByte(String given) {
        if (given == null || given.length() % 8 != 0 || given.isEmpty()) {
            return "";
        }
        StringBuilder source = new StringBuilder(given);
        StringBuilder target = new StringBuilder();
        while (source.length() > 0) {
            String pair = source.substring(0, 2);
            source.delete(0, 2);
            if (pair.charAt(0) == pair.charAt(1)) {
                target.append(pair.charAt(0));
            } else {
                target.append("?");
            }
        }
        if (target.substring(0,4).contains("?")) {
            target = new StringBuilder(decodeByteWithParity(target.toString()));
        }
        while (target.length() > 3) {
            target.deleteCharAt(target.length() - 1);
        }

        return target.toString();
    }

    /**
     * returns 3 bits of decoded message
     * @param given string containg bits (after reducing parity, unknown bits represented by ?)
     * @return 3 bits of decoded message or empty
     */
    protected String decodeByteWithParity(String given) {
        if (given == null || given.length() != 4) {
            return "";
        }
        if (given.charAt(3) == '?') {
            return given.substring(0, 3);
        }
        boolean parityActual = false;
        String result = given.substring(0, 3);
        for (char c : result.toCharArray()) {
            if (c == '1') {
                parityActual = !parityActual;
            }
        }
        boolean parityExpected = given.charAt(3) == '1';
        return parityExpected == parityActual ?
                result.replaceAll("\\?", "0") :
                result.replaceAll("\\?", "1");
    }


}
