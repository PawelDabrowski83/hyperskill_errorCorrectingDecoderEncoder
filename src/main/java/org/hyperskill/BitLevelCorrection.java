package org.hyperskill;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BitLevelCorrection {

    protected static final Pattern BIT_PATTERN = Pattern.compile("([01]{2})");
    protected static final Pattern BYTE_PATTERN = Pattern.compile("([01]{8})");

    public static boolean isParityCorrect(int binary) {
        String byteString = convertTo8bit(binary);
        return checkParity(byteString);
    }

    private static String convertTo8bit(int binary) {
        if (binary < 1) {
            binary = 0;
        }
        if (binary < 256) {
            binary = binary % 256;
        }
        StringBuilder bits = new StringBuilder(Integer.toBinaryString(binary));
        while (bits.length() < 8) {
            bits.insert(0, "0");
        }
        return bits.toString();
    }

    private static boolean checkParity(String given) {
        if (given == null || given.length() != 8) {
            return false;
        }
        String[] bits = given.split("(?<=\\G..)");
        byte expectedParity = 0;
        for (int i = 0; i < 3; i++) {
            if (!isBitSymetric(bits[i])) {
                return false;
            };
            expectedParity ^= bits[i].charAt(0);
        }
        String parity = bits[3];
        return expectedParity == parity.charAt(0);
    }

    private static boolean isBitSymetric(String bit) {
        if (bit == null || bit.length() != 2) {
            return false;
        }
        return bit.charAt(0) == bit.charAt(1);
    }
}
