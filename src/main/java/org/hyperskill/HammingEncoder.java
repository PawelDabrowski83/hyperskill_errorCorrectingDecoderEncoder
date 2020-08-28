package org.hyperskill;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HammingEncoder {
    protected static final Pattern BYTE_PATTERN = Pattern.compile("[01]{8}");

    private final String text;

    public HammingEncoder(String text) {
        if (text == null) {
            text = "";
        }
        Matcher matcher = BYTE_PATTERN.matcher(text);
        if (!matcher.find()) {
            text = "";
        }
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HammingEncoder encoder = (HammingEncoder) o;
        return text.equals(encoder.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }

    @Override
    public String toString() {
        return "HammingEncoder{" +
                "text='" + text + '\'' +
                '}';
    }

    /**
     * encode byte-string representation to hamming code
     * @return byte-string with parity bits
     */
    public String encode() {
        if (text.isEmpty() || text.length() % 8 != 0) {
            return "";
        }
        StringBuilder source = new StringBuilder(text);
        StringBuilder target = new StringBuilder();
        while (source.length() > 0) {
            String str = source.substring(0, 4);
            source.delete(0, 4);
            target.append(encodeSubstring(str));
        }
        return target.toString();
    }

    /**
     * Fills 4 data bits string with a hamming code parity bits
     * @param substring 4-bit string
     * @return 8-bit string with 4 data bits, 3 parity bits and 0bit on last position
     */
    protected String encodeSubstring(String substring) {
        StringBuilder builder = new StringBuilder(substring);
        builder = insertHammingCodePlaceholders(builder);
        builder.setCharAt(0, calculateParity(builder.toString(), HammingCode.P1));
        builder.setCharAt(1, calculateParity(builder.toString(), HammingCode.P2));
        builder.setCharAt(3, calculateParity(builder.toString(), HammingCode.P4));
        return builder.toString();
    }

    /**
     * Calculate parity bit for position given by HammingCode enum
     * @param substring byte-string with placeholders for hamming code
     * @param hammingCode P1, P2, P4 - parity bit position
     * @return parity bit as a char
     */
    protected char calculateParity(String substring, HammingCode hammingCode) {
        int hammingCodeStep = hammingCode.step;
        boolean parity = false;
        int currentPosition = hammingCodeStep - 1;
        int counter = hammingCodeStep;
        while (currentPosition < substring.length()) {
            if (counter > 0) {
                if (substring.charAt(currentPosition) == '1') {
                    parity = !parity;
                }
                counter--;
            } else {
                currentPosition += hammingCodeStep - 1;
                counter = hammingCodeStep;
            }
            currentPosition++;
        }

        return parity ? '1' : '0';
    }

    /**
     * position of hamming code parity bit
     */
    public enum HammingCode{
        P1(1), P2(2), P4(4);

        public final int step;

        HammingCode(int step) {
            this.step = step;
        }
    }

    /**
     * transform 4 data bytes into pattern with parity placeholders 00X0XXX0
     * @param builder 4 data bits represented by string
     * @return string-byte with parity placeholders
     */
    protected StringBuilder insertHammingCodePlaceholders(StringBuilder builder) {
        builder.insert(0, "00");
        builder.insert(3, "0");
        builder.insert(7, "0");
        return builder;
    }
}

