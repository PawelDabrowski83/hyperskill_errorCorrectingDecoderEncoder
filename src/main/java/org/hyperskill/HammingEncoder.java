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

    public String encode() {
        if (text.isEmpty() || text.length() % 8 != 0) {
            return "";
        }
        return "";
    }

    protected String encodeSubstring(String substring) {
        StringBuilder builder = new StringBuilder(substring);

        return "";
    }

    protected String calculateParity(String substring, HammingCode hammingCode) {
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

        return parity ? "1" : "0";
    }

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

