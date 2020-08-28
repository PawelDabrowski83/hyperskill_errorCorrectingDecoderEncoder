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
        return "";
    }
}

